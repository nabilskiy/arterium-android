package com.maritech.arterium.ui.dashboard.regionalManager.add_new_mp;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import com.maritech.arterium.R;
import com.maritech.arterium.databinding.FragmentAddNewMpBinding;
import com.maritech.arterium.ui.dashboard.regionalManager.add_new_mp.holder.SelectedDoctorAdapter;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.dashboard.regionalManager.add_new_mp.choose_doctor.data.ChooseDoctorContent;

import java.util.ArrayList;

public class AddNewMpFragment extends BaseFragment<FragmentAddNewMpBinding> {

    static final String BUNDLE_KEY = "selectedItem";
    static final String REQUEST_KEY = "requestKey";

    private Boolean isTwoStep = false;
    private Boolean isMpSelected = false;

    SelectedDoctorAdapter adapter;

    private ArrayList<ChooseDoctorContent> listSelectedObject = new ArrayList<>();

    AddNewMpNavigator navigator = new AddNewMpNavigator();
    private AddNewMpViewModel addNewPersonalViewModel;

    @Override
    protected int getContentView() {
        return R.layout.fragment_add_new_mp;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        addNewPersonalViewModel = new ViewModelProvider(this).get(AddNewMpViewModel.class);

        binding.toolbar.viewOne.setActivated(true);

        binding.toolbar.tvToolbarTitle.setText(getString(R.string.new_medical));

        binding.toolbar.tvHint.setText(getString(R.string.personal_data));

        binding.btnNextOne.setOnClickListener(v -> {
            binding.toolbar.viewTwo.setActivated(true);
            isTwoStep = true;

            binding.clProgressStepOne.setVisibility(View.GONE);
            binding.clProgressStepTwo.setVisibility(View.VISIBLE);
            binding.btnNextTwo.setVisibility(View.VISIBLE);
            binding.toolbar.tvHint.setText(getString(R.string.personal_data));

        });

        binding.btnNextTwo.setOnClickListener(v -> navigator.goToDashboard(navController));

        binding.toolbar.ivArrow.setOnClickListener(v -> {
            if (isTwoStep) {
                isTwoStep = false;
                binding.toolbar.viewTwo.setActivated(false);

                binding.clProgressStepOne.setVisibility(View.VISIBLE);
                binding.clProgressStepTwo.setVisibility(View.GONE);
                binding.btnNextTwo.setVisibility(View.GONE);
                binding.toolbar.tvHint.setText(getString(R.string.working_data));

            } else {
                requireActivity().onBackPressed();
            }
        });

        binding.clChooseMp.setOnClickListener(v -> navigator.goAddDoctor(navController));


        binding.btnNextTwo.setAlpha(0.7f);
        binding.btnNextTwo.setClickable(false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParentFragmentManager()
                .setFragmentResultListener(REQUEST_KEY, this, (requestKey, bundle) -> {
                    listSelectedObject = bundle.getParcelableArrayList(BUNDLE_KEY);

                    binding.tvAddDoctor.setVisibility(View.VISIBLE);
                    binding.tvMp.setVisibility(View.GONE);
                    binding.tvMpHint.setVisibility(View.GONE);

                    isMpSelected = true;

                    binding.toolbar.viewTwo.setActivated(true);
                    isTwoStep = true;
                    binding.btnNextTwo.setVisibility(View.VISIBLE);
                    binding.clProgressStepOne.setVisibility(View.GONE);
                    binding.clProgressStepTwo.setVisibility(View.VISIBLE);
                    binding.toolbar.tvHint.setText(getString(R.string.working_data));

                    binding.btnNextTwo.setAlpha(1.0f);
                    binding.btnNextTwo.setClickable(true);

                    adapter = new SelectedDoctorAdapter(listSelectedObject, (position, object) -> {
                        listSelectedObject.remove(object);
                        if (listSelectedObject.size() == 0) {
                            binding.btnNextTwo.setAlpha(0.7f);
                            binding.btnNextTwo.setClickable(false);

                            binding.tvAddDoctor.setVisibility(View.GONE);
                            binding.tvMp.setVisibility(View.VISIBLE);
                            binding.tvMpHint.setVisibility(View.VISIBLE);
                        }
                    });
                    binding.rvSelectedDoctors.setAdapter(adapter);
                });

    }


}

