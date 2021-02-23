package com.maritech.arterium.ui.my_profile;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.maritech.arterium.R;
import com.maritech.arterium.databinding.FragmentMyProfileBinding;
import com.maritech.arterium.ui.base.BaseFragment;

public class MyProfileFragment extends BaseFragment<FragmentMyProfileBinding> {

    MyProfileNavigator navigator = new MyProfileNavigator();

    @Override
    protected int getContentView() {
        return R.layout.fragment_my_profile;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        binding.myProfileToolbar.ivRight.setVisibility(View.INVISIBLE);
        binding.myProfileToolbar.tvToolbarTitle.setText(R.string.my_profile);
        binding.myProfileToolbar.ivArrow.setVisibility(View.INVISIBLE);

        binding.myProfileMainContentSettings.getRoot()
                .setOnClickListener(v -> navigator.goToSettings(navController));

        binding.myProfileCard.getRoot().setOnClickListener(v -> {
//                Bundle bundle = new Bundle();
//                bundle.putParcelable(PatientCardFragment.PATIENT_MODEL_KEY, object);
            navigator.goPatientCard(navController);
        });

        binding.pharmacyList.getRoot().setOnClickListener(v -> navigator.goToMap(navController));

        binding.myProfileMainContentNotifications.getRoot()
                .setOnClickListener(v -> navigator.goToNotifications(navController));

        setMyProfileContentList();
    }

    public void setMyProfileContentList(){
        binding.myProfileMainContentNotifications
                .tvMyProfileListTitle.setText(R.string.notification);
        binding.contactWithUs
                .tvMyProfileListTitle.setText(R.string.contact_with_us);
        binding.myProfileMainContentSettings
                .tvMyProfileListTitle.setText(R.string.settings);

        binding.myProfileMainContentNotifications
                .ivMyProfileListIcon.setBackgroundResource(R.drawable.ic_bell);
        binding.myProfileMainContentSettings
                .ivMyProfileListIcon.setBackgroundResource(R.drawable.ic_blue_settings);
        binding.contactWithUs.ivMyProfileListIcon
                .setBackgroundResource(R.drawable.ic_blue_phone);
    }
}