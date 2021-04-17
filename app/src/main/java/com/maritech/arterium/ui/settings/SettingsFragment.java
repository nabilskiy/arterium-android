package com.maritech.arterium.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.biometric.BiometricManager;

import com.maritech.arterium.R;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.databinding.FragmentSettingsBinding;
import com.maritech.arterium.ui.MainActivity;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.lock.pinCode.PinCodeActivity;

public class SettingsFragment extends BaseFragment<FragmentSettingsBinding> {

    SettingsNavigator navigator = new SettingsNavigator();

    @Override
    protected int getContentView() {
        return R.layout.fragment_settings;
    }

    @Override
    public void onViewCreated(@NonNull View root,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        boolean isPinCodeEnabled = Pref.getInstance().isPinCodeEnabled(requireContext());
        if (isPinCodeEnabled) {
            binding.switchAskPinCode.setChecked(true);
        }

        binding.switchAskPinCode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Pref.getInstance().setPinCode(requireContext(), "");
                Intent intent = new Intent(requireContext(), PinCodeActivity.class);
                intent.putExtra(PinCodeActivity.SETTINGS_EXTRA_KEY, true);
                startActivity(intent);
            } else {
                Pref.getInstance().setPinCodeEnable(requireContext(), false);
            }
        });

        boolean isBioEnabled = Pref.getInstance().isFingerPrintEnabled(requireContext());
        if (isBioEnabled) {
            binding.switchUseBio.setChecked(true);
        }

        if (isFingerPrintAvailable()) {
            binding.tvUseBio.setVisibility(View.VISIBLE);
            binding.switchUseBio.setVisibility(View.VISIBLE);
            binding.viewLineOne.setVisibility(View.VISIBLE);
            binding.switchUseBio.setOnCheckedChangeListener((buttonView, isChecked) ->
                    Pref.getInstance().setFingerPrintEnable(requireContext(), isChecked));
        }

        boolean isPushEnabled = Pref.getInstance().isPushEnabled(requireContext());
        if (isPushEnabled) {
            binding.switchGetPushNotifications.setChecked(true);
        }
        binding.switchGetPushNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Pref.getInstance().setPushEnable(requireActivity(), isChecked);
            ((MainActivity) getActivity()).sendFirebaseToken();
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

    private boolean isFingerPrintAvailable() {
        return BiometricManager.from(requireContext())
                .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) ==
                BiometricManager.BIOMETRIC_SUCCESS;
    }

}
