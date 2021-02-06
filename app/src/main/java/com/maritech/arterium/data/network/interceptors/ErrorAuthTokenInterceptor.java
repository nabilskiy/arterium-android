package com.maritech.arterium.data.network.interceptors;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

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
//            Pref.getInstance().saveAuthToken(App.getInstance(), "");
            //RegistrationActivity.start(context);
            return response;
        }
        return response;
    }
}
