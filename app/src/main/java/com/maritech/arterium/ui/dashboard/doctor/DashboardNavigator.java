package com.maritech.arterium.ui.dashboard.doctor;

import android.os.Bundle;

import androidx.navigation.NavController;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseNavigator;

public class DashboardNavigator extends BaseNavigator {

    void bottomGoToDashboardDoctor(NavController navController) {
        navController.navigate(R.id.action_navigation_dashboard_self);
    }

}
