package com.maritech.arterium.ui.fragment.loginSplash;

import androidx.navigation.NavController;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseNavigator;

public class LoginSplashNavigator extends BaseNavigator {

    void goToLogin(NavController navController) {
        navController.navigate(R.id.action_loginSplashFragment_to_loginFragment);
    }
}
