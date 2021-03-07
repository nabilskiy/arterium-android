package com.maritech.arterium.ui.dashboard.regionalManager;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.maritech.arterium.R;
import com.maritech.arterium.databinding.FragmentDashboardRmBinding;
import com.maritech.arterium.databinding.ItemDashboardBinding;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.dashboard.medicalRep.data.DoctorsContent;
import com.maritech.arterium.ui.dashboard.regionalManager.dialog.DialogNewAccount;

import java.util.ArrayList;

public class DashboardRmFragment extends BaseFragment<FragmentDashboardRmBinding> {

    DashboardRmNavigator navigator = new DashboardRmNavigator();

    private ArrayList<DoctorsContent> listDoctors = new ArrayList<>();

    private DashboardRmViewModel dashboardViewModel;

    @Override
    protected int getContentView() {
        return R.layout.fragment_dashboard_rm;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        dashboardViewModel = new ViewModelProvider(this).get(DashboardRmViewModel.class);

        dashboardViewModel.getText().observe(getViewLifecycleOwner(), s -> {
        });

        DialogListener errorModule = new DialogListener() {
            @Override
            public void addDoctor() {
                navigator.goToAddNewDoctor(navController);
            }

            @Override
            public void addMP() {
                navigator.goToAddNewMp(navController);
            }
        };

        DialogNewAccount customDialog = new DialogNewAccount(this.getContext(), errorModule);

        binding.details.ivSearch.setOnClickListener(v -> {
            binding.details.ivSearch.setVisibility(View.GONE);
            binding.details.tvDoctors.setVisibility(View.GONE);

            binding.details.ivClose.setVisibility(View.VISIBLE);
            binding.details.clSearch.setVisibility(View.VISIBLE);
        });

        binding.details.ivClose.setOnClickListener(v -> {
            binding.details.ivSearch.setVisibility(View.VISIBLE);
            binding.details.tvDoctors.setVisibility(View.VISIBLE);

            binding.details.ivClose.setVisibility(View.GONE);
            binding.details.clSearch.setVisibility(View.GONE);
        });

        binding.clBtnAddNewAccount.setOnClickListener(v -> customDialog.show());


//        BaseAdapter adapter =
//                new BaseAdapter(ItemDashboardBinding.class, DoctorsContent.class);
//        binding.details.rvDoctors.setAdapter(adapter);
//
//        prepareList(listDoctors);
//
//        adapter.setDataList(listDoctors);
    }

    private void prepareList(ArrayList<DoctorsContent> dataList) {
        dataList.add(new DoctorsContent(1, "Евгений Петров", "40", "A", "vas"));
        dataList.add(new DoctorsContent(2, "Евгений Петров", "40", "A", "vas"));
        dataList.add(new DoctorsContent(3, "Евгений Петров", "40", "C", "vas"));
        dataList.add(new DoctorsContent(4, "Андрей Сидоров", "40", "B", "vas"));
        dataList.add(new DoctorsContent(1, "Андрей Сидоров", "40", "D", "vas"));
        dataList.add(new DoctorsContent(1, "Андрей Сидоров", "40", "A", "vas"));
        dataList.add(new DoctorsContent(1, "Андрей Сидоров", "40", "C", "vas"));
        dataList.add(new DoctorsContent(1, "Андрей Сидоров", "40", "A", "vas"));
        dataList.add(new DoctorsContent(1, "Андрей Сидоров", "40", "A", "vas"));
        dataList.add(new DoctorsContent(1, "Евгений Петров", "40", "A", "vas"));
        dataList.add(new DoctorsContent(2, "Евгений Петров", "40", "A", "vas"));
        dataList.add(new DoctorsContent(3, "Евгений Петров", "40", "C", "vas"));
        dataList.add(new DoctorsContent(4, "Андрей Сидоров", "40", "B", "vas"));
        dataList.add(new DoctorsContent(1, "Андрей Сидоров", "40", "D", "vas"));
        dataList.add(new DoctorsContent(1, "Андрей Сидоров", "40", "A", "vas"));
        dataList.add(new DoctorsContent(1, "Андрей Сидоров", "40", "C", "vas"));
        dataList.add(new DoctorsContent(1, "Андрей Сидоров", "40", "A", "vas"));
        dataList.add(new DoctorsContent(1, "Андрей Сидоров", "40", "A", "vas"));
    }

}