package com.maritech.arterium.ui.my_profile_doctor;

import com.maritech.arterium.data.models.Profile;
import com.maritech.arterium.data.network.ArteriumDataProvider;
import com.maritech.arterium.data.network.DataProvider;
import com.maritech.arterium.ui.base.BaseViewModel;

public class ProfileViewModel extends BaseViewModel<Profile> {

    private final DataProvider model;

    public ProfileViewModel() {
        model = ArteriumDataProvider.getInstance();
    }

    public void getProfile() {
        loading.postValue(true);
        model.getProfile()
                .subscribe(
                        data -> {
                            loading.postValue(false);
                            responseLiveData.postValue(data.getData());
                        },
                        throwable -> {
                          loading.postValue(false);
                            error.postValue(throwable.getMessage());
                        }
                );
    }
}
