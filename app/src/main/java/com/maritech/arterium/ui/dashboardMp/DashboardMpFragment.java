package com.maritech.arterium.ui.dashboardMp;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import com.maritech.arterium.R;
import com.maritech.arterium.databinding.FragmentDashboardMpBinding;
import com.maritech.arterium.databinding.ItemDashboardBinding;
import com.maritech.arterium.ui.base.BaseAdapter;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.dashboardMp.data.DoctorsContent;

import java.util.ArrayList;

public class DashboardMpFragment extends BaseFragment<FragmentDashboardMpBinding> {

    DashboardMpNavigator navigator = new DashboardMpNavigator();

    private ArrayList<DoctorsContent> listDoctors = new ArrayList<>();

    private DashboardMpViewModel dashboardViewModel;

    @Override
    protected int getContentView() {
        return R.layout.fragment_dashboard_mp;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        dashboardViewModel = new ViewModelProvider(this).get(DashboardMpViewModel.class);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), s -> {
        });

        binding.details.clSearch.setOnClickListener(v -> {
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

        BaseAdapter adapter =
                new BaseAdapter(ItemDashboardBinding.class, DoctorsContent.class);

        binding.details.rvDoctors.setAdapter(adapter);
        prepareList(listDoctors);

        adapter.setDataList(listDoctors);
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