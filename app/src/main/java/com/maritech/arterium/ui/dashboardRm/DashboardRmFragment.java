package com.maritech.arterium.ui.dashboardRm;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.maritech.arterium.R;
import com.maritech.arterium.databinding.ItemDashboardBinding;
import com.maritech.arterium.ui.base.BaseAdapter;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.dashboardMp.data.DoctorsContent;
import com.maritech.arterium.ui.dialogs.dialog_new_account.DialogNewAccount;

import java.util.ArrayList;

public class DashboardRmFragment extends BaseFragment {

    private ImageView ivSearch;
    private TextView tvDoctors;
    private ImageView ivClose;
    private ConstraintLayout clSearch;
    private ConstraintLayout clBtnAddNewAccount;
    DashboardRmNavigator navigator = new DashboardRmNavigator();

    View navigation_statistics;
    View achievementsFragment;
    View myProfileFragment;
    View navigation_dashboard;


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

        ivSearch = root.findViewById(R.id.ivSearch);
        tvDoctors = root.findViewById(R.id.tvDoctors);
        ivClose = root.findViewById(R.id.ivClose);
        clSearch = root.findViewById(R.id.clSearch);
        clBtnAddNewAccount = root.findViewById(R.id.clBtnAddNewAccount);
        navigation_statistics = getActivity().findViewById(R.id.statistics);
        achievementsFragment = getActivity().findViewById(R.id.achievements);
        myProfileFragment = getActivity().findViewById(R.id.profile);
        navigation_dashboard = getActivity().findViewById(R.id.dashboard);


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

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivSearch.setVisibility(View.GONE);
                tvDoctors.setVisibility(View.GONE);

                ivClose.setVisibility(View.VISIBLE);
                clSearch.setVisibility(View.VISIBLE);
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivSearch.setVisibility(View.VISIBLE);
                tvDoctors.setVisibility(View.VISIBLE);

                ivClose.setVisibility(View.GONE);
                clSearch.setVisibility(View.GONE);
            }
        });

        clBtnAddNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.show();
            }
        });


        BaseAdapter adapter = new BaseAdapter(ItemDashboardBinding.class, DoctorsContent.class);
        RecyclerView rcv = (RecyclerView) root.findViewById(R.id.rvDoctors);
        rcv.setAdapter(adapter);
        prepareList(listDoctors);

        adapter.setDataList(listDoctors);

        myProfileFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.goToMyProfile(navController);
            }
        });

        navigation_dashboard.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                navigator.goToDashboardRm(navController);
            }

        });

        navigation_statistics.setVisibility(View.GONE);
        achievementsFragment.setVisibility(View.GONE);
    }

    public void goToItem() {

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