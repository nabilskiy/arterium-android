package com.maritech.arterium.ui.pharmacies;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import com.maritech.arterium.R;
import com.maritech.arterium.common.ContentState;
import com.maritech.arterium.data.models.PharmacyModel;
import com.maritech.arterium.databinding.FragmentPharmacyBinding;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.pharmacies.list.adapter.PharmaciesPagerAdapter;
import com.maritech.arterium.utils.ToastUtil;
import java.util.ArrayList;

public class PharmacyFragment extends BaseFragment<FragmentPharmacyBinding> {

    private PharmaciesViewModel pharmaciesViewModel;
    private final ArrayList<PharmacyModel> models = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.fragment_pharmacy;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        pharmaciesViewModel =
                new ViewModelProvider(requireActivity()).get(PharmaciesViewModel.class);

        binding.pharmacyPager.setUserInputEnabled(false);
        binding.pharmacyPager.setOffscreenPageLimit(2);

        PharmaciesPagerAdapter adapter =
                new PharmaciesPagerAdapter(getChildFragmentManager(), getLifecycle());
        binding.pharmacyPager.setAdapter(adapter);

        binding.ivPharmacyMap.setOnClickListener(
                view -> binding.pharmacyPager.setCurrentItem(1)
        );

        binding.ivPharmacyList.setOnClickListener(
                view -> binding.pharmacyPager.setCurrentItem(0)
        );

        binding.pharmacyListToolbar.ivRight.setOnClickListener(
                v -> requireActivity().onBackPressed()
        );

        binding.pharmacyListToolbar.etSearch.addTextChangedListener(textWatcher);
        binding.errorBtn.setOnClickListener(v -> getPharmacies());

        observeViewModel();

        getPharmacies();
    }

    private void observeViewModel() {
        pharmaciesViewModel.responseLiveData.observe(getViewLifecycleOwner(),
                response -> {
                    models.clear();
                    models.addAll(response.getData());
                    pharmaciesViewModel.modelList.setValue(models);
                });

//        pharmaciesViewModel.selectedPharmacyPosition.observe(
//                getViewLifecycleOwner(), position -> binding.pharmacyPager.setCurrentItem(1)
//        );

        pharmaciesViewModel.contentState.observe(this, contentState -> {
            if (contentState == ContentState.LOADING) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
            }

            binding.emptyContainer.setVisibility(
                    contentState == ContentState.ERROR ? View.VISIBLE : View.GONE
            );

            binding.lcListMap.setVisibility(
                    contentState == ContentState.CONTENT ? View.VISIBLE : View.GONE
            );

            if (contentState == ContentState.EMPTY) {
                ToastUtil.show(requireActivity(), getString(R.string.pharmacy_list_empty));
            }
        });
    }

    private void getPharmacies() {
        pharmaciesViewModel.getPharmacies();
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            ArrayList<PharmacyModel> newModel = new ArrayList<>();
            for (int i = 0; i < models.size(); i++) {
                if (models.get(i).getName().toLowerCase().contains(s.toString())) {
                    newModel.add(models.get(i));
                }
            }

            pharmaciesViewModel.modelList.setValue(newModel);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }
}