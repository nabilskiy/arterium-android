package com.maritech.arterium.ui.pharmacy_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseFragment;

public class PharmacyFragment  extends BaseFragment {

    PharmacyNavigator navigator = new PharmacyNavigator();
    ImageView goToMap;
    ImageView goToList;
    ImageView ivBack;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pharmacy, container, false);
        View navcontroller = root.findViewById(R.id.nav_pharmacy);

        goToMap = root.findViewById(R.id.ivPharmacyMap);
        goToList = root.findViewById(R.id.ivPharmacyList);
        ivBack = root.findViewById(R.id.pharmacyListToolbar).findViewById(R.id.ivRight);

        goToMap.setOnClickListener(view -> {
            requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
            navigator.goToMap(navcontroller);
        });

        goToList.setOnClickListener(view -> {
            requireActivity().findViewById(R.id.nav_view).setVisibility(View.VISIBLE);
            navigator.goToPharmacyList(navcontroller);
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });

        requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
        return root;
    }
}