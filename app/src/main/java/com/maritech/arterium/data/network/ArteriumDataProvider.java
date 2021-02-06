package com.maritech.arterium.data.network;


import android.util.Log;

import androidx.annotation.IntRange;

import com.maritech.arterium.App;
import com.maritech.arterium.data.models.LoginRequest;
import com.maritech.arterium.data.models.LoginResponse;
import com.maritech.arterium.data.models.ProfileResponse;
import com.maritech.arterium.data.network.interceptors.AuthenticationInterceptor;
import com.maritech.arterium.data.network.interceptors.ErrorAuthTokenInterceptor;
import com.maritech.arterium.data.sharePref.Pref;
import com.readystatesoftware.chuck.ChuckInterceptor;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ujujzk on 16.08.2017
 * Softensy Digital Studio
 * softensiteam@gmail.com
 */

public class ArteriumDataProvider implements DataProvider {

    private static final String BASE_URL = "https://doc.maritech.com.ua/";

    //================================== SINGLETON ==========================================

    private static volatile ArteriumDataProvider instance;

    private ArteriumDataProvider() {
        //Prevent form the reflection api.
        if (instance != null) {
            throw new AssertionError("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static ArteriumDataProvider getInstance() {
        if (instance == null) {
            synchronized (ArteriumDataProvider.class) {
                if (instance == null) {
                    instance = new ArteriumDataProvider();
                }
            }
        }
        return instance;
    }

    //Make singleton from serialize and deserialize operation.
    protected ArteriumDataProvider readResolve() {
        return getInstance();
    }

    private ApiService provideArteriumClient() {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(provideHttpClient(60))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class);
    }

    private OkHttpClient provideHttpClient(@IntRange(from = 0, to = 1000) int waitingTime) {
        return new OkHttpClient.Builder()
                .addInterceptor(new AuthenticationInterceptor())
                .addInterceptor(new ErrorAuthTokenInterceptor())
                .addInterceptor(new ChuckInterceptor(App.getInstance()))
                .connectTimeout(waitingTime, TimeUnit.SECONDS)
                .readTimeout(waitingTime, TimeUnit.SECONDS)
                .build();
    }


    //==================================== DATA PROVIDER ========================================


    @Override
    public Single<LoginResponse> login(String login, String password) {
        return Single.create(singleSubscriber -> {
            provideArteriumClient().login(new LoginRequest(login, password))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess(loginResponse -> Pref.getInstance().saveAuthToken(
                            App.getInstance(), loginResponse.getData().getAccessToken()
                    ))
                    .subscribe(
                            singleSubscriber::onSuccess,
                            singleSubscriber::onError
                    );
        });
    }

    @Override
    public Observable<ProfileResponse> getProfile() {

        //TODO To Remove Logs
        return Observable.mergeDelayError(
                Observable.just(Pref.getInstance().getUserProfile(App.getInstance()))
                        .filter(profileResponse -> profileResponse != null)
                        .doOnNext(profileResponse -> Log.e("Profile", "From Local")),
                provideArteriumClient().getProfile()
                        .doOnNext(profileResponse -> {
                            Pref.getInstance().setUserProfile(App.getInstance(), profileResponse);
                            Log.e("Profile", "From Server");
                        })
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true);

    }
}
