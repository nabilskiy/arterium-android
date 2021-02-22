package com.maritech.arterium.data.network.interceptors;

import android.util.Log;

import androidx.annotation.NonNull;

import com.maritech.arterium.App;
import com.maritech.arterium.data.models.LoginResponse;
import com.maritech.arterium.data.network.ArteriumDataProvider;
import com.maritech.arterium.data.sharePref.Pref;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;

/**
 * Created by Aleksandr Litvinchuck on 03.01.2018.
 */

public class ErrorAuthTokenInterceptor implements Interceptor {

    public ErrorAuthTokenInterceptor() {
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        if (response.code() == 401) {

//            Call<LoginResponse> responseCall = ArteriumDataProvider.getInstance().refreshToken();
//            LoginResponse tokenR = responseCall.execute().body();
//            String token = tokenR.getData().getAccessToken();
//
//
//            String uuid = Pref.getInstance().getDeviceUUID(App.getInstance());
//            Log.e("New Token", token);
//
//            return chain.proceed(response.request().newBuilder()
//                    .header("AUTHORIZATION", "Bearer " + token)
//                    .header("Device-Id", uuid)
//                    .build());
        }
        return response;
    }
}
