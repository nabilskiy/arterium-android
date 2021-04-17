package com.maritech.arterium.ui.dashboard.regionalManager.add_new_mp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maritech.arterium.R;
import com.maritech.arterium.common.ContentState;
import com.maritech.arterium.data.models.AgentRequestModel;
import com.maritech.arterium.data.models.DoctorsModel;
import com.maritech.arterium.databinding.ActivityAddNewMpBinding;
import com.maritech.arterium.ui.base.BaseActivity;
import com.maritech.arterium.ui.dashboard.regionalManager.add_new_mp.holder.SelectDoctorsAdapter;
import com.maritech.arterium.ui.dashboard.regionalManager.add_new_mp.holder.SelectedDoctorsAdapter;

import java.util.Collections;
import java.util.List;

import kotlin.Unit;

public class AddNewMpActivity extends BaseActivity<ActivityAddNewMpBinding> {

    static final String BUNDLE_KEY = "selectedItem";
    static final String REQUEST_KEY = "requestKey";

    private static final String TAG = "AddNewMpActivity_TAG";


    private int currentStep = 0;

    private AddNewMpViewModel viewModel;

    private SelectedDoctorsAdapter selectedDoctorAdapter = null;
    private SelectDoctorsAdapter selectDoctorsAdapter = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddNewMpViewModel.class);

        binding.toolbar.viewOne.setActivated(true);


