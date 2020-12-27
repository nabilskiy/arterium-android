package com.maritech.arterium.ui.dashboard;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
    private ImageView ivFilter;
    private ConstraintLayout clSearch;
    private ConstraintLayout clProgram;
    private ConstraintLayout clInfoUser;
    private ConstraintLayout clBtnAddNewPersonal;

    View navigation_statistics;
    View achievementsFragment;
    View myProfileFragment;
    View navigation_dashboard;

    DashboardNavigator navigator = new DashboardNavigator();



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

        clBtnAddNewPersonal = root.findViewById(R.id.clBtnAddNewPersonal);
        ivSearch = root.findViewById(R.id.ivSearch);
        ivFilter = root.findViewById(R.id.ivFilter);
        tvDoctors = root.findViewById(R.id.tvDoctors);
        ivClose = root.findViewById(R.id.ivClose);
        clSearch = root.findViewById(R.id.clSearch);
        clProgram = root.findViewById(R.id.clProgram);
        clInfoUser = root.findViewById(R.id.clInfoUser);

        navigation_statistics = getActivity().findViewById(R.id.navigation_statistics);
        achievementsFragment = getActivity().findViewById(R.id.achievementsFragment);
        myProfileFragment = getActivity().findViewById(R.id.myProfileFragment);
        navigation_dashboard = getActivity().findViewById(R.id.navigation_dashboard);
        DialogWithRecycler customDialog = new DialogWithRecycler(this.getContext(), "我是透明的");


//        navController.getPreviousBackStackEntry();

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivSearch.setVisibility(View.GONE);
                tvDoctors.setVisibility(View.GONE);
                ivFilter.setVisibility(View.GONE);
                ivClose.setVisibility(View.VISIBLE);
                clSearch.setVisibility(View.VISIBLE);
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivSearch.setVisibility(View.VISIBLE);
                tvDoctors.setVisibility(View.VISIBLE);
                ivFilter.setVisibility(View.VISIBLE);
                ivClose.setVisibility(View.GONE);
                clSearch.setVisibility(View.GONE);
            }
        });

        clBtnAddNewPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            navigator.addNewPersonal(navController);
            }
        });

        clProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // showDialog();
                customDialog.show();

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

        myProfileFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.bottomGoToMyProfileDoctor(navController);
            }
        });

        navigation_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.bottomGoToDashboardDoctor(navController);
            }
        });
        achievementsFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.bottomGoToAchievements(navController);
            }
        });

        navigation_statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.bottomGoToStat(navController);
            }
        });

        return root;
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

//
//    public void showDialog() {
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        DialogWithRecycler newFragment = new DialogWithRecycler();
//        //WindowManager.LayoutParams params = newFragment.getAttributes();
//        WindowManager.LayoutParams params = newFragment.getFragmentManager().getAttributes();
//        newFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.ChooseProgramDialog);
//        newFragment.show(fragmentManager, "dialog");
//        params.gravity = Gravity.BOTTOM;
//        params.y = 50;
//        newFragment.
//        newFragment.getActivity().getWindow().setAttributes(params);
//
////        AlertDialog.Builder builder = new Builder(this);
////        builder.setTitle("Are you sure?").setPositiveButton("OK", new DialogInterface.OnClickListener(){
////            public void onClick(DialogInterface dialog, int which){
////                dialog.dismiss();
////            }
////        });
//////
////        Dialog dialog = builder.create();
////        dialog.show();
//    }

    public void setLvlTheme(int clProgramColor, int clInfoUserColor) {
        clProgram.setBackgroundResource(clProgramColor);
        clInfoUser.setBackgroundResource(clProgramColor);

    }

//    public void setSagrada() {
//
//
//    }

}
