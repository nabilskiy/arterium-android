package com.maritech.arterium.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import com.maritech.arterium.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    AppBarConfiguration appBarConfiguration;

    ActivityActionViewModel viewModel;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ActivityActionViewModel.class);

        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_host_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }

//        navController = Navigation.findNavController(this, R.id.main_host_fragment);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();

        listenBackStackChange();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    private void listenBackStackChange() {
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_host_fragment);

        if (navHostFragment != null) {
            FragmentManager navHostChildFragmentManager = navHostFragment.getChildFragmentManager();
            navHostChildFragmentManager.addOnBackStackChangedListener(() -> {
                int backStackEntryCount = navHostChildFragmentManager.getBackStackEntryCount();
                List<Fragment> fragments = navHostChildFragmentManager.getFragments();
                int fragmentCount = fragments.size();

                Log.e("MainActivity", "Main graph backStackEntryCount: " + backStackEntryCount + " fragmentCount: " + fragmentCount + " fragments: " + fragments);
            });
        }
    }

    @Override
    public void onBackPressed() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.main_host_fragment);

        if (navHostFragment != null) {
            FragmentManager navHostChildFragmentManager = navHostFragment.getChildFragmentManager();

            int backStackEntryCount = navHostChildFragmentManager.getBackStackEntryCount();
            List<Fragment> fragments = navHostChildFragmentManager.getFragments();
            int fragmentCount = fragments.size();

            Log.e("MainActivity", "Main graph backStackEntryCount: " + backStackEntryCount + " fragmentCount: " + fragmentCount + " fragments: " + fragments);

            if (backStackEntryCount > 0) {
                navController.navigateUp();
            } else {
                if (fragmentCount == 1) {
                    viewModel.onBackPress.setValue(true);
                } else {
                    super.onBackPressed();
                }
            }

        } else {
            super.onBackPressed();
        }



//        int count = getSupportFragmentManager().getBackStackEntryCount();
//
//        if (count == 0) {
//            super.onBackPressed();
//        } else {
//            getSupportFragmentManager().popBackStack();
//        }

    }
}