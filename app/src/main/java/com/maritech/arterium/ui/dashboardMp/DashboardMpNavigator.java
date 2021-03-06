package com.maritech.arterium.ui.dashboardMp;

import androidx.navigation.NavController;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseNavigator;

public class DashboardMpNavigator extends BaseNavigator {

    public void goToDashboard(NavController navController) {
        navController.navigate(R.id.action_navigation_dashboard_self);
    }
    public void goToMyProfile(NavController navController) {
        navController.navigate(R.id.action_navigation_dashboard_to_myProfileFragment);
    }
}
