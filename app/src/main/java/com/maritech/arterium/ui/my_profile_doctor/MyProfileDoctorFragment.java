package com.maritech.arterium.ui.my_profile_doctor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.maritech.arterium.R;
import com.maritech.arterium.data.models.DrugProgramModel;
import com.maritech.arterium.data.models.Profile;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.databinding.FragmentMyProfileBinding;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.feedback.FeedbackActivity;
import com.maritech.arterium.ui.login.LoginActivity;
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
        binding.myProfileToolbar.tvToolbarTitle.setText(getString(R.string.doctor_profile));
        binding.myProfileToolbar.ivArrow.setVisibility(View.INVISIBLE);

        binding.myProfileMainContentSettings.getRoot()
                .setOnClickListener(v -> navigator.goToSettings(navController));

        binding.pharmacyList.getRoot().setOnClickListener(v -> navigator.goToMap(navController));

//        binding.achievements.getRoot()
//                .setOnClickListener(v -> navigator.goToAchievements(navController));

        binding.contactWithUs.getRoot().setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), FeedbackActivity.class);
            startActivity(intent);
        });

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
                            DrugProgramModel model = null;
                            int drugProgramId = Pref.getInstance().getDrugProgramId(requireContext());
                            if (profileData.getDrugPrograms() != null)
                                for (DrugProgramModel m : profileData.getDrugPrograms()) {
                                    if (m.getId() == drugProgramId) {
                                        model = m;
                                    }
                                }

                            if (model == null && profileData.getDrugPrograms() != null){
                                model = profileData.getDrugPrograms().get(0);
                            }


                            if (model != null) {
                                binding.tvMyProfileShopingAmount.setText(
                                        getString(R.string.whole_shopping_items1,
                                                model.getPrimarySoldCount())
                                );
                            }
                            if (profileData.getParent() != null) {
                                Profile.Parent parent = profileData.getParent();
                                String role = parent.getRoleKey();
                                String phone = "+" + parent.getLogin();

                                if (role != null && !role.isEmpty() &&
                                        role.equalsIgnoreCase("AGENT")) {

                                    role = getString(R.string.medical);
                                }
                                binding.myProfileCard
                                        .tvCardPersonSkill.setText(role);
                                binding.myProfileCard
                                        .tvCardPersonName.setText(parent.getName());
                                binding.myProfileCard
                                        .tvCardPersonTelephoneNumber.setText(phone);

                                binding.myProfileCard.getRoot().setVisibility(View.VISIBLE);

                                binding.myProfileCard.callIv
                                        .setOnClickListener(view -> startCall(phone));
                            } else {
                                binding.myProfileCard.getRoot().setVisibility(View.GONE);
                            }
                        });

        logoutViewModel.logout
                .observe(getViewLifecycleOwner(),
                        loginData -> {
                            Pref.getInstance().setUserFirstLaunch(requireActivity(), true);
                            Pref.getInstance().setUserProfile(requireActivity(), null);
                            Pref.getInstance().setAuthToken(requireActivity(), "");
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                            requireActivity().finishAffinity();
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
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        builder.setTitle(getString(R.string.account_title));
        builder.setMessage(getString(R.string.dialog_logout_message));

        builder.setPositiveButton(
                getString(R.string.dialog_logout_ok), (dialog, which) -> logout()
        );

        builder.setNegativeButton(
                getString(R.string.dialog_logout_cancel), (dialog, which) -> dialog.dismiss()
        );

        builder.show();
    }

    public void setMyProfileContentList() {
//        binding.achievements
//                .tvMyProfileListTitle.setText(R.string.achievements);
        binding.contactWithUs
                .tvMyProfileListTitle.setText(R.string.contact_with_us);
        binding.myProfileMainContentSettings
                .tvMyProfileListTitle.setText(R.string.settings);

//        binding.achievements
//                .ivMyProfileListIcon.setBackgroundResource(R.drawable.ic_achives);
        binding.myProfileMainContentSettings
                .ivMyProfileListIcon.setBackgroundResource(R.drawable.ic_blue_settings);
        binding.contactWithUs
                .ivMyProfileListIcon.setBackgroundResource(R.drawable.ic_blue_phone);
    }

    private void startCall(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
        startActivity(intent);
    }

    private void logout() {
        logoutViewModel.logout();
    }
}