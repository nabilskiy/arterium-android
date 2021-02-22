package com.maritech.arterium.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.maritech.arterium.ui.achievements.AchievementsNavigationFragment;
import com.maritech.arterium.ui.dashboardDoctor.MainNavigationFragment;
import com.maritech.arterium.ui.my_profile_doctor.ProfileNavigationFragment;
import com.maritech.arterium.ui.statistics.StatisticsNavigationFragment;

public class ViewPagerFragmentAdapter extends FragmentStateAdapter {

    public ViewPagerFragmentAdapter(@NonNull FragmentManager fragmentManager,
                                    @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new StatisticsNavigationFragment();
            case 2:
                return new AchievementsNavigationFragment();
            case 3:
                return new ProfileNavigationFragment();
            default:
                return new MainNavigationFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}