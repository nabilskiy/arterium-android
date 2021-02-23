package com.maritech.arterium.ui.settings;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.maritech.arterium.R;
import com.maritech.arterium.databinding.FragmentSettingsBinding;
import com.maritech.arterium.ui.base.BaseFragment;

public class SettingsFragment extends BaseFragment<FragmentSettingsBinding> {

    SettingsNavigator navigator = new SettingsNavigator();

    @Override
    protected int getContentView() {
        return R.layout.fragment_settings;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        binding.tvAskPinCode.setOnClickListener(v -> {
            binding.switchAskPinCode.setOpened(!binding.switchAskPinCode.isOpened());
        });

        binding.tvUseBio.setOnClickListener(v -> {
            binding.switchUseBio.setOpened(!binding.switchUseBio.isOpened());
        });

        binding.tvGetPushNotifications.setOnClickListener(v -> {
            binding.switchGetPushNotifications.setOpened(!binding.switchGetPushNotifications.isOpened());
        });

        binding.tvSetLanguage.setOnClickListener(
                v -> navigator.goToChangeLanguage(navController)
        );

        binding.ivGoChangeLanguage.setOnClickListener(
                v -> navigator.goToChangeLanguage(navController)
        );

        binding.header.ivBack.setOnClickListener(
                v -> requireActivity().onBackPressed()
        );

    }
}
