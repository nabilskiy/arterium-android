package com.maritech.arterium.ui.pharmacy_list;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.maritech.arterium.R;
import com.maritech.arterium.databinding.FragmentPharmacyListBinding;
import com.maritech.arterium.ui.base.BaseFragment;

public class PharmacyListFragment extends BaseFragment<FragmentPharmacyListBinding> {

    PharmacyNavigator navigator = new PharmacyNavigator();

    @Override
    protected int getContentView() {
        return R.layout.fragment_pharmacy_list;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        setPharmacyList();

        View navController = root.findViewById(R.id.nav_pharmacy);

        binding.goodDayPharmacy.getRoot()
                .setOnClickListener(view -> navigator.goToMap(navController));

        binding.wholesalePharmacyPrice.getRoot()
                .setOnClickListener(view -> navigator.goToMap(navController));

        binding.TASPharmacy.getRoot()
                .setOnClickListener(view -> navigator.goToMap(navController));

        binding.monetPharmacy.getRoot()
                .setOnClickListener(view -> navigator.goToMap(navController));
    }

    private void setPharmacyList() {
        binding.wholesalePharmacyPrice.tvPharmacyItemTitle.setText(R.string.wholesale_pharmacy);
        binding.TASPharmacy.tvPharmacyItemTitle.setText(R.string.tas_pharmacy);
        binding.monetPharmacy.tvPharmacyItemTitle.setText(R.string.monet_pharmacy);
        binding.swissPharmacy.tvPharmacyItemTitle.setText(R.string.swiss_pharmacy);

//        Log.e("Bottom", this.getClass().getName());
//        baseActivity.findViewById(R.id.bottom_nav_view).setVisibility(View.GONE);
    }
}