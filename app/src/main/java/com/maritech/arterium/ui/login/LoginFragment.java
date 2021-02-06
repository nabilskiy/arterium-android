package com.maritech.arterium.ui.login;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

import com.maritech.arterium.R;
import com.maritech.arterium.data.models.Profile;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.base.BaseViewModel;
import com.maritech.arterium.ui.my_profile_doctor.ProfileViewModel;
import com.maritech.arterium.utils.ToastUtil;

import org.jetbrains.annotations.NotNull;

public class LoginFragment extends BaseFragment {

    static final String BUNDLE_KEY = "login";
    static final String REQUEST_KEY = "requestLoginKey";

    CheckBox checkBox;
    EditText password;
    EditText login;
    TextView btnLogin;

    LoginNavigator navigator = new LoginNavigator();
    LoginViewModel viewModel = new LoginViewModel();
    ProfileViewModel profileViewModel = new ProfileViewModel();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        checkBox = root.findViewById(R.id.cbShowHide);
        password = root.findViewById(R.id.etInputPassword);
        login = root.findViewById(R.id.etInputLogin);
        btnLogin = root.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            viewModel.login(login.getText().toString(), password.getText().toString());
        });

        checkBox.setOnClickListener(v -> {
            if (checkBox.isChecked()) {
                password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                password.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });

        requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        observeViewModel();
    }

    private void observeViewModel() {
        LifecycleOwner lifecycleOwner = getViewLifecycleOwner();

        profileViewModel.responseLiveData
                .observe(lifecycleOwner, profile -> {
                    hideProgressDialog();
                    Pref.getInstance().setUserFirstLaunch(getContext(), false);

                    checkUserType(profile);
                });

        profileViewModel.error.observe(lifecycleOwner,
                error -> {
                    hideProgressDialog();
                    ToastUtil.show(requireContext(), error);
                });

        viewModel.responseLiveData
                .observe(lifecycleOwner,
                        loginData -> {
                            Log.e("Login", "Logged Success");
                            profileViewModel.getProfile();
                        });

        viewModel.loading
                .observe(lifecycleOwner, isLoading -> {
                    if (isLoading) {
                        showProgressDialog();
                    }
                });

        viewModel.error.observe(lifecycleOwner,
                error -> {
                    hideProgressDialog();
                    ToastUtil.show(requireContext(), error);
                });
    }

    private void checkUserType(Profile profile) {
        Bundle result = new Bundle();

        String role = profile.getRoleKey();

//        boolean isPinEnabled = Pref.getInstance().isPinEnabled();

        if (role.toLowerCase().equals(UserType.DOCTOR.toString())) {
            result.putString(BUNDLE_KEY, "doctor");
            navigator.goToDoctorDashboard(navController);

        } else if (role.toLowerCase().equals(UserType.REGIONAL.toString())) {
            result.putString(BUNDLE_KEY, "regional");
            navigator.goToRegionalDashboard(navController);

        } else if (role.toLowerCase().equals(UserType.MEDICAL.toString())) {
            navigator.goToMedicalDashboard(navController);
            result.putString(BUNDLE_KEY, "medical");
        }

        //TODO Pin Function
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