//        binding.clChooseMp.setOnClickListener(v -> navigator.goAddDoctor(navController));


        binding.btnNextTwo.setAlpha(0.7f);
        binding.btnNextTwo.setClickable(false);
        init();
        initListeners();
        viewModel.getDoctorsList();
    }

    private void init() {
        binding.toolbar.tvToolbarTitle.setText(getString(R.string.new_medical));

        binding.toolbar.tvHint.setText(getString(R.string.personal_data));

        binding.rvDoctors.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        selectDoctorsAdapter = new SelectDoctorsAdapter(selectDoctorOnClickListener);
//        binding.rvDoctors.setAdapter(selectDoctorsAdapter);

        binding.rvSelectedDoctors.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        selectedDoctorAdapter = new SelectedDoctorsAdapter(removeDoctorsOnClickListener);
//        binding.rvDoctors.setAdapter(selectedDoctorAdapter);

    }

    private void initListeners() {
        binding.addDoctors.setOnClickListener(view -> showDoctorsList());
        binding.toolbar.ivArrow.setOnClickListener(view -> goBack());
        binding.toolbar.ivDone.setOnClickListener(view -> goBack());
        binding.btnNextOne.setOnClickListener(v -> {
            showSecondStep();
        });

        binding.btnNextTwo.setOnClickListener(v -> {
            save();
//            finish();
        });
        binding.ccInputName.addTextChangeListener(watcher);
        binding.ccInputPhoneNumber.addTextChangedListener(watcher);
        binding.ccInputSecondName.addTextChangeListener(watcher);

        viewModel.getDoctors().observe(this, this::doctorsObserver);
        viewModel.getSelectedDoctors().observe(this, this::selectedDoctorsObserver);
        viewModel.getDoctorsStateLiveData().observe(this, this::doctorsContentStateObserver);
        viewModel.getContentState().observe(this, this::observeContentState);
        viewModel.getSaveResponseLiveData().observe(this, this::saveRequestObserver);
    }

    private void observeContentState(ContentState state) {
        if (state == ContentState.LOADING) {
            binding.frameProgress.setVisibility(View.VISIBLE);
            return;
        } else if (state == ContentState.ERROR) {
            Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
        }
        binding.frameProgress.setVisibility(View.GONE);
    }

    private void saveRequestObserver(Unit v) {
        finish();
    }

    private void goBack() {
        if (currentStep == 1)
            showFirstStep();
        else if (currentStep == 2)
            showSecondStep();
        else finish();
    }

    private void save() {
        String gender = binding.radioGroup.getCheckedRadioButtonId() == R.id.radio_female ? "f" : "m";
        AgentRequestModel model = new AgentRequestModel(
                "380" + binding.ccInputPhoneNumber.getRawText(),
                binding.ccInputName.getText() + " " + binding.ccInputSecondName.getText(),
                gender
        );
        viewModel.save(model);
    }

    private void showFirstStep() {
        currentStep = 0;
        binding.toolbar.viewTwo.setActivated(false);
        binding.clProgressStepOne.setVisibility(View.VISIBLE);
        binding.clProgressStepTwo.setVisibility(View.GONE);
        binding.btnNextTwo.setVisibility(View.GONE);
        binding.toolbar.tvHint.setText(getString(R.string.working_data));
    }

    private void showSecondStep() {
        currentStep = 1;
        binding.toolbar.viewTwo.setActivated(true);
        binding.clProgressStepOne.setVisibility(View.GONE);
        binding.clProgressStepTwo.setVisibility(View.VISIBLE);
        binding.btnNextTwo.setVisibility(View.VISIBLE);
        binding.toolbar.tvHint.setText(getString(R.string.personal_data));
        binding.thirdStepCL.setVisibility(View.GONE);
        binding.toolbar.llProgress.setVisibility(View.VISIBLE);
        binding.toolbar.llSearch.setVisibility(View.GONE);
        binding.toolbar.ivDone.setVisibility(View.GONE);
        if (binding.rvSelectedDoctors.getAdapter() == null)
            binding.rvSelectedDoctors.setAdapter(selectedDoctorAdapter);
        selectedDoctorAdapter.setDoctors(viewModel.getSelectedDoctors().getValue());
    }

    private void showDoctorsList() {
        currentStep = 2;
        binding.toolbar.viewTwo.setActivated(false);
        binding.toolbar.llProgress.setVisibility(View.GONE);
        binding.toolbar.llSearch.setVisibility(View.VISIBLE);
        binding.clProgressStepTwo.setVisibility(View.GONE);
        binding.clProgressStepOne.setVisibility(View.GONE);
        binding.btnNextTwo.setVisibility(View.GONE);
        binding.toolbar.tvHint.setText(getString(R.string.choose_doctor));
        binding.thirdStepCL.setVisibility(View.VISIBLE);
        if (binding.rvDoctors.getAdapter() == null)
            binding.rvDoctors.setAdapter(selectDoctorsAdapter);
        selectDoctorsAdapter.setDoctors(viewModel.getDoctors().getValue());
    }

    private final SelectedDoctorsAdapter.RemoveDoctorsOnClickListener removeDoctorsOnClickListener =
            new SelectedDoctorsAdapter.RemoveDoctorsOnClickListener() {
                @Override
                public void onClick() {
                    viewModel.notifyDoctorsSetChanged();
                }
            };

    private final SelectDoctorsAdapter.SelectDoctorOnClickListener selectDoctorOnClickListener =
            new SelectDoctorsAdapter.SelectDoctorOnClickListener() {
                @Override
                public void onClick() {
                    viewModel.notifyDoctorsSetChanged();
                }
            };

    private void doctorsObserver(List<DoctorsModel> doctors) {
        Collections.reverse(doctors);
        selectDoctorsAdapter.setDoctors(doctors);
    }

    private void selectedDoctorsObserver(List<DoctorsModel> doctors) {
        selectedDoctorAdapter.setDoctors(doctors);
        changeSecStepNextButState();
        if (currentStep == 1) {
            if (doctors.size() > 0) {
                binding.toolbar.ivDone.setVisibility(View.VISIBLE);
                binding.tvChooseDoctors.setVisibility(View.GONE);
            } else {
                binding.toolbar.ivDone.setVisibility(View.GONE);
                binding.tvChooseDoctors.setVisibility(View.VISIBLE);
            }
        } else binding.tvChooseDoctors.setVisibility(View.GONE);
    }

    private void doctorsContentStateObserver(ContentState state) {
        if (state == ContentState.LOADING) {
            binding.doctorsProgress.setVisibility(View.VISIBLE);
            binding.rvDoctors.setVisibility(View.GONE);
        } else {
            binding.doctorsProgress.setVisibility(View.GONE);
            binding.rvDoctors.setVisibility(View.VISIBLE);
        }
    }

    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            changeFirstStepNextButState();
        }
    };

    private void changeFirstStepNextButState() {
        if (isFirstStepValid()) {
            binding.btnNextOne.setAlpha(1.0f);
            binding.btnNextOne.setEnabled(true);
        } else {
            binding.btnNextOne.setAlpha(0.7f);
            binding.btnNextOne.setEnabled(false);
        }
    }

    private void changeSecStepNextButState() {
        if (isSecStepValid()) {
            binding.btnNextTwo.setAlpha(1.0f);
            binding.btnNextTwo.setEnabled(true);
        } else {
            binding.btnNextTwo.setAlpha(0.7f);
            binding.btnNextTwo.setEnabled(false);
        }
    }

    private boolean isSecStepValid() {
        return viewModel.getSelectedDoctors().getValue().size() > 0;
    }

    private boolean isFirstStepValid() {
        return (!binding.ccInputName.getText().isEmpty() &&
                !binding.ccInputSecondName.getText().isEmpty() &&
                !binding.ccInputPhoneNumber.getRawText().isEmpty()) &&
                binding.ccInputPhoneNumber.getRawText().length() >= 9;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_new_mp;
    }

}

