package com.maritech.arterium.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private NavController navController;

    private ActivityActionViewModel viewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ActivityActionViewModel.class);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_host_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }

//        navController = Navigation.findNavController(this, R.id.main_host_fragment);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.main_host_fragment);

        if (navHostFragment != null) {
            FragmentManager navHostChildFragmentManager = navHostFragment.getChildFragmentManager();

            int backStackEntryCount = navHostChildFragmentManager.getBackStackEntryCount();
//            List<Fragment> fragments = navHostChildFragmentManager.getFragments();
//            int fragmentCount = fragments.size();

            if (backStackEntryCount > 0) {
                navController.navigateUp();
            } else {
                if (viewModel.onBackPress.getValue() != null && !viewModel.onBackPress.getValue()) {
                    viewModel.onBackPress.setValue(true);
                } else {
                    super.onBackPressed();
                }
            }

        } else {
            super.onBackPressed();
        }
    }
}