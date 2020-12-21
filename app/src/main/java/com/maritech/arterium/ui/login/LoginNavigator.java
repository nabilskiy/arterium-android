package com.maritech.arterium.ui.login;

import androidx.navigation.NavController;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseNavigator;

public class LoginNavigator extends BaseNavigator {

    void goToPin(NavController navController) {
        navController.navigate(R.id.action_loginFragment_to_pinCodeFragment);
    }
}

