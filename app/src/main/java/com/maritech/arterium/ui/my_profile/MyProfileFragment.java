package com.maritech.arterium.ui.my_profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseFragment;

public class MyProfileFragment extends BaseFragment {

    ImageView arrow;
    ImageView edit;
    TextView toolbarTitle;
    ImageView imageNotification;
    ImageView imageSetting;
    ImageView imageContact;
    TextView notification;
    TextView contact;
    TextView setting;
    View myProfileMainContentSettings;
    View myProfileCard;
    View pharmacyList;
    View myNotifications;
    MyProfileNavigator navigator = new MyProfileNavigator();

    View navigation_statistics;
    View achievementsFragment;
    View myProfileFragment;
    View navigation_dashboard;

    @Override
    protected int getContentView() {
        return R.layout.fragment_my_profile;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        arrow = root.findViewById(R.id.myProfileToolbar).findViewById(R.id.ivArrow);
        toolbarTitle = root.findViewById(R.id.myProfileToolbar).findViewById(R.id.tvToolbarTitle);
        edit = root.findViewById(R.id.ivRight);
        myProfileMainContentSettings = root.findViewById(R.id.myProfileMainContentSettings);
        myProfileCard = root.findViewById(R.id.myProfileCard);
        pharmacyList = root.findViewById(R.id.pharmacyList);
        myNotifications = root.findViewById(R.id.myProfileMainContentNotifications);
        navigation_statistics = getActivity().findViewById(R.id.statistics);
        achievementsFragment = getActivity().findViewById(R.id.achievements);
        myProfileFragment = getActivity().findViewById(R.id.profile);
        navigation_dashboard = getActivity().findViewById(R.id.dashboard);

        edit.setVisibility(View.INVISIBLE);
        toolbarTitle.setText(R.string.my_profile);
        arrow.setVisibility(View.INVISIBLE);


        myProfileMainContentSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.goToSettings(navController);
            }
        });

        myProfileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Bundle bundle = new Bundle();
//                bundle.putParcelable(PatientCardFragment.PATIENT_MODEL_KEY, object);
                navigator.goPatientCard(navController);
            }
        });

        pharmacyList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.goToMap(navController);
            }
        });

        myNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.goToNotifications(navController);
            }
        });

        setMyProfileContentList(root);
    }

    public void setMyProfileContentList(View root){
        imageNotification = root.findViewById(R.id.myProfileMainContentNotifications).findViewById(R.id.ivMyProfileListIcon);
        imageSetting = root.findViewById(R.id.myProfileMainContentSettings).findViewById(R.id.ivMyProfileListIcon);
        imageContact = root.findViewById(R.id.contactWithUs).findViewById(R.id.ivMyProfileListIcon);

        notification = root.findViewById(R.id.myProfileMainContentNotifications).findViewById(R.id.tvMyProfileListTitle);
        contact = root.findViewById(R.id.contactWithUs).findViewById(R.id.tvMyProfileListTitle);
        setting = root.findViewById(R.id.myProfileMainContentSettings).findViewById(R.id.tvMyProfileListTitle);

        notification.setText(R.string.notification);
        contact.setText(R.string.contact_with_us);
        setting.setText(R.string.settings);

        imageNotification.setBackgroundResource(R.drawable.ic_bell);
        imageSetting.setBackgroundResource(R.drawable.ic_blue_settings);
        imageContact.setBackgroundResource(R.drawable.ic_blue_phone);
    }
}