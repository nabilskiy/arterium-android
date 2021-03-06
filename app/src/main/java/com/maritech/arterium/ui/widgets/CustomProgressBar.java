package com.maritech.arterium.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.maritech.arterium.R;
import com.maritech.arterium.utils.ScreenSize;

public class CustomProgressBar extends LinearLayout {

    private TextView amount;
    private Integer maxVal = 20;

    public CustomProgressBar(Context context) {
        super(context);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custome_progress_bar, this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressBar);
        Integer value = typedArray.getInteger(R.styleable.CustomProgressBar_cp_value, 0);
        initControl(context, value);

        setInsideProgress(value);
        typedArray.recycle();
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initControl(Context context, Integer value) {
        amount = findViewById(R.id.tvCpbValue);
        amount.setText(value.toString());
    }

    private void setInsideProgress(Integer value){
        View view = findViewById(R.id.cpbInsideBgColor);
        View layout = findViewById(R.id.clCustomProgress);

        int screenWidth = ScreenSize.getViewHeight(layout);
            screenWidth = (int) (screenWidth - 0.14 * screenWidth);
        int insideBgColor = (int) (screenWidth * value / maxVal);

        layout.setLayoutParams(new LayoutParams(screenWidth, 230 ));
        view.setLayoutParams(new ConstraintLayout.LayoutParams(insideBgColor, ConstraintLayout.LayoutParams.MATCH_PARENT));
    }
}