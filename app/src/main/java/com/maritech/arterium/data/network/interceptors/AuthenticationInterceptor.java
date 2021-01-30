package com.maritech.arterium.data.network.interceptors;

import android.content.Context;
import android.text.TextUtils;

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

    private String authtoken;


    public AuthenticationInterceptor(Context context) {
        Pref pref = Pref.getInstance();
        this.authtoken = pref.getUser(context);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder();

        if (!TextUtils.isEmpty(authtoken)) {
            builder.header("Authorization", authtoken);
        }

        builder.header("Accept", "application/json");
        builder.header("X-Requested-With", "XMLHttpRequest");
        builder.header("Device-Id", UUID.randomUUID().toString());
        Request request = builder.build();
        return chain.proceed(request);
    }
}