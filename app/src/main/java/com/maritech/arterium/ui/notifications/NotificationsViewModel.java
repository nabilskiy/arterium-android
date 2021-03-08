package com.maritech.arterium.ui.notifications;

import androidx.lifecycle.ViewModel;

import com.maritech.arterium.common.ContentState;
import com.maritech.arterium.data.models.NotificationResponse;
import com.maritech.arterium.data.network.ArteriumDataProvider;
import com.maritech.arterium.data.network.DataProvider;
import com.maritech.arterium.ui.base.SingleLiveEvent;

public class NotificationsViewModel extends ViewModel {

    public SingleLiveEvent<NotificationResponse> responseLiveData = new SingleLiveEvent<>();
    public SingleLiveEvent<ContentState> contentState = new SingleLiveEvent<>();
    public SingleLiveEvent<String> errorMessage = new SingleLiveEvent<>();

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

}