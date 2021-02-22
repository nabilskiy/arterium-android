package com.maritech.arterium.ui.dashboardDoctor;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import com.maritech.arterium.ui.MainActivity;
import com.maritech.arterium.R;
import com.maritech.arterium.common.PurchasesType;
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

public class DashboardFragment extends BaseFragment {

    private TextView tvUserName;
    private TextView tvPost;
    private TextView tvAllBuy;
    private TextView tvLvl;

    private ImageView ivSearch;
    private TextView tvDoctors;
    private ImageView ivClose;
    private ImageView ivFilter;
    private EditText etSearch;
    private LinearLayout clSearch;
    private ConstraintLayout clProgram;
    private ConstraintLayout clInfoUser;
    private ConstraintLayout clBtnAddNewPersonal;
    private ConstraintLayout clInfoClose;

    private TextView tvTabOne;
    private TextView tvTabTwo;
    private TextView tvTabThree;

    private View details;

    View navigation_statistics;
    View achievementsFragment;
    View myProfileFragment;
    View navigation_dashboard;
    NestedScrollView details_view;
    DialogWithRecycler customDialog;

    DashboardNavigator navigator = new DashboardNavigator();

    private DashboardMpViewModel dashboardViewModel;
    private PatientsSharedViewModel sharedViewModel;

    private final ProfileViewModel viewModel = new ProfileViewModel();

    private final SimpleDateFormat dateFormat =
            new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

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

        clBtnAddNewPersonal = root.findViewById(R.id.clBtnAddNewPersonal);
        ivSearch = root.findViewById(R.id.ivSearch);
        ivFilter = root.findViewById(R.id.ivFilter);
        tvDoctors = root.findViewById(R.id.tvDoctors);
        ivClose = root.findViewById(R.id.ivClose);
        clSearch = root.findViewById(R.id.clSearch);
        etSearch = root.findViewById(R.id.etSearch);
        clProgram = root.findViewById(R.id.clProgram);
        clInfoUser = root.findViewById(R.id.clInfoUser);
        clInfoClose = root.findViewById(R.id.clInfoClose);
        details = root.findViewById(R.id.details);
        tvTabOne = details.findViewById(R.id.tvOne);
        tvTabTwo = details.findViewById(R.id.tvTwo);
        tvTabThree = details.findViewById(R.id.tvThree);
        details_view = root.findViewById(R.id.details_view);
        details_view = root.findViewById(R.id.details_view);

        tvUserName = root.findViewById(R.id.tvUserName);
        tvPost = root.findViewById(R.id.tvPost);
        tvAllBuy = root.findViewById(R.id.tvAllBuy);
        tvLvl = root.findViewById(R.id.tvLvl);


        getChildFragmentManager().beginTransaction()
                .add(R.id.vpPatients, PatientsFragment.getInstance())
                .commit();

        details_view.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                        ivSearch.setVisibility(View.VISIBLE);
                        tvDoctors.setVisibility(View.VISIBLE);
                        ivFilter.setVisibility(View.VISIBLE);
                        ivClose.setVisibility(View.GONE);
                        clSearch.setVisibility(View.GONE);
                    }
                });
        etSearch.addTextChangedListener(textWatcher);

        tvTabOne.setActivated(true);
        tvTabOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedViewModel.purchasesFilter.setValue(PurchasesType.ALL);

                tvTabOne.setActivated(true);
                tvTabTwo.setActivated(false);
                tvTabThree.setActivated(false);
            }
        });

        tvTabTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedViewModel.purchasesFilter.setValue(PurchasesType.WITH);

                tvTabOne.setActivated(false);
                tvTabTwo.setActivated(true);
                tvTabThree.setActivated(false);
            }
        });

        tvTabThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedViewModel.purchasesFilter.setValue(PurchasesType.WITHOUT);

                tvTabOne.setActivated(false);
                tvTabTwo.setActivated(false);
                tvTabThree.setActivated(true);
            }
        });

        sharedViewModel.purchasesFilter.setValue(PurchasesType.ALL);

        String[] dates = new String[2];
        Calendar calendar = Calendar.getInstance();
        dates[1] = dateFormat.format(calendar.getTime());

        calendar.add(Calendar.MONTH, -3);
        dates[0] = dateFormat.format(calendar.getTime());

        sharedViewModel.dates.setValue(dates);

        navigation_statistics = getActivity().findViewById(R.id.statistics);
        achievementsFragment = getActivity().findViewById(R.id.achievements);
        myProfileFragment = getActivity().findViewById(R.id.profile);
        navigation_dashboard = getActivity().findViewById(R.id.dashboard);

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivSearch.setVisibility(View.GONE);
                tvDoctors.setVisibility(View.GONE);
                ivFilter.setVisibility(View.GONE);
                ivClose.setVisibility(View.VISIBLE);
                clSearch.setVisibility(View.VISIBLE);
            }
        });

        ivClose.setOnClickListener(v -> {
            ivSearch.setVisibility(View.VISIBLE);
            tvDoctors.setVisibility(View.VISIBLE);
            ivFilter.setVisibility(View.VISIBLE);
            ivClose.setVisibility(View.GONE);
            clSearch.setVisibility(View.GONE);
        });

        clBtnAddNewPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.addNewPersonal(navController);
            }
        });

        clProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // showDialog();
                customDialog.show();

            }
        });

        clInfoClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.goToDashboardMP(navController);
            }
        });

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
                            tvUserName.setText(profileData.getName());
                            tvPost.setText(profileData.getInstitutionType());
                            tvAllBuy.setText(getString(R.string.whole_shopping_items1,
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
        clProgram.setBackgroundResource(clProgramColor);
        clInfoUser.setBackgroundResource(clProgramColor);
    }

    private TextWatcher textWatcher = new TextWatcher() {
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

//    public void setSagrada() {
//
//
//    }

}
