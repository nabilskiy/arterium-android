package com.maritech.arterium.ui.pharmacy_list;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseFragment;

public class PharmacyListFragment extends BaseFragment {

    PharmacyNavigator navigator = new PharmacyNavigator();
    View goodDayPharmacy;
    View wholesalePharmacyPrice;
    View TASPharmacy;
    View monetPharmacy;

    @Override
    protected int getContentView() {
        return R.layout.fragment_pharmacy_list;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        setPharmacyList(root);
        View navcontroller = root.findViewById(R.id.nav_pharmacy);

        goodDayPharmacy.setOnClickListener(view -> {
            navigator.goToMap(navcontroller);
            Log.e("!!!","!!!!");
        });
        wholesalePharmacyPrice.setOnClickListener(view -> {
            navigator.goToMap(navcontroller);
            Log.e("!!!","!!!!");
        });
        TASPharmacy.setOnClickListener(view -> {
            navigator.goToMap(navcontroller);
            Log.e("!!!","!!!!");
        });
        monetPharmacy.setOnClickListener(view -> {
            navigator.goToMap(navcontroller);
            Log.e("!!!","!!!!");
        });
    }

    private void setPharmacyList(View root) {
        TextView wholesale;
        TextView tas;
        TextView tvMonetPharmacy;
        TextView tvSwissPharmacy;


        wholesale = root.findViewById(R.id.wholesalePharmacyPrice).findViewById(R.id.tvPharmacyItemTitle);
        tas = root.findViewById(R.id.TASPharmacy).findViewById(R.id.tvPharmacyItemTitle);
        tvMonetPharmacy = root.findViewById(R.id.monetPharmacy).findViewById(R.id.tvPharmacyItemTitle);
        tvSwissPharmacy = root.findViewById(R.id.swissPharmacy).findViewById(R.id.tvPharmacyItemTitle);

        goodDayPharmacy = root.findViewById(R.id.goodDayPharmacy);
        wholesalePharmacyPrice = root.findViewById(R.id.wholesalePharmacyPrice);
        TASPharmacy = root.findViewById(R.id.TASPharmacy);
        monetPharmacy = root.findViewById(R.id.monetPharmacy);





        wholesale.setText(R.string.wholesale_pharmacy);
        tas.setText(R.string.tas_pharmacy);
        tvMonetPharmacy.setText(R.string.monet_pharmacy);
        tvSwissPharmacy.setText(R.string.swiss_pharmacy);
//        Log.e("Bottom", this.getClass().getName());
//        baseActivity.findViewById(R.id.bottom_nav_view).setVisibility(View.GONE);
    }
}