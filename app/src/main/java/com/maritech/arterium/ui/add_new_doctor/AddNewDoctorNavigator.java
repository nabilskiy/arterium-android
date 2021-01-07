package com.maritech.arterium.ui.add_new_doctor;

import androidx.navigation.NavController;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseNavigator;

public class AddNewDoctorNavigator extends BaseNavigator {

    public void goAddMp(NavController navController) {
        navController.navigate(R.id.action_addNewDoctorFragment_to_chooseMpFragment);
    }

}