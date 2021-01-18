package com.maritech.arterium.ui.achievements;

import android.os.Bundle;

import androidx.navigation.NavController;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseNavigator;

public class AchievementsNavigator  extends BaseNavigator {

    public void goToAchievementDetails(NavController navController) {
        navController.navigate(R.id.action_achievementsFragment_to_achievementsDetailsFragment);
    }

}
