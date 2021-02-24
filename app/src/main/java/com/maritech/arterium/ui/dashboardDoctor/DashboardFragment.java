package com.maritech.arterium.ui.dashboardDoctor;

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
import com.maritech.arterium.databinding.FragmentDashboardBinding;
import com.maritech.arterium.ui.ActivityActionViewModel;
import com.maritech.arterium.ui.base.BaseActivity;
import com.maritech.arterium.ui.base.BaseFragment;
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

    private PatientsSharedViewModel sharedViewModel;

    private ProfileViewModel profileViewModel;

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
        actionViewModel = new ViewModelProvider(requireActivity()).get(ActivityActionViewModel.class);

        if (sharedViewModel == null) {
            sharedViewModel =
                    new ViewModelProvider(requireActivity()).get(PatientsSharedViewModel.class);
        }
        if (profileViewModel == null) {
            profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        }

//        final int clProgramColorGliptar = R.drawable.gradient_light_red;
//        final int clProgramColorSagrada = R.drawable.gradient_light_blue;
//        final int clInfoUserColorGliptar = R.drawable.ic_gliptar;
//        final int clInfoUserColorSagrada = R.drawable.ic_sagrada;

        getChildFragmentManager().beginTransaction()
                .add(R.id.vpPatients, PatientsFragment.getInstance())
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
                v -> navigator.addNewPersonal(navController)
        );

        binding.clProgram.setOnClickListener(v -> customDialog.show());

        binding.clInfoClose.setOnClickListener(v -> actionViewModel.onRecreate.setValue(true));

        observeViewModel();

        profileViewModel.getProfile();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customDialog = new DialogWithRecycler(this.getContext(), "DialogChooseTheme");

        customDialog.setListener(content -> {
            if (content == 0) {
                baseActivity.setThemeDefault();
                BaseActivity.setStatusBarGradientDrawable(
                        requireActivity(), R.drawable.gradient_primary
                );
            }
            if (content == 1) {
                baseActivity.setThemeBlue();
                BaseActivity.setStatusBarGradientDrawable(
                        requireActivity(), R.drawable.gradient_primary
                );
            }
            if (content == 2) {
                baseActivity.setThemeRed();
                BaseActivity.setStatusBarGradientDrawable(
                        requireActivity(), R.drawable.gradient_primary
                );
            }

            actionViewModel.onRecreate.setValue(true);
        });
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

//        viewModel.contentState
//                .observe(getViewLifecycleOwner(), contentState -> {
//                    if (contentState.isLoading()) {
//                        showProgressDialog();
//                    } else {
//                        hideProgressDialog();
//                    }
//                });

        profileViewModel.errorMessage
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
