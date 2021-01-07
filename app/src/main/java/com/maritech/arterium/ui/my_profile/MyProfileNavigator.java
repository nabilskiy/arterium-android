package com.maritech.arterium.ui.my_profile;

import androidx.navigation.NavController;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseNavigator;

public class MyProfileNavigator extends BaseNavigator {

    void goToSettings(NavController navController) {
        navController.navigate(R.id.action_myProfileFragment_to_settingsFragment);
    }

    void goPatientCard(NavController navController) {
        navController.navigate(R.id.action_myProfileFragment_to_patientCardFragment);
    }

    void goToMap(NavController navController) {
        navController.navigate(R.id.action_global_pharmacyFragment);
    }

    public void goToDashboardMp(NavController navController) {
        navController.navigate(R.id.action_myProfileFragment_to_navigation_dashboard);
    }

    public void goToDashboardRm(NavController navController) {
        navController.navigate(R.id.action_myProfileFragment_to_dashboardRmFragment);
    }

    public void goToMyProfile(NavController navController) {
        navController.navigate(R.id.action_myProfileFragment_self);
    }

    void goToNotifications(NavController navController) {
        navController.navigate(R.id.action_myProfileFragment_to_navigation_notifications);
    }
}