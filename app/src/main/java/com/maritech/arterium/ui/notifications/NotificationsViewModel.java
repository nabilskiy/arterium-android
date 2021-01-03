package com.maritech.arterium.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.maritech.arterium.ui.notifications.data.ReadNotificationsContent;
import com.maritech.arterium.ui.notifications.data.UnreadNotificationsContent;

import java.util.ArrayList;

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    MutableLiveData<Integer> newNotificationsCount = new MutableLiveData<>();
    MutableLiveData<ArrayList<UnreadNotificationsContent>> unreadNotificationsList = new MutableLiveData<>(prepareListUnread());
    MutableLiveData<ArrayList<ReadNotificationsContent>> readNotificationsList = new MutableLiveData<>(prepareListRead());



    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }


    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<ArrayList<UnreadNotificationsContent>> getUnreadList() {
        return unreadNotificationsList;
    }
    public LiveData<ArrayList<ReadNotificationsContent>> getReadList() {
        return readNotificationsList;
    }

    public void addNewReadNotifications(UnreadNotificationsContent content, int index){
        ArrayList<ReadNotificationsContent> dataListRead = new ArrayList<>(readNotificationsList.getValue());
        dataListRead.add(new ReadNotificationsContent(content.getMessage(), content.getData()));
        readNotificationsList.setValue(dataListRead);

        ArrayList<UnreadNotificationsContent> dataListUnread = new ArrayList<>(unreadNotificationsList.getValue());
        dataListUnread.remove(index);
        unreadNotificationsList.setValue(dataListUnread);
    }

    private  ArrayList<UnreadNotificationsContent> prepareListUnread() {
        ArrayList<UnreadNotificationsContent> dataListUnread = new ArrayList<UnreadNotificationsContent>();
        dataListUnread.add(new UnreadNotificationsContent("Пациент Евгений Петров  ", "12:48"));
        dataListUnread.add(new UnreadNotificationsContent("Пациент Евгений Петров .  ", "12:48"));
        dataListUnread.add(new UnreadNotificationsContent("Пациент Евгений Петров принимает препи на дозу 50 мг.  ", "12:48"));
        dataListUnread.add(new UnreadNotificationsContent("Пациент Евгений Петров приниьте перевести на дозу 50 мг.  ", "12:48"));
        dataListUnread.add(new UnreadNotificationsContent("Пациент Евгений Петров принимает препарат уже более месяца, не забудьте перевести на дозу 50 мг.  ", "12:48"));
        dataListUnread.add(new UnreadNotificationsContent("Пациент Евгений Петров принимает преп перевести на дозу 50 мг.  ", "12:48"));
        dataListUnread.add(new UnreadNotificationsContent("Пациент Евгений Петров принимает препар перевести на дозу 50 мг.  ", "12:48"));
        dataListUnread.add(new UnreadNotificationsContent("Пациент Евгений Петров принимае, не забудьте перевести на дозу 50 мг.  ", "12:48"));
        dataListUnread.add(new UnreadNotificationsContent("Пациент Евгений Петров принимает препе забудьте перевести на дозу 50 мг.  ", "12:48"));
        return dataListUnread;
    }

    private ArrayList<ReadNotificationsContent> prepareListRead() {
        ArrayList<ReadNotificationsContent> dataListRead = new ArrayList<ReadNotificationsContent>();
        dataListRead.add(new ReadNotificationsContent("Пациент Евгений Петров  ", "12:48"));
        dataListRead.add(new ReadNotificationsContent("Пациент Евгений Петров .  ", "12:48"));
        dataListRead.add(new ReadNotificationsContent("Пациент Евгений Петров принимает препи на дозу 50 мг.  ", "12:48"));
        dataListRead.add(new ReadNotificationsContent("Пациент Евгений Петров приниьте перевести на дозу 50 мг.  ", "12:48"));
        dataListRead.add(new ReadNotificationsContent("Пациент Евгений Петров принимает препарат уже более месяца, не забудьте перевести на дозу 50 мг.  ", "12:48"));
        dataListRead.add(new ReadNotificationsContent("Пациент Евгений Петров принимает преп перевести на дозу 50 мг.  ", "12:48"));
        dataListRead.add(new ReadNotificationsContent("Пациент Евгений Петров принимает препар перевести на дозу 50 мг.  ", "12:48"));
        dataListRead.add(new ReadNotificationsContent("Пациент Евгений Петров принимае, не забудьте перевести на дозу 50 мг.  ", "12:48"));
        dataListRead.add(new ReadNotificationsContent("Пациент Евгений Петров принимает препе забудьте перевести на дозу 50 мг.  ", "12:48"));
        return dataListRead;
    }
}