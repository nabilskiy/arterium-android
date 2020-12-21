package com.maritech.arterium.ui.fragment.settings;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.login.LoginNavigator;

public class SettingsFragment extends BaseFragment {

   // ImageView arrow;
    ImageView ivGoChangeLanguage;


    SettingsNavigator navigator = new SettingsNavigator();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

       // arrow = root.findViewById(R.id.header).findViewById(R.id.ivArrow);
        ivGoChangeLanguage = root.findViewById(R.id.ivGoChangeLanguage);

        ivGoChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.goToChangeLanguage(navController);
            }
        });
        requireActivity().findViewById(R.id.nav_view).setVisibility(View.VISIBLE);
        return root;
    }
}
