package com.maritech.arterium.ui;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.maritech.arterium.R;
import com.maritech.arterium.databinding.FragmentViewPagerBinding;
import com.maritech.arterium.utils.ActivityFragmentStateAdapter;

public class ViewPagerContainerFragment
        extends BaseFragment<FragmentViewPagerBinding> {

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_view_pager;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getDataBinding() != null) {
            ViewPager2 viewPager2 = getDataBinding().viewPager;
            BottomNavigationView bottomNavigationView = getDataBinding().bottomNav;

            viewPager2.setUserInputEnabled(false);
            viewPager2.setOffscreenPageLimit(4);

            viewPager2.setAdapter(new ActivityFragmentStateAdapter(requireActivity()));

            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    case R.id.dashboard:
                        viewPager2.setCurrentItem(0, false);
                        return true;
                    case R.id.statistics:
                        viewPager2.setCurrentItem(1, false);
                        return true;
                    case R.id.achievements:
                        viewPager2.setCurrentItem(2, false);
                        return true;
                    case R.id.profile:
                        viewPager2.setCurrentItem(3, false);
                        return true;
                }

                return false;
            });
        }

    }

}
