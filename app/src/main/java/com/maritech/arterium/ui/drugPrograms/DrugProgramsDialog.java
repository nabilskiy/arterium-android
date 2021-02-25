package com.maritech.arterium.ui.drugPrograms;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.maritech.arterium.R;
import com.maritech.arterium.data.models.DrugProgramModel;
import com.maritech.arterium.databinding.DialogWithRecyclerBinding;
import com.maritech.arterium.ui.drugPrograms.adapter.DrugProgramsAdapter;
import com.maritech.arterium.ui.drugPrograms.data.DialogContent;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DrugProgramsDialog extends DialogFragment {

    private static final String NAME = "ThemeColors", KEY = "ThemeColors";

    private OnChooseItem onChooseItem;

    private String data;
    private ArrayList<DrugProgramModel> listContent = new ArrayList<>();
    private DrugProgramsAdapter adapter;

    private DrugProgramsViewModel drugProgramsViewModel;

    private DialogWithRecyclerBinding binding;

//    public DrugProgramsDialog(Context context, String data) {
//        super(context, R.style.ChooseProgramDialog);
//        this.data = data;
//        initView(context);
//    }

    @Nullable
    @org.jetbrains.annotations.Nullable
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
//        adapter = new DrugProgramsAdapter(listContent, (position, object, tittle) -> {
//            for(int i=0; i<listContent.size(); i++){
//                listContent.get(i).setActive(false);
//            }
//            object.setActive(true);
//
//            if(onChooseItem != null) {
//                onChooseItem.onChoose(position);
//            }

//            dismiss();
//        });
//        binding.rvStyle.setAdapter(adapter);

        binding.tvBack.setOnClickListener(v -> dismiss());

        if (getDialog() != null) {
            getDialog().setCanceledOnTouchOutside(true);
        }

//        WindowManager.LayoutParams attributes = getWindow().getAttributes();
//        attributes.gravity = Gravity.BOTTOM;
//        attributes.y = 50;

//        getWindow().setAttributes(attributes);

        getPrograms();
    }

    public void setListener(OnChooseItem onChooseItem) {
        this.onChooseItem = onChooseItem;
    }

    private void getPrograms() {
        drugProgramsViewModel.getDrugPrograms();
    }

    public interface OnChooseItem {
        void onChoose(int content);
    }

}
