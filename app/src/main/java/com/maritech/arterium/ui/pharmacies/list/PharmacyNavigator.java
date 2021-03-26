package com.maritech.arterium.ui.pharmacies.list;

import android.view.View;
import androidx.navigation.Navigation;
import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseNavigator;

public class PharmacyNavigator extends BaseNavigator {

    public void goToMap(View navController) {
        try {
            Navigation.findNavController(navController).navigate(R.id.action_pharmacyListFragment_to_mapFragment2);
        } catch (Exception e) {

        }
    }

    public void goToPharmacyList(View navController) {
        try {
            Navigation.findNavController(navController).navigate(R.id.action_mapFragment2_to_pharmacyListFragment);
        } catch (Exception e) {

        }
    }
}
