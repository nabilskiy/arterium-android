package com.maritech.arterium.ui.dashboard.regionalManager.add_new_mp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.maritech.arterium.R;
import com.maritech.arterium.databinding.ActivityAddNewMpBinding;
import com.maritech.arterium.ui.base.BaseActivity;
import com.maritech.arterium.ui.dashboard.regionalManager.add_new_mp.holder.SelectedDoctorAdapter;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.dashboard.regionalManager.add_new_mp.choose_doctor.data.ChooseDoctorContent;

import java.util.ArrayList;

public class AddNewMpActivity extends BaseActivity<ActivityAddNewMpBinding> {

    static final String BUNDLE_KEY = "selectedItem";
    static final String REQUEST_KEY = "requestKey";

    private Boolean isTwoStep = false;
    private Boolean isMpSelected = false;

    SelectedDoctorAdapter adapter;

    private ArrayList<ChooseDoctorContent> listSelectedObject = new ArrayList<>();

    AddNewMpNavigator navigator = new AddNewMpNavigator();
    private AddNewMpViewModel addNewPersonalViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addNewPersonalViewModel = new ViewModelProvider(this).get(AddNewMpViewModel.class);

        binding.toolbar.viewOne.setActivated(true);


//        binding.clChooseMp.setOnClickListener(v -> navigator.goAddDoctor(navController));


        binding.btnNextTwo.setAlpha(0.7f);
        binding.btnNextTwo.setClickable(false);
        init();
        initListeners();
    }



    private void init() {
        binding.toolbar.tvToolbarTitle.setText(getString(R.string.new_medical));

        binding.toolbar.tvHint.setText(getString(R.string.personal_data));
    }

    private void initListeners() {
        binding.toolbar.ivArrow.setOnClickListener(view -> goBack());
        binding.btnNextOne.setOnClickListener(v -> {
            showSecondStep();
        });

        binding.btnNextTwo.setOnClickListener(v -> {
            save();
            finish();
        });
        binding.ccInputName.addTextChangeListener(watcher);
        binding.ccInputPhoneNumber.addTextChangeListener(watcher);
        binding.ccInputSecondName.addTextChangeListener(watcher);
    }

    private void goBack() {
        if (isTwoStep)
            showFirstStep();
        else finish();
    }

    private void save() {

    }

    private void showFirstStep() {
        isTwoStep = false;
        binding.toolbar.viewTwo.setActivated(false);

        binding.clProgressStepOne.setVisibility(View.VISIBLE);
        binding.clProgressStepTwo.setVisibility(View.GONE);
        binding.btnNextTwo.setVisibility(View.GONE);
        binding.toolbar.tvHint.setText(getString(R.string.working_data));
    }

    private void showSecondStep() {
        binding.toolbar.viewTwo.setActivated(true);
        isTwoStep = true;

        binding.clProgressStepOne.setVisibility(View.GONE);
        binding.clProgressStepTwo.setVisibility(View.VISIBLE);
        binding.btnNextTwo.setVisibility(View.VISIBLE);
        binding.toolbar.tvHint.setText(getString(R.string.personal_data));
    }

    private void showDoctorsList() {

    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!isTwoStep)
                changeFirstStepNextButState();
            else changeSecStepNextButState();
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
        return false;
    }

    private boolean isFirstStepValid() {
        return (!binding.ccInputName.getText().isEmpty() &&
                !binding.ccInputSecondName.getText().isEmpty() &&
                !binding.ccInputPhoneNumber.getText().isEmpty());
    }


//        getParentFragmentManager()
//                .setFragmentResultListener(REQUEST_KEY, this, (requestKey, bundle) -> {
//                    listSelectedObject = bundle.getParcelableArrayList(BUNDLE_KEY);
//
//                    binding.tvAddDoctor.setVisibility(View.VISIBLE);
//                    binding.tvMp.setVisibility(View.GONE);
//                    binding.tvMpHint.setVisibility(View.GONE);
//
//                    isMpSelected = true;
//
//                    binding.toolbar.viewTwo.setActivated(true);
//                    isTwoStep = true;
//                    binding.btnNextTwo.setVisibility(View.VISIBLE);
//                    binding.clProgressStepOne.setVisibility(View.GONE);
//                    binding.clProgressStepTwo.setVisibility(View.VISIBLE);
//                    binding.toolbar.tvHint.setText(getString(R.string.working_data));
//
//                    binding.btnNextTwo.setAlpha(1.0f);
//                    binding.btnNextTwo.setClickable(true);
//
//                    adapter = new SelectedDoctorAdapter(listSelectedObject, (position, object) -> {
//                        listSelectedObject.remove(object);
//                        if (listSelectedObject.size() == 0) {
//                            binding.btnNextTwo.setAlpha(0.7f);
//                            binding.btnNextTwo.setClickable(false);
//
//                            binding.tvAddDoctor.setVisibility(View.GONE);
//                            binding.tvMp.setVisibility(View.VISIBLE);
//                            binding.tvMpHint.setVisibility(View.VISIBLE);
//                        }
//                    });
//                    binding.rvSelectedDoctors.setAdapter(adapter);
//                });



    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_new_mp;
    }
}

