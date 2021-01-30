package com.maritech.arterium.data.network;

import com.maritech.arterium.data.models.LoginRequest;
import com.maritech.arterium.data.models.LoginData;
import com.maritech.arterium.data.models.LoginResponse;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Single;

/**
 * Created by ujujzk on 15.08.2017
 * Softensy Digital Studio
 * softensiteam@gmail.com
 */

public interface LoginAPI {

    @POST("api/v1/auth/login")
    Single<LoginResponse> login(
            @Body LoginRequest body);

}
