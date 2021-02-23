package com.maritech.arterium.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.maritech.arterium.R;
import com.maritech.arterium.databinding.ActivityMain2Binding;

import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private NavController navController;
    AppBarConfiguration appBarConfiguration;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);


        navController = Navigation.findNavController(this, R.id.main_host_fragment);
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
}