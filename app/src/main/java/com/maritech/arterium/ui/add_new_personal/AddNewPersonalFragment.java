package com.maritech.arterium.ui.add_new_personal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.maritech.arterium.R;
import com.maritech.arterium.databinding.ItemDashboardBinding;
import com.maritech.arterium.ui.base.BaseAdapter;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.dashboard.DashboardNavigator;
import com.maritech.arterium.ui.dashboardMp.DashboardMpViewModel;
import com.maritech.arterium.ui.dashboardMp.data.DoctorsContent;
import com.maritech.arterium.ui.dialogs.dialog_with_recycler.DialogWithRecycler;

import java.util.ArrayList;

public class AddNewPersonalFragment extends BaseFragment {


    private View viewProgressOne;
    private View viewProgressTwo;
    private RadioGroup radioGroup;
    private TextView btnNextOne;
    private Boolean isTwoStep = false;
    private ImageView btnBack;
    private ImageView btnAuto;

    private TextView tvToolbarTitle;
    private TextView tvHint;


    private ConstraintLayout clProgressStepOne;
    private ConstraintLayout clProgressStepTwo;
    private ConstraintLayout clInfoUser;

    AddNewPersonalNavigator navigator = new AddNewPersonalNavigator();
    private AddNewPersonalViewModel addNewPersonalViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addNewPersonalViewModel = new ViewModelProvider(this).get(AddNewPersonalViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_new_personal, container, false);

        clProgressStepOne = root.findViewById(R.id.clProgressStepOne);
        clProgressStepTwo = root.findViewById(R.id.clProgressStepTwo);
        tvToolbarTitle = root.findViewById(R.id.toolbar).findViewById(R.id.tvToolbarTitle);
        tvHint = root.findViewById(R.id.toolbar).findViewById(R.id.tvHint);
        radioGroup = root.findViewById(R.id.radioGroup);
        btnNextOne = root.findViewById(R.id.btnNextOne);
        viewProgressOne = root.findViewById(R.id.toolbar).findViewById(R.id.viewOne);
        viewProgressOne.setActivated(true);
        viewProgressTwo = root.findViewById(R.id.toolbar).findViewById(R.id.viewTwo);
        btnBack = root.findViewById(R.id.toolbar).findViewById(R.id.ivRight);
        btnAuto = root.findViewById(R.id.toolbar).findViewById(R.id.ivLeft);


        tvToolbarTitle.setText("Новий пацієнт");
        tvHint.setText("Медичні дані");

//        ivSearch = root.findViewById(R.id.ivSearch);


        btnNextOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewProgressTwo.setActivated(true);
                isTwoStep = true;

                clProgressStepOne.setVisibility(View.GONE);
                clProgressStepTwo.setVisibility(View.VISIBLE);

                btnAuto.setVisibility(View.VISIBLE);

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

                    btnAuto.setVisibility(View.GONE);

                } else {
                    navigator.goToDashboard(navController);
                }


            }
        });


        requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
        return root;
    }


}

