package com.maritech.arterium.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;
import com.maritech.arterium.R;
import com.maritech.arterium.common.UserType;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.databinding.ActivityMainBinding;
import com.maritech.arterium.ui.base.BaseActivity;
import com.maritech.arterium.ui.login.LoginActivity;

import static com.maritech.arterium.ui.login.LoginActivity.BUNDLE_KEY;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private static final String TAG = MainActivity.class.getName();

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
        viewModel.onRecreate.observe(this, onRecreateObserver);

        String role;
        if (getIntent() != null && getIntent().getExtras() != null)
            role = getIntent().getExtras().getString(BUNDLE_KEY, UserType.DOCTOR.toString());
        else role = Pref.getInstance().getRole(this);

        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle == null) {
                Log.i(TAG, "onCreate: NULL");
            } else Log.i(TAG, "onCreate: " + bundle.getString(BUNDLE_KEY));
        }




        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_host_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            Bundle bundle = new Bundle();
            bundle.putString("role", role);
            navController.setGraph(navController.getGraph(), bundle);
        }

        observeViewModel();

        sendFirebaseToken();
    }

    public void openMpDashboardFromRM(Bundle bundle) {
        bundle.putString("role", UserType.VIEW_ONLY_MEDICAL.toString());
//        navController.setGraph(navController.getGraph(), bundle);
        navController.navigate(R.id.dashboard, bundle);
    }

    public void openDoctorMpDashboardFromMP(Bundle bundle) {
        bundle.putString("role", UserType.VIEW_ONLY_DOCTOR.toString());
        navController.navigate(R.id.mainFragment, bundle);
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

    private final Observer<Boolean> onRecreateObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean aBoolean) {
            if (aBoolean) {
                viewModel.onRecreate.setValue(false);
                recreate();
            }
        }
    };

    @Override
    public void onBackPressed() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_host_fragment);

        if (navHostFragment != null) {
            FragmentManager navHostChildFragmentManager = navHostFragment.getChildFragmentManager();

            int backStackEntryCount = navHostChildFragmentManager.getBackStackEntryCount();

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