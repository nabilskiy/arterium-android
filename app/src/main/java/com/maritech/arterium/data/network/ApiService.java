package com.maritech.arterium.data.network;

import com.google.gson.JsonObject;
import com.maritech.arterium.data.models.AddDoctorsRequestModel;
import com.maritech.arterium.data.models.AgentRequestModel;
import com.maritech.arterium.data.models.AgentResponseModel;
import com.maritech.arterium.data.models.CreateAgentResponseModel;
import com.maritech.arterium.data.models.DoctorsResponseModel;
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

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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
    Single<LoginResponse> refreshToken();

    @POST("api/v1/auth/logout")
    Single<BaseResponse> logout();

    @GET("api/v1/info/drug-programs")
    Observable<DrugProgramsResponse> getDrugPrograms();

    @GET("api/v1/info/levels")
    Single<LevelsResponse> getLevels(@Query("drug_program_id") int drugProgram);

    @GET("api/v1/profile")
    Observable<ProfileResponse> getProfile();

    @GET("api/v1/doctors/patients")
    Single<PatientListResponse> getPatients(@Query("purchases_filter") int purchasesFilter,
                                            @Query("created_from") String startDate,
                                            @Query("end_date") String endDate,
                                            @Query("drug_program_id") int drugProgram,
                                            @Query("search") String search
    );

    @GET("api/v1/doctors/patients/{patientId}")
    Single<PatientResponse> getPatient(@Path("patientId") int patientId);

    @GET("api/v1/doctors/patients/{patientId}/img")
    Single<ResponseBody> getPatientImage(@Path("patientId") int patientId);

    @DELETE("api/v1/doctors/patients/{patientId}/img")
    Single<PatientCreateResponse> deletePatientImage(@Path("patientId") int patientId);

    @Multipart
    @POST("api/v1/doctors/patients")
    Single<PatientCreateResponse> createPatient(@Part MultipartBody.Part img,
                                                @PartMap Map<String, RequestBody> body);

    @Multipart
    @POST("api/v1/doctors/patients/{patientId}")
    Single<PatientCreateResponse> editPatient(@Path("patientId") int patientId,
                                              @Part MultipartBody.Part img,
                                              @PartMap Map<String, RequestBody> body);

    @GET("/api/v1/doctors/sales/statistics")
    Single<StatisticsResponse> getStatistics(@Query("from") String from,
                                             @Query("to") String to,
                                             @Query("force") int force,
                                             @Query("drug_program_id") int drugProgramId);

    @GET("/api/v1/doctors/sales")
    Single<PurchasesResponse> getDoctorSales(@Query("from") String from,
                                             @Query("to") String to,
//                                                  @Query("purchases_filter") int purchases_filter,
                                             @Query("drug_program_id") int drugProgramId);

    @GET("/api/v1/profile/notifications")
    Single<NotificationResponse> getNotifications();

    @POST("/api/v1/profile/notifications")
    Single<BaseResponse> readNotifications(@Body JsonObject body);

    @POST("/api/v1/profile/fcm-token")
    Single<BaseResponse> sendFirebaseToken(@Body JsonObject body);

    @POST("/api/v1/contact-us")
    Single<BaseResponse> sendFeedback(@Body RequestBody body);

    @GET("/api/v1/info/pharmacies")
    Single<PharmacyResponse> getPharmacies();

    @GET("/api/v1/agents")
    Single<AgentResponseModel> getAgents();

    @GET("/api/v1/doctors")
    Single<DoctorsResponseModel> getDoctors();

    @POST("/api/v1/agents")
    Single<CreateAgentResponseModel> saveAgent(@Body AgentRequestModel agent);

    @FormUrlEncoded
    @POST("/api/v1/agents/{agentId}/doctors")
    Single<BaseResponse> addDoctorsToAgent(@Path("agentId") String agent_id,
                                           @Field("doctor_ids") AddDoctorsRequestModel ids);
}
