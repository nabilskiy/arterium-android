package com.maritech.arterium.ui.add_new_personal;

import androidx.navigation.NavController;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseNavigator;

class AddNewPersonalNavigator extends BaseNavigator {

    void goToDashboard(NavController navController) {
        navController.navigate(R.id.action_addNewPersonalFragment_to_dashboardFragment);
    }

}