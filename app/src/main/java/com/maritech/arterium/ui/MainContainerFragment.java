package com.maritech.arterium.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.maritech.arterium.R;
import com.maritech.arterium.common.UserType;
import com.maritech.arterium.databinding.FragmentMainContainerBinding;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.patients.PatientsSharedViewModel;
import com.maritech.arterium.ui.patients.add_new_personal.AddNewPersonalActivity;

public class MainContainerFragment
        extends BaseFragment<FragmentMainContainerBinding> {

    private static final String TAG = MainContainerFragment.class.getName();

    private PatientsSharedViewModel sharedViewModel;
    private ActivityActionViewModel viewModel;
    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;
    private String role = "doctor";

    private FragmentStateAdapter pagerAdapter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_main_container;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        sharedViewModel = new ViewModelProvider(requireActivity()).get(PatientsSharedViewModel.class);
        viewModel = new ViewModelProvider(requireActivity()).get(ActivityActionViewModel.class);
        viewModel.onBackPress.observe(lifecycleOwner, onBackPressObserver);


        bottomNavigationView = binding.bottomNav;
        viewPager2 = binding.viewPager;



        if(getArguments() != null &&
                getArguments().containsKey("role")){
            role = getArguments().getString("role");
            setRole();
        }
    }

    private void setRole() {
        Log.i(TAG, "setRole: " + role);
        if(role.equals(UserType.DOCTOR.toString()))
            setDoctorRole();
        else if(role.equals(UserType.REGIONAL.toString()))
            setRMRole();
        else if(role.equals(UserType.MEDICAL.toString()))
            setMPRole();
        else setViewOnlyDoctorRole();
    }

    private void setDoctorRole() {
        Log.i(TAG, "setDoctorRole: ");
        viewPager2.setUserInputEnabled(false);
        viewPager2.setOffscreenPageLimit(4);

        pagerAdapter = new DoctorRoleAdapter(
                getChildFragmentManager(), lifecycleOwner.getLifecycle()
        );
        bottomNavigationView.inflateMenu(R.menu.bottom_nav_menu_doctor);
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
        binding.addFab.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), AddNewPersonalActivity.class);
            startActivityForResult(intent, AddNewPersonalActivity.PATIENT_REQUEST_CODE);
        });
        viewPager2.setAdapter(pagerAdapter);
    }

    private void setRMRole() {
        Log.i(TAG, "setRMRole: ");
        viewPager2.setUserInputEnabled(false);
        viewPager2.setOffscreenPageLimit(2);

        pagerAdapter = new RMRoleAdapter(getChildFragmentManager(),
                lifecycleOwner.getLifecycle());

        bottomNavigationView.inflateMenu(R.menu.bottom_nav_menu_rm_mp);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if(item.getItemId() == R.id.dashboard){
                navigatePager(0);
            } else {
                navigatePager(1);
            }
            return true;
        });
        binding.addFab.setVisibility(View.GONE);
        viewPager2.setAdapter(pagerAdapter);

    }

    private void setMPRole() {
        Log.i(TAG, "setMPRole: ");
        viewPager2.setUserInputEnabled(false);
        viewPager2.setOffscreenPageLimit(2);

        pagerAdapter = new RMRoleAdapter(getChildFragmentManager(),
                lifecycleOwner.getLifecycle());

        bottomNavigationView.inflateMenu(R.menu.bottom_nav_menu_rm_mp);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if(item.getItemId() == R.id.dashboard){
                navigatePager(0);
            } else {
                navigatePager(1);
            }
            return true;
        });
        binding.addFab.setVisibility(View.GONE);
        viewPager2.setAdapter(pagerAdapter);
    }

    private void setViewOnlyDoctorRole() {
        Log.i(TAG, "setViewOnlyDoctorRole: ");
    }


    private void navigatePager(int position) {
//        bottomNavigationView.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
        viewPager2.setCurrentItem(position, false);
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AddNewPersonalActivity.PATIENT_REQUEST_CODE) {
                sharedViewModel.reload.setValue(true);
            }
        }
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


}