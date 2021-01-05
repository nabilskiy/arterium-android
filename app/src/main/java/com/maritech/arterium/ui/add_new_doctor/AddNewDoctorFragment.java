package com.maritech.arterium.ui.add_new_doctor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseFragment;

public class AddNewDoctorFragment extends BaseFragment {


    private View viewProgressOne;
    private View viewProgressTwo;
    private TextView btnNextOne;
    private Boolean isTwoStep = false;
    private ImageView btnBack;
    private ImageView btnAuto;

    private TextView tvToolbarTitle;
    private TextView tvHint;


    private ConstraintLayout clProgressStepOne;
    private ConstraintLayout clProgressStepTwo;
    private ConstraintLayout clRenial;
    private ConstraintLayout clGliptar;
    private ConstraintLayout clSagrada;

    private ConstraintLayout clChooseMp;

    private ImageView ivBtnCheckOne;
    private ImageView ivBtnCheckTwo;
    private ImageView ivBtnCheckThree;

    private Boolean btnCheckOneIsActive = false;
    private Boolean btnCheckTwoIsActive = false;
    private Boolean btnCheckThreeIsActive = false;

    AddNewDoctorNavigator navigator = new AddNewDoctorNavigator();
    private AddNewDoctorViewModel addNewPersonalViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        addNewPersonalViewModel = new ViewModelProvider(this).get(AddNewDoctorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_new_doctor, container, false);

        clProgressStepOne = root.findViewById(R.id.clProgressStepOne);
        clProgressStepTwo = root.findViewById(R.id.clProgressStepTwo);
        tvToolbarTitle = root.findViewById(R.id.toolbar).findViewById(R.id.tvToolbarTitle);
        tvHint = root.findViewById(R.id.toolbar).findViewById(R.id.tvHint);
        btnNextOne = root.findViewById(R.id.btnNextOne);
        viewProgressOne = root.findViewById(R.id.toolbar).findViewById(R.id.viewOne);
        viewProgressOne.setActivated(true);
        viewProgressTwo = root.findViewById(R.id.toolbar).findViewById(R.id.viewTwo);
        btnBack = root.findViewById(R.id.toolbar).findViewById(R.id.ivRight);
        btnAuto = root.findViewById(R.id.toolbar).findViewById(R.id.ivLeft);
        clRenial = root.findViewById(R.id.clRenial);
        clGliptar = root.findViewById(R.id.clGliptar);
        clSagrada = root.findViewById(R.id.clSagrada);
        ivBtnCheckOne = root.findViewById(R.id.ivBtnCheckOne);
        ivBtnCheckTwo = root.findViewById(R.id.ivBtnCheckTwo);
        ivBtnCheckThree = root.findViewById(R.id.ivBtnCheckThree);
        clChooseMp = root.findViewById(R.id.clChooseMp);

        tvToolbarTitle.setText("Новий доктор");
        tvHint.setText("Персональнi данi");


        btnNextOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewProgressTwo.setActivated(true);
                isTwoStep = true;

                clProgressStepOne.setVisibility(View.GONE);
                clProgressStepTwo.setVisibility(View.VISIBLE);
                tvHint.setText("Персональнi данi");
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTwoStep) {
                    isTwoStep = false;
                    viewProgressTwo.setActivated(false);

                    clProgressStepOne.setVisibility(View.VISIBLE);
                    clProgressStepTwo.setVisibility(View.GONE);
                    tvHint.setText("Робочі дані");

                } else {
                    requireActivity().onBackPressed();
                }
            }
        });

        clRenial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOnBtnCheck(ivBtnCheckOne);
            }
        });
        clGliptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOnBtnCheck(ivBtnCheckTwo);
            }
        });
        clSagrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOnBtnCheck(ivBtnCheckThree);
            }
        });

        clChooseMp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.goAddMp(navController);
            }
        });




      //  getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
//            @Override
//            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
//                // We use a String here, but any type that can be put in a Bundle is supported
//                String result = bundle.getString("bundleKey");
//                // Do something with the result
//            }
//        });


        requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
        return root;
    }

    private void clickOnBtnCheck(ImageView imageView){
        if (imageView.isActivated()){
            imageView.setActivated(false);
        }
        else {
            imageView.setActivated(true);
        }
    }


}

