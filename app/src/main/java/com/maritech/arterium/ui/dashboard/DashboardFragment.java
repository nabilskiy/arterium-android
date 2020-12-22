package com.maritech.arterium.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.maritech.arterium.R;
import com.maritech.arterium.databinding.ItemDashboardBinding;
import com.maritech.arterium.ui.base.BaseAdapter;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.dashboardMp.DashboardMpNavigator;
import com.maritech.arterium.ui.dashboardMp.DashboardMpViewModel;
import com.maritech.arterium.ui.dashboardMp.data.DoctorsContent;
import com.maritech.arterium.ui.dialogs.dialog_with_recycler.DialogWithRecycler;

import java.util.ArrayList;


public class DashboardFragment extends BaseFragment {


    private ImageView ivSearch;
    private TextView tvDoctors;
    private ImageView ivClose;
    private ConstraintLayout clSearch;
    private ConstraintLayout clProgram;
    private ConstraintLayout clInfoUser;

    DashboardNavigator navigator = new DashboardNavigator();

    View navigation_statistics;
    View achievementsFragment;


    private ArrayList<DoctorsContent> listDoctors = new ArrayList<>();

    private DashboardMpViewModel dashboardViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardMpViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), s -> {
        });


        final int clProgramColorGliptar = R.drawable.gradient_light_red;
        final int clProgramColorSagrada = R.drawable.gradient_light_blue;
        final int clInfoUserColorGliptar = R.drawable.ic_gliptar;
        final int clInfoUserColorSagrada = R.drawable.ic_sagrada;


        ivSearch = root.findViewById(R.id.ivSearch);
        tvDoctors = root.findViewById(R.id.tvDoctors);
        ivClose = root.findViewById(R.id.ivClose);
        clSearch = root.findViewById(R.id.clSearch);
        clProgram = root.findViewById(R.id.clProgram);
        clInfoUser = root.findViewById(R.id.clInfoUser);
        navigation_statistics = getActivity().findViewById(R.id.navigation_statistics);
        achievementsFragment = getActivity().findViewById(R.id.achievementsFragment);

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

        clProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        BaseAdapter adapter = new BaseAdapter(ItemDashboardBinding.class, DoctorsContent.class);
        RecyclerView rcv = (RecyclerView) root.findViewById(R.id.rvDoctors);
        rcv.setAdapter(adapter);
        prepareList(listDoctors);

        adapter.setDataList(listDoctors);
        requireActivity().findViewById(R.id.nav_view).setVisibility(View.VISIBLE);

        navigation_statistics.setVisibility(View.VISIBLE);
        achievementsFragment.setVisibility(View.VISIBLE);
        return root;
    }

    public void goToItem() {
        navigator.goToDashboard(navController);
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


    public void showDialog() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        DialogWithRecycler newFragment = new DialogWithRecycler();
        newFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AppCompat_Dialog);
        newFragment.show(fragmentManager, "dialog");
    }

    public void setLvlTheme(int clProgramColor, int clInfoUserColor) {
        clProgram.setBackgroundResource(clProgramColor);
        clInfoUser.setBackgroundResource(clProgramColor);

    }

//    public void setSagrada() {
//
//
//    }

}
