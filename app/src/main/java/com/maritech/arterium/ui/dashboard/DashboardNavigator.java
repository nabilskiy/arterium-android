package com.maritech.arterium.ui.dashboard;

import androidx.navigation.NavController;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseNavigator;

public class DashboardNavigator extends BaseNavigator {

    void goToDashboard(NavController navController) {
        navController.navigate(R.id.action_achievementsFragment_to_navigation_dashboard);
    }
}
