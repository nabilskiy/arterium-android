package com.maritech.arterium.ui.base;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;

import com.maritech.arterium.BuildConfig;
import com.maritech.arterium.R;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.ui.ActivityActionViewModel;
import com.maritech.arterium.ui.dashboard.doctor.DashboardViewModel;

import java.util.Locale;

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    public T binding;
    private ActivityActionViewModel viewModel;

    public ActivityActionViewModel getViewModel() {
        return viewModel;
    }

    @LayoutRes
    protected abstract int getLayoutId();

    //todo move const
    public final int PROGRAM_RENIAL = 1;
    public final int PROGRAM_GLIPTAR = 2;
    public final int PROGRAM_SAGRADA = BuildConfig.DEBUG ? 4 : 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setThemeDefault();
        setupLocale();
        super.onCreate(savedInstanceState);
        if (viewModel == null) {
            viewModel = new ViewModelProvider(this).get(ActivityActionViewModel.class);
        }
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setLifecycleOwner(this);
        viewModel.changeTheme.observe(this, this::changeTheme);
    }



    public void changeTheme(int drugProgramId) {
        int currentDrugProgramId = Pref.getInstance().getDrugProgramId(this);
        Log.i(DashboardViewModel.TAG, "setTheme: " + drugProgramId + " " + currentDrugProgramId);
        if(currentDrugProgramId == drugProgramId)
            return;
        String TAG = DashboardViewModel.TAG;


        Pref.getInstance().setDrugProgramId(this, drugProgramId);
        if (drugProgramId == PROGRAM_RENIAL) {
            Log.i(TAG, "setTheme: RENIAL");
            setThemeDefault();
            setStatusBarGradientDrawable(this, R.drawable.gradient_primary);
        } else if (drugProgramId == PROGRAM_GLIPTAR) {
            Log.i(TAG, "setTheme: GLIPTAR");
            setThemeBlue();
            setStatusBarGradientDrawable(this, R.drawable.gradient_primary);
        } else if (drugProgramId == PROGRAM_SAGRADA) {
            Log.i(TAG, "setTheme: SAGRADA");
            setThemeRed();
            setStatusBarGradientDrawable(this, R.drawable.gradient_primary);
        }
        viewModel.onRecreateFragment.setValue(true);
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
        }
    }

    public void setThemeDefault() {
        setTheme(R.style.Theme_Arterium);
    }

    public void setThemeBlue() {
        setTheme(R.style.Theme_Arterium_Blue);
    }

    public void setThemeRed() {
        setTheme(R.style.Theme_Arterium_Red);
    }

    public static int fetchPrimaryDarkColor(Context mContext) {
        TypedValue typedValue = new TypedValue();
        TypedArray a = mContext.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorPrimaryDark});
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }

    private ProgressDialog dialog;

    public void showProgressDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setMessage(getString(R.string.wait));
            dialog.show();
        }
    }

    public void hideProgressDialog() {
        if (dialog != null) {
            dialog.hide();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        binding = null;
    }

    private void setupLocale() {
        String lang = Pref.getInstance().getLanguage(this);
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }
}