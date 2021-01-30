package com.maritech.arterium.ui.login;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.maritech.arterium.data.models.LoginData;
import com.maritech.arterium.data.network.ArteriumDataProvider;
import com.maritech.arterium.data.network.DataProvider;

public class LoginViewModel extends ViewModel {
    private DataProvider model;

    MutableLiveData<LoginData> ldData = new MutableLiveData<>();

    public LoginViewModel() {
        model = ArteriumDataProvider.getInstance();
    }

    void login(String login, String password) {
        model.login(login, password)
                .subscribe(
                        data -> {
                            ldData.postValue(data.getData());
                        },
                        throwable -> Log.e("!!!", throwable.getMessage())
                );
    }
}
