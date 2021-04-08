package com.maritech.arterium.data.network;

import androidx.annotation.IntRange;

import com.google.gson.JsonObject;
import com.maritech.arterium.App;
import com.maritech.arterium.BuildConfig;
import com.maritech.arterium.data.models.AgentResponseModel;
import com.maritech.arterium.data.models.DoctorsResponseModel;
import com.maritech.arterium.data.models.DrugProgramModel;
import com.maritech.arterium.data.models.DrugProgramsResponse;
import com.maritech.arterium.data.models.LevelsResponse;
import com.maritech.arterium.data.models.LoginRequest;
import com.maritech.arterium.data.models.LoginResponse;
import com.maritech.arterium.data.models.NotificationResponse;
import com.maritech.arterium.data.models.PatientCreateResponse;
import com.maritech.arterium.data.models.PatientListResponse;
import com.maritech.arterium.data.models.PatientResponse;
import com.maritech.arterium.data.models.PharmacyResponse;
import com.maritech.arterium.data.models.ProfileResponse;
import com.maritech.arterium.data.models.BaseResponse;
import com.maritech.arterium.data.models.PurchasesResponse;
import com.maritech.arterium.data.models.StatisticsResponse;
import com.maritech.arterium.data.network.interceptors.AuthenticationInterceptor;
import com.maritech.arterium.data.sharePref.Pref;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Scheduler;
import rx.Single;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ArteriumDataProvider implements DataProvider {

    private static final String BASE_URL = BuildConfig.BASE_URL;

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

    private ApiService provideClient() {
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
                .addInterceptor(new ChuckInterceptor(App.getInstance()))
                .addInterceptor(interceptor)
                .connectTimeout(waitingTime, TimeUnit.SECONDS)
                .readTimeout(waitingTime, TimeUnit.SECONDS)
                .build();
    }


    //==================================== DATA PROVIDER ========================================

    public static Func1<Observable<? extends Throwable>, Observable<?>> isAuthException() {
        return throwableObservable -> throwableObservable
                .flatMap((Func1<Throwable, Observable<?>>) throwable -> {
                    if (throwable instanceof HttpException) {
                        HttpException httpException = (HttpException) throwable;

                        if (httpException.code() == 401) {
                            return refreshToken().toObservable();
                        }
                    }
                    return Observable.error(throwable);
                });
    }

    public synchronized static Single<LoginResponse> refreshToken() {
        return getInstance().provideClient()
                .refreshToken()
                .subscribeOn(Schedulers.io())
                .flatMap((Func1<LoginResponse, Single<LoginResponse>>) Single::just)
                .doOnSuccess(loginResponse -> Pref.getInstance().setAuthToken(
                        App.getInstance(), loginResponse.getData().getAccessToken()
                ));
    }

    @Override
    public Single<LoginResponse> login(String login, String password) {
        return Single.create(singleSubscriber ->
                provideClient()
                        .login(new LoginRequest(login, password))
                        .subscribeOn(Schedulers.io())
                        .retryWhen(isAuthException())
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
        return Single.create(singleSubscriber -> provideClient().logout()
                .subscribeOn(Schedulers.io())
                .retryWhen(isAuthException())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(baseResponse -> {
                    if (baseResponse.isSuccess()) {
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
    public Observable<ProfileResponse> getProfile() {
        return Observable.mergeDelayError(
                Observable.just(Pref.getInstance().getUserProfile(App.getInstance()))
                        .filter(profileResponse -> profileResponse != null),
                provideClient().getProfile()
                        .subscribeOn(Schedulers.io())
                        .retryWhen(isAuthException())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(
                                profileResponse -> Pref.getInstance().setUserProfile(
                                        App.getInstance(), profileResponse)
                        )
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true);
    }

    @Override
    public Single<PatientListResponse> getPatients(int purchasesFilter,
                                                   String startDate,
                                                   String endDate,
                                                   int drugProgram,
                                                   String search) {

        return Single.create(singleSubscriber -> provideClient()
                .getPatients(purchasesFilter, startDate, endDate, drugProgram, search)
                .subscribeOn(Schedulers.io())
                .retryWhen(isAuthException())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        singleSubscriber::onSuccess,
                        singleSubscriber::onError
                ));
    }


    @Override
    public Single<AgentResponseModel> getAgents() {
        return Single.create(singleSubscriber -> provideClient()
                .getAgents()
                .subscribeOn(Schedulers.io())
                .retryWhen(isAuthException())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        singleSubscriber::onSuccess,
                        singleSubscriber::onError
                ));
    }

    @Override
    public Single<DoctorsResponseModel> getDoctors() {
        return Single.create(singleSubscriber -> provideClient()
                .getDoctors()
                .subscribeOn(Schedulers.io())
                .retryWhen(isAuthException())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        singleSubscriber::onSuccess,
                        singleSubscriber::onError
                ));
    }


    @Override
    public Single<PatientResponse> getPatient(int patientId) {

        return Single.create(singleSubscriber -> provideClient()
                .getPatient(patientId)
                .subscribeOn(Schedulers.io())
                .retryWhen(isAuthException())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        singleSubscriber::onSuccess,
                        singleSubscriber::onError
                ));
    }

    @Override
    public Single<ResponseBody> getPatientImage(int patientId) {

        return Single.create(singleSubscriber -> provideClient()
                .getPatientImage(patientId)
                .subscribeOn(Schedulers.io())
                .retryWhen(isAuthException())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        singleSubscriber::onSuccess,
                        singleSubscriber::onError
                ));
    }

    @Override
    public Single<PatientCreateResponse> deletePatientImage(int patientId) {
        return Single.create(singleSubscriber -> provideClient()
                .deletePatientImage(patientId)
                .subscribeOn(Schedulers.io())
                .retryWhen(isAuthException())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        singleSubscriber::onSuccess,
                        singleSubscriber::onError
                ));
    }

    @Override
    public Single<PatientCreateResponse> createPatient(MultipartBody.Part img,
                                                       Map<String, RequestBody> body) {
        return Single.create(singleSubscriber -> provideClient()
                .createPatient(img, body)
                .subscribeOn(Schedulers.io())
                .retryWhen(isAuthException())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        singleSubscriber::onSuccess,
                        singleSubscriber::onError
                ));
    }

    @Override
    public Single<PatientCreateResponse> editPatient(int patientId,
                                                     MultipartBody.Part img,
                                                     Map<String, RequestBody> body) {
        return Single.create(singleSubscriber -> provideClient()
                .editPatient(patientId, img, body)
                .subscribeOn(Schedulers.io())
                .retryWhen(isAuthException())
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
                        .filter(drugPrograms -> drugPrograms != null),
                provideClient().getDrugPrograms()
                        .subscribeOn(Schedulers.io())
                        .retryWhen(isAuthException())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap((Func1<DrugProgramsResponse, Observable<List<DrugProgramModel>>>) response -> {
                            Pref.getInstance().setDrugProgramList(
                                    App.getInstance(), response.getData());

                            return Observable.just(response.getData());
                        })
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true);
    }

    @Override
    public Single<LevelsResponse> getLevels(int programId) {
        return Single.create(singleSubscriber -> provideClient()
                .getLevels(programId)
                .subscribeOn(Schedulers.io())
                .retryWhen(isAuthException())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        singleSubscriber::onSuccess,
                        singleSubscriber::onError
                ));
    }

    @Override
    public Single<StatisticsResponse> getStatistics(String from,
                                                    String to,
                                                    int force,
                                                    int drugProgramId) {
        return Single.create(singleSubscriber -> provideClient()
                .getStatistics(from, to, force, drugProgramId)
                .subscribeOn(Schedulers.io())
                .retryWhen(isAuthException())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        singleSubscriber::onSuccess,
                        singleSubscriber::onError
                ));
    }

    @Override
    public Single<PurchasesResponse> getDoctorsSales(String from,
                                                     String to,
                                                     int drugProgramId) {
        return Single.create(singleSubscriber -> provideClient()
                .getDoctorSales(from, to, drugProgramId)
                .subscribeOn(Schedulers.io())
                .retryWhen(isAuthException())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        singleSubscriber::onSuccess,
                        singleSubscriber::onError
                ));
    }

    @Override
    public Single<NotificationResponse> getNotifications() {
        return Single.create(singleSubscriber -> provideClient()
                .getNotifications()
                .subscribeOn(Schedulers.io())
                .flatMap((Func1<NotificationResponse, Single<NotificationResponse>>)
                        notificationResponse -> {
                            if (notificationResponse != null &&
                                    notificationResponse.getData() != null) {
                                Collections.sort(notificationResponse.getData(),
                                        (o1, o2) ->
                                                Long.compare(o2.getCreatedAt(), o1.getCreatedAt()));
                                Collections.reverse(notificationResponse.getData());
                            }
                            return Single.just(notificationResponse);
                        })
                .retryWhen(isAuthException())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new SingleSubscriber<NotificationResponse>() {
                            @Override
                            public void onSuccess(NotificationResponse notificationResponse) {
                                singleSubscriber.onSuccess(notificationResponse);
                            }

                            @Override
                            public void onError(Throwable error) {
                                singleSubscriber.onError(error);
                            }
                        }
                ));
    }

    @Override
    public Single<BaseResponse> readNotification(JsonObject body) {
        return Single.create(singleSubscriber -> provideClient()
                .readNotifications(body)
                .subscribeOn(Schedulers.io())
                .retryWhen(isAuthException())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        singleSubscriber::onSuccess,
                        singleSubscriber::onError
                ));
    }

    @Override
    public Single<BaseResponse> sendFirebaseToken(JsonObject body) {
        return Single.create(singleSubscriber -> provideClient()
                .sendFirebaseToken(body)
                .subscribeOn(Schedulers.io())
                .retryWhen(isAuthException())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        singleSubscriber::onSuccess,
                        singleSubscriber::onError
                ));
    }

    @Override
    public Single<BaseResponse> sendFeedback(RequestBody body) {
        return Single.create(singleSubscriber -> provideClient()
                .sendFeedback(body)
                .subscribeOn(Schedulers.io())
                .retryWhen(isAuthException())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        singleSubscriber::onSuccess,
                        singleSubscriber::onError
                ));
    }

    @Override
    public Single<PharmacyResponse> getPharmacies() {
        return Single.create(singleSubscriber -> provideClient()
                .getPharmacies()
                .subscribeOn(Schedulers.io())
                .retryWhen(isAuthException())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        singleSubscriber::onSuccess,
                        singleSubscriber::onError
                ));
    }
}
