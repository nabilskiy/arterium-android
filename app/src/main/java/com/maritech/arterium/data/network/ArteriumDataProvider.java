package com.maritech.arterium.data.network;


import androidx.annotation.IntRange;

import com.maritech.arterium.App;
import com.maritech.arterium.data.models.DrugProgramModel;
import com.maritech.arterium.data.models.DrugProgramsResponse;
import com.maritech.arterium.data.models.LoginRequest;
import com.maritech.arterium.data.models.LoginResponse;
import com.maritech.arterium.data.models.PatientCreateResponse;
import com.maritech.arterium.data.models.PatientsResponse;
import com.maritech.arterium.data.models.ProfileResponse;
import com.maritech.arterium.data.models.BaseResponse;
import com.maritech.arterium.data.network.interceptors.AuthenticationInterceptor;
import com.maritech.arterium.data.network.interceptors.ErrorAuthTokenInterceptor;
import com.maritech.arterium.data.sharePref.Pref;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
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
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(new AuthenticationInterceptor())
                .addInterceptor(new ErrorAuthTokenInterceptor())
                .addInterceptor(new ChuckInterceptor(App.getInstance()))
                .addInterceptor(interceptor)
                .connectTimeout(waitingTime, TimeUnit.SECONDS)
                .readTimeout(waitingTime, TimeUnit.SECONDS)
                .build();
    }


    //==================================== DATA PROVIDER ========================================


    @Override
    public Single<LoginResponse> login(String login, String password) {
        return Single.create(singleSubscriber ->
                provideArteriumClient().login(new LoginRequest(login, password))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSuccess(loginResponse -> Pref.getInstance().setAuthToken(
                                App.getInstance(), loginResponse.getData().getAccessToken()
                        ))
                        .subscribe(
                                singleSubscriber::onSuccess,
                                singleSubscriber::onError
                        ));
    }

    @Override
    public Single<BaseResponse> logout() {
        return Single.create(singleSubscriber -> provideArteriumClient().logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(baseResponse -> {
                    if (baseResponse.isSuccess()) {
                        Pref.getInstance().setDeviceUUID(App.getInstance(), "");
                        Pref.getInstance()
                                .setUserFirstLaunch(App.getInstance(), true);
                        Pref.getInstance()
                                .setUserProfile(App.getInstance(), null);
                        Pref.getInstance().setAuthToken(App.getInstance(), "");
                    }
                })
                .subscribe(
                        singleSubscriber::onSuccess,
                        singleSubscriber::onError
                ));
    }

    @Override
    public Call<LoginResponse> refreshToken() {
        return provideArteriumClient().refreshToken();
    }

    @Override
    public Observable<ProfileResponse> getProfile() {
        return Observable.mergeDelayError(
                Observable.just(Pref.getInstance().getUserProfile(App.getInstance()))
                        .filter(profileResponse -> profileResponse != null),
                provideArteriumClient().getProfile()
//                        .retryWhen(BaseDataManager.isAuthorizeException())
                        .doOnNext(
                                profileResponse -> Pref.getInstance().setUserProfile(
                                        App.getInstance(), profileResponse)
                        )
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true);
    }

    @Override
    public Single<PatientsResponse> getPatients(int purchasesFilter,
                                                String startDate,
                                                String endDate,
                                                int drugProgram,
                                                String search) {

        return Single.create(singleSubscriber -> provideArteriumClient()
                .getPatients(purchasesFilter, startDate, endDate, drugProgram, search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        singleSubscriber::onSuccess,
                        singleSubscriber::onError
                ));
    }

    @Override
    public Single<ResponseBody> getPatientImage(int patientId) {

        return Single.create(singleSubscriber -> provideArteriumClient()
                .getPatientImage(patientId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        singleSubscriber::onSuccess,
                        singleSubscriber::onError
                ));
    }

    @Override
    public Single<PatientCreateResponse> createPatient(MultipartBody.Part img, Map<String, RequestBody> body) {
        return Single.create(singleSubscriber -> provideArteriumClient()
                .createPatient(img, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        singleSubscriber::onSuccess,
                        singleSubscriber::onError
                ));
    }

    @Override
    public Observable<List<DrugProgramModel>> getDrugPrograms() {
        return Observable.mergeDelayError(
                Observable.just(Pref.getInstance().getDrugProgramList(App.getInstance()))
                        .filter(profileResponse -> profileResponse != null),
                provideArteriumClient().getDrugPrograms()
                        .flatMap((Func1<DrugProgramsResponse, Observable<List<DrugProgramModel>>>) response -> {
                            Pref.getInstance().setDrugProgramList(
                                    App.getInstance(), response.getData());

                            return Observable.just(response.getData());
                        })
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true);
    }
}
