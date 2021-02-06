package com.maritech.arterium.data.network.interceptors;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.maritech.arterium.App;
import com.maritech.arterium.data.sharePref.Pref;

import java.io.IOException;
import java.util.UUID;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Aleksandr Litvinchuck on 03.01.2018.
 */

public class AuthenticationInterceptor implements Interceptor {

    public AuthenticationInterceptor() {
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder();

        String authToken = Pref.getInstance().getAuthToken(App.getInstance());

        if (!TextUtils.isEmpty(authToken)) {
            builder.header("Authorization", "Bearer " + authToken);
        }

        builder.header("Accept", "application/json");
        builder.header("X-Requested-With", "XMLHttpRequest");

        String uuid = Pref.getInstance().getDeviceUUID(App.getInstance());

        if (uuid.isEmpty()) {
            uuid = UUID.randomUUID().toString();
            Pref.getInstance().setDeviceUUID(App.getInstance(), uuid);
        }
        Log.e("UUID", uuid);
        Log.e("TOKEN", authToken);

        builder.header("Device-Id", uuid);
        Request request = builder.build();
        return chain.proceed(request);
    }
}