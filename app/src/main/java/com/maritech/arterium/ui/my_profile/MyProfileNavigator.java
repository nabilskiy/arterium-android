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
}