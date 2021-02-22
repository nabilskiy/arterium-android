package com.maritech.arterium.utils;

import android.content.Intent;
import android.util.SparseArray;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.maritech.arterium.R;

import java.util.List;

public class NavigationExtensions {
    private String selectedItemTag;
    private boolean isOnFirstFragment;
    private MutableLiveData<NavController> selectedNavController = new MutableLiveData<>();
    private int firstFragmentGraphId = 0;
    private SparseArray<String> graphIdToTagMap = new SparseArray<>();

    public LiveData<NavController> setupWithNavController(final BottomNavigationView navView,
                                                          List<Integer> navGraphIds,
                                                          final FragmentManager fragmentManager,
                                                          int containerId,
                                                          Intent intent) {

        for (Integer navGraphId : navGraphIds) {
            String fragmentTag = getFragmentTag(navGraphIds.indexOf(navGraphId));
            NavHostFragment navHostFragment = obtainNavHostFragment(
                    fragmentManager,
                    fragmentTag,
                    navGraphId,
                    containerId
            );
            int graphId = navHostFragment.getNavController().getGraph().getId();

            if (navGraphIds.indexOf(navGraphId) == 0) {
                firstFragmentGraphId = graphId;
            }

            graphIdToTagMap.append(graphId, fragmentTag);

            if (navView.getSelectedItemId() == graphId) {
                selectedNavController.postValue(navHostFragment.getNavController());

                attachNavHostFragment(fragmentManager,
                        navHostFragment, navGraphIds.indexOf(navGraphId) == 0);

            } else {
                detachNavHostFragment(fragmentManager, navHostFragment);
            }
        }

        selectedItemTag = graphIdToTagMap.get(navView.getSelectedItemId());
        final String firstFragmentTag = graphIdToTagMap.get(firstFragmentGraphId);
        isOnFirstFragment = selectedItemTag.equals(firstFragmentTag);

        navView.setOnNavigationItemSelectedListener(item -> {
            if (fragmentManager.isStateSaved()) {
                return false;
            } else {
                final String newlySelectedItemTag = graphIdToTagMap.get(item.getItemId());
                if (!selectedItemTag.equals(newlySelectedItemTag)) {

                    fragmentManager.popBackStack(firstFragmentTag,
                            FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    NavHostFragment selectedFragment = (NavHostFragment)
                            fragmentManager.findFragmentByTag(newlySelectedItemTag);

                    if (!firstFragmentTag.equals(newlySelectedItemTag) &&
                            selectedFragment != null) {

                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                                .setCustomAnimations(
                                        R.anim.nav_default_enter_anim,
                                        R.anim.nav_default_exit_anim,
                                        R.anim.nav_default_pop_enter_anim,
                                        R.anim.nav_default_pop_exit_anim)
                                .attach(selectedFragment)
                                .setPrimaryNavigationFragment(selectedFragment);

                        for (int i = 0; i < graphIdToTagMap.size(); i++) {
                            if (!graphIdToTagMap.valueAt(i).equals(newlySelectedItemTag)) {
                                if (fragmentManager.findFragmentByTag(firstFragmentTag) != null) {
                                    Fragment fragment =
                                            fragmentManager.findFragmentByTag(firstFragmentTag);

                                    if (fragment != null) {
                                        fragmentTransaction.detach(fragment);
                                    }
                                }
                            }
                        }
                        fragmentTransaction
                                .addToBackStack(firstFragmentTag)
                                .setReorderingAllowed(true)
                                .commit();
                    }
                    selectedItemTag = newlySelectedItemTag;
                    isOnFirstFragment = selectedItemTag.equals(firstFragmentTag);

                    if (selectedFragment != null) {
                        selectedNavController.postValue(selectedFragment.getNavController());
                    }
                    return true;
                } else return false;

            }
        });

        setupItemReselected(navView, fragmentManager);

        setupDeepLinks(navView, navGraphIds, fragmentManager, containerId, intent);

        fragmentManager.addOnBackStackChangedListener(() -> {
            if (!isOnFirstFragment && !isOnBackStack(fragmentManager, firstFragmentTag)) {
                navView.setSelectedItemId(firstFragmentGraphId);
            }

            NavController controller = selectedNavController.getValue();
            if (controller != null && controller.getCurrentDestination() == null) {
                controller.navigate(controller.getGraph().getId());
            }

        });

        return selectedNavController;
    }

    private void setupDeepLinks(BottomNavigationView navView,
                                List<Integer> navGraphIds,
                                FragmentManager fragmentManager,
                                int containerId,
                                Intent intent) {

        for (int index = 0; index < navGraphIds.size(); index++) {
            int navGraphId = navGraphIds.get(index);
            String fragmentTag = getFragmentTag(navGraphIds.indexOf(navGraphId));

            NavHostFragment navHostFragment = obtainNavHostFragment(
                    fragmentManager,
                    fragmentTag,
                    navGraphId,
                    containerId
            );

            if (navHostFragment.getNavController().handleDeepLink(intent)
                    && navView.getSelectedItemId() !=
                    navHostFragment.getNavController().getGraph().getId()) {

                navView.setSelectedItemId(navHostFragment.getNavController().getGraph().getId());
            }
        }
    }

    private void setupItemReselected(BottomNavigationView navView,
                                     FragmentManager fragmentManager) {
        navView.setOnNavigationItemReselectedListener(item -> {
            String newlySelectedItemTag = graphIdToTagMap.get(item.getItemId());
            NavHostFragment selectedFragment =
                    (NavHostFragment) fragmentManager.findFragmentByTag(newlySelectedItemTag);

            if (selectedFragment != null) {
                NavController navController = selectedFragment.getNavController();

                navController.popBackStack(
                        navController.getGraph().getStartDestination(), false
                );
            }
        });
    }

    private NavHostFragment obtainNavHostFragment(FragmentManager fragmentManager,
                                                  String fragmentTag,
                                                  int navGraphId,
                                                  int containerId) {
        if (fragmentManager.findFragmentByTag(fragmentTag) instanceof NavHostFragment) {
            return (NavHostFragment) fragmentManager.findFragmentByTag(fragmentTag);
        }

        NavHostFragment navHostFragment = NavHostFragment.create(navGraphId);
        fragmentManager.beginTransaction()
                .add(containerId, navHostFragment, fragmentTag)
                .commitNow();
        return navHostFragment;
    }

    private void attachNavHostFragment(FragmentManager fragmentManager,
                                       NavHostFragment navHostFragment,
                                       boolean isPrimaryNavFragment) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.attach(navHostFragment);
        if (isPrimaryNavFragment) {
            fragmentTransaction.setPrimaryNavigationFragment(navHostFragment);
        }
        fragmentTransaction.commitNow();
    }

    private void detachNavHostFragment(FragmentManager fragmentManager,
                                       NavHostFragment navHostFragment) {
        
        fragmentManager.beginTransaction()
                .detach(navHostFragment)
                .commitNow();
    }

    private boolean isOnBackStack(FragmentManager fragmentManager, String backStackName) {
        int backStackCount = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            String name = fragmentManager.getBackStackEntryAt(i).getName();

            if (name != null && name.equals(backStackName)) {
                return true;
            }
        }
        return false;
    }

    private String getFragmentTag(int index) {
        return "bottomNavigation#" + index;
    }
}