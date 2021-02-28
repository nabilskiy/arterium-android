package com.maritech.arterium.data.network;

import com.maritech.arterium.data.models.DrugProgramsResponse;
import com.maritech.arterium.data.models.LoginRequest;
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
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import rx.Single;

public interface ApiService {

    @POST("api/v1/auth/login")
    Single<LoginResponse> login(@Body LoginRequest body);

    @POST("api/v1/auth/refresh")
    Call<LoginResponse> refreshToken();

    @POST("api/v1/auth/logout")
    Single<BaseResponse> logout();

    @GET("api/v1/info/drug-programs")
    Observable<DrugProgramsResponse> getDrugPrograms();

    @GET("api/v1/profile")
    Observable<ProfileResponse> getProfile();

    @GET("api/v1/doctors/patients")
    Single<PatientsResponse> getPatients(@Query("purchases_filter") int purchasesFilter,
                                         @Query("created_from") String startDate,
                                         @Query("end_date") String endDate,
                                         @Query("drug_program_id") int drugProgram,
                                         @Query("search") String search
    );

    @GET("api/v1/doctors/patients/{patientId}/img")
    Single<ResponseBody> getPatientImage(@Path("patientId") int patientId);

    @Multipart
    @POST("api/v1/doctors/patients")
    Single<PatientCreateResponse> createPatient(@Part MultipartBody.Part img,
                                                @PartMap Map<String, RequestBody> body);
}
