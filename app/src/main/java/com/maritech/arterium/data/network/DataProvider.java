package com.maritech.arterium.data.network;


import com.maritech.arterium.data.models.LoginResponse;
import com.maritech.arterium.data.models.PatientCreateResponse;
import com.maritech.arterium.data.models.PatientsResponse;
import com.maritech.arterium.data.models.ProfileResponse;
import com.maritech.arterium.data.models.BaseResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import rx.Observable;
import rx.Single;

/**
 * Created by ujujzk on 16.08.2017
 * Softensy Digital Studio
 * softensiteam@gmail.com
 */

public interface DataProvider {

    Single<LoginResponse> login(String login, String password);

    Call<LoginResponse> refreshToken();

    Single<BaseResponse> logout();

    Observable<ProfileResponse> getProfile();

    Single<PatientsResponse> getPatients(int purchasesFilter,
                                         String startDate,
                                         String endDate,
                                         int drugProgram,
                                         String search);

    Single<ResponseBody> getPatientImage(int patientId);

    Single<PatientCreateResponse> createPatient(Map<String, RequestBody> body, MultipartBody.Part img);
}
