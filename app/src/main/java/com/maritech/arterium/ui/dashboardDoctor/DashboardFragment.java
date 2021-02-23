package com.maritech.arterium.ui.dashboardDoctor;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import com.maritech.arterium.R;
import com.maritech.arterium.common.PurchasesType;
import com.maritech.arterium.databinding.FragmentDashboardBinding;
import com.maritech.arterium.ui.base.BaseActivity;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.dashboardMp.DashboardMpViewModel;
import com.maritech.arterium.ui.dialogs.dialog_with_recycler.DialogWithRecycler;
import com.maritech.arterium.ui.my_profile_doctor.ProfileViewModel;
import com.maritech.arterium.ui.patients.PatientsFragment;
import com.maritech.arterium.ui.patients.PatientsSharedViewModel;
import com.maritech.arterium.utils.ToastUtil;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DashboardFragment extends BaseFragment<FragmentDashboardBinding> {

    DashboardNavigator navigator = new DashboardNavigator();

    private DashboardMpViewModel dashboardViewModel;
    private PatientsSharedViewModel sharedViewModel;

    private final ProfileViewModel viewModel = new ProfileViewModel();

    private final SimpleDateFormat dateFormat =
            new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    DialogWithRecycler customDialog;

    @Override
    protected int getContentView() {
        return R.layout.fragment_dashboard;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        if (sharedViewModel == null) {
            sharedViewModel =
                    new ViewModelProvider(requireActivity()).get(PatientsSharedViewModel.class);
        }
        if (dashboardViewModel == null) {
            dashboardViewModel = new ViewModelProvider(this).get(DashboardMpViewModel.class);
        }

        dashboardViewModel.getText().observe(getViewLifecycleOwner(), s -> {
        });
        // ((MainActivity) getActivity()).setTheme(R.style.Theme_Arterium_Blue);

        final int clProgramColorGliptar = R.drawable.gradient_light_red;
        final int clProgramColorSagrada = R.drawable.gradient_light_blue;
        final int clInfoUserColorGliptar = R.drawable.ic_gliptar;
        final int clInfoUserColorSagrada = R.drawable.ic_sagrada;

        getChildFragmentManager().beginTransaction()
                .add(R.id.vpPatients, PatientsFragment.getInstance())
                .commit();

        binding.detailsView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                        binding.details.findViewById(R.id.ivSearch).setVisibility(View.VISIBLE);
                        binding.details.findViewById(R.id.tvDoctors).setVisibility(View.VISIBLE);
                        binding.details.findViewById(R.id.ivFilter).setVisibility(View.VISIBLE);
                        binding.details.findViewById(R.id.ivClose).setVisibility(View.GONE);
                        binding.details.findViewById(R.id.clSearch).setVisibility(View.GONE);
                    }
                });

        ((EditText) binding.details.findViewById(R.id.etSearch)).addTextChangedListener(textWatcher);

        binding.details.findViewById(R.id.tvOne).setActivated(true);
        binding.details.findViewById(R.id.tvOne).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedViewModel.purchasesFilter.setValue(PurchasesType.ALL);

                binding.details.findViewById(R.id.tvOne).setActivated(true);
                binding.details.findViewById(R.id.tvTwo).setActivated(false);
                binding.details.findViewById(R.id.tvThree).setActivated(false);
            }
        });

        binding.details.findViewById(R.id.tvTwo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedViewModel.purchasesFilter.setValue(PurchasesType.WITH);

                binding.details.findViewById(R.id.tvOne).setActivated(false);
                binding.details.findViewById(R.id.tvTwo).setActivated(true);
                binding.details.findViewById(R.id.tvThree).setActivated(false);
            }
        });

        binding.detailsView.findViewById(R.id.tvThree).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedViewModel.purchasesFilter.setValue(PurchasesType.WITHOUT);

                binding.details.findViewById(R.id.tvOne).setActivated(false);
                binding.details.findViewById(R.id.tvTwo).setActivated(false);
                binding.details.findViewById(R.id.tvThree).setActivated(true);
            }
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
            binding.details.findViewById(R.id.ivClose).setVisibility(View.VISIBLE);
            binding.details.findViewById(R.id.clSearch).setVisibility(View.VISIBLE);
        });

        binding.details.findViewById(R.id.ivClose).setOnClickListener(v -> {
            binding.details.findViewById(R.id.ivSearch).setVisibility(View.VISIBLE);
            binding.details.findViewById(R.id.tvDoctors).setVisibility(View.VISIBLE);
            binding.details.findViewById(R.id.ivFilter).setVisibility(View.VISIBLE);
            binding.details.findViewById(R.id.ivClose).setVisibility(View.GONE);
            binding.details.findViewById(R.id.clSearch).setVisibility(View.GONE);
        });

        binding.clBtnAddNewPersonal.setOnClickListener(
                v -> navigator.addNewPersonal(navController)
        );

        binding.clProgram.setOnClickListener(v -> customDialog.show());

        binding.clInfoClose.setOnClickListener(v -> navigator.goToDashboardMP(navController));

        observeViewModel();

        viewModel.getProfile();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customDialog = new DialogWithRecycler(this.getContext(), "DialogChooseTheme");

        customDialog.setListener((DialogWithRecycler.OnChooseItem) content -> {
            Log.e("!!!", String.valueOf(content));
            if (content == 0) {
                baseActivity.setTheme(R.style.Theme_Arterium);
                BaseActivity.setStatusBarGradientDrawable(requireActivity(), R.drawable.gradient_primary);
                navigator.bottomGoToDashboardDoctor(navController);
            }
            if (content == 1) {
                baseActivity.setTheme(R.style.Theme_Arterium_Blue);
                BaseActivity.setStatusBarGradientDrawable(requireActivity(), R.drawable.gradient_primary);
                navigator.bottomGoToDashboardDoctor(navController);
            }
            if (content == 2) {
                baseActivity.setTheme(R.style.Theme_Arterium_Red);
                BaseActivity.setStatusBarGradientDrawable(requireActivity(), R.drawable.gradient_primary);
                navigator.bottomGoToDashboardDoctor(navController);
            }

        });
    }

    private void observeViewModel() {
        viewModel.responseLiveData
                .observe(getViewLifecycleOwner(),
                        profileData -> {
                            binding.tvUserName.setText(profileData.getName());
                            binding.tvPost.setText(profileData.getInstitutionType());
                            binding.tvAllBuy.setText(getString(R.string.whole_shopping_items1,
                                    profileData.getSoldCount()));
//                            tvLvl.setText(profileData.get());

                        });

//        viewModel.contentState
//                .observe(getViewLifecycleOwner(), contentState -> {
//                    if (contentState.isLoading()) {
//                        showProgressDialog();
//                    } else {
//                        hideProgressDialog();
//                    }
//                });

        viewModel.errorMessage
                .observe(getViewLifecycleOwner(), error -> {
                    ToastUtil.show(requireContext(), error);
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

}
