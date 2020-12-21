package com.maritech.arterium;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.maritech.arterium.ui.base.BaseActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends BaseActivity {

    CharSequence currentFragment;
   public BottomNavigationView navView;
//    @Override
//    protected void onPause() {
//        super.onPause();
//        currentFragment = Navigation.findNavController(this, R.id.nav_host_fragment).getCurrentDestination().getLabel();
//        //isActivePin = currentFragment != "LoginFragment" && showPin
//        if ("LoginSplashFragment".contentEquals(currentFragment) || "loginFragment".contentEquals(currentFragment) || "PinCodeFragment".contentEquals(currentFragment) || "AchievementsFragment".contentEquals(currentFragment)){
//            navView.setVisibility(View.GONE);
//        }
//        else {
//            navView.setVisibility(View.VISIBLE);
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_dashboard, R.id.navigation_statistics, R.id.achievementsFragment,
//                R.id.myProfileFragment)
//                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

//        currentFragment = Navigation.findNavController(this, R.id.nav_host_fragment).getCurrentDestination().getLabel();
//        //isActivePin = currentFragment != "LoginFragment" && showPin
//        if ("LoginSplashFragment".contentEquals(currentFragment) || "loginFragment".contentEquals(currentFragment) || "PinCodeFragment".contentEquals(currentFragment) || "AchievementsFragment".contentEquals(currentFragment)){
//            navView.setVisibility(View.GONE);
//        }
//        else {
//            navView.setVisibility(View.VISIBLE);
//        }



    }


}