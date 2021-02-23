package com.maritech.arterium.ui.choose_mp;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maritech.arterium.R;
import com.maritech.arterium.databinding.FragmentChooseDoctorBinding;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.choose_mp.data.ChooseMpContent;
import com.maritech.arterium.ui.choose_mp.holder.ChooseMpAdapter;

import java.util.ArrayList;

public class ChooseMpFragment extends BaseFragment<FragmentChooseDoctorBinding> {

    static final String BUNDLE_KEY = "selectedItem";
    static final String REQUEST_KEY = "requestKey";

    private ChooseMpViewModel viewModel;
    private ChooseMpNavigator navigator = new ChooseMpNavigator();

    private ArrayList<ChooseMpContent> listContent = new ArrayList<>();
    private ChooseMpContent selectedObject;

    @Override
    protected int getContentView() {
        return R.layout.fragment_choose_doctor;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ChooseMpViewModel.class);

        ChooseMpAdapter adapter;
        RecyclerView rcv = root.findViewById(R.id.rvChoose);

        binding.toolbar.tvToolbarTitle.setText("Оберiть мед. представника");

        binding.toolbar.ivRight.setOnClickListener(
                v -> requireActivity().onBackPressed()
        );

        prepareList(listContent);

        adapter = new ChooseMpAdapter(listContent, (position, object) -> selectedObject = object);
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv.setAdapter(adapter);

        binding.tvBtnAdd.setOnClickListener(v -> {
            Bundle result = new Bundle();
            result.putParcelable(BUNDLE_KEY, selectedObject);
            getParentFragmentManager().setFragmentResult(REQUEST_KEY, result);
            requireActivity().onBackPressed();
        });
    }

    private void prepareList(ArrayList<ChooseMpContent> dataList) {
        dataList.add(new ChooseMpContent("Евгений Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseMpContent("Евгений Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
    }


}
