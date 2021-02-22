package com.maritech.arterium.ui.patients.add_new_personal;

import androidx.navigation.NavController;
import com.maritech.arterium.ui.base.BaseNavigator;

class AddNewPersonalNavigator extends BaseNavigator {

    void goToDashboard(NavController navController) {
        navController.navigateUp();
    }

}