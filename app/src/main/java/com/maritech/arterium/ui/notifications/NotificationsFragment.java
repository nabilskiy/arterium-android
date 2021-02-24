package com.maritech.arterium.ui.notifications;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.maritech.arterium.R;
import com.maritech.arterium.databinding.FragmentNotificationsBinding;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.notifications.data.NotificationsContent;
import com.maritech.arterium.ui.notifications.holder.NotificationsAdapter;
import java.util.ArrayList;

public class NotificationsFragment extends BaseFragment<FragmentNotificationsBinding> {

    private NotificationsViewModel notificationsViewModel;

    private NotificationsAdapter adapterUnread;
    private NotificationsAdapter adapterRead;

    @Override
    protected int getContentView() {
        return R.layout.fragment_notifications;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding.toolbar.ivRight.setVisibility(View.GONE);
        binding.toolbar.ivArrow.setVisibility(View.GONE);
        binding.toolbar.tvToolbarTitle.setText("Повідомлення");

        RecyclerView rvUnread = root.findViewById(R.id.rvUnread);
        RecyclerView rvRead = root.findViewById(R.id.rvRead);

        ArrayList<NotificationsContent> dataListUnread = new ArrayList<>();
        ArrayList<NotificationsContent> dataListRead = new ArrayList<>();
        prepareListUnread(dataListUnread);
        prepareListRead(dataListRead);

        adapterUnread = new NotificationsAdapter(dataListUnread, (position, object) -> {
            dataListUnread.remove(object);
            changeValueUnreadNotifications(dataListUnread.size());
            dataListRead.add(0, object);
            adapterRead.notifyDataSetChanged();
            if(dataListUnread.isEmpty()){
                binding.clNewNotifications.setVisibility(View.GONE);
            }
        });

        adapterRead = new NotificationsAdapter(dataListRead, (position, object) -> {
            //selectedObject = object;
        });

        rvUnread.setAdapter(adapterUnread);
        rvRead.setAdapter(adapterRead);

        binding.toolbar.ivArrow.setOnClickListener(v -> requireActivity().onBackPressed());

        changeValueUnreadNotifications(dataListUnread.size());
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
        binding.tvUnreadCount.setText(String.valueOf(newValue));
    }

}