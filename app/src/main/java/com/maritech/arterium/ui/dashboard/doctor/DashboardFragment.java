package com.maritech.arterium.ui.dashboard.doctor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.maritech.arterium.R;
import com.maritech.arterium.common.PurchasesType;
import com.maritech.arterium.data.models.DoctorsModel;
import com.maritech.arterium.data.models.DrugProgramModel;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.databinding.FragmentDashboardBinding;
import com.maritech.arterium.ui.ActivityActionViewModel;
import com.maritech.arterium.ui.MainActivity;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.calendar.CalendarBottomSheetDialog;
import com.maritech.arterium.ui.dashboard.doctor.levels.LevelsContainerDialog;
import com.maritech.arterium.ui.dashboard.medicalRep.DashboardMpFragment;
import com.maritech.arterium.ui.dashboard.regionalManager.DashboardRmFragment;
import com.maritech.arterium.ui.drugPrograms.DrugProgramsFragment;
import com.maritech.arterium.ui.my_profile_doctor.ProfileViewModel;
import com.maritech.arterium.ui.patients.PatientsFragment;
import com.maritech.arterium.ui.patients.PatientsSharedViewModel;
import com.maritech.arterium.ui.patients.add_new_personal.AddNewPersonalActivity;
import com.maritech.arterium.utils.DateTimeUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.maritech.arterium.ui.dashboard.doctor.DashboardViewModel.TAG;

public class DashboardFragment extends BaseFragment<FragmentDashboardBinding> {


    private PatientsSharedViewModel sharedViewModel;

    private ProfileViewModel profileViewModel;
    private DashboardViewModel viewModel;

    private final SimpleDateFormat dateFormat =
            new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private ArrayList<DrugProgramModel> programModels;

    private int id = -1;
    private String name = null;
    private boolean isFromMP = false;

    private int drugProgramId;

    private final String[] dates = new String[2];
    private final Calendar calendar = Calendar.getInstance();
    private final Calendar calendarCurrent = Calendar.getInstance();


    @Override
    protected int getContentView() {
        return R.layout.fragment_dashboard;
    }

    private void initBundle() {
        if (getArguments() != null) {
            if (getArguments().containsKey(DashboardMpFragment.ID_KEY_BUNDLE)) {
                isFromMP = true;
                id = getArguments().getInt(DashboardMpFragment.ID_KEY_BUNDLE, -1);
                binding.clProgram.setVisibility(View.GONE);
                binding.emptyView.setVisibility(View.VISIBLE);
            }
        }
        Log.i(TAG, "initBundle: " + isFromMP + " " + id);
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        initBundle();
        actionViewModel = new ViewModelProvider(requireActivity()).get(ActivityActionViewModel.class);

        if (sharedViewModel == null && getParentFragment() != null) {
            sharedViewModel =
                    new ViewModelProvider(requireActivity()).get(PatientsSharedViewModel.class);
        }
        if (profileViewModel == null) {
            profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        }
        if (viewModel == null)
            viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        if (!isFromMP)
            drugProgramId = Pref.getInstance().getDrugProgramId(requireContext());

        getChildFragmentManager().beginTransaction()
                .replace(R.id.patients_container, PatientsFragment.getInstance(getArguments()))
                .commit();

        binding.detailsView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                        binding.details.findViewById(R.id.ivSearch).setVisibility(View.VISIBLE);
                        binding.details.findViewById(R.id.tvDoctors).setVisibility(View.VISIBLE);
                        binding.details.findViewById(R.id.ivFilter).setVisibility(View.VISIBLE);
                        binding.details.findViewById(R.id.clSearch).setVisibility(View.GONE);
                    }
                });

        ((EditText) binding.details.findViewById(R.id.etSearch)).addTextChangedListener(textWatcher);

        binding.details.findViewById(R.id.tvOne).setActivated(true);
        binding.details.findViewById(R.id.tvOne).setOnClickListener(v -> {
            sharedViewModel.purchasesFilter.setValue(PurchasesType.ALL);

            binding.details.findViewById(R.id.tvOne).setActivated(true);
            binding.details.findViewById(R.id.tvTwo).setActivated(false);
            binding.details.findViewById(R.id.tvThree).setActivated(false);
        });

        binding.details.findViewById(R.id.tvTwo).setOnClickListener(v -> {
            sharedViewModel.purchasesFilter.setValue(PurchasesType.WITH);

            binding.details.findViewById(R.id.tvOne).setActivated(false);
            binding.details.findViewById(R.id.tvTwo).setActivated(true);
            binding.details.findViewById(R.id.tvThree).setActivated(false);
        });

        binding.detailsView.findViewById(R.id.tvThree).setOnClickListener(v -> {
            sharedViewModel.purchasesFilter.setValue(PurchasesType.WITHOUT);
            binding.details.findViewById(R.id.tvOne).setActivated(false);
            binding.details.findViewById(R.id.tvTwo).setActivated(false);
            binding.details.findViewById(R.id.tvThree).setActivated(true);
        });

        sharedViewModel.purchasesFilter.setValue(PurchasesType.ALL);


