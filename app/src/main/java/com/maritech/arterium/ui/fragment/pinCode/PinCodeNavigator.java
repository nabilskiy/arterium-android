package com.maritech.arterium.ui.fragment.pinCode;

import androidx.navigation.NavController;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseNavigator;

public class PinCodeNavigator extends BaseNavigator {

    void goToDashboardAfterEnterPin(NavController navController) {
        navController.navigate(R.id.action_pinCodeFragment_to_navigation_dashboard);
    }
}




