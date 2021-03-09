package com.maritech.arterium.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;
import com.maritech.arterium.R;
import com.maritech.arterium.databinding.ActivityMainBinding;
import com.maritech.arterium.ui.base.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private NavController navController;

    private ActivityActionViewModel viewModel;
    private FirebaseViewModel firebaseViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ActivityActionViewModel.class);
        firebaseViewModel = new ViewModelProvider(this).get(FirebaseViewModel.class);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_host_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }

        observeViewModel();

        sendFirebaseToken();
    }

    private void observeViewModel() {
        firebaseViewModel.responseLiveData.observe(
                this,
                response -> Log.e("SEND FIREBASE TOKEN", "Send: " + response.isSuccess())
        );
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

    private void sendFirebaseToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        return;
                    }

                    String token = task.getResult();

                    JsonObject body = new JsonObject();
                    body.addProperty("fcm_token", token);

                    firebaseViewModel.sendFirebaseToken(body);
                });
    }
}