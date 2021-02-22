package com.maritech.arterium.ui.achievements;

import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.maritech.arterium.R;
import com.maritech.arterium.databinding.NavigationAchievementsBinding;
import com.maritech.arterium.ui.AppbarViewModel;
import com.maritech.arterium.ui.BaseFragment;
import com.maritech.arterium.utils.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AchievementsNavigationFragment
        extends BaseFragment<NavigationAchievementsBinding> {

    @Override
    public int getLayoutRes() {
        return R.layout.navigation_achievements;
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
    }
}