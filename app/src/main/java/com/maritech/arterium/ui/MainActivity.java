package com.maritech.arterium.ui;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseActivity;
import com.maritech.arterium.utils.NavigationExtensions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private LiveData<NavController> currentNavController;

    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBottomNavigationBar();

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

    private void setupBottomNavigationBar() {
        navigationView = findViewById(R.id.bottom_nav_view);

        List<Integer> navGraphIds = new ArrayList<>();
        navGraphIds.add(R.navigation.dashboard_navigation);
        navGraphIds.add(R.navigation.statistic_navigation);
        navGraphIds.add(R.navigation.achivement_navigation);
        navGraphIds.add(R.navigation.profile_navigation);

        NavigationExtensions extensions = new NavigationExtensions();
        LiveData<NavController> controller = extensions.setupWithNavController(
                navigationView,
                navGraphIds,
                getSupportFragmentManager(),
                R.id.nav_host_fragment,
                getIntent()
        );

        controller.observe(this, navController -> {
//            NavigationUI.setupActionBarWithNavController(this, navController);
        });

        currentNavController = controller;
    }

    @Override
    public boolean onNavigateUp() {
        if (currentNavController != null) {
            if (currentNavController.getValue() != null) {
                return currentNavController.getValue().navigateUp();
            }
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        boolean backStack = currentNavController.getValue().popBackStack();

        if (!backStack) {
            super.onBackPressed();
        }
    }

}