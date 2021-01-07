package com.maritech.arterium.ui.fragment.loginSplash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseFragment;

public class LoginSplashFragment extends BaseFragment {

    LoginSplashNavigator navigator = new LoginSplashNavigator();

    TextView btnLogin;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login_splash, container, false);

        btnLogin = root.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.goToLogin(navController);
            }
        });

        //requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
        return root;
    }

}
