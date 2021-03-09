package com.maritech.arterium.ui.notifications;

import androidx.lifecycle.ViewModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.maritech.arterium.common.ContentState;
import com.maritech.arterium.data.models.NotificationResponse;
import com.maritech.arterium.data.network.ArteriumDataProvider;
import com.maritech.arterium.data.network.DataProvider;
import com.maritech.arterium.ui.base.SingleLiveEvent;

public class NotificationsViewModel extends ViewModel {

    public SingleLiveEvent<NotificationResponse> responseLiveData = new SingleLiveEvent<>();
    public SingleLiveEvent<ContentState> contentState = new SingleLiveEvent<>();
    public SingleLiveEvent<String> errorMessage = new SingleLiveEvent<>();
    public SingleLiveEvent<NotificationResponse.Data> readLiveData = new SingleLiveEvent<>();
    public SingleLiveEvent<ContentState> readContentState = new SingleLiveEvent<>();
    public SingleLiveEvent<String> readErrorMessage = new SingleLiveEvent<>();

    private final DataProvider model;

    public NotificationsViewModel() {
        model = ArteriumDataProvider.getInstance();
    }

    public void getNotifications() {
        contentState.postValue(ContentState.LOADING);

        model.getNotifications()
                .subscribe(data -> {
                            if (data != null && data.getData() != null &&
                                    !data.getData().isEmpty()) {
                                contentState.postValue(ContentState.CONTENT);
                                responseLiveData.postValue(data);
                            } else {
                                contentState.postValue(ContentState.EMPTY);
                            }

                        },
                        throwable -> {
                            contentState.postValue(ContentState.ERROR);
                            errorMessage.postValue(throwable.getMessage());
                        }
                );

    }

    public void readNotification(NotificationResponse.Data data) {

        JsonObject body = new JsonObject();
        JsonArray ids = new JsonArray();
        ids.add(data.getMessage());
        body.add("ids", ids);

        model.readNotification(body)
                .subscribe(response -> {
                            data.setRead(response.isSuccess());
                            readLiveData.postValue(data);
                        },
                        throwable -> {
                            data.setRead(false);
                            readLiveData.postValue(data);
                            readContentState.postValue(ContentState.ERROR);
                            readErrorMessage.postValue(throwable.getMessage());
                        }
                );

    }

}