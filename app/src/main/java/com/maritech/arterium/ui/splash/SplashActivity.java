package com.maritech.arterium.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.maritech.arterium.R;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.databinding.ActivitySplashBinding;
import com.maritech.arterium.ui.MainActivity;
import com.maritech.arterium.ui.base.BaseActivity;
import com.maritech.arterium.ui.login.LoginActivity;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isFirstLaunch =
                Pref.getInstance().isUserFirstLaunch(SplashActivity.this);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent;
            if (isFirstLaunch) {
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, MainActivity.class);
            }
            startActivity(intent);
            finish();
        },  1000);
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
