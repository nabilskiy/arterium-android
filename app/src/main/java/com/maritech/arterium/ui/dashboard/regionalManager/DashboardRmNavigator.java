package com.maritech.arterium.ui.dashboard.regionalManager;

import android.os.Bundle;

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

    public void goToAddNewMp(NavController navController) {
        navController.navigate(R.id.action_dashboardRmFragment_to_addNewMpFragment);
    }
    public void goToAddNewDoctor(NavController navController) {
        navController.navigate(R.id.action_dashboardRmFragment_to_addNewDoctorFragment);
    }

    public void goToMPDashboard(NavController navController, Bundle bundle) {
        navController.navigate(R.id.action_dashboardRmFragment_to_dashboardFragment, bundle);
    }

}
