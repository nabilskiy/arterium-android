package com.maritech.arterium.ui;

import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.maritech.arterium.common.ContentState;
import com.maritech.arterium.data.models.BaseResponse;
import com.maritech.arterium.data.network.ArteriumDataProvider;
import com.maritech.arterium.data.network.DataProvider;
import com.maritech.arterium.ui.base.SingleLiveEvent;

public class FirebaseViewModel extends ViewModel {

    public SingleLiveEvent<BaseResponse> responseLiveData = new SingleLiveEvent<>();
    public SingleLiveEvent<ContentState> contentState = new SingleLiveEvent<>();
    public SingleLiveEvent<String> errorMessage = new SingleLiveEvent<>();

    private final DataProvider model;

    public FirebaseViewModel() {
        model = ArteriumDataProvider.getInstance();
    }

    public void sendFirebaseToken(JsonObject body) {
        contentState.postValue(ContentState.LOADING);
        model.sendFirebaseToken(body).subscribe(data -> responseLiveData.postValue(data),
                throwable -> {
                    contentState.postValue(ContentState.ERROR);
                    errorMessage.postValue(throwable.getMessage());
                }
        );
    }

}
