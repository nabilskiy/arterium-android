package com.maritech.arterium.ui.base;

import android.content.res.Resources;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;

import com.maritech.arterium.R;

import java.lang.ref.WeakReference;


public class BaseNavigator {


//    Resources resources = Resources.getSystem();
//    Resources res = getResources();

    WeakReference<FragmentActivity> activity;

    void attach(FragmentActivity activity) {
        this.activity = new WeakReference(activity);
    }

    void release() {
        this.activity = new WeakReference<FragmentActivity>(null);
    }

//    void goToLogin(NavController navHostController) {
//        navHostController.navigate(R.id.)
//    }
//
//
//    void goToProfile(NavController navHostController) {
//        navHostController.navigate(resources.R.id.action_bottomTabNavigator_to_profileFragment)
//    }

    void back() {
        this.activity.get().onBackPressed();
    }
}