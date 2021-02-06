package com.maritech.arterium.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.iielse.switchbutton.SwitchView;
import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseFragment;

public class SettingsFragment extends BaseFragment {

    // ImageView arrow;
    ImageView ivGoChangeLanguage;

    TextView tvAskPinCode;
    TextView tvUseBio;
    TextView tvGetPushNotifications;
    TextView tvSetLanguage;

    SwitchView switchAskPinCode;
    SwitchView switchUseBio;
    SwitchView switchGetPushNotifications;
    ImageView ivBack;
    View toolbar;


    SettingsNavigator navigator = new SettingsNavigator();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        // arrow = root.findViewById(R.id.header).findViewById(R.id.ivArrow);
        ivGoChangeLanguage = root.findViewById(R.id.ivGoChangeLanguage);
        tvSetLanguage = root.findViewById(R.id.tvSetLanguage);
        tvAskPinCode = root.findViewById(R.id.tvAskPinCode);
        tvUseBio = root.findViewById(R.id.tvUseBio);
        tvGetPushNotifications = root.findViewById(R.id.tvGetPushNotifications);
        toolbar = root.findViewById(R.id.header);
        ivBack = toolbar.findViewById(R.id.ivBack);


        switchAskPinCode = root.findViewById(R.id.switchAskPinCode);
        switchUseBio = root.findViewById(R.id.switchUseBio);
        switchGetPushNotifications = root.findViewById(R.id.switchGetPushNotifications);

        tvAskPinCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchAskPinCode.isOpened()) {
                    switchAskPinCode.setOpened(false);
                } else {
                    switchAskPinCode.setOpened(true);
                }
            }
        });

        tvUseBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchUseBio.isOpened()) {
                    switchUseBio.setOpened(false);
                } else {
                    switchUseBio.setOpened(true);
                }
            }
        });

        tvGetPushNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchGetPushNotifications.isOpened()) {
                    switchGetPushNotifications.setOpened(false);
                } else {
                    switchGetPushNotifications.setOpened(true);
                }
            }
        });

        tvSetLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.goToChangeLanguage(navController);
            }
        });

        ivGoChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.goToChangeLanguage(navController);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });

        requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
        return root;
    }
}
