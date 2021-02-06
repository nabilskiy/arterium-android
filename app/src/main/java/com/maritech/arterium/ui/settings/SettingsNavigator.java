package com.maritech.arterium.ui.settings;

import androidx.navigation.NavController;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseNavigator;

public class SettingsNavigator extends BaseNavigator {

    void goToChangeLanguage(NavController navController) {
        navController.navigate(R.id.action_settingsFragment_to_interfaceNameFragment);
    }
    
}