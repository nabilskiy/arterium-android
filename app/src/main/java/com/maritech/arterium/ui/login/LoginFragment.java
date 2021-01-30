package com.maritech.arterium.ui.login;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.maritech.arterium.R;
import com.maritech.arterium.data.models.LoginData;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.choose_doctor.data.ChooseDoctorContent;
import com.maritech.arterium.ui.fragment.loginSplash.LoginSplashNavigator;

import java.util.ArrayList;

public class LoginFragment extends BaseFragment {

    static final String BUNDLE_KEY = "login";
    static final String REQUEST_KEY = "requestLoginKey";

    CheckBox checkBox;
    EditText password;
    EditText login;
    TextView btnLogin;

    LoginNavigator navigator = new LoginNavigator();
    LoginViewModel viewModel = new LoginViewModel();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        checkBox = root.findViewById(R.id.cbShowHide);
        password = root.findViewById(R.id.etInputPassword);
        login  = root.findViewById(R.id.etInputLogin);
        btnLogin = root.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {

            Bundle result = new Bundle();


            viewModel.login(login.getText().toString(), password.getText().toString());

//            if(String.valueOf(login.getText()).equals("doctor") && String.valueOf(password.getText()).equals("test1")){
//                result.putString(BUNDLE_KEY, "doctor");
//            }else if(String.valueOf(login.getText()).equals("regional") && String.valueOf(password.getText()).equals("test2")){
//                result.putString(BUNDLE_KEY, "regional");
//            }else if(String.valueOf(login.getText()).equals("medical") && String.valueOf(password.getText()).equals("test3")){
//                result.putString(BUNDLE_KEY, "medical");
//            }else {return;}
//
//            getParentFragmentManager().setFragmentResult(REQUEST_KEY, result);
//            navigator.goToPin(navController);
        });


        checkBox.setOnClickListener(v -> {
            if (checkBox.isChecked()) {
                password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
        requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.ldData.observe(getViewLifecycleOwner(), loginData -> {
            Pref.getInstance().saveAuthtoken(getContext(), loginData.getAccessToken());
            navigator.goToDoctorDashboard(navController);
        });
    }
}