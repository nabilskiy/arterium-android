package com.maritech.arterium.ui.add_new_doctor;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import com.maritech.arterium.R;
import com.maritech.arterium.databinding.FragmentAddNewDoctorBinding;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.choose_mp.data.ChooseMpContent;

public class AddNewDoctorFragment extends BaseFragment<FragmentAddNewDoctorBinding> {
    static final String BUNDLE_KEY = "selectedItem";
    static final String REQUEST_KEY = "requestKey";

    private Boolean isTwoStep = false;
    private Boolean isMpSelected = false;

    AddNewDoctorNavigator navigator = new AddNewDoctorNavigator();
    AddNewDoctorViewModel addNewPersonalViewModel;

    @Override
    protected int getContentView() {
        return R.layout.fragment_add_new_doctor;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        addNewPersonalViewModel = new ViewModelProvider(this).get(AddNewDoctorViewModel.class);

        binding.toolbar.viewOne.setActivated(true);
        binding.toolbar.tvToolbarTitle.setText(getString(R.string.new_doctor));
        binding.toolbar.tvHint.setText(getString(R.string.personal_data));

        if (isMpSelected) {
            binding.toolbar.viewTwo.setActivated(true);
            isTwoStep = true;

            binding.clProgressStepOne.setVisibility(View.GONE);
            binding.clProgressStepTwo.setVisibility(View.VISIBLE);
            binding.toolbar.tvHint.setText(getString(R.string.working_data));
        } else {
            isTwoStep = false;
            binding.toolbar.viewTwo.setActivated(false);

            binding.clProgressStepOne.setVisibility(View.VISIBLE);
            binding.clProgressStepTwo.setVisibility(View.GONE);
            binding.toolbar.tvHint.setText(getString(R.string.personal_data));
        }

        binding.btnNextOne.setOnClickListener(v -> {
            binding.toolbar.viewTwo.setActivated(true);
            isTwoStep = true;

            binding.clProgressStepOne.setVisibility(View.GONE);
            binding.clProgressStepTwo.setVisibility(View.VISIBLE);
            binding.toolbar.tvHint.setText(getString(R.string.working_data));

            binding.btnNextTwo.setAlpha(0.7f);
            binding.btnNextTwo.setClickable(false);
            binding.btnNextTwo.setEnabled(false);
        });

        binding.btnNextTwo.setOnClickListener(v -> navigator.goToDashboard(navController));

        binding.toolbar.ivArrow.setOnClickListener(v -> {
            if (isTwoStep) {
                isTwoStep = false;
                binding.toolbar.viewTwo.setActivated(false);

                binding.clProgressStepOne.setVisibility(View.VISIBLE);
                binding.clProgressStepTwo.setVisibility(View.GONE);
                binding.toolbar.tvHint.setText(getString(R.string.personal_data));

            } else {
                requireActivity().onBackPressed();
            }
        });

        binding.clRenial.setOnClickListener(v -> clickOnBtnCheck(binding.ivBtnCheckOne));
        binding.clGliptar.setOnClickListener(v -> clickOnBtnCheck(binding.ivBtnCheckTwo));
        binding.clSagrada.setOnClickListener(v -> clickOnBtnCheck(binding.ivBtnCheckThree));

        binding.clChooseMp.setOnClickListener(v -> navigator.goAddMp(navController));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParentFragmentManager().setFragmentResultListener(
                REQUEST_KEY,
                this,
                (requestKey, bundle) -> {
                    ChooseMpContent result = bundle.getParcelable(BUNDLE_KEY);

                    binding.tvMp.setText(result.getName());
                    binding.tvMpHint.setText(getString(R.string.medical));
                    binding.ivChooseMp.setImageResource(result.getPhoto());

                    isMpSelected = true;

                    binding.toolbar.viewTwo.setActivated(true);
                    isTwoStep = true;

                    binding.clProgressStepOne.setVisibility(View.GONE);
                    binding.clProgressStepTwo.setVisibility(View.VISIBLE);
                    binding.toolbar.tvHint.setText(getString(R.string.working_data));
                }
        );
    }


    private void clickOnBtnCheck(ImageView imageView) {
        imageView.setActivated(!imageView.isActivated());
    }


}

