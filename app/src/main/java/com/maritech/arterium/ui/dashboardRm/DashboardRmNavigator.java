package com.maritech.arterium.ui.dashboardRm;

import androidx.navigation.NavController;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseNavigator;

public class DashboardRmNavigator extends BaseNavigator {

    public void goToDashboardRm(NavController navController) {
        navController.navigate(R.id.action_dashboardRmFragment_self);
    }

    public void goToMyProfile(NavController navController) {
        navController.navigate(R.id.action_dashboardRmFragment_to_myProfileFragment);
    }

    public void goToAddNewPersonal(NavController navController) {
        navController.navigate(R.id.action_dashboardRmFragment_to_addNewPersonalFragment);
    }

}
