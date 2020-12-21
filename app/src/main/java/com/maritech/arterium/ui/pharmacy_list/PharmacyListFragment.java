package com.maritech.arterium.ui.pharmacy_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.maritech.arterium.R;

public class PharmacyListFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pharmacy_list, container, false);

        setPharmacyList(root);
        return root;
    }

    private void setPharmacyList(View root){
        TextView wholesale;
        TextView tas;
        TextView monetPharmacy;
        TextView swissPharmacy;

        wholesale = root.findViewById(R.id.wholesalePharmacyPrice).findViewById(R.id.tvPharmacyItemTitle);
        tas = root.findViewById(R.id.TASPharmacy).findViewById(R.id.tvPharmacyItemTitle);
        monetPharmacy = root.findViewById(R.id.monetPharmacy).findViewById(R.id.tvPharmacyItemTitle);
        swissPharmacy = root.findViewById(R.id.swissPharmacy).findViewById(R.id.tvPharmacyItemTitle);

        wholesale.setText(R.string.wholesale_pharmacy);
        tas.setText(R.string.tas_pharmacy);
        monetPharmacy.setText(R.string.monet_pharmacy);
        swissPharmacy.setText(R.string.swiss_pharmacy);
    }
}