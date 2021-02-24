package com.maritech.arterium.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.maritech.arterium.R;
import com.maritech.arterium.databinding.FragmentMainContainerBinding;
import com.maritech.arterium.ui.base.BaseFragment;

public class MainContainerFragment
        extends BaseFragment<FragmentMainContainerBinding> {

    private ActivityActionViewModel viewModel;
    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;

    private MainFragmentAdapter pagerAdapter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_main_container;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ActivityActionViewModel.class);
        viewModel.onBackPress.observe(lifecycleOwner, onBackPressObserver);
        viewModel.onRecreate.observe(lifecycleOwner, onRecreateObserver);

        bottomNavigationView = binding.bottomNav;
        viewPager2 = binding.viewPager;

        viewPager2.setUserInputEnabled(false);
        viewPager2.setOffscreenPageLimit(4);

        pagerAdapter = new MainFragmentAdapter(
                getChildFragmentManager(), lifecycleOwner.getLifecycle()
        );

        viewPager2.setAdapter(pagerAdapter);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.dashboard) {
                navigatePager(0);
                return true;
            } else if (item.getItemId() == R.id.statistics) {
                navigatePager(1);
                return true;
            } else if (item.getItemId() == R.id.notifications) {
                navigatePager(2);
                return true;
            } else if (item.getItemId() == R.id.profile) {
                navigatePager(3);
                return true;
            }

            return false;
        });
    }

    private void navigatePager(int position) {
//        bottomNavigationView.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
        viewPager2.setCurrentItem(position, false);
    }

    private final Observer<Boolean> onBackPressObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean aBoolean) {
            if (aBoolean) {
                if (viewPager2.getCurrentItem() == 0) {
                    requireActivity().onBackPressed();
                } else {
                    bottomNavigationView.setSelectedItemId(R.id.dashboard);
                    viewModel.onBackPress.setValue(false);
                }
            }
        }
    };

    private final Observer<Boolean> onRecreateObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean aBoolean) {
            if (aBoolean) {
                viewPager2.setAdapter(pagerAdapter);
                actionViewModel.onRecreate.setValue(false);
            }
        }
    };
}