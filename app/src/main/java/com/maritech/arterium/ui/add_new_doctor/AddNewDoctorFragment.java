package com.maritech.arterium.ui.add_new_doctor;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.choose_mp.data.ChooseMpContent;

public class AddNewDoctorFragment extends BaseFragment {
    static final String BUNDLE_KEY = "selectedItem";
    static final String REQUEST_KEY = "requestKey";

    private View viewProgressOne;
    private View viewProgressTwo;
    private TextView btnNextOne;
    private TextView btnNextTwo;
    private Boolean isTwoStep = false;
    private Boolean isMpSelected = false;
    private ImageView btnBack;
    private ImageView btnAuto;
    private ImageView ivChooseMp;

    private TextView tvToolbarTitle;
    private TextView tvHint;
    private TextView tvMp;
    private TextView tvMpHint;

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

    @Override
    protected int getContentView() {
        return R.layout.fragment_add_new_doctor;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        addNewPersonalViewModel = new ViewModelProvider(this).get(AddNewDoctorViewModel.class);

        clProgressStepOne = root.findViewById(R.id.clProgressStepOne);
        clProgressStepTwo = root.findViewById(R.id.clProgressStepTwo);
        tvToolbarTitle = root.findViewById(R.id.toolbar).findViewById(R.id.tvToolbarTitle);
        tvHint = root.findViewById(R.id.toolbar).findViewById(R.id.tvHint);
        btnNextOne = root.findViewById(R.id.btnNextOne);
        btnNextTwo = root.findViewById(R.id.btnNextTwo);
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
        tvMp = root.findViewById(R.id.tvMp);
        tvMpHint= root.findViewById(R.id.tvMpHint);
        ivChooseMp= root.findViewById(R.id.ivChooseMp);

        tvToolbarTitle.setText("Новий доктор");
        tvHint.setText("Персональнi данi");

        if(isMpSelected){
            viewProgressTwo.setActivated(true);
            isTwoStep = true;

            clProgressStepOne.setVisibility(View.GONE);
            clProgressStepTwo.setVisibility(View.VISIBLE);
            tvHint.setText("Робочі дані");
        }else {
            isTwoStep = false;
            viewProgressTwo.setActivated(false);

            clProgressStepOne.setVisibility(View.VISIBLE);
            clProgressStepTwo.setVisibility(View.GONE);
            tvHint.setText("Персональнi данi");
        }

        btnNextOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewProgressTwo.setActivated(true);
                isTwoStep = true;

                clProgressStepOne.setVisibility(View.GONE);
                clProgressStepTwo.setVisibility(View.VISIBLE);
                tvHint.setText("Робочі дані");

                btnNextTwo.setAlpha(0.7f);
                btnNextTwo.setClickable(false);
                btnNextTwo.setEnabled(false);
            }
        });

        btnNextTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.goToDashboard(navController);
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
                    tvHint.setText("Персональнi данi");

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
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParentFragmentManager().setFragmentResultListener(REQUEST_KEY, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                ChooseMpContent result = bundle.getParcelable(BUNDLE_KEY);

                tvMp.setText(result.getName());
                tvMpHint.setText("Медичний представник");
                ivChooseMp.setImageResource(result.getPhoto());

                isMpSelected = true;

                viewProgressTwo.setActivated(true);
                isTwoStep = true;

                clProgressStepOne.setVisibility(View.GONE);
                clProgressStepTwo.setVisibility(View.VISIBLE);
                tvHint.setText("Робочі дані");
            }
        });
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

