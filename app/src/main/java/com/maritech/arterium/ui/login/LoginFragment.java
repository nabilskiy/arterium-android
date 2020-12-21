package com.maritech.arterium.ui.login;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.fragment.loginSplash.LoginSplashNavigator;

public class LoginFragment extends BaseFragment {

    CheckBox checkBox;
    EditText password;
    Button btnEnter;

    LoginNavigator navigator = new LoginNavigator();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        checkBox = root.findViewById(R.id.cbShowHide);
        password = root.findViewById(R.id.etInputPassword);
        btnEnter = root.findViewById(R.id.btnEnter);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.goToPin(navController);
            }
        });


        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
        return root;
    }
}