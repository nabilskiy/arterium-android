package com.maritech.arterium.ui.achievements;

import androidx.navigation.NavController;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseNavigator;

public class AchievementsNavigator  extends BaseNavigator {

    void goToDashboard(NavController navController) {
        navController.navigate(R.id.action_achievementsFragment_to_dashboardFragment);
    }
}
