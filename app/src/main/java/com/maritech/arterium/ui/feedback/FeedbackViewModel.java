package com.maritech.arterium.ui.feedback;

import androidx.lifecycle.ViewModel;
import com.maritech.arterium.common.ContentState;
import com.maritech.arterium.data.models.BaseResponse;
import com.maritech.arterium.data.network.ArteriumDataProvider;
import com.maritech.arterium.data.network.DataProvider;
import com.maritech.arterium.ui.base.SingleLiveEvent;

import okhttp3.RequestBody;

public class FeedbackViewModel extends ViewModel {

    public SingleLiveEvent<BaseResponse> responseLiveData = new SingleLiveEvent<>();
    public SingleLiveEvent<ContentState> contentState = new SingleLiveEvent<>();
    public SingleLiveEvent<String> errorMessage = new SingleLiveEvent<>();

    private final DataProvider model;

    public FeedbackViewModel() {
        model = ArteriumDataProvider.getInstance();
    }

    public void sendFeedback(RequestBody body) {
        contentState.postValue(ContentState.LOADING);

        model.sendFeedback(body)
                .subscribe(data -> responseLiveData.postValue(data),
                        throwable -> {
                            contentState.postValue(ContentState.ERROR);
                            errorMessage.postValue(throwable.getMessage());
                        }
                );

    }

}