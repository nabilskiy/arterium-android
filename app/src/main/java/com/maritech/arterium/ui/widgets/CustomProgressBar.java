package com.maritech.arterium.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.maritech.arterium.R;

import static android.widget.Toast.*;

public class CustomProgressBar extends LinearLayout {

    private TextView amount;

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

        //setInsideProgress(170);
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

        makeText(getContext(), String.valueOf(view.getRootView().getWidth()), Toast.LENGTH_LONG).show();

        view.setLayoutParams(new ConstraintLayout.LayoutParams((ConstraintLayout.LayoutParams.MATCH_PARENT), ConstraintLayout.LayoutParams.MATCH_PARENT));
    }
}