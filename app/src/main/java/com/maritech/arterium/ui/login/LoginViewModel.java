package com.maritech.arterium.ui.login;

import com.maritech.arterium.data.models.LoginData;
import com.maritech.arterium.data.network.ArteriumDataProvider;
import com.maritech.arterium.data.network.DataProvider;
import com.maritech.arterium.ui.base.BaseViewModel;

public class LoginViewModel extends BaseViewModel<LoginData> {

    private final DataProvider model;

    public LoginViewModel() {
        model = ArteriumDataProvider.getInstance();
    }

    void login(String login, String password) {
        loading.postValue(true);
        model.login(login, password)
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
