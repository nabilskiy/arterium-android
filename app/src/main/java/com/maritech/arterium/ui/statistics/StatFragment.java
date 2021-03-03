package com.maritech.arterium.ui.statistics;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;

import com.maritech.arterium.R;
import com.maritech.arterium.common.PurchasesType;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.databinding.FragmentStatBinding;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.calendar.CalendarBottomSheetDialog;
import com.maritech.arterium.ui.patients.PatientsFragment;
import com.maritech.arterium.ui.patients.PatientsSharedViewModel;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class StatFragment extends BaseFragment<FragmentStatBinding> {

    Calendar calendar = Calendar.getInstance();
    Calendar calendarFrom = Calendar.getInstance();

    private int currentMonthNum;

    private String fromDate;
    private String toDate;

    private StatisticsViewModel statisticsViewModel;
    private PatientsSharedViewModel sharedViewModel;

    private final SimpleDateFormat dateFormat =
            new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private final SimpleDateFormat outputDateFormat =
            new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
    private final SimpleDateFormat outputDateFormatShort =
            new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    long mPrevClickTime = 0;
    long mNextClickTime = 0;

    @Override
    protected int getContentView() {
        return R.layout.fragment_stat;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        if (statisticsViewModel == null) {
            statisticsViewModel = new ViewModelProvider(this).get(StatisticsViewModel.class);
        }
        if (sharedViewModel == null) {
            sharedViewModel =
                    new ViewModelProvider(this).get(PatientsSharedViewModel.class);
        }

        getChildFragmentManager().beginTransaction()
                .replace(R.id.patients_container, PatientsFragment.getInstance())
                .commit();

        binding.statisticToolbar.ivArrow.setVisibility(View.INVISIBLE);
        binding.statisticToolbar.ivRight.setVisibility(View.INVISIBLE);

        binding.statisticToolbar.tvToolbarTitle.setText(R.string.statistic);
        ((TextView) binding.details.findViewById(R.id.tvDoctors))
                .setText(getString(R.string.stat_purchases));

        binding.appBar.setOutlineProvider(null);

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

        binding.ivPreviousMonth.setOnClickListener(view -> {
            if (SystemClock.elapsedRealtime() - mPrevClickTime < 1000) {
                return;
            }
            mPrevClickTime = SystemClock.elapsedRealtime();

            currentMonthNum--;
            if (currentMonthNum < 0) currentMonthNum = 11;

            calendar.add(Calendar.MONTH, -1);

            initMonths();
        });

        binding.ivNextMonth.setOnClickListener(view -> {
            if (SystemClock.elapsedRealtime() - mNextClickTime < 1000) {
                return;
            }
            mNextClickTime = SystemClock.elapsedRealtime();

            currentMonthNum++;
            if (currentMonthNum > 11) currentMonthNum = 0;

            calendar.add(Calendar.MONTH, 1);

            initMonths();
        });

        binding.details.findViewById(R.id.ivFilter).setOnClickListener(
                v -> CalendarBottomSheetDialog.Companion.newInstance(
                        (dateFrom, dateTo) -> {
                            String[] dates = new String[2];

                            calendar.setTimeInMillis(dateTo);
                            dates[1] = dateFormat.format(calendar.getTime());

                            calendar.setTimeInMillis(dateFrom);
                            dates[0] = dateFormat.format(calendar.getTime());

                            sharedViewModel.dates.setValue(dates);
                        }, getString(R.string.date_filter_title))
                        .show(getChildFragmentManager(),
                                CalendarBottomSheetDialog.Companion.getTAG())
        );

        binding.tvStatisticMonth.setOnClickListener(
                v -> CalendarBottomSheetDialog.Companion.newInstance(
                        (dateFrom, dateTo) -> {
                            calendar.setTimeInMillis(dateTo);
                            calendarFrom.setTimeInMillis(dateFrom);

                            currentMonthNum = calendar.get(Calendar.MONTH);

                            String[] dates = new String[2];
                            dates[1] = dateFormat.format(calendar.getTime());
                            dates[0] = dateFormat.format(calendarFrom.getTime());

                            fromDate = dates[0];
                            toDate = dates[1];

                            sharedViewModel.dates.setValue(dates);

                            getStatistics();

                            setMonthLabels();
                        }, getString(R.string.date_filter_title))
                        .show(getChildFragmentManager(),
                                CalendarBottomSheetDialog.Companion.getTAG())
        );

        setCurrentMonth();

        observeViewModel();
    }

    private void getStatistics() {
        statisticsViewModel.getStatistics(fromDate,
                toDate,
                0,
                Pref.getInstance().getDrugProgramId(requireContext()));
    }

    private void observeViewModel() {
        statisticsViewModel.responseLiveData
                .observe(getViewLifecycleOwner(),
                        data -> {
                            binding.tvStatisticShoppingAmount
                                    .setText(String.valueOf(data.getTotal()));

                            long millis = data.getLastUpdateAt() * 1000;
                            binding.tvStatisticRestartData
                                    .setText(outputDateFormat.format(new Date(millis)));
                            binding.tvStatisticPeriod
                                    .setText(outputDateFormatShort.format(new Date(millis)));

                            binding.tvCpbValue.setText(String.valueOf(data.getPrimaryCount()));
                            binding.cpbStatisticGreen.setMax(data.getTotal());
                            binding.cpbStatisticGreen.setProgress(data.getPrimaryCount());

                            binding.tvCpbValueOrange.setText(String.valueOf(data.getSecondaryCount()));
                            binding.opbStatisticOrange.setMax(data.getTotal());
                            binding.opbStatisticOrange.setProgress(data.getSecondaryCount());
                        });

        statisticsViewModel.errorMessage
                .observe(getViewLifecycleOwner(), error -> {

                });
    }

    private void setCurrentMonth() {
        currentMonthNum = calendar.get(Calendar.MONTH);

        initMonths();
    }

    private void initMonths() {
        String[] dates = new String[2];

        dates[1] = dateFormat.format(calendar.getTime());

        calendarFrom.setTime(calendar.getTime());
        calendarFrom.add(Calendar.MONTH, -1);

        dates[0] = dateFormat.format(calendarFrom.getTime());

        fromDate = dates[0];
        toDate = dates[1];

        sharedViewModel.dates.setValue(dates);

        getStatistics();

        setMonthLabels();
    }

    private void setMonthLabels() {
        String[] months = getResources().getStringArray(R.array.calendar_month_general);

        String month = months[currentMonthNum];

        binding.tvStatisticMonth.setText(month);
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