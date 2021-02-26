package com.maritech.arterium.ui.dashboardDoctor;

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

import com.maritech.arterium.R;
import com.maritech.arterium.common.PurchasesType;
import com.maritech.arterium.data.models.DrugProgramModel;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.databinding.FragmentDashboardBinding;
import com.maritech.arterium.ui.ActivityActionViewModel;
import com.maritech.arterium.ui.MainActivity;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.drugPrograms.DrugProgramsDialog;
import com.maritech.arterium.ui.drugPrograms.DrugProgramsViewModel;
import com.maritech.arterium.ui.my_profile_doctor.ProfileViewModel;
import com.maritech.arterium.ui.patients.PatientsFragment;
import com.maritech.arterium.ui.patients.PatientsSharedViewModel;
import com.maritech.arterium.ui.patients.add_new_personal.AddNewPersonalActivity;
import com.maritech.arterium.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DashboardFragment extends BaseFragment<FragmentDashboardBinding> {

    private PatientsSharedViewModel sharedViewModel;

    private ProfileViewModel profileViewModel;
    private DrugProgramsViewModel drugProgramsViewModel;

    private final SimpleDateFormat dateFormat =
            new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private final int ADD_PATIENT_REQUEST_CODE = 500;

    @Override
    protected int getContentView() {
        return R.layout.fragment_dashboard;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        actionViewModel = new ViewModelProvider(requireActivity()).get(ActivityActionViewModel.class);

        if (sharedViewModel == null) {
            sharedViewModel =
                    new ViewModelProvider(requireActivity()).get(PatientsSharedViewModel.class);
        }
        if (profileViewModel == null) {
            profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        }
        if (drugProgramsViewModel == null) {
            drugProgramsViewModel =
                    new ViewModelProvider(this).get(DrugProgramsViewModel.class);
        }

//        final int clProgramColorGliptar = R.drawable.gradient_light_red;
//        final int clProgramColorSagrada = R.drawable.gradient_light_blue;
//        final int clInfoUserColorGliptar = R.drawable.ic_gliptar;
//        final int clInfoUserColorSagrada = R.drawable.ic_sagrada;

        getChildFragmentManager().beginTransaction()
                .replace(R.id.vpPatients, PatientsFragment.getInstance())
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

        binding.details.findViewById(R.id.ivClose).setOnClickListener(v -> {
            binding.details.findViewById(R.id.ivSearch).setVisibility(View.VISIBLE);
            binding.details.findViewById(R.id.tvDoctors).setVisibility(View.VISIBLE);
            binding.details.findViewById(R.id.ivFilter).setVisibility(View.VISIBLE);
            binding.details.findViewById(R.id.clSearch).setVisibility(View.GONE);
        });

        binding.clBtnAddNewPersonal.setOnClickListener(
                v -> {
                    Intent intent = new Intent(requireActivity(), AddNewPersonalActivity.class);
                    startActivityForResult(intent, ADD_PATIENT_REQUEST_CODE);
                }
        );

        binding.clProgram.setOnClickListener(v -> {
            DrugProgramsDialog customDialog = new DrugProgramsDialog();

            customDialog.setListener(content -> {
                ((MainActivity) requireActivity()).setTheme();

                customDialog.dismiss();
                actionViewModel.onRecreate.setValue(true);
            });

            customDialog.show(getChildFragmentManager(), "DrugProgramsFragment");
        });

        binding.clInfoClose.setOnClickListener(v -> actionViewModel.onRecreate.setValue(true));

        observeViewModel();

        profileViewModel.getProfile();

        drugProgramsViewModel.getDrugPrograms();
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
                            binding.tvAllBuy.setText(getString(R.string.whole_shopping_items1,
                                    profileData.getSoldCount()));
//                            tvLvl.setText(profileData.get());

                        });

        profileViewModel.errorMessage
                .observe(getViewLifecycleOwner(), error -> {
                    ToastUtil.show(requireContext(), error);
                });

        drugProgramsViewModel.responseLiveData
                .observe(getViewLifecycleOwner(),
                        list -> {
                            int programId = Pref.getInstance().getDrugProgramId(requireContext());
                            DrugProgramModel model = null;

                            for (DrugProgramModel m : list) {
                                if (m.getId() == programId) {
                                    model = m;
                                    break;
                                }
                            }

                            if (model != null) {
                                binding.tvCurrentProgram.setText(String.format("%s - \"%s\"", model.getTitle(), model.getSlogan()));

                                if (model.getDescription() != null) {
                                    binding.tvInfoProgram.setText(model.getDescription());
                                } else {
                                    binding.tvInfoProgram.setText("Немає опису");
                                }
                            }
                        });

        drugProgramsViewModel.errorMessage
                .observe(getViewLifecycleOwner(), error -> {
//                    ToastUtil.show(requireContext(), error);
                });

        drugProgramsViewModel.contentState
                .observe(getViewLifecycleOwner(), contentState -> {
                    if (contentState.isLoading()) {
//                        binding.progressBar.setVisibility(View.VISIBLE);
                    } else {
//                        binding.progressBar.setVisibility(View.GONE);
                    }
                });
    }

    public void setLvlTheme(int clProgramColor, int clInfoUserColor) {
        binding.clProgram.setBackgroundResource(clProgramColor);
        binding.clInfoUser.setBackgroundResource(clProgramColor);
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
            if (requestCode == ADD_PATIENT_REQUEST_CODE) {
                sharedViewModel.reload.setValue(true);

                binding.details.findViewById(R.id.tvOne).setActivated(true);
                binding.details.findViewById(R.id.tvTwo).setActivated(false);
                binding.details.findViewById(R.id.tvThree).setActivated(false);
            }
        }
    }
}
