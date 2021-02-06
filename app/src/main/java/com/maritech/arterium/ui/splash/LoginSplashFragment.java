package com.maritech.arterium.ui.splash;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.maritech.arterium.R;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.ui.base.BaseFragment;

import java.util.UUID;

public class LoginSplashFragment extends BaseFragment {

    LoginSplashNavigator navigator = new LoginSplashNavigator();

    TextView btnLogin;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login_splash, container, false);

        btnLogin = root.findViewById(R.id.btnLogin);

//        Pref.getInstance().setDeviceUUID(getActivity(), "5104f417-7ee6-48e5-818b-62c7b9a0a894");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFirstLaunch = Pref.getInstance().isUserFirstLaunch(getActivity());
                if (isFirstLaunch) {
                    navigator.goToLogin(navController);
                } else {
                    navigator.goToDoctorDashboard(navController);
                }
            }
        });

        //requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
        return root;
    }

}
