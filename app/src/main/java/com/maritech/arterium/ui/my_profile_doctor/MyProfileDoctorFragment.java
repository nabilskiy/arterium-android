package com.maritech.arterium.ui.my_profile_doctor;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.maritech.arterium.ui.MainActivity;
import com.maritech.arterium.R;
import com.maritech.arterium.data.models.Profile;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.login.LoginViewModel;
import com.maritech.arterium.utils.ToastUtil;

@SuppressLint("CutPasteId")
public class MyProfileDoctorFragment extends BaseFragment {

    ImageView arrow;
    ImageView edit;
    TextView toolbarTitle;
    ImageView imageNotification;
    ImageView imageSetting;
    ImageView imageContact;
    TextView notification;
    TextView contact;
    TextView setting;
    TextView tvMyProfileName;
    TextView tvMyProfileSkill;
    TextView tvMyProfileShopingAmount;
    ConstraintLayout clLogOut;
    View myProfileMainContentSettings;
    View myProfileCard;
    TextView tvCardPersonName;
    TextView tvCardPersonSkill;
    TextView tvCardPersonTelephoneNumber;
    View pharmacyList;
    MyProfileDoctorNavigator navigator = new MyProfileDoctorNavigator();

    View myNotifications;

    ProfileViewModel viewModel = new ProfileViewModel();
    LoginViewModel logoutViewModel = new LoginViewModel();

    @Override
    protected int getContentView() {
        return R.layout.fragment_my_profile;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        arrow = root.findViewById(R.id.myProfileToolbar).findViewById(R.id.ivArrow);
        toolbarTitle = root.findViewById(R.id.myProfileToolbar).findViewById(R.id.tvToolbarTitle);
        edit = root.findViewById(R.id.ivRight);
        myProfileMainContentSettings = root.findViewById(R.id.myProfileMainContentSettings);
        myProfileCard = root.findViewById(R.id.myProfileCard);
        tvCardPersonName = myProfileCard.findViewById(R.id.tvCardPersonName);
        tvCardPersonSkill = myProfileCard.findViewById(R.id.tvCardPersonSkill);
        tvCardPersonTelephoneNumber = myProfileCard.findViewById(R.id.tvCardPersonTelephoneNumber);

        pharmacyList = root.findViewById(R.id.pharmacyList);
        myNotifications = root.findViewById(R.id.myProfileMainContentNotifications);

        tvMyProfileName = root.findViewById(R.id.tvMyProfileName);
        tvMyProfileSkill = root.findViewById(R.id.tvMyProfileSkill);
        tvMyProfileShopingAmount = root.findViewById(R.id.tvMyProfileShopingAmount);
        clLogOut = root.findViewById(R.id.clLogOut);

        clLogOut.setOnClickListener(v -> showLogOutDialog());

        edit.setVisibility(View.INVISIBLE);
        toolbarTitle.setText("Профіль доктора");
        arrow.setVisibility(View.INVISIBLE);

        myProfileMainContentSettings.setOnClickListener(v -> navigator.goToSettings(navController));

        myProfileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.goPatientCard(navController);
            }
        });

        pharmacyList.setOnClickListener(v -> navigator.goToMap(navController));

        myNotifications.setOnClickListener(v -> navigator.goToNotifications(navController));

        setMyProfileContentList(root);

        observeViewModel();

        viewModel.getProfile();
    }

    private void observeViewModel() {
        viewModel.responseLiveData
                .observe(getViewLifecycleOwner(),
                        profileData -> {
                            tvMyProfileName.setText(profileData.getName());
                            tvMyProfileSkill.setText(profileData.getInstitutionType());
                            tvMyProfileShopingAmount.setText(
                                    getString(R.string.whole_shopping_items1,
                                            profileData.getSoldCount())
                            );

                            if (profileData.getParent() != null) {
                                Profile.Parent parent = profileData.getParent();
                                tvCardPersonName.setText(parent.getName());
                                tvCardPersonSkill.setText(parent.getName());
                                tvCardPersonTelephoneNumber.setText(parent.getName());
                            } else {
                                myProfileCard.setVisibility(View.GONE);
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

    public void setMyProfileContentList(View root) {
        imageNotification = root.findViewById(R.id.myProfileMainContentNotifications)
                .findViewById(R.id.ivMyProfileListIcon);
        imageSetting = root.findViewById(R.id.myProfileMainContentSettings)
                .findViewById(R.id.ivMyProfileListIcon);
        imageContact = root.findViewById(R.id.contactWithUs)
                .findViewById(R.id.ivMyProfileListIcon);

        notification = root.findViewById(R.id.myProfileMainContentNotifications)
                .findViewById(R.id.tvMyProfileListTitle);
        contact = root.findViewById(R.id.contactWithUs)
                .findViewById(R.id.tvMyProfileListTitle);
        setting = root.findViewById(R.id.myProfileMainContentSettings)
                .findViewById(R.id.tvMyProfileListTitle);

        notification.setText(R.string.notification);
        contact.setText(R.string.contact_with_us);
        setting.setText(R.string.settings);

        imageNotification.setBackgroundResource(R.drawable.ic_bell);
        imageSetting.setBackgroundResource(R.drawable.ic_blue_settings);
        imageContact.setBackgroundResource(R.drawable.ic_blue_phone);
    }

    private void logout() {
        logoutViewModel.logout();
    }
}