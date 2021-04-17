package com.maritech.arterium.ui.my_profile_doctor;

import com.maritech.arterium.common.ContentState;
import com.maritech.arterium.data.models.Profile;
import com.maritech.arterium.data.network.ArteriumDataProvider;
import com.maritech.arterium.data.network.DataProvider;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.ui.base.BaseViewModel;
import com.maritech.arterium.ui.base.SingleLiveEvent;

public class ProfileViewModel extends BaseViewModel {

    public SingleLiveEvent<Profile> responseLiveData = new SingleLiveEvent<>();
    public SingleLiveEvent<ContentState> contentState = new SingleLiveEvent<>();
    public SingleLiveEvent<String> errorMessage = new SingleLiveEvent<>();

    private final DataProvider model;

    public ProfileViewModel() {
        model = ArteriumDataProvider.getInstance();
    }

    public void getProfile() {
        contentState.postValue(ContentState.LOADING);
        model.getProfile()
                .subscribe(
                        data -> {
                            if (data != null) {
                                contentState.postValue(ContentState.CONTENT);
                                responseLiveData.postValue(data.getData());
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
