package com.maritech.arterium.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.maritech.arterium.ui.achievements.AchievementsFragment;
import com.maritech.arterium.ui.dashboardDoctor.DashboardFragment;
import com.maritech.arterium.ui.my_profile_doctor.MyProfileDoctorFragment;
import com.maritech.arterium.ui.statistics.StatFragment;

import org.jetbrains.annotations.NotNull;

public class MainFragmentAdapter extends FragmentStateAdapter {

    public MainFragmentAdapter(@NonNull FragmentManager fragmentManager,
                               @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);

        registerFragmentTransactionCallback(new FragmentTransactionCallback() {
            @NonNull
            @NotNull
            @Override
            public OnPostEventListener onFragmentMaxLifecyclePreUpdated(@NonNull @NotNull Fragment fragment, @NonNull @NotNull Lifecycle.State maxLifecycleState) {
                if (maxLifecycleState == Lifecycle.State.RESUMED) {
                    return () -> {
                        fragment.getParentFragmentManager()
                                .beginTransaction().commitNow();
                        fragment.getParentFragmentManager()
                                .beginTransaction().setPrimaryNavigationFragment(fragment);
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
                return new StatFragment();
            case 2:
                return new AchievementsFragment();
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