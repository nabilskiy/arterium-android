package com.maritech.arterium.ui.patients;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.maritech.arterium.R;
import com.maritech.arterium.common.PurchasesType;
import com.maritech.arterium.data.models.PatientModel;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.databinding.FragmentPatientsBinding;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.patients.adapter.PatientPurchasesAdapter;
import com.maritech.arterium.utils.ToastUtil;

import java.util.ArrayList;

public class PatientsFragment extends BaseFragment<FragmentPatientsBinding> {

    private PatientsViewModel viewModel;
    private PatientsSharedViewModel sharedViewModel;

    private String createdFromDate;
    private String createdToDate;
    private int purchasesFilter;
    private int drugProgramId = 0;
    private String searchQuery;

    private ArrayList<PatientModel> allList = new ArrayList<>();
    private ArrayList<PatientModel> filteredList = new ArrayList<>();
    private PatientPurchasesAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_patients;
    }

    public static Fragment getInstance() {
        PatientsFragment fragment = new PatientsFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drugProgramId = Pref.getInstance().getDrugProgramId(requireContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("Patients", "onViewCreated");
        binding.setVm(viewModel);

        initView();

        sharedViewModel =
                new ViewModelProvider(requireActivity()).get(PatientsSharedViewModel.class);

        viewModel =
                new ViewModelProvider(requireActivity()).get(PatientsViewModel.class);

        observeViewModel();
    }

    private void observeViewModel() {
        sharedViewModel.dates.observe(getViewLifecycleOwner(), strings -> {
            createdFromDate = strings[0];
            createdToDate = strings[1];

            getPatientList();
        });

        sharedViewModel.purchasesFilter.observe(getViewLifecycleOwner(), filter -> {
            if (filter == PurchasesType.ALL) {
                adapter.setData(allList);
            } else if (filter == PurchasesType.WITH) {
                ArrayList<PatientModel> models = new ArrayList<>();
                for (PatientModel model : filteredList) {
                    if (model.getLastPurchaseAt() != null) {
                        models.add(model);
                    }
                }

                adapter.setData(models);
            } else if (filter == PurchasesType.WITHOUT) {
                ArrayList<PatientModel> models = new ArrayList<>();
                for (PatientModel model : filteredList) {
                    if (model.getLastPurchaseAt() == null) {
                        models.add(model);
                    }
                }

                adapter.setData(models);
            }
        });

//        sharedViewModel.filter.observe(getViewLifecycleOwner(), s -> {
//
//        });

        sharedViewModel.reload.observe(getViewLifecycleOwner(), isReload -> {
            if (isReload) {
                getPatientList();
            }
        });

        sharedViewModel.searchQuery.observe(getViewLifecycleOwner(), query -> {
            searchQuery = query;

            if (searchQuery.isEmpty()) {
                adapter.setData(allList);
            } else {
                ArrayList<PatientModel> models = new ArrayList<>();
                for (PatientModel model : filteredList) {
                    if (model.getName().toLowerCase().contains(searchQuery.toLowerCase())) {
                        models.add(model);
                    }
                }

                adapter.setData(models);
            }
        });

        viewModel.responseLiveData
                .observe(getViewLifecycleOwner(),
                        data -> {
                            allList = data.getData();
                            filteredList = data.getData();

                            adapter.setData(filteredList);
                        });

        viewModel.contentState
                .observe(getViewLifecycleOwner(), contentState -> {
                    binding.progressBar.setVisibility(contentState.isLoading() ? View.VISIBLE : View.GONE);
                    binding.emptyContainer.setVisibility(contentState.isEmpty() ? View.VISIBLE : View.GONE);
                    binding.rvPatients.setVisibility(!contentState.isLoading() && !contentState.isEmpty() ? View.VISIBLE : View.GONE);

                    if (contentState.isError()) {
                        ToastUtil.show(requireContext(), "Ошибка при получении данных");
                    }
                });
    }

    private void initView() {
        adapter = new PatientPurchasesAdapter(
                requireContext(),
                filteredList,
                (position, object) -> {
                    Intent intent = new Intent(requireActivity(), PatientCardActivity.class);
                    intent.putExtra(PatientCardActivity.PATIENT_MODEL_KEY, object);
                    startActivity(intent);
                }
        );
        binding.rvPatients.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvPatients.setAdapter(adapter);

    }

    private void getPatientList() {
        viewModel.getPatients(
                purchasesFilter,
                createdFromDate,
                createdToDate,
                drugProgramId,
                searchQuery
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (viewModel != null && viewModel.responseLiveData != null) {
            if (viewModel.responseLiveData.hasObservers()) {
                viewModel.responseLiveData.removeObservers(lifecycleOwner);
            }
            if (viewModel.contentState.hasObservers()) {
                viewModel.contentState.removeObservers(lifecycleOwner);
            }
            if (viewModel.errorMessage.hasObservers()) {
                viewModel.errorMessage.removeObservers(lifecycleOwner);
            }
            viewModel = null;
        }
    }
}