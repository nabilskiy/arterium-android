package com.maritech.arterium.ui.dashboard.doctor.levels.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.maritech.arterium.ui.dashboard.doctor.levels.LevelFragment;

import java.util.List;

public class LevelsContainerAdapter extends FragmentStateAdapter {

    private final List<Integer> programIdList;

    public LevelsContainerAdapter(@NonNull FragmentActivity fragmentActivity,
                                  List<Integer> programIdList) {
        super(fragmentActivity);
        this.programIdList = programIdList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return LevelFragment.getInstance(programIdList.get(position));
    }

    @Override
    public int getItemCount() {
        return programIdList.size();
    }
}