package com.maritech.arterium.ui.choose_doctor;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.maritech.arterium.R;
import com.maritech.arterium.databinding.FragmentChooseMpBinding;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.choose_doctor.data.ChooseDoctorContent;
import com.maritech.arterium.ui.choose_doctor.holder.ChooseDoctorAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ChooseDoctorFragment extends BaseFragment<FragmentChooseMpBinding> {

    static final String BUNDLE_KEY = "selectedItem";
    static final String REQUEST_KEY = "requestKey";

    private ChooseDoctorViewModel viewModel;
    private ChooseDoctorNavigator navigator = new ChooseDoctorNavigator();

    private ArrayList<ChooseDoctorContent> listContent = new ArrayList<>();
    private ChooseDoctorContent selectedObject;
    private ArrayList<ChooseDoctorContent> listSelectedObject = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.fragment_choose_mp;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ChooseDoctorViewModel.class);

        ChooseDoctorAdapter adapter;

        binding.toolbar.tvToolbarTitle.setText(getString(R.string.choose_doctor));

        binding.toolbar.ivRight.setOnClickListener(v -> requireActivity().onBackPressed());

        prepareList(listContent);

        adapter = new ChooseDoctorAdapter(listContent, (position, object) -> {
            selectedObject = object;
            ArrayList<ChooseDoctorContent> list = new ArrayList<>(listSelectedObject);

            if (object.getSelected()) {
                listSelectedObject.add(object);
            }
        });

        binding.rvChoose.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvChoose.setAdapter(adapter);

        binding.tvBtnAdd.setOnClickListener(v -> {
            Bundle result = new Bundle();
            ArrayList<ChooseDoctorContent> listTmp = new ArrayList<>(listSelectedObject);

            for (int i = 0; i < listTmp.size(); i++) {
                if (!listTmp.get(i).getSelected()) {
                    listSelectedObject.remove(i);
                }
            }
            Set<ChooseDoctorContent> set = new HashSet<>(listSelectedObject);
            listSelectedObject.clear();
            listSelectedObject.addAll(set);

            result.putParcelableArrayList(BUNDLE_KEY, listSelectedObject);
            getParentFragmentManager().setFragmentResult(REQUEST_KEY, result);
            requireActivity().onBackPressed();
        });
    }

    private void prepareList(ArrayList<ChooseDoctorContent> dataList) {
        dataList.add(new ChooseDoctorContent("Евгений1 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений2 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений3 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений4 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений5 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений6 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений7 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений8 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений9 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений10 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений11 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений12 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений13 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений14 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
        dataList.add(new ChooseDoctorContent("Евгений15 Петров", R.drawable.ic_default_avatar, "+380994192021", "Київська обл."));
    }


}