package com.maritech.arterium.ui.pharmacy_list;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseFragment;

public class PharmacyListFragment extends BaseFragment {

    PharmacyNavigator navigator = new PharmacyNavigator();
    View goodDayPharmacy;
    View wholesalePharmacyPrice;
    View TASPharmacy;
    View monetPharmacy;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pharmacy_list, container, false);
        setPharmacyList(root);
        View navcontroller = root.findViewById(R.id.nav_pharmacy);

        goodDayPharmacy.setOnClickListener(view -> {
            requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
            navigator.goToMap(navcontroller);
            Log.e("!!!","!!!!");
        });
        wholesalePharmacyPrice.setOnClickListener(view -> {
            requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
            navigator.goToMap(navcontroller);
            Log.e("!!!","!!!!");
        });
        TASPharmacy.setOnClickListener(view -> {
            requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
            navigator.goToMap(navcontroller);
            Log.e("!!!","!!!!");
        });
        monetPharmacy.setOnClickListener(view -> {
            requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
            navigator.goToMap(navcontroller);
            Log.e("!!!","!!!!");
        });
        return root;
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
        requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);

    }
}