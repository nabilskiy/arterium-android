package com.maritech.arterium.ui.notifications.data;

public class UnreadNotificationsContent {

    String message;
    String data;

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


}
