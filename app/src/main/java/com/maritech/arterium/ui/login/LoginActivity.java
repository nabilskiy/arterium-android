package com.maritech.arterium.ui.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.maritech.arterium.BuildConfig;
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

    private static final String TAG = "LoginActivity_TAG";
    public static final String BUNDLE_KEY = "login";
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
        Log.i(TAG, "onCreate: ");
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
                    String loginStr = login.getText().toString().trim();
                    String pass = password.getText().toString().trim();
                    if (!loginStr.isEmpty() && !pass.isEmpty()) {
                        loginViewModel.login(loginStr, password.getText().toString());
                    } else {
                        ToastUtil.show(this, getString(R.string.login_error));
                    }
                }

        );
        debugListener();
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
                    ToastUtil.show(this, error);
//                    if (error.contains("logged_in_from_another_device")) {
//                        MaterialAlertDialogBuilder builder =
//                                new MaterialAlertDialogBuilder(this);
//                        builder.setTitle(getString(R.string.account_title));
//                        builder.setMessage(getString(R.string.account_session_error));
//                        builder.show();
//                    } else {
//                        ToastUtil.show(this, getString(R.string.login_response_error));
//                    }
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
                    ToastUtil.show(this, error);
//                    if (error.contains("logged_in_from_another_device")) {
//                        MaterialAlertDialogBuilder builder =
//                                new MaterialAlertDialogBuilder(this);
//                        builder.setTitle(getString(R.string.account_title));
//                        builder.setMessage(getString(R.string.account_session_error));
//                        builder.show();
//                    } else {
//                        if (error.contains("422")) {
//                            ToastUtil.show(this, getString(R.string.login_response_error));
//                        } else
//                            ToastUtil.show(this, error);
//                    }
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

    private void debugListener() {
        if (BuildConfig.DEBUG) {
            binding.etInputLogin.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    binding.etInputPassword.setText(s);
                }
            });
        }
    }

    private void checkUserType(Profile profile) {
        Bundle result = new Bundle();
        String role = profile.getRoleKey().toLowerCase();
        Log.i(TAG, "checkUserType: " + role);
//        boolean isPinEnabled = Pref.getInstance().isPinEnabled();
        Intent intent = new Intent(this, MainActivity.class);
        if (role.equals(UserType.DOCTOR.toString())) {
            intent.putExtra(BUNDLE_KEY, UserType.DOCTOR.toString());
            Pref.getInstance().setDrugProgramId(this, profile.getDrugPrograms().get(0).getId());
//            navigator.goToDoctorDashboard(navController);
        } else if (role.equals(UserType.REGIONAL.toString())) {
            intent.putExtra(BUNDLE_KEY, UserType.REGIONAL.toString());
//            navigator.goToRegionalDashboard(navController);
        } else if (role.equals(UserType.MEDICAL.toString())) {
//            navigator.goToMedicalDashboard(navController);
            intent.putExtra(BUNDLE_KEY, UserType.MEDICAL.toString());
        }
        Pref.getInstance().setRole(this, role);
        startActivity(intent);
        finish();
//        if (isPinEnabled) {
//            getParentFragmentManager().setFragmentResult(REQUEST_KEY, result);
//            navigator.goToPin(navController);
//        }
    }


}