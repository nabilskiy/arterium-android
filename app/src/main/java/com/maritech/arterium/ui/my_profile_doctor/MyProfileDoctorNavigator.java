package com.maritech.arterium.ui.my_profile_doctor;

import androidx.navigation.NavController;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseNavigator;

public class MyProfileDoctorNavigator extends BaseNavigator {

    void goToSettings(NavController navController) {
        navController.navigate(R.id.action_myProfileDoctorFragment_to_settingsFragment);
    }

    void goPatientCard(NavController navController) {
        navController.navigate(R.id.action_myProfileDoctorFragment_to_patientCardFragment);
    }

    void goToMap(NavController navController) {
        navController.navigate(R.id.action_myProfileDoctorFragment_to_pharmacyFragment);
    }

    void goToNotifications(NavController navController) {
        navController.navigate(R.id.action_myProfileDoctorFragment_to_navigation_notifications);
    }

}
