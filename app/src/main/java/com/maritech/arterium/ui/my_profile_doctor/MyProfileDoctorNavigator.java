package com.maritech.arterium.ui.my_profile_doctor;

import androidx.navigation.NavController;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseNavigator;

public class MyProfileDoctorNavigator extends BaseNavigator {

    void goToSettings(NavController navController) {
        navController.navigate(R.id.action_myProfileDoctorFragment_to_settingsFragment);
    }

    void goToMap(NavController navController) {
        navController.navigate(R.id.action_myProfileDoctorFragment_to_pharmacyFragment);
    }

    void goToAchievements(NavController navController) {
        navController.navigate(R.id.action_profile_to_achievementsFragment);
    }

}
