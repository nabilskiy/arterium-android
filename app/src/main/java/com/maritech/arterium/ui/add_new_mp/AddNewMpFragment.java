package com.maritech.arterium.ui.add_new_mp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.add_new_mp.holder.SelectedDoctorAdapter;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.choose_doctor.data.ChooseDoctorContent;
import com.maritech.arterium.ui.choose_doctor.holder.ChooseDoctorAdapter;
import com.maritech.arterium.ui.choose_mp.data.ChooseMpContent;

import java.util.ArrayList;

public class AddNewMpFragment extends BaseFragment {
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
    private TextView tvMp;
    private TextView tvMpHint;
    private TextView tvAddDoctor;

    private TextView tvToolbarTitle;
    private TextView tvHint;

    private ConstraintLayout clProgressStepOne;
    private ConstraintLayout clProgressStepTwo;
    private ConstraintLayout clChooseMp;
    SelectedDoctorAdapter adapter;
    RecyclerView rcv;
    private ArrayList<ChooseDoctorContent> listSelectedObject = new ArrayList<>();


    AddNewMpNavigator navigator = new AddNewMpNavigator();
    private AddNewMpViewModel addNewPersonalViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addNewPersonalViewModel = new ViewModelProvider(this).get(AddNewMpViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_new_mp, container, false);

        rcv = (RecyclerView) root.findViewById(R.id.rvSelectedDoctors);

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
        clChooseMp = root.findViewById(R.id.clChooseMp);
        tvAddDoctor = root.findViewById(R.id.tvAddDoctor);

        tvMp = root.findViewById(R.id.tvMp);
        tvMpHint= root.findViewById(R.id.tvMpHint);
        ivChooseMp= root.findViewById(R.id.ivChooseMp);

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

        clChooseMp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.goAddDoctor(navController);
            }
        });


            btnNextTwo.setAlpha(0.7f);
            btnNextTwo.setClickable(false);




        requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        getParentFragmentManager().setFragmentResultListener(REQUEST_KEY, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                listSelectedObject = bundle.getParcelableArrayList(BUNDLE_KEY);

                tvAddDoctor.setVisibility(View.VISIBLE);
                tvMp.setVisibility(View.GONE);
                tvMpHint.setVisibility(View.GONE);

                isMpSelected = true;

                viewProgressTwo.setActivated(true);
                isTwoStep = true;
                btnNextTwo.setVisibility(View.VISIBLE);
                clProgressStepOne.setVisibility(View.GONE);
                clProgressStepTwo.setVisibility(View.VISIBLE);
                tvHint.setText("Робочі дані");

                btnNextTwo.setAlpha(1.0f);
                btnNextTwo.setClickable(true);

                adapter = new SelectedDoctorAdapter(listSelectedObject, new SelectedDoctorAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClicked(int position, ChooseDoctorContent object) {
                        listSelectedObject.remove(object);
                        if(listSelectedObject.size() == 0){
                            btnNextTwo.setAlpha(0.7f);
                            btnNextTwo.setClickable(false);

                            tvAddDoctor.setVisibility(View.GONE);
                            tvMp.setVisibility(View.VISIBLE);
                            tvMpHint.setVisibility(View.VISIBLE);
                        }
                    }
                });
                rcv.setAdapter(adapter);
            }
        });

    }


}

