package com.maritech.arterium.ui.pharmacy_list;

import android.view.View;
import androidx.navigation.Navigation;
import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseNavigator;

public class PharmacyNavigator extends BaseNavigator {

    void goToMap (View navController) {
        Navigation.findNavController(navController).navigate(R.id.action_pharmacyListFragment_to_mapFragment2);
    }

    void goToPharmacyList(View navController) {
        Navigation.findNavController(navController).navigate(R.id.action_mapFragment2_to_pharmacyListFragment);
    }
}