//        dates[1] = dateFormat.format(calendar.getTime());
//        dates[0] = Calendar.getInstance().get(Calendar.YEAR) + "-01-01";

        dates[1] = null;
        dates[0] = null;


        sharedViewModel.dates.setValue(dates);

        binding.lvlLayout.setOnClickListener(v -> {
            LevelsContainerDialog fragment =
                    LevelsContainerDialog.getInstance(programModels);
            fragment.show(getChildFragmentManager(), "LevelFragment");
        });

        observeViewModel();
        if (!isFromMP)
            profileViewModel.getProfile();
        else
            viewModel.getDoctor(id);
        initListeners();

        if (isFromMP) {
            binding.clBtnAddNewPersonal.setVisibility(View.GONE);
            binding.clInfoClose.setVisibility(View.VISIBLE);
        }
    }

    private void initListeners() {
        binding.details.findViewById(R.id.ivClose).setOnClickListener(v -> {
            closeSearchOnClick();
        });
        binding.ivBack.setOnClickListener(v -> getActivity().onBackPressed());
        binding.clBtnAddNewPersonal.setOnClickListener(
                v -> {
                    Intent intent = new Intent(requireActivity(), AddNewPersonalActivity.class);
                    startActivityForResult(intent, AddNewPersonalActivity.PATIENT_REQUEST_CODE);
                }
        );
        binding.clProgram.setOnClickListener(v -> {
            drugProgramOnClick();
        });
        binding.details.findViewById(R.id.ivFilter).setOnClickListener(v -> filterOnCLick());
        binding.details.findViewById(R.id.ivSearch).setOnClickListener(v -> {
            searchOnClick();
        });
        binding.clInfoClose.setOnClickListener(
                v -> actionViewModel.onRecreate.setValue(true)
        );
    }

    private void searchOnClick() {
        binding.details.findViewById(R.id.ivSearch).setVisibility(View.GONE);
        binding.details.findViewById(R.id.tvDoctors).setVisibility(View.GONE);
        binding.details.findViewById(R.id.ivFilter).setVisibility(View.GONE);
        binding.details.findViewById(R.id.clSearch).setVisibility(View.VISIBLE);
    }

    private void filterOnCLick() {
        CalendarBottomSheetDialog.Companion.newInstance(
                (dateFrom, dateTo) -> {
                    calendar.setTimeInMillis(dateTo);
                    dates[1] = dateFormat.format(calendar.getTime());
                    calendar.setTimeInMillis(dateFrom);
                    dates[0] = dateFormat.format(calendar.getTime());
                    sharedViewModel.dates.setValue(dates);
                },
                null,
                null,
                calendarCurrent.getTimeInMillis(),
                null,
                getString(R.string.date_filter_title))
                .show(getChildFragmentManager(),
                        CalendarBottomSheetDialog.Companion.getTAG());
    }

    private void drugProgramOnClick() {
        DrugProgramsFragment customDialog = DrugProgramsFragment.getInstance(programModels);
        customDialog.setListener(content -> {
            ((MainActivity) requireActivity()).setTheme();
            customDialog.dismiss();
            actionViewModel.onRecreate.setValue(true);
        });

        customDialog.show(getChildFragmentManager(), "DrugProgramsFragment");
    }

    private void closeSearchOnClick() {
        binding.details.findViewById(R.id.ivSearch).setVisibility(View.VISIBLE);
        binding.details.findViewById(R.id.tvDoctors).setVisibility(View.VISIBLE);
        binding.details.findViewById(R.id.ivFilter).setVisibility(View.VISIBLE);
        binding.details.findViewById(R.id.clSearch).setVisibility(View.GONE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void observeDoctorLiveData(DoctorsModel doctor) {
        binding.tvUserName.setText(doctor.getName());
        binding.tvPost.setText(doctor.getInstitutionType());
        binding.tvAllBuy.setText(getString(R.string.total_sold, doctor.getTotalSold()));
        binding.tvLvlLitter.setText(doctor.getPrograms().get(0).getLevel().toUpperCase());
        programModels = new ArrayList<>(doctor.getPrograms());
//        initDrugPrograms();
    }

    private void observeViewModel() {
        profileViewModel.responseLiveData
                .observe(getViewLifecycleOwner(),
                        profileData -> {
                            binding.tvUserName.setText(profileData.getName());
                            binding.tvPost.setText(profileData.getInstitutionType());
                            programModels = profileData.getDrugPrograms();
                            initDrugPrograms();
                        });

        profileViewModel.errorMessage
                .observe(getViewLifecycleOwner(), error -> {
                    if (error.contains("logged_in_from_another_device")) {
                        MaterialAlertDialogBuilder builder =
                                new MaterialAlertDialogBuilder(requireContext());
                        builder.setTitle(getString(R.string.account_title));
                        builder.setMessage(getString(R.string.account_session_error));
                        builder.show();
                    }
                });
        viewModel.getDoctorLiveData().observe(lifecycleOwner, this::observeDoctorLiveData);
    }

    private void initDrugPrograms() {
        if (programModels != null && programModels.size() != 0) {
            DrugProgramModel model = null;
            for (DrugProgramModel m : programModels) {
                if (m.getId() == drugProgramId) {
                    model = m;
                    break;
                }
            }

            if (model == null && programModels != null) {
                model = programModels.get(0);
                Pref.getInstance().setDrugProgramId(requireContext(), programModels.get(0).getId());
                ((MainActivity) requireActivity()).setTheme();
                actionViewModel.onRecreate.setValue(true);
            }

            if (model != null) {
                binding.tvCurrentProgram.setText(String.format("%s - \"%s\"", model.getTitle(), model.getSlogan()));
                if (model.getDescription() != null) {
                    binding.tvInfoProgram.setText(model.getDescription());
                } else {
                    binding.tvInfoProgram.setText(getString(R.string.drug_program_desc));
                }
                binding.tvAllBuy.setText(getString(R.string.whole_shopping_items1,
                        model.getPrimarySoldCount(), DateTimeUtil.getCurrentMonth()));
                binding.tvLvlLitter.setText(model.getLevel());
                binding.tvLvl.setText(getString(R.string.level_value, model.getLevel()));
            }
        }
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
            sharedViewModel.searchQuery.setValue(s.toString());
        }
    };

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AddNewPersonalActivity.PATIENT_REQUEST_CODE) {
                sharedViewModel.reload.setValue(true);

                binding.details.findViewById(R.id.tvOne).setActivated(true);
                binding.details.findViewById(R.id.tvTwo).setActivated(false);
                binding.details.findViewById(R.id.tvThree).setActivated(false);
            }
        }
    }
}
