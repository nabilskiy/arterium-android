package com.maritech.arterium.ui.statistics;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.maritech.arterium.R;
import com.maritech.arterium.databinding.FragmentStatBinding;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.calendar.CalendarBottomSheetDialog;

public class StatFragment extends BaseFragment<FragmentStatBinding> {

    Integer count = 1;

    StatNavigator navigator = new StatNavigator();

    TextView month;

    @Override
    protected int getContentView() {
        return R.layout.fragment_stat;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        binding.statisticToolbar.ivArrow.setVisibility(View.INVISIBLE);
        binding.statisticToolbar.ivRight.setVisibility(View.INVISIBLE);

        month = binding.tvStatisticMonth;

        binding.cpbStatisticGreen.setElevation(9);

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

        month.setOnClickListener(v -> CalendarBottomSheetDialog.Companion.newInstance(
                (dateFrom, dateTo) -> {
                }, "Фільтр по даті")
                .show(getChildFragmentManager(), CalendarBottomSheetDialog.Companion.getTAG()));

        changeMonth();
    }

    private void changeMonth() {
        binding.ivPreviousMonth.setOnClickListener(view -> {
            count--;
            if (count < 0) count = 11;
            newMonth();
        });
        binding.ivNextMonth.setOnClickListener(view -> {
            count++;
            if (count > 11) count = 0;
            newMonth();
        });
    }


    private void newMonth() {
        switch (count) {
            case 0:
                month.setText(R.string.january);
                break;
            case 1:
                month.setText(R.string.february);
                break;
            case 2:
                month.setText(R.string.march);
                break;
            case 3:
                month.setText(R.string.april);
                break;
            case 4:
                month.setText(R.string.may);
                break;
            case 5:
                month.setText(R.string.june);
                break;
            case 6:
                month.setText(R.string.july);
                break;
            case 7:
                month.setText(R.string.august);
                break;
            case 8:
                month.setText(R.string.september);
                break;
            case 9:
                month.setText(R.string.october);
                break;
            case 10:
                month.setText(R.string.november);
                break;
            default:
                month.setText(R.string.december);
                break;
        }
    }
}