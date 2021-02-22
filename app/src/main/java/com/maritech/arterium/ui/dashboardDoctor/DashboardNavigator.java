package com.maritech.arterium.ui.dashboardDoctor;

import android.os.Bundle;

import androidx.navigation.NavController;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseNavigator;

public class DashboardNavigator extends BaseNavigator {

    void addNewPersonal(NavController navController) {
        navController.navigate(R.id.action_dashboardFragment_to_addNewPersonalFragment);
    }

    void bottomGoToDashboardDoctor(NavController navController) {
        navController.navigate(R.id.action_navigation_dashboard_self);
    }

    void goToDashboardMP(NavController navController) {
        navController.navigate(R.id.action_dashboardFragment_to_navigation_dashboard);
    }

    public void goToPatientCard(NavController navController, Bundle bundle) {
        navController.navigate(R.id.action_dashboardFragment_to_patientCardFragment, bundle);
    }


}
