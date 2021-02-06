package com.maritech.arterium.ui.splash;

import androidx.navigation.NavController;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseNavigator;

public class LoginSplashNavigator extends BaseNavigator {

    void goToLogin(NavController navController) {
        navController.navigate(R.id.action_loginSplashFragment_to_loginFragment);
    }

    void goToPin(NavController navController) {
        navController.navigate(R.id.action_loginFragment_to_pinCodeFragment);
    }

    void goToDoctorDashboard(NavController navController) {
        navController.navigate(R.id.action_loginFragment_to_dashboardFragment);
    }
}
