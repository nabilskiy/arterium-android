package com.maritech.arterium.ui.login;

import androidx.lifecycle.MutableLiveData;

import com.maritech.arterium.common.ContentState;
import com.maritech.arterium.data.models.LoginData;
import com.maritech.arterium.data.network.ArteriumDataProvider;
import com.maritech.arterium.data.network.DataProvider;
import com.maritech.arterium.ui.base.BaseViewModel;

public class LoginViewModel extends BaseViewModel {

    public MutableLiveData<Boolean> logout = new MutableLiveData<>();
    public MutableLiveData<LoginData> login = new MutableLiveData<>();
    public MutableLiveData<ContentState> contentState = new MutableLiveData<>();
    public MutableLiveData<String> error = new MutableLiveData<>();

    private final DataProvider model;

    public LoginViewModel() {
        model = ArteriumDataProvider.getInstance();
    }

    public void login(String login, String password) {
        contentState.postValue(ContentState.LOADING);
        model.login(login, password)
                .subscribe(
                        data -> {
                            contentState.postValue(ContentState.CONTENT);
                            this.login.postValue(data.getData());
                        },
                        throwable -> {
                            contentState.postValue(ContentState.ERROR);
                            error.postValue(throwable.getMessage());
                        }
                );
    }

    public void logout() {
        contentState.postValue(ContentState.LOADING);
        model.logout()
                .subscribe(
                        data -> {
                            contentState.postValue(ContentState.CONTENT);
                            boolean isLoggedOut = data.isSuccess();
                            logout.postValue(isLoggedOut);
                        },
                        throwable -> {
                            contentState.postValue(ContentState.ERROR);
                            error.postValue(throwable.getMessage());
                            logout.postValue(true);
                        }
                );
    }
}
