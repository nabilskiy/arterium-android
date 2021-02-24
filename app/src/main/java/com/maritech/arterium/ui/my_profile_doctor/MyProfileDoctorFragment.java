package com.maritech.arterium.ui.my_profile_doctor;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.maritech.arterium.R;
import com.maritech.arterium.data.models.Profile;
import com.maritech.arterium.databinding.FragmentMyProfileBinding;
import com.maritech.arterium.ui.MainActivity;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.login.LoginViewModel;
import com.maritech.arterium.utils.ToastUtil;

@SuppressLint("CutPasteId")
public class MyProfileDoctorFragment extends BaseFragment<FragmentMyProfileBinding> {

    MyProfileDoctorNavigator navigator = new MyProfileDoctorNavigator();

    ProfileViewModel viewModel = new ProfileViewModel();
    LoginViewModel logoutViewModel = new LoginViewModel();

    @Override
    protected int getContentView() {
        return R.layout.fragment_my_profile;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        binding.clLogOut.setOnClickListener(v -> showLogOutDialog());

        binding.myProfileToolbar.ivRight.setVisibility(View.INVISIBLE);
        binding.myProfileToolbar.tvToolbarTitle.setText("Профіль доктора");
        binding.myProfileToolbar.ivArrow.setVisibility(View.INVISIBLE);

        binding.myProfileMainContentSettings.getRoot()
                .setOnClickListener(v -> navigator.goToSettings(navController));

        binding.myProfileCard.getRoot().setOnClickListener(
                v -> navigator.goPatientCard(navController)
        );

        binding.pharmacyList.getRoot().setOnClickListener(v -> navigator.goToMap(navController));

        binding.achievements.getRoot()
                .setOnClickListener(v -> navigator.goToAchievements(navController));

        setMyProfileContentList();

        observeViewModel();

        viewModel.getProfile();
    }

    private void observeViewModel() {
        viewModel.responseLiveData
                .observe(getViewLifecycleOwner(),
                        profileData -> {
                            binding.tvMyProfileName.setText(profileData.getName());
                            binding.tvMyProfileSkill.setText(profileData.getInstitutionType());
                            binding.tvMyProfileShopingAmount.setText(
                                    getString(R.string.whole_shopping_items1,
                                            profileData.getSoldCount())
                            );

                            if (profileData.getParent() != null) {
                                Profile.Parent parent = profileData.getParent();
                                binding.myProfileCard
                                        .tvCardPersonName.setText(parent.getName());
                                binding.myProfileCard
                                        .tvCardPersonSkill.setText(parent.getName());
                                binding.myProfileCard
                                        .tvCardPersonTelephoneNumber.setText(parent.getName());
                            } else {
                                binding.myProfileCard.getRoot().setVisibility(View.GONE);
                            }
                        });

        logoutViewModel.logout
                .observe(getViewLifecycleOwner(),
                        loginData -> {
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            baseActivity.finishAffinity();
                        });

        logoutViewModel.contentState
                .observe(getViewLifecycleOwner(), contentState -> {
                    if (contentState.isLoading()) {
                        showProgressDialog();
                    } else {
                        hideProgressDialog();
                    }
                });

        logoutViewModel.error.observe(getViewLifecycleOwner(),
                error -> ToastUtil.show(requireContext(), error));
    }

    private void showLogOutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(getString(R.string.account_title));
        builder.setMessage(getString(R.string.dialog_logout_message));

        builder.setPositiveButton(
                getString(R.string.dialog_logout_ok), (dialog, which) -> logout()
        );

        builder.setNegativeButton(
                getString(R.string.dialog_logout_cancel), (dialog, which) -> dialog.dismiss()
        );

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void setMyProfileContentList() {
        binding.achievements
                .tvMyProfileListTitle.setText(R.string.achievements);
        binding.contactWithUs
                .tvMyProfileListTitle.setText(R.string.contact_with_us);
        binding.myProfileMainContentSettings
                .tvMyProfileListTitle.setText(R.string.settings);

        binding.achievements
                .ivMyProfileListIcon.setBackgroundResource(R.drawable.ic_achives);
        binding.myProfileMainContentSettings
                .ivMyProfileListIcon.setBackgroundResource(R.drawable.ic_blue_settings);
        binding.contactWithUs
                .ivMyProfileListIcon.setBackgroundResource(R.drawable.ic_blue_phone);
    }

    private void logout() {
        logoutViewModel.logout();
    }
}