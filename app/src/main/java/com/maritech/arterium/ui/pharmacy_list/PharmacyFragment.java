package com.maritech.arterium.ui.pharmacy_list;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.maritech.arterium.R;
import com.maritech.arterium.databinding.FragmentPharmacyBinding;
import com.maritech.arterium.ui.base.BaseFragment;

public class PharmacyFragment extends BaseFragment<FragmentPharmacyBinding> {

    PharmacyNavigator navigator = new PharmacyNavigator();

    @Override
    protected int getContentView() {
        return R.layout.fragment_pharmacy;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        View navController = root.findViewById(R.id.nav_pharmacy);

        binding.ivPharmacyMap.setOnClickListener(
                view -> navigator.goToMap(navController)
        );

        binding.ivPharmacyList.setOnClickListener(
                view -> navigator.goToPharmacyList(navController)
        );

        binding.pharmacyListToolbar.ivRight.setOnClickListener(
                v -> requireActivity().onBackPressed()
        );
    }
}