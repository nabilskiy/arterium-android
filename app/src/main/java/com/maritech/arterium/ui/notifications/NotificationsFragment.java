package com.maritech.arterium.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.notifications.data.NotificationsContent;
import com.maritech.arterium.ui.notifications.holder.NotificationsAdapter;

import java.util.ArrayList;

public class NotificationsFragment extends BaseFragment {
    private NotificationsViewModel notificationsViewModel;

    private TextView tvUnreadCount;
    private ConstraintLayout clNewNotifications;
    private NotificationsAdapter adapterUnread;
    private NotificationsAdapter adapterRead;

    private View toolbar;
    private TextView toolbarTittle;
    private ImageView ivBack;
    private ImageView ivRightToolbar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        tvUnreadCount = root.findViewById(R.id.tvUnreadCount);
        clNewNotifications = root.findViewById(R.id.clNewNotifications);

        toolbar = root.findViewById(R.id.toolbar);
        toolbarTittle = toolbar.findViewById(R.id.tvToolbarTitle);
        ivBack = toolbar.findViewById(R.id.ivArrow);
        ivRightToolbar = toolbar.findViewById(R.id.ivRight);

        ivRightToolbar.setVisibility(View.GONE);
        toolbarTittle.setText("Повідомлення");

        RecyclerView rvUnread = (RecyclerView) root.findViewById(R.id.rvUnread);
        RecyclerView rvRead = (RecyclerView) root.findViewById(R.id.rvRead);

        ArrayList<NotificationsContent> dataListUnread = new ArrayList<>();
        ArrayList<NotificationsContent> dataListRead = new ArrayList<>();
        prepareListUnread(dataListUnread);
        prepareListRead(dataListRead);

        adapterUnread = new NotificationsAdapter(dataListUnread, new NotificationsAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, NotificationsContent object) {
                dataListUnread.remove(object);
                changeValueUnreadNotifications(dataListUnread.size());
                dataListRead.add(0, object);
                adapterRead.notifyDataSetChanged();
                if(dataListUnread.isEmpty()){
                    clNewNotifications.setVisibility(View.GONE);
                }
            }
        });

        adapterRead = new NotificationsAdapter(dataListRead, new NotificationsAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, NotificationsContent object) {
                //selectedObject = object;
            }
        });

        rvUnread.setAdapter(adapterUnread);
        rvRead.setAdapter(adapterRead);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });

        changeValueUnreadNotifications(dataListUnread.size());
        requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
        return root;
    }

    private void prepareListUnread(ArrayList<NotificationsContent> dataList) {
        dataList.add(new NotificationsContent("Пациент Евгений Новое Уведомление", "12:48"));
        dataList.add(new NotificationsContent("Пациент Евгений Новое Уведомление", "12:48"));
        dataList.add(new NotificationsContent("Пациент Евгений Новое Уведомление", "12:48"));
        dataList.add(new NotificationsContent("Пациент Евгений Новое Уведомление", "12:48"));
        dataList.add(new NotificationsContent("Пациент Евгений Новое Уведомление", "12:48"));
        dataList.add(new NotificationsContent("Пациент Евгений Новое Уведомление", "12:48"));
        dataList.add(new NotificationsContent("Пациент Евгений Новое Уведомление", "12:48"));
        dataList.add(new NotificationsContent("Пациент Евгений Новое Уведомление", "12:48"));
        dataList.add(new NotificationsContent("Пациент Евгений Новое Уведомление", "12:48"));
    }

    private void prepareListRead(ArrayList<NotificationsContent> dataList) {
        dataList.add(new NotificationsContent("Пациент Евгений Петров Старое Уведомление", "12:48"));
        dataList.add(new NotificationsContent("Пациент Евгений Петров Старое Уведомление", "12:48"));
        dataList.add(new NotificationsContent("Пациент Евгений Петров Старое Уведомление", "12:48"));
        dataList.add(new NotificationsContent("Пациент Евгений Петров Старое Уведомление", "12:48"));
        dataList.add(new NotificationsContent("Пациент Евгений Петров Старое Уведомление", "12:48"));

    }

    private void changeValueUnreadNotifications(int newValue) {
        tvUnreadCount.setText(String.valueOf(newValue));
    }

}