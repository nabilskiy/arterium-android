package com.maritech.arterium.ui.dashboardDoctor;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maritech.arterium.MainActivity;
import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseActivity;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.dashboardDoctor.DashboardNavigator;
import com.maritech.arterium.ui.dashboardDoctor.data.PatientPurchasesContent;
import com.maritech.arterium.ui.dashboardDoctor.holder.PatientPurchasesAdapter;
import com.maritech.arterium.ui.dashboardMp.DashboardMpViewModel;
import com.maritech.arterium.ui.dialogs.dialog_with_recycler.DialogWithRecycler;
import com.maritech.arterium.ui.dialogs.dialog_with_recycler.data.DialogContent;

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
    private ConstraintLayout clInfoClose;


    private TextView tvTabOne;
    private TextView tvTabTwo;
    private TextView tvTabThree;

    private View details;

    View navigation_statistics;
    View achievementsFragment;
    View myProfileFragment;
    View navigation_dashboard;
    NestedScrollView details_view;
    DialogWithRecycler customDialog;

    DashboardNavigator navigator = new DashboardNavigator();



    private ArrayList<PatientPurchasesContent> listPatientPurchases = new ArrayList<>();

    private DashboardMpViewModel dashboardViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardMpViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), s -> {
        });
       // ((MainActivity) getActivity()).setTheme(R.style.Theme_Arterium_Blue);


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
        clInfoClose = root.findViewById(R.id.clInfoClose);
        details = root.findViewById(R.id.details);
        tvTabOne = details.findViewById(R.id.tvOne);
        tvTabTwo = details.findViewById(R.id.tvTwo);
        tvTabThree = details.findViewById(R.id.tvThree);
        details_view = (NestedScrollView) root.findViewById(R.id.details_view);

        PatientPurchasesAdapter adapter;
        RecyclerView rcv = details_view.findViewById(R.id.rvPatients);

        details_view.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.e("gggggggggggg","ggggggg");

//                if (scrollY > 100) {
//                    ivSearch.setVisibility(View.VISIBLE);
//                    tvDoctors.setVisibility(View.VISIBLE);
//                    ivFilter.setVisibility(View.VISIBLE);
//                    ivClose.setVisibility(View.GONE);
//                    clSearch.setVisibility(View.GONE);
//                }
//                if (scrollY < 100) {
//                    ivSearch.setVisibility(View.GONE);
//                    tvDoctors.setVisibility(View.GONE);
//                    ivFilter.setVisibility(View.GONE);
//                    ivClose.setVisibility(View.VISIBLE);
//                    clSearch.setVisibility(View.VISIBLE);
//                }
//                if (scrollY > oldScrollY) {
//                    Log.i(TAG, "Scroll DOWN");
//                }
//                if (scrollY < oldScrollY) {
//                    Log.i(TAG, "Scroll UP");
//                }
//
//                if (scrollY == 0) {
//                    Log.i(TAG, "TOP SCROLL");
//                }
//
//                if (scrollY == (v.getMeasuredHeight() - v.getChildAt(0).getMeasuredHeight())) {
//                    Log.i(TAG, "BOTTOM SCROLL");
//
//                }
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                                        ivSearch.setVisibility(View.VISIBLE);
                    tvDoctors.setVisibility(View.VISIBLE);
                    ivFilter.setVisibility(View.VISIBLE);
                    ivClose.setVisibility(View.GONE);
                    clSearch.setVisibility(View.GONE);
                }
            }
        });


        tvTabOne.setActivated(true);

        navigation_statistics = getActivity().findViewById(R.id.navigation_statistics);
        achievementsFragment = getActivity().findViewById(R.id.achievementsFragment);
        myProfileFragment = getActivity().findViewById(R.id.myProfileFragment);
        navigation_dashboard = getActivity().findViewById(R.id.navigation_dashboard);

//        customDialog.setListener((DialogWithRecycler.OnChooseItem) content ->{
//            Log.e("!!!", String.valueOf(content));
//            if(content == 1) {
//                ((MainActivity) getActivity()).setTheme(R.style.Theme_Arterium);
//                super.onCreate(savedInstanceState);
//            }
//            if(content == 2) {
//                ((MainActivity) getActivity()).setTheme(R.style.Theme_Arterium_Blue);
//                super.onCreate(savedInstanceState);
//            }
//            if(content == 3) {
//                ((MainActivity) getActivity()).setTheme(R.style.Theme_Arterium_Red);
//                super.onCreate(savedInstanceState);
//            }
//
//        });

//        if(getArguments().getBoolean("isPreviousRmOrMp")) {
//            clInfoClose.setVisibility(View.VISIBLE);
//        }
//        else {
//            clInfoClose.setVisibility(View.GONE);
//        }


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

        clInfoClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.goToDashboardMP(navController);
            }
        });

       prepareList(listPatientPurchases);

        adapter = new PatientPurchasesAdapter(listPatientPurchases, new PatientPurchasesAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, PatientPurchasesContent object) {
                navigator.goToPatientCard(navController);
            }
        });
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv.setAdapter(adapter);

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customDialog = new DialogWithRecycler(this.getContext(), "DialogChooseTheme");

        customDialog.setListener((DialogWithRecycler.OnChooseItem) content -> {
            Log.e("!!!", String.valueOf(content));
            if (content == 0) {
                ((MainActivity) getActivity()).setTheme(R.style.Theme_Arterium);
                BaseActivity.setStatusBarGradientDrawable(requireActivity(), R.drawable.gradient_primary);
                navigator.bottomGoToDashboardDoctor(navController);
            }
            if (content == 1) {
                ((MainActivity) getActivity()).setTheme(R.style.Theme_Arterium_Blue);
                BaseActivity.setStatusBarGradientDrawable(requireActivity(), R.drawable.gradient_primary);
                navigator.bottomGoToDashboardDoctor(navController);
            }
            if (content == 2) {
                ((MainActivity) getActivity()).setTheme(R.style.Theme_Arterium_Red);
                BaseActivity.setStatusBarGradientDrawable(requireActivity(), R.drawable.gradient_primary);
                navigator.bottomGoToDashboardDoctor(navController);
            }

        });
    }

    private void prepareList(ArrayList<PatientPurchasesContent> dataList) {
        dataList.add(new PatientPurchasesContent("Евгений Петров", "10 ЛЮТОГО 2020", "Остання покупка в середу 10.02.20"));
        dataList.add(new PatientPurchasesContent("Евгений Петров", "10 ЛЮТОГО 2020", "Остання покупка в понеділок 10.02.20"));
        dataList.add(new PatientPurchasesContent("Евгений Петров", "12 ЛЮТОГО 2020", "Остання покупка в середу 12.01.20"));
        dataList.add(new PatientPurchasesContent("Евгений Петров", "12 ЛЮТОГО 2020", "Остання покупка в середу 12.01.20"));
        dataList.add(new PatientPurchasesContent("Евгений Петров", "16 ЛЮТОГО 2020", "0"));
        dataList.add(new PatientPurchasesContent("Евгений Петров", "16 ЛЮТОГО 2020", "Остання покупка в середу 16.01.20"));
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
