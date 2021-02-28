package com.maritech.arterium.ui.drugPrograms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.maritech.arterium.R;
import com.maritech.arterium.data.models.DrugProgramModel;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.databinding.DialogWithRecyclerBinding;
import com.maritech.arterium.ui.drugPrograms.adapter.DrugProgramsAdapter;

import java.util.ArrayList;

public class DrugProgramsFragment extends BottomSheetDialogFragment {

    private OnChooseItem onChooseItem;

    private final ArrayList<DrugProgramModel> listContent = new ArrayList<>();
    private DrugProgramsAdapter adapter;

    private DrugProgramsViewModel drugProgramsViewModel;

    private DialogWithRecyclerBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.ChooseProgramDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.dialog_with_recycler, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        drugProgramsViewModel =
                new ViewModelProvider(requireActivity()).get(DrugProgramsViewModel.class);

        initView();
    }

    private void initView() {
        adapter = new DrugProgramsAdapter(listContent, (position, object, tittle) -> {
            if (!object.isSelected()) {
                for (int i = 0; i < listContent.size(); i++) {
                    listContent.get(i).setSelected(false);
                }

                object.setSelected(true);

                if (onChooseItem != null) {
                    Pref.getInstance().setDrugProgramId(requireContext(), object.getId());
                    onChooseItem.onChoose(object.getId());
                }
            }
        });
        binding.rvStyle.setAdapter(adapter);

        binding.tvBack.setOnClickListener(v -> dismiss());

        if (getDialog() != null) {
            getDialog().setCanceledOnTouchOutside(true);
        }

        observerViewModel();

        getPrograms();
    }

    public void setListener(OnChooseItem onChooseItem) {
        this.onChooseItem = onChooseItem;
    }

    private void observerViewModel() {
        drugProgramsViewModel.responseLiveData
                .observe(getViewLifecycleOwner(),
                        list -> {
                            int selectedId = Pref.getInstance().getDrugProgramId(requireContext());
                            listContent.clear();
                            listContent.addAll(list);

                            for (int i = 0; i < listContent.size(); i++) {
                                listContent.get(i).setSelected(false);

                                if (selectedId == listContent.get(i).getId()) {
                                    listContent.get(i).setSelected(true);
                                }
                            }

                            adapter.notifyDataSetChanged();
                        });

        drugProgramsViewModel.errorMessage
                .observe(getViewLifecycleOwner(), error -> {
//                    ToastUtil.show(requireContext(), error);
                });

        drugProgramsViewModel.contentState
                .observe(getViewLifecycleOwner(), contentState -> {
                    if (contentState.isLoading()) {
                        binding.progressBar.setVisibility(View.VISIBLE);
                    } else {
                        binding.progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void getPrograms() {
        drugProgramsViewModel.getDrugPrograms();
    }

    public interface OnChooseItem {
        void onChoose(int content);
    }

}
