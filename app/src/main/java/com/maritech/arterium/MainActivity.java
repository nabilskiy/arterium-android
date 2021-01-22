package com.maritech.arterium;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.maritech.arterium.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {
   public BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        this.onRestart();
        setStatusBarGradientDrawable(this, R.drawable.gradient_primary);
    }

    void changeTheme(int theme) {
        switch (theme) {
            case 0:
                setTheme(R.style.MyNoActionBarShadowTheme);
                break;
            case 1:
                setTheme(R.style.MyNoActionBarShadowThemeRed);
                break;
            case 2:
                setTheme(R.style.MyNoActionBarShadowThemeBlue);
                break;
        }
    }


}