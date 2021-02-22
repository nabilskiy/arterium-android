package com.maritech.arterium.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.maritech.arterium.ui.achievements.AchievementsNavigationFragment;
import com.maritech.arterium.ui.dashboardDoctor.MainNavigationFragment;
import com.maritech.arterium.ui.my_profile_doctor.ProfileNavigationFragment;
import com.maritech.arterium.ui.statistics.StatisticsNavigationFragment;

public class ActivityFragmentStateAdapter extends FragmentStateAdapter {

    public ActivityFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

        registerFragmentTransactionCallback(new FragmentTransactionCallback() {
            @NonNull
            @Override
            public OnPostEventListener onFragmentMaxLifecyclePreUpdated(@NonNull Fragment fragment,
                                                                        @NonNull Lifecycle.State maxLifecycleState) {

                if (maxLifecycleState == Lifecycle.State.RESUMED) {
                    return () -> {
                        FragmentTransaction transaction =
                                fragment.getParentFragmentManager().beginTransaction();
                        transaction.setPrimaryNavigationFragment(fragment);
                        transaction.commitNow();
                    };

                }
                return super.onFragmentMaxLifecyclePreUpdated(fragment, maxLifecycleState);
            }
        });
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
