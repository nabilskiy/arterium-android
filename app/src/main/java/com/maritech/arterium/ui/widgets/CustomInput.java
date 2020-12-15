package com.maritech.arterium.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import com.maritech.arterium.R;


public class CustomInput  extends LinearLayout {

    public CustomInput(Context context) { super(context); }

    public CustomInput(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custome_input, this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomInput);
        String title = typedArray.getString(R.styleable.CustomInput_ci_title);
        String fieldValue = typedArray.getString(R.styleable.CustomInput_ci_field_value);

        setInput(title, fieldValue);
        typedArray.recycle();
    }

    public CustomInput(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setInput(String title, String fieldText) {
        TextView customInputTitle;
        TextView customInputValue;

        customInputTitle = findViewById(R.id.inputTitle);
        customInputTitle.setText(title);
        customInputValue = findViewById(R.id.etCustomInput);
        customInputValue.setHint(fieldText);
    }
}