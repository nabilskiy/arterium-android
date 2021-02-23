package com.maritech.arterium.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import com.maritech.arterium.R;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.ui.MainActivity;
import com.maritech.arterium.ui.base.BaseActivity;
import com.maritech.arterium.ui.login.LoginActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Pref.getInstance().setDeviceUUID(this, "383b5b4f-dbe9-439c-a6ea-88e73f81b25f");
//        Pref.getInstance().setAuthToken(getActivity(), "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiYTQ5NzI0ZGEyYmZlZjZhOWE1MDRhODZhYzQwMjEwZTFkOGM0ODQ0MGQ2NmQ4YWJkNTBmMTAwNmQzNTQ4ZDc4YjQwNmMwNjU4YzUzNDJjMDUiLCJpYXQiOiIxNjEyNzM5MzU1Ljg1NDg2NSIsIm5iZiI6IjE2MTI3MzkzNTUuODU0ODY5IiwiZXhwIjoiMTY0NDI3NTM1NS44NTE2MTYiLCJzdWIiOiI1OCIsInNjb3BlcyI6W119.YK-hPXZ3_TnA4D3N3o-RWbokEBZEoXOi5P65L4C6nowqB41MYeg4rrbbJWXKHR1yxGhjUGECIU505MUubOeKc_QAQjg2HNsmS915wfFJnyf_b1-A2bic5JhppOu4t7zOzJhJ7rgG02_ccCbZMsvE0GVh3R4y8JcGSLBvFaNXy3T7TnZtbRcDttKPkMq_b-VxZrc0T0cDio4mW_PQ1WyubImBjBeafnRFOFBMoUSHvLibbABMKBUlZzLRVsC_FsJgRo2eOyQCBb_XzgMuRx5CyUWy0UCsUJ-pX-E0giy-S02_PweL5JnT7WYWgQ47RssLsl4as2dXYrjiUoZMMfUjDNzZmdki4d9Kqxb17eieV0gq62rMDj_ymylAbJz0qrXWku5pZeLNn1FKq72HphZvyiPkvYFfpbV0dIJlX18fOon65aW7YsMp-x6gP4Rfc515FKBXQ453UtbMuitQ8lYJq1xjVqzvIX9LqvxO8wgzAuLJQH1OujLg4cXEObvY6e0IRADyloxI2bySa6td4YFkfZkRP2RiUdkShOO1-oFTQMMqloegoYtG8mbQbqDD_SDdUcw235Ig4JyAIVvlR1_N3yEdVdYCSkOFGP-nb8zFbzJjmSteYWSaExqGtUYem77T1CO-7R5QAyOG3HLQ80JDzvpbUlSDFsVQr9DNwTW36PU");

        boolean isFirstLaunch =
                Pref.getInstance().isUserFirstLaunch(SplashActivity.this);

        Intent intent;
        if (isFirstLaunch) {
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        } else {
            intent = new Intent(SplashActivity.this, MainActivity.class);
        }
        startActivity(intent);
        finish();
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
