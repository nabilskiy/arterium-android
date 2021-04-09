package com.maritech.arterium.ui.pharmacies.list;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.model.LatLng;
import com.maritech.arterium.R;
import com.maritech.arterium.data.models.PharmacyModel;
import com.maritech.arterium.databinding.FragmentPharmacyListBinding;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.pharmacies.PharmaciesViewModel;
import com.maritech.arterium.ui.pharmacies.list.adapter.PharmaciesAdapter;
import com.maritech.arterium.utils.NavigateUtils;

import java.util.ArrayList;

public class PharmacyListFragment extends BaseFragment<FragmentPharmacyListBinding> {

    private PharmaciesViewModel pharmaciesViewModel;

    private final ArrayList<PharmacyModel> models = new ArrayList<>();
    private PharmaciesAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_pharmacy_list;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        pharmaciesViewModel =
                new ViewModelProvider(requireActivity()).get(PharmaciesViewModel.class);

        initRecyclerView();

        observeViewModel();
    }

    private void observeViewModel() {
        pharmaciesViewModel.modelList.observe(getViewLifecycleOwner(),
                list -> adapter.setData(list));
    }

    private void initRecyclerView() {
        adapter = new PharmaciesAdapter(requireActivity(),
                models,
                (position, object) -> {
                    NavigateUtils.navigateGoogleMaps(requireActivity(), object);
//                    pharmaciesViewModel.selectedPharmacyPosition.setValue(position)
                });
        binding.list.setAdapter(adapter);
    }


}