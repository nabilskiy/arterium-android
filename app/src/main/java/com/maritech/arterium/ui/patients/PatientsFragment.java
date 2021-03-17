package com.maritech.arterium.ui.patients;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.maritech.arterium.ui.patients.adapter.PatientsAdapter;
import com.maritech.arterium.ui.patients.add_new_personal.AddNewPersonalActivity;
import com.maritech.arterium.utils.ToastUtil;

import java.util.ArrayList;

public class PatientsFragment extends BaseFragment<FragmentPatientsBinding> {

    private PatientsViewModel viewModel;
    private PatientsSharedViewModel sharedViewModel;

    private String createdFromDate;
    private String createdToDate;
    private int drugProgramId = 0;
    private String searchQuery;

    private ArrayList<PatientModel> allList = new ArrayList<>();
    private ArrayList<PatientModel> filteredList = new ArrayList<>();
    private PatientsAdapter adapter;

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
        binding.setVm(viewModel);

        initView();

        if (getParentFragment() != null) {
            sharedViewModel =
                    new ViewModelProvider(requireActivity()).get(PatientsSharedViewModel.class);
        }

        viewModel =
                new ViewModelProvider(this).get(PatientsViewModel.class);

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

            binding.emptyContainer
                    .setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        });

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

                binding.emptyContainer
                        .setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
            }
        });

        viewModel.allPatients
                .observe(getViewLifecycleOwner(),
                        data -> {
                            allList = data.getData();
                            filteredList = data.getData();

                            adapter.setData(filteredList);
                        });

        viewModel.allPatientsState
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
        adapter = new PatientsAdapter(
                requireContext(),
                filteredList,
                (position, object) -> {
                    Intent intent = new Intent(requireActivity(), PatientCardActivity.class);
                    intent.putExtra(PatientCardActivity.PATIENT_ID_KEY, object.getId());
                    startActivityForResult(intent, AddNewPersonalActivity.PATIENT_REQUEST_CODE);
                }
        );
        binding.rvPatients.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvPatients.setAdapter(adapter);

    }

    private void getPatientList() {
        viewModel.getPatients(
                createdFromDate,
                createdToDate,
                drugProgramId,
                searchQuery
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AddNewPersonalActivity.PATIENT_REQUEST_CODE) {
                getPatientList();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (viewModel != null && viewModel.allPatients != null) {
            if (viewModel.allPatients.hasObservers()) {
                viewModel.allPatients.removeObservers(lifecycleOwner);
            }
            if (viewModel.allPatientsState.hasObservers()) {
                viewModel.allPatientsState.removeObservers(lifecycleOwner);
            }
            if (viewModel.allPatientsMessage.hasObservers()) {
                viewModel.allPatientsMessage.removeObservers(lifecycleOwner);
            }
            viewModel = null;
        }
    }
}