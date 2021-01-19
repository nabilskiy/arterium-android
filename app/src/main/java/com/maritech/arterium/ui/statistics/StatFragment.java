package com.maritech.arterium.ui.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.calendar.CalendarBottomSheetDialog;
import com.maritech.arterium.ui.widgets.CustomTabComponent;

public class StatFragment extends BaseFragment {

    View toolbar;
    TextView title;
    View appBar;
    ImageView moveLeft;
    ImageView moveRight;
    TextView month;
    Integer count = 1;

    View navigation_statistics;
    View achievementsFragment;
    View myProfileFragment;
    View navigation_dashboard;

    View stat_details;

    ImageView ivStatDetailSearch;
    TextView tvPurchasesForCurrentMonth;
    ImageView ivStatDetailFilter;
    ImageView ivClose;
    ConstraintLayout clSearch;

    StatNavigator navigator = new StatNavigator();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_stat, container, false);
        toolbar = root.findViewById(R.id.statisticToolbar);
        toolbar.findViewById(R.id.ivArrow).setVisibility(View.INVISIBLE);
        toolbar.findViewById(R.id.ivRight).setVisibility(View.INVISIBLE);

        navigation_statistics = getActivity().findViewById(R.id.navigation_statistics);
        achievementsFragment = getActivity().findViewById(R.id.achievementsFragment);
        myProfileFragment = getActivity().findViewById(R.id.myProfileFragment);
        navigation_dashboard = getActivity().findViewById(R.id.navigation_dashboard);

        stat_details = root.findViewById(R.id.stat_details);
        clSearch = stat_details.findViewById(R.id.clSearch);
        ivClose = stat_details.findViewById(R.id.ivClose);
        tvPurchasesForCurrentMonth = stat_details.findViewById(R.id.tvPurchasesForCurrentMonth);
        ivStatDetailSearch = stat_details.findViewById(R.id.ivStatDetailSearch);
        ivStatDetailFilter = stat_details.findViewById(R.id.ivStatDetailFilter);

        title = toolbar.findViewById(R.id.tvToolbarTitle);
        title.setText(R.string.statistic);

        appBar = root.findViewById(R.id.appBar);
        appBar.setOutlineProvider(null);

        month = root.findViewById(R.id.tvStatisticMonth);

        CustomTabComponent tabComponent = root.findViewById(R.id.ctcStatDetails);
        tabComponent.initForDetails();

        requireActivity().findViewById(R.id.nav_view).setVisibility(View.VISIBLE);

        myProfileFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.bottomGoToMyProfileDoctor(navController);
            }
        });

        navigation_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.bottomGoToDashboardDoctor(navController);
            }
        });

        achievementsFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.bottomGoToAchievements(navController);
            }
        });

        navigation_statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.bottomGoToStat(navController);
            }
        });

        ivStatDetailSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivStatDetailSearch.setVisibility(View.GONE);
                tvPurchasesForCurrentMonth.setVisibility(View.GONE);
                ivStatDetailFilter.setVisibility(View.GONE);
                ivClose.setVisibility(View.VISIBLE);
                clSearch.setVisibility(View.VISIBLE);
            }
        });

        ivClose.setOnClickListener(v -> {
            ivStatDetailSearch.setVisibility(View.VISIBLE);
            tvPurchasesForCurrentMonth.setVisibility(View.VISIBLE);
            ivStatDetailFilter.setVisibility(View.VISIBLE);
            ivClose.setVisibility(View.GONE);
            clSearch.setVisibility(View.GONE);
        });

        month.setOnClickListener(v -> {
            CalendarBottomSheetDialog.Companion.newInstance((dateFrom, dateTo) -> {

            }, "Фільтр по даті").show(getChildFragmentManager(), CalendarBottomSheetDialog.Companion.getTAG());
        });

        changeMonth(root);
        return root;
    }

    private void changeMonth(View root) {
        moveLeft = root.findViewById(R.id.ivPreviousMonth);
        moveRight = root.findViewById(R.id.ivNextMonth);

        moveLeft.setOnClickListener(view -> {
            count--;
            if (count < 0) count = 11;
            newMonth();
        });
        moveRight.setOnClickListener(view -> {
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