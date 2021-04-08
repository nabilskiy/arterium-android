package com.maritech.arterium.ui.dashboard.doctor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.maritech.arterium.R;
import com.maritech.arterium.common.PurchasesType;
import com.maritech.arterium.data.models.DrugProgramModel;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.databinding.FragmentDashboardBinding;
import com.maritech.arterium.ui.ActivityActionViewModel;
import com.maritech.arterium.ui.MainActivity;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.calendar.CalendarBottomSheetDialog;
import com.maritech.arterium.ui.dashboard.doctor.levels.LevelsContainerDialog;
import com.maritech.arterium.ui.drugPrograms.DrugProgramsFragment;
import com.maritech.arterium.ui.my_profile_doctor.ProfileViewModel;
import com.maritech.arterium.ui.patients.PatientsFragment;
import com.maritech.arterium.ui.patients.PatientsSharedViewModel;
import com.maritech.arterium.ui.patients.add_new_personal.AddNewPersonalActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DashboardFragment extends BaseFragment<FragmentDashboardBinding> {

    private PatientsSharedViewModel sharedViewModel;

    private ProfileViewModel profileViewModel;

    private final SimpleDateFormat dateFormat =
            new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private ArrayList<DrugProgramModel> programModels;

    private int drugProgramId;

    @Override
    protected int getContentView() {
        return R.layout.fragment_dashboard;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        actionViewModel = new ViewModelProvider(requireActivity()).get(ActivityActionViewModel.class);

        if (sharedViewModel == null && getParentFragment() != null) {
            sharedViewModel =
                    new ViewModelProvider(requireActivity()).get(PatientsSharedViewModel.class);
        }
        if (profileViewModel == null) {
            profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        }

        drugProgramId = Pref.getInstance().getDrugProgramId(requireContext());

        getChildFragmentManager().beginTransaction()
                .replace(R.id.patients_container, PatientsFragment.getInstance())
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

        String[] dates = new String[2];
        Calendar calendar = Calendar.getInstance();
        dates[1] = dateFormat.format(calendar.getTime());

        calendar.add(Calendar.MONTH, -3);
        dates[0] = dateFormat.format(calendar.getTime());

        sharedViewModel.dates.setValue(dates);

        binding.details.findViewById(R.id.ivSearch).setOnClickListener(v -> {
            binding.details.findViewById(R.id.ivSearch).setVisibility(View.GONE);
            binding.details.findViewById(R.id.tvDoctors).setVisibility(View.GONE);
            binding.details.findViewById(R.id.ivFilter).setVisibility(View.GONE);
            binding.details.findViewById(R.id.clSearch).setVisibility(View.VISIBLE);
        });

        Calendar calendarCurrent = Calendar.getInstance();
        binding.details.findViewById(R.id.ivFilter).setOnClickListener(
                v -> CalendarBottomSheetDialog.Companion.newInstance(
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
                                CalendarBottomSheetDialog.Companion.getTAG())
        );

        binding.details.findViewById(R.id.ivClose).setOnClickListener(v -> {
            binding.details.findViewById(R.id.ivSearch).setVisibility(View.VISIBLE);
            binding.details.findViewById(R.id.tvDoctors).setVisibility(View.VISIBLE);
            binding.details.findViewById(R.id.ivFilter).setVisibility(View.VISIBLE);
            binding.details.findViewById(R.id.clSearch).setVisibility(View.GONE);
        });

        binding.clBtnAddNewPersonal.setOnClickListener(
                v -> {
                    Intent intent = new Intent(requireActivity(), AddNewPersonalActivity.class);
                    startActivityForResult(intent, AddNewPersonalActivity.PATIENT_REQUEST_CODE);
                }
        );

        binding.clProgram.setOnClickListener(v -> {
            DrugProgramsFragment customDialog = DrugProgramsFragment.getInstance(programModels);
            customDialog.setListener(content -> {
                ((MainActivity) requireActivity()).setTheme();
                customDialog.dismiss();
                actionViewModel.onRecreate.setValue(true);
            });

            customDialog.show(getChildFragmentManager(), "DrugProgramsFragment");
        });

        binding.clInfoClose.setOnClickListener(
                v -> actionViewModel.onRecreate.setValue(true)
        );

        binding.lvlLayout.setOnClickListener(v -> {
            LevelsContainerDialog fragment =
                    LevelsContainerDialog.getInstance(programModels);
            fragment.show(getChildFragmentManager(), "LevelFragment");
        });

        observeViewModel();
        profileViewModel.getProfile();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                        model.getPrimarySoldCount()));
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
