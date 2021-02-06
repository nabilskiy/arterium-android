package com.maritech.arterium.data.network;


import com.maritech.arterium.data.models.LoginResponse;
import com.maritech.arterium.data.models.ProfileResponse;

import rx.Observable;
import rx.Single;

/**
 * Created by ujujzk on 16.08.2017
 * Softensy Digital Studio
 * softensiteam@gmail.com
 */

public interface DataProvider {

    Single<LoginResponse> login(String login, String password);

    Observable<ProfileResponse> getProfile();

}
