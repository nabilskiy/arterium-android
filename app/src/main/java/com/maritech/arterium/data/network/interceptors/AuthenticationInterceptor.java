package com.maritech.arterium.data.network.interceptors;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.maritech.arterium.App;
import com.maritech.arterium.data.network.ArteriumDataProvider;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.utils.DeviceUtil;

import java.io.IOException;
import java.util.UUID;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthenticationInterceptor implements Interceptor {

    public AuthenticationInterceptor() {
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder();

        String authToken = getToken();

        String id = DeviceUtil.getDeviceId(App.getInstance());

        if (!TextUtils.isEmpty(authToken)) {
            builder.header("Authorization", "Bearer " + authToken);
        }

        builder.header("Accept", "application/json");
        builder.header("X-Requested-With", "XMLHttpRequest");
        builder.header("Device-Id", id);

        Request request = builder.build();
        return chain.proceed(request);
    }

    private String getToken() {
        return Pref.getInstance().getAuthToken(App.getInstance());
    }


}