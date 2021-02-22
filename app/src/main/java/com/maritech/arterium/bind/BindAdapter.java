package com.maritech.arterium.bind;

import android.view.View;

import androidx.databinding.BindingAdapter;

public class BindAdapter {
    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, Boolean value) {
        view.setVisibility(value ? View.VISIBLE : View.GONE);
    }
}
