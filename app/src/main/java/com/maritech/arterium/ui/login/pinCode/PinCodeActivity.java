package com.maritech.arterium.ui.login.pinCode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.maritech.arterium.R;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.databinding.FragmentPinBinding;
import com.maritech.arterium.ui.MainActivity;
import com.maritech.arterium.ui.base.BaseActivity;
import com.maritech.arterium.utils.ToastUtil;

public class PinCodeActivity extends BaseActivity<FragmentPinBinding> {

    public static final String SETTINGS_EXTRA_KEY = "settingsExtraKey";
    static final String BUNDLE_KEY = "login";
    static final String REQUEST_KEY = "requestLoginKey";
    private String typeOfUser;

    String localPinCode;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pin;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.verificationCodeInput.requestFocus();
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        }

        boolean isFromSettings = getIntent().getBooleanExtra(SETTINGS_EXTRA_KEY, false);

        localPinCode = Pref.getInstance().getPinCode(this);

        if (isFromSettings) {
            if (localPinCode.isEmpty()) {
                binding.tvEnterPin.setText("Введіть новый Пін-код");
            }
        } else {
            binding.tvEnterPin.setText("Введіть Ваш пін-код");
        }

        binding.verificationCodeInput.setOnPinEnteredListener(str -> {
            if (isFromSettings) {
                if (localPinCode.isEmpty()) {
                    localPinCode = str.toString();

                    binding.verificationCodeInput.setText(null);
                    binding.tvEnterPin.setText("Повторіть Ваш пін-код");
                } else {
                    if (str.toString().equals(localPinCode)) {
                        ToastUtil.show(this, "Ваш пін-код збережений");

                        Pref.getInstance().setPinCode(this, localPinCode);
                        Pref.getInstance().enablePinCode(this, true);

                        finish();
                    } else {
                        binding.tvEnterPin.setText("Пін-код не совпадает");
                        binding.verificationCodeInput.setText(null);
                    }
                }
            } else {
                if (str.toString().equals(localPinCode)) {
                    hideKeyboard(this);

                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);

                    finish();
                } else {
                    binding.verificationCodeInput.setText(null);
                }
            }
        });
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();

        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
