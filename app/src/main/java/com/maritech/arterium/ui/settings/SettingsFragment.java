package com.maritech.arterium.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.maritech.arterium.R;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.databinding.FragmentSettingsBinding;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.security.pinCode.PinCodeActivity;

public class SettingsFragment extends BaseFragment<FragmentSettingsBinding> {

    SettingsNavigator navigator = new SettingsNavigator();

    @Override
    protected int getContentView() {
        return R.layout.fragment_settings;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        boolean isPinCodeEnabled = Pref.getInstance().isPinCodeEnabled(requireContext());
        if (isPinCodeEnabled) {
            binding.switchAskPinCode.setOpened(true);
        }

        binding.tvAskPinCode.setOnClickListener(v -> {
            binding.switchAskPinCode.setOpened(!binding.switchAskPinCode.isOpened());

            if (binding.switchAskPinCode.isOpened()) {
                Pref.getInstance().setPinCode(requireContext(), "");

                Intent intent = new Intent(requireContext(), PinCodeActivity.class);
                intent.putExtra(PinCodeActivity.SETTINGS_EXTRA_KEY, true);
                startActivity(intent);
            } else {
                Pref.getInstance().enablePinCode(requireContext(), false);
            }
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
