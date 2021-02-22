package com.maritech.arterium.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.maritech.arterium.R;
import com.maritech.arterium.databinding.ActivityMain2Binding;
import com.maritech.arterium.utils.ActivityFragmentStateAdapter;

public class MainActivity2 extends AppCompatActivity {

    private AppbarViewModel appbarViewModel;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appbarViewModel = new ViewModelProvider(this).get(AppbarViewModel.class);

        ActivityMain2Binding dataBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_main2);

        appbarViewModel.getCurrentNavController().observe(this, navControllerEvent -> {
            if (navControllerEvent != null) {
                NavController navController = navControllerEvent.getContentIfNotHandled();
                if (navController != null) {
                    AppBarConfiguration appBarConfiguration =
                            new AppBarConfiguration.Builder(navController.getGraph()).build();

                }
            }
        });
    }

}