package com.maritech.arterium.ui.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.profile.HomeViewModel;
import com.maritech.arterium.ui.widgets.CustomTabComponent;

public class StatFragment extends Fragment {

    View toolbar;
    TextView title;
    View appBar;
    ImageView moveLeft;
    ImageView moveRight;
    TextView month;
    Integer count = 1;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_stat, container, false);
        toolbar = root.findViewById(R.id.statisticToolbar);
        toolbar.findViewById(R.id.ivArrow).setVisibility(View.INVISIBLE);
        toolbar.findViewById(R.id.ivRight).setVisibility(View.INVISIBLE);

        title = toolbar.findViewById(R.id.tvToolbarTitle);
        title.setText(R.string.statistic);

        appBar = root.findViewById(R.id.appBar);
        appBar.setOutlineProvider(null);

        month = root.findViewById(R.id.tvStatisticMonth);

        CustomTabComponent tabComponent = root.findViewById(R.id.ctcStatDetails);
        tabComponent.initForDetails();

        changeMonth(root);
        return root;
    }

    private void changeMonth(View root){
        moveLeft = root.findViewById(R.id.ivPreviousMonth);
        moveRight = root.findViewById(R.id.ivNextMonth);

        moveLeft.setOnClickListener(view -> {
            count--;
            if (count<0) count = 11;
            newMonth();
        });
        moveRight.setOnClickListener(view -> {
            count++;
            if (count>11) count = 0;
            newMonth();
        });
    }


    private void newMonth(){
        switch (count){
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