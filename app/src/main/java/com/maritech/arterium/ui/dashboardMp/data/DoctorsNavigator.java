package com.maritech.arterium.ui.dashboardMp.data;

import androidx.navigation.NavController;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseNavigator;

public class DoctorsNavigator extends BaseNavigator {

    public void goToDashboardMp(NavController navController) {
        navController.navigate(R.id.action_dashboardMpFragment_to_dashboardFragment);
    }

    public void goToDashboardRp(NavController navController) {
        navController.navigate(R.id.action_dashboardRmFragment_to_dashboardFragment);
    }
}
