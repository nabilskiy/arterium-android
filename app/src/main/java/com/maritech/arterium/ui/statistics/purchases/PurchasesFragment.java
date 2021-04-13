package com.maritech.arterium.ui.statistics.purchases;

import android.app.Activity;
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
import com.maritech.arterium.data.models.PurchaseModel;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.databinding.FragmentPurchasesBinding;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.dashboard.medicalRep.DashboardMpFragment;
import com.maritech.arterium.ui.patients.PatientCardActivity;
import com.maritech.arterium.ui.patients.add_new_personal.AddNewPersonalActivity;
import com.maritech.arterium.ui.statistics.purchases.adapter.PurchasesAdapter;
import com.maritech.arterium.utils.ToastUtil;

import java.util.ArrayList;

import static com.maritech.arterium.ui.dashboard.doctor.DashboardViewModel.TAG;

public class PurchasesFragment extends BaseFragment<FragmentPurchasesBinding> {

    private PurchasesViewModel viewModel;
    private StatSharedViewModel sharedViewModel;

    private String createdFromDate;
    private String createdToDate;
    private int drugProgramId = 0;
    private int doctorId = -1;

    private String searchQuery;
    private ArrayList<PurchaseModel> allList = new ArrayList<>();
    private ArrayList<PurchaseModel> filteredList = new ArrayList<>();
    private PurchasesAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_purchases;
    }

    public static Fragment getInstance() {
        PurchasesFragment fragment = new PurchasesFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static Fragment getInstance(Bundle bundle) {
        if (bundle == null) return getInstance();
        PurchasesFragment fragment = new PurchasesFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drugProgramId = Pref.getInstance().getDrugProgramId(requireContext());
        if (getArguments() != null && getArguments().containsKey(DashboardMpFragment.ID_KEY_BUNDLE)) {
            doctorId = getArguments().getInt(DashboardMpFragment.ID_KEY_BUNDLE, -1);
        }
        Log.i(TAG, "onCreate PurchasesFragment: " + doctorId);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setVm(viewModel);
        initView();
        if (getParentFragment() != null) {
            sharedViewModel = new ViewModelProvider(getParentFragment()).get(StatSharedViewModel.class);
        }
        viewModel = new ViewModelProvider(this).get(PurchasesViewModel.class);
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
                ArrayList<PurchaseModel> models = new ArrayList<>();
                for (PurchaseModel model : filteredList) {
                    if (model.getSoldSum() != null) {
                        models.add(model);
                    }
                }

                adapter.setData(models);
            } else if (filter == PurchasesType.WITHOUT) {
                ArrayList<PurchaseModel> models = new ArrayList<>();
                for (PurchaseModel model : filteredList) {
                    if (model.getSoldSum() == null) {
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
                ArrayList<PurchaseModel> models = new ArrayList<>();
                for (PurchaseModel model : filteredList) {
                    if (model.getPatientName().toLowerCase().contains(searchQuery.toLowerCase())) {
                        models.add(model);
                    }
                }

                adapter.setData(models);

                binding.emptyContainer
                        .setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
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
                    binding.progressBar.setVisibility(
                            contentState.isLoading() ? View.VISIBLE : View.GONE
                    );
                    binding.emptyContainer.setVisibility(
                            contentState.isEmpty() ? View.VISIBLE : View.GONE
                    );
                    binding.rvPatients.setVisibility(
                            !contentState.isLoading() && !contentState.isEmpty() ? View.VISIBLE : View.GONE
                    );

                    if (contentState.isError()) {
                        ToastUtil.show(requireContext(), "Ошибка при получении данных");
                    }
                });
    }

    private void initView() {
        adapter = new PurchasesAdapter(
                requireContext(),
                filteredList,
                (position, object) -> {
                    Intent intent = new Intent(requireActivity(), PatientCardActivity.class);
                    intent.putExtra(PatientCardActivity.PATIENT_ID_KEY, object.getPatientId());
                    if (doctorId >= 0)
                        intent.putExtra(DashboardMpFragment.ID_KEY_BUNDLE, doctorId);
                    startActivityForResult(intent, AddNewPersonalActivity.PATIENT_REQUEST_CODE);
                }
        );
        binding.rvPatients.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvPatients.setAdapter(adapter);

    }

    private void getPatientList() {
        if (doctorId >= 0)
            viewModel.getPurchasesByDoctorId(doctorId, createdFromDate, createdToDate, drugProgramId);
        else
            viewModel.getPurchases(createdFromDate, createdToDate, drugProgramId);
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