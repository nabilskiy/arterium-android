package com.maritech.arterium.ui.pharmacies.list.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.maritech.arterium.ui.pharmacies.list.PharmacyListFragment;
import com.maritech.arterium.ui.pharmacies.map.PharmacyMapFragment;

public class PharmaciesPagerAdapter extends FragmentStateAdapter {

    public PharmaciesPagerAdapter(@NonNull FragmentManager fragmentManager,
                                  @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new PharmacyListFragment();
        }
        return new PharmacyMapFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}