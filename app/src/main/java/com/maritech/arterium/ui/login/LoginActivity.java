package com.maritech.arterium.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.maritech.arterium.R;
import com.maritech.arterium.data.models.Profile;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.databinding.ActivityLoginBinding;
import com.maritech.arterium.ui.MainActivity;
import com.maritech.arterium.ui.base.BaseActivity;
import com.maritech.arterium.ui.my_profile_doctor.ProfileViewModel;
import com.maritech.arterium.utils.ToastUtil;

import org.jetbrains.annotations.NotNull;

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
                v -> loginViewModel.login(login.getText().toString(), password.getText().toString())
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
                        builder.setTitle("Авторизація");
                        builder.setMessage("Вхід був проведений за допомогою іншого пристрою");
                        builder.show();
                    } else {
                        ToastUtil.show(this, error);
                    }
                });

        loginViewModel.login
                .observe(this,
                        loginData -> {
                            Log.e("Login", "Logged Success");
                            profileViewModel.getProfile();
                        });

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
                        builder.setTitle("Авторизація");
                        builder.setMessage("Вхід був проведений за допомогою іншого пристрою");
                        builder.show();
                    } else {
                        ToastUtil.show(this, error);
                    }
                });
    }

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

    public enum UserType {
        DOCTOR("doctor"),
        MEDICAL("medical"),
        REGIONAL("regional");

        private final String type;

        UserType(String stringVal) {
            type = stringVal;
        }

        @NotNull
        public String toString() {
            return type;
        }

        public static String getEnumByString(String code) {
            for (UserType e : UserType.values()) {
                if (e.name().equals(code)) return e.name();
            }
            return null;
        }
    }
}