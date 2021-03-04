package com.maritech.arterium.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.maritech.arterium.ui.dashboard.doctor.DashboardFragment;
import com.maritech.arterium.ui.my_profile_doctor.MyProfileDoctorFragment;
import com.maritech.arterium.ui.notifications.NotificationsFragment;
import com.maritech.arterium.ui.statistics.StatFragment;

public class MainFragmentAdapter extends FragmentStateAdapter {

    public MainFragmentAdapter(@NonNull FragmentManager fragmentManager,
                               @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new StatFragment();
            case 2:
                return new NotificationsFragment();
            case 3:
                return new MyProfileDoctorFragment();
            default:
                return new DashboardFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

}