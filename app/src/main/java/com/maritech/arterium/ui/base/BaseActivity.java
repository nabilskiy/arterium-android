package com.maritech.arterium.ui.base;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;

import com.maritech.arterium.R;

public abstract class BaseActivity extends AppCompatActivity {

    @LayoutRes
    protected abstract int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
    }

    public static void setStatusBarGradient(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = ContextCompat.getDrawable(activity, R.drawable.gradient_purple);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(color));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.black));
            window.setBackgroundDrawable(background);
        }
    }

    public static void setStatusBarGradientDark(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = ContextCompat.getDrawable(activity, R.drawable.gradient_purple);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.black));
            window.setBackgroundDrawable(background);
        }
    }

    public static void setStatusBarGradientDrawable(Activity activity, int drawableId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = ContextCompat.getDrawable(activity, drawableId);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(fetchPrimaryDarkColor(activity));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.black));
            window.setBackgroundDrawable(background);
            //activity.recreate();
        }
    }

    public static int fetchPrimaryDarkColor(Context mContext) {
        TypedValue typedValue = new TypedValue();
        TypedArray a = mContext.obtainStyledAttributes(typedValue.data, new int[] { R.attr.colorPrimaryDark });
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }

    private ProgressDialog dialog;
    public void showProgressDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setMessage("Подождите..");
            dialog.show();
        }
    }

    public void hideProgressDialog() {
        if (dialog != null) {
            dialog.hide();
        }
    }
}