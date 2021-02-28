package com.maritech.arterium.data.network;


import com.maritech.arterium.data.models.DrugProgramModel;
import com.maritech.arterium.data.models.LoginResponse;
import com.maritech.arterium.data.models.PatientCreateResponse;
import com.maritech.arterium.data.models.PatientsResponse;
import com.maritech.arterium.data.models.ProfileResponse;
import com.maritech.arterium.data.models.BaseResponse;

import java.util.List;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import rx.Observable;
import rx.Single;

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

    Single<PatientCreateResponse> createPatient(MultipartBody.Part img, Map<String, RequestBody> body);

    Observable<List<DrugProgramModel>> getDrugPrograms();
}
