package com.maritech.arterium.ui.add_new_mp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseFragment;

public class AddNewMpFragment extends BaseFragment {


    private View viewProgressOne;
    private View viewProgressTwo;
    private TextView btnNextOne;
    private TextView btnNextTwo;
    private Boolean isTwoStep = false;
    private ImageView btnBack;
    private ImageView btnAuto;

    private TextView tvToolbarTitle;
    private TextView tvHint;

    private ConstraintLayout clProgressStepOne;
    private ConstraintLayout clProgressStepTwo;


    AddNewMpNavigator navigator = new AddNewMpNavigator();
    private AddNewMpViewModel addNewPersonalViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addNewPersonalViewModel = new ViewModelProvider(this).get(AddNewMpViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_new_mp, container, false);

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

        tvToolbarTitle.setText("Новий мед. представник");
        tvHint.setText("Персональнi данi");

        btnNextOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewProgressTwo.setActivated(true);
                isTwoStep = true;

                clProgressStepOne.setVisibility(View.GONE);
                clProgressStepTwo.setVisibility(View.VISIBLE);
                btnNextTwo.setVisibility(View.VISIBLE);
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
                    btnNextTwo.setVisibility(View.GONE);
                    tvHint.setText("Робочі дані");

                } else {
                    requireActivity().onBackPressed();
                }
            }
        });



        requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
        return root;
    }


}

