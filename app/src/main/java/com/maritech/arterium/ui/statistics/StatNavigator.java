package com.maritech.arterium.ui.statistics;

import androidx.navigation.NavController;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseNavigator;

public class StatNavigator  extends BaseNavigator {

    void bottomGoToDashboardDoctor(NavController navController) {
        navController.navigate(R.id.action_navigation_statistics_to_dashboardFragment);
    }

    void bottomGoToMyProfileDoctor(NavController navController) {
        navController.navigate(R.id.action_navigation_statistics_to_myProfileDoctorFragment);
    }

    void bottomGoToStat(NavController navController) {
        navController.navigate(R.id.action_navigation_statistics_self);
    }

    void bottomGoToAchievements(NavController navController) {
        navController.navigate(R.id.action_navigation_statistics_to_achievementsFragment);
    }
}
