package com.maritech.arterium.ui.dashboardDoctor;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.maritech.arterium.R;
import com.maritech.arterium.databinding.NavigationMainBinding;
import com.maritech.arterium.ui.AppbarViewModel;
import com.maritech.arterium.ui.BaseFragment;
import com.maritech.arterium.utils.Event;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MainNavigationFragment
        extends BaseFragment<NavigationMainBinding> {

    @Override
    public int getLayoutRes() {
        return R.layout.navigation_main;
    }

    private AppbarViewModel appbarViewModel;
    private NavController navController;

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appbarViewModel = new ViewModelProvider(requireActivity()).get(AppbarViewModel.class);

        NavHostFragment navHostFragment =
                (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        Log.e("Navigation", "onViewCreated");
        listenOnBackPressed();
    }

    private void listenOnBackPressed() {
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    @Override
    public void onResume() {
        super.onResume();

        if (navController != null) {
            appbarViewModel.getCurrentNavController().setValue(new Event(navController));
        }

        Log.e("Navigation", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.e("Navigation", "onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Log.e("Navigation", "onDestroyView");
    }
}