package com.maritech.arterium.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maritech.arterium.R;
import com.maritech.arterium.databinding.ItemAchievementsBinding;
import com.maritech.arterium.databinding.ItemNotificationsReadBinding;
import com.maritech.arterium.databinding.ItemNotificationsUnreadBinding;
import com.maritech.arterium.ui.achievements.data.AchievementsContent;
import com.maritech.arterium.ui.base.BaseAdapter;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.notifications.data.ReadNotificationsContent;
import com.maritech.arterium.ui.notifications.data.UnreadNotificationsContent;

import java.util.ArrayList;

public class NotificationsFragment extends BaseFragment {

    private TextView tvUnreadCount;

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        ArrayList<UnreadNotificationsContent> dataListUnread = new ArrayList<UnreadNotificationsContent>();
        prepareListUnread(dataListUnread);
        ArrayList<ReadNotificationsContent> dataListRead = new ArrayList<ReadNotificationsContent>();
        prepareListRead(dataListRead);

        tvUnreadCount = root.findViewById(R.id.tvUnreadCount);

        BaseAdapter adapterUnread = new BaseAdapter(ItemNotificationsUnreadBinding.class, UnreadNotificationsContent.class);
        RecyclerView rvUnread = (RecyclerView) root.findViewById(R.id.rvUnread);
        rvUnread.setAdapter(adapterUnread);
        adapterUnread.setDataList(dataListUnread);

        BaseAdapter adapterRead = new BaseAdapter(ItemNotificationsReadBinding.class, ReadNotificationsContent.class);
        RecyclerView rvRead = (RecyclerView) root.findViewById(R.id.rvRead);
        rvRead.setAdapter(adapterRead);
        adapterRead.setDataList(dataListRead);



        requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
        return root;
    }

    private void prepareListUnread(ArrayList<UnreadNotificationsContent> dataList) {
        dataList.add(new UnreadNotificationsContent("Пациент Евгений Петров  ", "12:48"));
        dataList.add(new UnreadNotificationsContent("Пациент Евгений Петров .  ", "12:48"));
        dataList.add(new UnreadNotificationsContent("Пациент Евгений Петров принимает препи на дозу 50 мг.  ", "12:48"));
        dataList.add(new UnreadNotificationsContent("Пациент Евгений Петров приниьте перевести на дозу 50 мг.  ", "12:48"));
        dataList.add(new UnreadNotificationsContent("Пациент Евгений Петров принимает препарат уже более месяца, не забудьте перевести на дозу 50 мг.  ", "12:48"));
        dataList.add(new UnreadNotificationsContent("Пациент Евгений Петров принимает преп перевести на дозу 50 мг.  ", "12:48"));
        dataList.add(new UnreadNotificationsContent("Пациент Евгений Петров принимает препар перевести на дозу 50 мг.  ", "12:48"));
        dataList.add(new UnreadNotificationsContent("Пациент Евгений Петров принимае, не забудьте перевести на дозу 50 мг.  ", "12:48"));
        dataList.add(new UnreadNotificationsContent("Пациент Евгений Петров принимает препе забудьте перевести на дозу 50 мг.  ", "12:48"));
    }

    private void prepareListRead(ArrayList<ReadNotificationsContent> dataList) {
        dataList.add(new ReadNotificationsContent("Пациент Евгений Петров  ", "12:48"));
        dataList.add(new ReadNotificationsContent("Пациент Евгений Петров .  ", "12:48"));
        dataList.add(new ReadNotificationsContent("Пациент Евгений Петров принимает препи на дозу 50 мг.  ", "12:48"));
        dataList.add(new ReadNotificationsContent("Пациент Евгений Петров приниьте перевести на дозу 50 мг.  ", "12:48"));
        dataList.add(new ReadNotificationsContent("Пациент Евгений Петров принимает препарат уже более месяца, не забудьте перевести на дозу 50 мг.  ", "12:48"));
        dataList.add(new ReadNotificationsContent("Пациент Евгений Петров принимает преп перевести на дозу 50 мг.  ", "12:48"));
        dataList.add(new ReadNotificationsContent("Пациент Евгений Петров принимает препар перевести на дозу 50 мг.  ", "12:48"));
        dataList.add(new ReadNotificationsContent("Пациент Евгений Петров принимае, не забудьте перевести на дозу 50 мг.  ", "12:48"));
        dataList.add(new ReadNotificationsContent("Пациент Евгений Петров принимает препе забудьте перевести на дозу 50 мг.  ", "12:48"));
    }

}