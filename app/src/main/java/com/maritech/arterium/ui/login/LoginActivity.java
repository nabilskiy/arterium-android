package com.maritech.arterium.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.maritech.arterium.R;
import com.maritech.arterium.common.UserType;
import com.maritech.arterium.data.models.Profile;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.databinding.ActivityLoginBinding;
import com.maritech.arterium.ui.MainActivity;
import com.maritech.arterium.ui.base.BaseActivity;
import com.maritech.arterium.ui.my_profile_doctor.ProfileViewModel;
import com.maritech.arterium.utils.ToastUtil;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    static final String BUNDLE_KEY = "login";
    static final String REQUEST_KEY = "requestLoginKey";

    private EditText password;
    private EditText login;

    private LoginViewModel loginViewModel;
    private ProfileViewModel profileViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        login = findViewById(R.id.etInputLogin);
        password = findViewById(R.id.etInputPassword);

        login.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);

        CheckBox checkBox = findViewById(R.id.cbShowHide);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                password.setTransformationMethod(null);
            } else {
                password.setTransformationMethod(new PasswordTransformationMethod());
            }
            password.setSelection(password.length());
        });

        TextView btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(
                v -> {
                    String loginStr = login.getText().toString();
                    String pass = password.getText().toString();

                    if (!loginStr.isEmpty() && !pass.isEmpty()) {
                        loginViewModel.login(loginStr, password.getText().toString());
                    } else {
                        ToastUtil.show(this, getString(R.string.login_error));
                    }
                }

        );

        observeViewModel();
    }

    private void observeViewModel() {
        profileViewModel.responseLiveData
                .observe(this, profile -> {
                    hideProgressDialog();
                    Pref.getInstance().setUserFirstLaunch(this, false);

                    checkUserType(profile);
                });

        profileViewModel.contentState.observe(this,
                contentState -> {
                    if (contentState.isLoading()) {
                        showProgressDialog();
                    } else {
                        hideProgressDialog();
                    }
                });

        profileViewModel.errorMessage.observe(this,
                error -> {
                    if (error.contains("logged_in_from_another_device")) {
                        MaterialAlertDialogBuilder builder =
                                new MaterialAlertDialogBuilder(this);
                        builder.setTitle(getString(R.string.account_title));
                        builder.setMessage(getString(R.string.account_session_error));
                        builder.show();
                    } else {
                        ToastUtil.show(this, error);
                    }
                });

        loginViewModel.login
                .observe(this,
                        loginData -> profileViewModel.getProfile());

        loginViewModel.contentState
                .observe(this, contentState -> {
                    if (contentState.isLoading()) {
                        showProgressDialog();
                    } else {
                        hideProgressDialog();
                    }
                });

        loginViewModel.error.observe(this,
                error -> {
                    if (error.contains("logged_in_from_another_device")) {
                        MaterialAlertDialogBuilder builder =
                                new MaterialAlertDialogBuilder(this);
                        builder.setTitle(getString(R.string.account_title));
                        builder.setMessage(getString(R.string.account_session_error));
                        builder.show();
                    } else {
                        ToastUtil.show(this, error);
                    }
                });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String loginStr = login.getText().toString();
            String pass = password.getText().toString();

            if (!loginStr.isEmpty() && !pass.isEmpty()) {
                binding.btnLogin.setEnabled(true);
            } else {
                binding.btnLogin.setEnabled(false);
            }
        }
    };

    private void checkUserType(Profile profile) {
        Bundle result = new Bundle();

        String role = profile.getRoleKey();

//        boolean isPinEnabled = Pref.getInstance().isPinEnabled();

        Intent intent = new Intent(this, MainActivity.class);

        if (role.toLowerCase().equals(UserType.DOCTOR.toString())) {
            result.putString(BUNDLE_KEY, "doctor");
//            navigator.goToDoctorDashboard(navController);

            startActivity(intent);
            finish();
        } else if (role.toLowerCase().equals(UserType.REGIONAL.toString())) {
            result.putString(BUNDLE_KEY, "regional");
//            navigator.goToRegionalDashboard(navController);

        } else if (role.toLowerCase().equals(UserType.MEDICAL.toString())) {
//            navigator.goToMedicalDashboard(navController);
            result.putString(BUNDLE_KEY, "medical");
        }

//        if (isPinEnabled) {
//            getParentFragmentManager().setFragmentResult(REQUEST_KEY, result);
//            navigator.goToPin(navController);
//        }
    }


}