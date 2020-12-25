package com.maritech.arterium.ui.dashboard;

import androidx.navigation.NavController;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseNavigator;

public class DashboardNavigator extends BaseNavigator {

    void addNewPersonal(NavController navController) {
        navController.navigate(R.id.action_dashboardFragment_to_addNewPersonalFragment);
    }

    void bottomGoToDashboardDoctor(NavController navController) {
        navController.navigate(R.id.action_dashboardFragment_self);
    }

    void bottomGoToMyProfileDoctor(NavController navController) {
        navController.navigate(R.id.action_dashboardFragment_to_myProfileDoctorFragment);
    }
    void bottomGoToStat(NavController navController) {
        navController.navigate(R.id.action_dashboardFragment_to_navigation_statistics);
    }

    void bottomGoToAchievements(NavController navController) {
        navController.navigate(R.id.action_dashboardFragment_to_achievementsFragment);
    }
}
