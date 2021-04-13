package com.maritech.arterium.data.network;


import com.google.gson.JsonObject;
import com.maritech.arterium.data.models.AddDoctorsRequestModel;
import com.maritech.arterium.data.models.AgentModel;
import com.maritech.arterium.data.models.AgentRequestModel;
import com.maritech.arterium.data.models.AgentResponseModel;
import com.maritech.arterium.data.models.CreateAgentResponseModel;
import com.maritech.arterium.data.models.CreateDoctorRequestModel;
import com.maritech.arterium.data.models.CreateDoctorResponseModel;
import com.maritech.arterium.data.models.DoctorsResponseModel;
import com.maritech.arterium.data.models.DrugProgramModel;
import com.maritech.arterium.data.models.LevelsResponse;
import com.maritech.arterium.data.models.LoginResponse;
import com.maritech.arterium.data.models.NotificationResponse;
import com.maritech.arterium.data.models.PatientCreateResponse;
import com.maritech.arterium.data.models.PatientListResponse;
import com.maritech.arterium.data.models.PatientResponse;
import com.maritech.arterium.data.models.PharmacyResponse;
import com.maritech.arterium.data.models.ProfileResponse;
import com.maritech.arterium.data.models.BaseResponse;
import com.maritech.arterium.data.models.PurchasesResponse;
import com.maritech.arterium.data.models.RegionsResponseModel;
import com.maritech.arterium.data.models.StatisticsResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import rx.Single;

public interface DataProvider {

    Single<LoginResponse> login(String login, String password);

    Single<BaseResponse> logout();

    Observable<ProfileResponse> getProfile();

    Single<PatientListResponse> getPatients(int purchasesFilter,
                                            String startDate,
                                            String endDate,
                                            int drugProgram,
                                            String search);

    Single<AgentResponseModel> getAgents();

    Single<PatientResponse> getPatient(int patientId);

    Single<ResponseBody> getPatientImage(int patientId);

    Single<PatientCreateResponse> deletePatientImage(int patientId);

    Single<PatientCreateResponse> createPatient(MultipartBody.Part img,
                                                Map<String, RequestBody> body);

    Single<PatientCreateResponse> editPatient(int patientId,
                                              MultipartBody.Part img,
                                              Map<String, RequestBody> body);

    Observable<List<DrugProgramModel>> getDrugPrograms();

    Single<LevelsResponse> getLevels(int programId);

    Single<StatisticsResponse> getStatistics(String from,
                                             String to,
                                             int force,
                                             int drugProgramId);

    Single<StatisticsResponse> getStatisticByDoctorId(int doctorId,
                                                      String from,
                                                      String to,
                                                      int force,
                                                      int drugProgramId);

    Single<PurchasesResponse> getDoctorsSales(String from,
                                              String to,
                                              int drugProgramId);

    Single<NotificationResponse> getNotifications();

    Single<BaseResponse> readNotification(JsonObject body);

    Single<BaseResponse> sendFirebaseToken(JsonObject body);

    Single<BaseResponse> sendFeedback(RequestBody body);

    Single<PharmacyResponse> getPharmacies();

    Single<DoctorsResponseModel> getDoctors();

    Single<CreateAgentResponseModel> saveAgent(AgentRequestModel requestModel);

    Single<BaseResponse> addDoctors(int id, AddDoctorsRequestModel doctors);

    Single<CreateDoctorResponseModel> createDoctor(CreateDoctorRequestModel doctor);

    Single<RegionsResponseModel> getRegions();

    Single<DoctorsResponseModel> getDoctorsById(int id);

    Single<CreateDoctorResponseModel> getDoctorById(int id);

    Single<PatientListResponse> getDoctorsPatients(int id,
                                                   Integer filter,
                                                   String dateCreatedFrom,
                                                   String dateCreatedTo,
                                                   int drugProgram,
                                                   String search);
}
