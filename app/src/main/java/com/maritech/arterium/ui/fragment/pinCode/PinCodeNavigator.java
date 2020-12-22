package com.maritech.arterium.ui.fragment.pinCode;

import androidx.navigation.NavController;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseNavigator;

public class PinCodeNavigator extends BaseNavigator {

    void goToDashboardMpAfterEnterPin(NavController navController) {
        navController.navigate(R.id.action_pinCodeFragment_to_dashboardMpFragment);
    }

    void goToDashboardRmAfterEnterPin(NavController navController) {
        navController.navigate(R.id.action_pinCodeFragment_to_dashboardRmFragment);
    }




}




