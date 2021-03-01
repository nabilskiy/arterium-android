package com.maritech.arterium.ui.statistics;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    private StatNavigator navigator = new StatNavigator();

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
                    new ViewModelProvider(requireActivity()).get(PatientsSharedViewModel.class);
        }

        binding.statisticToolbar.ivArrow.setVisibility(View.INVISIBLE);
        binding.statisticToolbar.ivRight.setVisibility(View.INVISIBLE);

        binding.statisticToolbar.tvToolbarTitle.setText(R.string.statistic);

        binding.appBar.setOutlineProvider(null);

        binding.statDetails.ctcStatDetails.initForDetails();

        binding.statDetails.ivStatDetailSearch.setOnClickListener(v -> {
            binding.statDetails.ivStatDetailSearch.setVisibility(View.GONE);
            binding.statDetails.tvPurchasesForCurrentMonth.setVisibility(View.GONE);
            binding.statDetails.ivStatDetailFilter.setVisibility(View.GONE);
            binding.statDetails.ivClose.setVisibility(View.VISIBLE);
            binding.statDetails.clSearch.setVisibility(View.VISIBLE);
        });

        binding.statDetails.ivClose.setOnClickListener(v -> {
            binding.statDetails.ivStatDetailSearch.setVisibility(View.VISIBLE);
            binding.statDetails.tvPurchasesForCurrentMonth.setVisibility(View.VISIBLE);
            binding.statDetails.ivStatDetailFilter.setVisibility(View.VISIBLE);
            binding.statDetails.ivClose.setVisibility(View.GONE);
            binding.statDetails.clSearch.setVisibility(View.GONE);
        });

        binding.statDetails.ctcStatDetails.findViewById(R.id.tvOne).setActivated(true);
        binding.statDetails.ctcStatDetails.findViewById(R.id.tvOne).setOnClickListener(v -> {
            sharedViewModel.purchasesFilter.setValue(PurchasesType.ALL);

            binding.statDetails.ctcStatDetails.findViewById(R.id.tvOne).setActivated(true);
            binding.statDetails.ctcStatDetails.findViewById(R.id.tvTwo).setActivated(false);
            binding.statDetails.ctcStatDetails.findViewById(R.id.tvThree).setActivated(false);
        });

        binding.statDetails.ctcStatDetails.findViewById(R.id.tvTwo).setOnClickListener(v -> {
            sharedViewModel.purchasesFilter.setValue(PurchasesType.WITH);

            binding.statDetails.ctcStatDetails.findViewById(R.id.tvOne).setActivated(false);
            binding.statDetails.ctcStatDetails.findViewById(R.id.tvTwo).setActivated(true);
            binding.statDetails.ctcStatDetails.findViewById(R.id.tvThree).setActivated(false);
        });

        binding.statDetails.ctcStatDetails.findViewById(R.id.tvThree).setOnClickListener(v -> {
            sharedViewModel.purchasesFilter.setValue(PurchasesType.WITHOUT);

            binding.statDetails.ctcStatDetails.findViewById(R.id.tvOne).setActivated(false);
            binding.statDetails.ctcStatDetails.findViewById(R.id.tvTwo).setActivated(false);
            binding.statDetails.ctcStatDetails.findViewById(R.id.tvThree).setActivated(true);
        });

        sharedViewModel.purchasesFilter.setValue(PurchasesType.ALL);

        long mPrevClickTime = 0;
        binding.ivPreviousMonth.setOnClickListener(view -> {
            if (SystemClock.elapsedRealtime() - mPrevClickTime < 1000){
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            currentMonthNum--;
            if (currentMonthNum < 0) currentMonthNum = 11;

            calendar.add(Calendar.MONTH, -1);

            initMonths();
        });
        binding.ivNextMonth.setOnClickListener(view -> {
            currentMonthNum++;
            if (currentMonthNum > 11) currentMonthNum = 0;

            calendar.add(Calendar.MONTH, 1);

            initMonths();
        });

        getChildFragmentManager().beginTransaction()
                .replace(R.id.vpPatients, PatientsFragment.getInstance())
                .commit();

        binding.tvStatisticMonth.setOnClickListener(v -> CalendarBottomSheetDialog.Companion.newInstance(
                (dateFrom, dateTo) -> {
                    fromDate = String.valueOf(dateFrom);
                    toDate = String.valueOf(dateTo);

                    getStatistics();
                }, "Фільтр по даті")
                .show(getChildFragmentManager(), CalendarBottomSheetDialog.Companion.getTAG()));

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

        sharedViewModel.dates.setValue(dates);

        fromDate = dates[0];
        toDate = dates[1];

        getStatistics();

        setMonthLabels();
    }

    private void setMonth() {

    }

    private void setMonthLabels() {
        switch (currentMonthNum) {
            case 0:
                binding.tvStatisticMonth.setText(R.string.january);
                break;
            case 1:
                binding.tvStatisticMonth.setText(R.string.february);
                break;
            case 2:
                binding.tvStatisticMonth.setText(R.string.march);
                break;
            case 3:
                binding.tvStatisticMonth.setText(R.string.april);
                break;
            case 4:
                binding.tvStatisticMonth.setText(R.string.may);
                break;
            case 5:
                binding.tvStatisticMonth.setText(R.string.june);
                break;
            case 6:
                binding.tvStatisticMonth.setText(R.string.july);
                break;
            case 7:
                binding.tvStatisticMonth.setText(R.string.august);
                break;
            case 8:
                binding.tvStatisticMonth.setText(R.string.september);
                break;
            case 9:
                binding.tvStatisticMonth.setText(R.string.october);
                break;
            case 10:
                binding.tvStatisticMonth.setText(R.string.november);
                break;
            default:
                binding.tvStatisticMonth.setText(R.string.december);
                break;
        }
    }
}