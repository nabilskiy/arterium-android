package com.maritech.arterium.data.network;

import com.maritech.arterium.data.models.LoginRequest;
import com.maritech.arterium.data.models.LoginResponse;
import com.maritech.arterium.data.models.ProfileResponse;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;
import rx.Single;

/**
 * Created by ujujzk on 15.08.2017
 * Softensy Digital Studio
 * softensiteam@gmail.com
 */

public interface ApiService {

    @POST("api/v1/auth/login")
    Single<LoginResponse> login(@Body LoginRequest body);

    @GET("api/v1/profile")
    Observable<ProfileResponse> getProfile();

}
