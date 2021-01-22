package com.maritech.arterium.ui.add_new_mp;

import androidx.navigation.NavController;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseNavigator;

public class AddNewMpNavigator extends BaseNavigator {

    void goAddDoctor(NavController navController) {
        navController.navigate(R.id.action_addNewMpFragment_to_chooseDoctorFragment);
    }
    void goToDashboard(NavController navController) {
        navController.navigate(R.id.   action_addNewMpFragment_to_dashboardRmFragment);
    }


}
