package com.maritech.arterium.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.maritech.arterium.R;
import com.maritech.arterium.utils.ScreenSize;

public class OrangeProgressBar extends LinearLayout {

    private TextView amount;
    private Integer maxVal = 20;

    public OrangeProgressBar(Context context) { super(context); }

    public OrangeProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.orange_progress_bar, this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.OrangeProgressBar);
        Integer value = typedArray.getInteger(R.styleable.OrangeProgressBar_opb_value, 0);
        initControl(context, value);

        setInsideProgress(value);
        typedArray.recycle();
    }

    public OrangeProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initControl(Context context, Integer value) {
        amount = findViewById(R.id.tvOrangeProgressValue);
        amount.setText(value.toString());
    }

    public void setValue(Integer value) {
        amount.setText(String.valueOf(value));
    }

    private void setInsideProgress(Integer value){
        View view = findViewById(R.id.vOrangeBg);
        View layout = findViewById(R.id.clOrange);

        int screenWidth = ScreenSize.getViewHeight(layout);
        screenWidth = (int) (screenWidth - 0.14 * screenWidth);
        int insideBgColor = (int) (screenWidth * value / maxVal);

        layout.setLayoutParams(new LayoutParams(screenWidth, 180 ));
        view.setLayoutParams(new ConstraintLayout.LayoutParams(insideBgColor, ConstraintLayout.LayoutParams.MATCH_PARENT));
    }
}