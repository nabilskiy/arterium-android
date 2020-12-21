package com.maritech.arterium.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.maritech.arterium.R;

public class CustomTabComponent extends ConstraintLayout {

    Integer value;
    String textOne;
    String textTwo;
    String textThree;
    String textFour;

    TextView tvOne;
    TextView tvTwo;
    TextView tvThree;
    TextView tvFour;

    View viewOne;
    View viewTwo;
    View viewThree;


    public CustomTabComponent(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_tab_component, this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTabComponent);

        prepare(typedArray);

        initSwitch(context, value, typedArray);

        tvOne.setOnClickListener(v -> {
            tvOne.setActivated(true);
            tvTwo.setActivated(false);
            tvThree.setActivated(false);
            tvFour.setActivated(false);

            viewOne.setVisibility(INVISIBLE);
            viewTwo.setVisibility(VISIBLE);
            viewThree.setVisibility(VISIBLE);

        });

        tvTwo.setOnClickListener(v -> {
            tvOne.setActivated(false);
            tvTwo.setActivated(true);
            tvThree.setActivated(false);
            tvFour.setActivated(false);

            viewOne.setVisibility(INVISIBLE);
            viewTwo.setVisibility(INVISIBLE);
            viewThree.setVisibility(VISIBLE);
        });

        tvThree.setOnClickListener(v -> {
            tvOne.setActivated(false);
            tvTwo.setActivated(false);
            tvThree.setActivated(true);
            tvFour.setActivated(false);

            viewOne.setVisibility(VISIBLE);
            viewTwo.setVisibility(INVISIBLE);
            viewThree.setVisibility(INVISIBLE);
        });

        tvFour.setOnClickListener(v -> {
            tvOne.setActivated(false);
            tvTwo.setActivated(false);
            tvThree.setActivated(false);
            tvFour.setActivated(true);

            viewOne.setVisibility(VISIBLE);
            viewTwo.setVisibility(VISIBLE);
            viewThree.setVisibility(INVISIBLE);
        });

        tvTwo.setOnClickListener(v -> {
            tvOne.setActivated(false);
            tvTwo.setActivated(true);
            tvThree.setActivated(false);
            tvFour.setActivated(false);

            viewOne.setVisibility(INVISIBLE);
            viewTwo.setVisibility(INVISIBLE);
            viewThree.setVisibility(VISIBLE);
        });
        typedArray.recycle();
    }

    private void initSwitch(Context context, Integer value, TypedArray typedArray) {

        if (value == 1) {
            tvOne.setActivated(true);
            tvOne.setText(textOne);

            tvTwo.setVisibility(GONE);
            tvThree.setVisibility(GONE);
            tvFour.setVisibility(GONE);

            viewOne.setVisibility(GONE);
            viewTwo.setVisibility(GONE);
            viewThree.setVisibility(GONE);
        } else if (value == 2) {
            tvOne.setText(textOne);
            tvTwo.setText(textTwo);

            tvThree.setVisibility(GONE);
            tvFour.setVisibility(GONE);

            viewTwo.setVisibility(GONE);
            viewThree.setVisibility(GONE);
        } else if (value == 3) {
            tvOne.setText(textOne);
            tvTwo.setText(textTwo);
            tvThree.setText(textThree);

            tvFour.setVisibility(GONE);

            viewThree.setVisibility(GONE);
        } else if (value == 4) {
            tvOne.setText(textOne);
            tvTwo.setText(textTwo);
            tvThree.setText(textThree);
            tvFour.setText(textFour);
        }

    }

    private void prepare(TypedArray typedArray) {
        value = typedArray.getInteger(R.styleable.CustomTabComponent_csc_count, 4);
        textOne = typedArray.getString(R.styleable.CustomTabComponent_csc_text_one);
        textTwo = typedArray.getString(R.styleable.CustomTabComponent_csc_text_two);
        textThree = typedArray.getString(R.styleable.CustomTabComponent_csc_text_three);
        textFour = typedArray.getString(R.styleable.CustomTabComponent_csc_text_four);

        tvOne = findViewById(R.id.tvOne);
        tvTwo = findViewById(R.id.tvTwo);
        tvThree = findViewById(R.id.tvThree);
        tvFour = findViewById(R.id.tvFour);

        viewOne = findViewById(R.id.viewOne);
        viewTwo = findViewById(R.id.viewTwo);
        viewThree = findViewById(R.id.viewThree);
    }

    public void initForDetails(){
        tvOne.setActivated(true);
        tvOne.setText(textOne);
        tvTwo.setText(textTwo);
        tvThree.setText(textThree);

        tvFour.setVisibility(GONE);

        viewThree.setVisibility(GONE);
    }

}