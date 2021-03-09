package com.maritech.arterium.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import com.maritech.arterium.R;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.databinding.ActivitySplashBinding;
import com.maritech.arterium.ui.MainActivity;
import com.maritech.arterium.ui.base.BaseActivity;
import com.maritech.arterium.ui.login.LoginActivity;
import com.maritech.arterium.ui.lock.pinCode.PinCodeActivity;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    private BiometricManager biometricManager;
    private boolean isPinEnabled;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isPinEnabled = Pref.getInstance().isPinCodeEnabled(this);

        biometricManager = BiometricManager.from(this);

        boolean isFirstLaunch =
                Pref.getInstance().isUserFirstLaunch(SplashActivity.this);

        if (isFirstLaunch) {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            finishAffinity();
            startActivity(intent);
        } else {
            if (isFingerPrintEnabled()) {
                initBiometricPrompt();
            } else {
                checkPinCodeEnable();
            }
        }
    }

    private boolean isFingerPrintEnabled() {
        boolean isFingerPrintEnabled =
                Pref.getInstance().isFingerPrintEnabled(this);

        return isFingerPrintEnabled && biometricManager.canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_STRONG
        ) == BiometricManager.BIOMETRIC_SUCCESS;
    }

    private void initBiometricPrompt() {
        BiometricPrompt biometricPrompt = new BiometricPrompt(this, callback);

        BiometricPrompt.PromptInfo.Builder builder = new BiometricPrompt.PromptInfo.Builder();
        builder.setTitle(getString(R.string.bio_lock_title));

        if (isPinEnabled) {
            builder.setNegativeButtonText(getString(R.string.pin_code_button));
        } else {
            builder.setDeviceCredentialAllowed(true);
        }

        biometricPrompt.authenticate(builder.build());
    }

    BiometricPrompt.AuthenticationCallback callback = new BiometricPrompt.AuthenticationCallback() {
        @Override
        public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);

            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            finishAffinity();
            startActivity(intent);
        }

        @Override
        public void onAuthenticationError(int errorCode,
                                          @NonNull CharSequence errString) {
            super.onAuthenticationError(errorCode, errString);

            if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                checkPinCodeEnable();
            } else {
                finish();
            }
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();

            initBiometricPrompt();
        }
    };

    private void checkPinCodeEnable() {
        if (isPinEnabled) {
            Intent intent = new Intent(SplashActivity.this, PinCodeActivity.class);
            finishAffinity();
            startActivity(intent);
        } else {
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                finishAffinity();
                startActivity(intent);
            }, 1000);

        }
    }


//    void goToLogin(NavController navController) {
//        navController.navigate(R.id.action_loginSplashFragment_to_loginFragment);
//    }
//
//    void goToPin(NavController navController) {
//        navController.navigate(R.id.action_loginFragment_to_pinCodeFragment);
//    }
//
//    void goToDoctorDashboard(NavController navController) {
//        navController.navigate(R.id.action_loginFragment_to_dashboardFragment);
//    }
}
