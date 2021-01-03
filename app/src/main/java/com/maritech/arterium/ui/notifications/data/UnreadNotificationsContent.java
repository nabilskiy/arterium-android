package com.maritech.arterium.ui.notifications.data;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.maritech.arterium.ui.notifications.NotificationsFragment;
import com.maritech.arterium.ui.notifications.NotificationsViewModel;

public class UnreadNotificationsContent {

    String message;
    String data;
    Boolean thisNotificationsIsRead = false;

    public UnreadNotificationsContent(String message, String data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void thisItemIsRead(View v) {
        thisNotificationsIsRead = true;
    }

    public Boolean getThisNotificationsIsRead(){
        return thisNotificationsIsRead;
    }

}
