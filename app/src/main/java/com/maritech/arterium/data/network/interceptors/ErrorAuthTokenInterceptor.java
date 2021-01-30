package com.maritech.arterium.data.network.interceptors;

import android.content.Context;

import com.maritech.arterium.data.sharePref.Pref;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Aleksandr Litvinchuck on 03.01.2018.
 */

public class ErrorAuthTokenInterceptor implements Interceptor {

    private Pref pref;
    private Context context;

    public ErrorAuthTokenInterceptor(Context context) {
        pref = Pref.getInstance();
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        if (response.code() == 401) {
            pref.saveAuthtoken(context, "");
            //RegistrationActivity.start(context);
            return response;
        }
        return response;
    }
}
