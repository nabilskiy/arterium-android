package com.maritech.arterium.ui.pharmacy_list;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseFragment;

public class PharmacyFragment extends BaseFragment {

    PharmacyNavigator navigator = new PharmacyNavigator();
    ImageView goToMap;
    ImageView goToList;
    ImageView ivBack;

    @Override
    protected int getContentView() {
        return R.layout.fragment_pharmacy;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        View navcontroller = root.findViewById(R.id.nav_pharmacy);

        goToMap = root.findViewById(R.id.ivPharmacyMap);
        goToList = root.findViewById(R.id.ivPharmacyList);
        ivBack = root.findViewById(R.id.pharmacyListToolbar).findViewById(R.id.ivRight);

        goToMap.setOnClickListener(view -> {
            navigator.goToMap(navcontroller);
        });

        goToList.setOnClickListener(view -> {
            navigator.goToPharmacyList(navcontroller);
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });
    }
}