package com.maritech.arterium.ui.patients;

import android.util.Log;

import com.maritech.arterium.common.ContentState;
import com.maritech.arterium.data.models.PatientCreateModel;
import com.maritech.arterium.data.models.PatientListResponse;
import com.maritech.arterium.data.models.PatientResponse;
import com.maritech.arterium.data.network.ArteriumDataProvider;
import com.maritech.arterium.data.network.DataProvider;
import com.maritech.arterium.data.network.interceptors.ErrorModel;
import com.maritech.arterium.ui.base.BaseViewModel;
import com.maritech.arterium.ui.base.SingleLiveEvent;

import java.io.IOException;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

import static com.maritech.arterium.ui.dashboard.doctor.DashboardViewModel.TAG;

public class PatientsViewModel extends BaseViewModel {

    public SingleLiveEvent<PatientListResponse> allPatients = new SingleLiveEvent<>();
    public SingleLiveEvent<ContentState> allPatientsState = new SingleLiveEvent<>();
    public SingleLiveEvent<String> allPatientsMessage = new SingleLiveEvent<>();
    public SingleLiveEvent<PatientResponse> patientById = new SingleLiveEvent<>();
    public SingleLiveEvent<ContentState> patientByIdState = new SingleLiveEvent<>();
    public SingleLiveEvent<String> patientByIdMessage = new SingleLiveEvent<>();
    public SingleLiveEvent<PatientCreateModel> createPatient = new SingleLiveEvent<>();
    public SingleLiveEvent<ContentState> createPatientState = new SingleLiveEvent<>();
    public SingleLiveEvent<String> createErrorMessage = new SingleLiveEvent<>();
    public SingleLiveEvent<ResponseBody> imageResponse = new SingleLiveEvent<>();
    public SingleLiveEvent<ContentState> imageState = new SingleLiveEvent<>();
    public SingleLiveEvent<String> imageErrorMessage = new SingleLiveEvent<>();
    public SingleLiveEvent<PatientCreateModel> deleteImageResponse = new SingleLiveEvent<>();
    public SingleLiveEvent<ContentState> deleteImageState = new SingleLiveEvent<>();
    public SingleLiveEvent<String> deleteImageErrorMessage = new SingleLiveEvent<>();

    private final DataProvider model;

    public PatientsViewModel() {
        model = ArteriumDataProvider.getInstance();
    }

    public void getPatients(String startDate,
                            String endDate,
                            int drugProgram,
                            String search) {

        allPatientsState.postValue(ContentState.LOADING);
        model.getPatients(0, startDate, endDate, drugProgram, search)
                .subscribe(
                        data -> {
                            if (data != null && data.getData() != null && !data.getData().isEmpty()) {
                                allPatientsState.postValue(ContentState.CONTENT);
                                allPatients.postValue(data);
                            } else {
                                allPatientsState.postValue(ContentState.EMPTY);
                            }

                        },
                        throwable -> {
                            allPatientsState.postValue(ContentState.ERROR);
                            allPatientsMessage.postValue(ErrorModel.showErrorBody(throwable));
                        }
                );
    }

    public void getPatientsByDoctoID(int doctorId,
                                     String startDate,
                                     String endDate,
                                     int drugProgram,
                                     String search) {

        allPatientsState.postValue(ContentState.LOADING);
        model.getDoctorsPatients(doctorId, 0, startDate, endDate, drugProgram, search)
                .subscribe(
                        data -> {
                            if (data != null && data.getData() != null && !data.getData().isEmpty()) {
                                allPatientsState.postValue(ContentState.CONTENT);
                                allPatients.postValue(data);
                            } else {
                                allPatientsState.postValue(ContentState.EMPTY);
                            }

                        },
                        throwable -> {
                            allPatientsState.postValue(ContentState.ERROR);
                            allPatientsMessage.postValue(ErrorModel.showErrorBody(throwable));
                        }
                );
    }

    public void getPatientById(int patientId) {
        patientByIdState.postValue(ContentState.LOADING);
        model.getPatient(patientId)
                .subscribe(
                        data -> {
                            if (data != null && data.getData() != null) {
                                Log.i(TAG, "getPatientById: NULL");
                                patientByIdState.postValue(ContentState.CONTENT);
                                patientById.postValue(data);
                            } else {
                                patientByIdState.postValue(ContentState.EMPTY);
                            }

                        },
                        throwable -> {
                            Log.i(TAG, "getPatientById: " + throwable.getMessage());
                            patientByIdState.postValue(ContentState.ERROR);
                            patientByIdMessage.postValue(ErrorModel.showErrorBody(throwable));
                        }
                );
    }

    public void getPatientByIdByDoctorId(int doctorId, int patientId) {
        patientByIdState.postValue(ContentState.LOADING);
        model.getPatientByDoctorId(doctorId, patientId)
                .subscribe(
                        data -> {
                            if (data != null && data.getData() != null) {
                                Log.i(TAG, "getPatientById: NULL");
                                patientByIdState.postValue(ContentState.CONTENT);
                                patientById.postValue(data);
                            } else {
                                patientByIdState.postValue(ContentState.EMPTY);
                            }

                        },
                        throwable -> {
                            Log.i(TAG, "getPatientById: " + throwable.getMessage());
                            patientByIdState.postValue(ContentState.ERROR);
                            patientByIdMessage.postValue(ErrorModel.showErrorBody(throwable));
                        }
                );
    }

    public void getPatientImage(int patientId) {
        imageState.postValue(ContentState.LOADING);
        model.getPatientImage(patientId)
                .subscribe(
                        data -> {
                            if (data != null) {
                                imageState.postValue(ContentState.CONTENT);
                                imageResponse.postValue(data);
                            } else {
                                imageState.postValue(ContentState.EMPTY);
                            }

                        },
                        throwable -> {
                            imageState.postValue(ContentState.ERROR);
                            imageErrorMessage.postValue(ErrorModel.showErrorBody(throwable));
                        }
                );
    }

    public void deletePatientImage(int patientId) {
        deleteImageState.postValue(ContentState.LOADING);
        model.deletePatientImage(patientId)
                .subscribe(
                        data -> {
                            if (data != null && data.getData() != null) {
                                deleteImageState.postValue(ContentState.CONTENT);
                                deleteImageResponse.postValue(data.getData());
                            } else {
                                deleteImageState.postValue(ContentState.EMPTY);
                            }
                        },
                        throwable -> {
                            deleteImageState.postValue(ContentState.ERROR);
                            deleteImageErrorMessage.postValue(ErrorModel.showErrorBody(throwable));
                        }
                );
    }

    public void createPatient(Map<String, RequestBody> requestBody, MultipartBody.Part img) {
        createPatientState.postValue(ContentState.LOADING);
        model.createPatient(img, requestBody)
                .subscribe(
                        data -> {
                            if (data != null) {
                                createPatientState.postValue(ContentState.CONTENT);
                                createPatient.postValue(data.getData());
                            } else {
                                createPatientState.postValue(ContentState.EMPTY);
                            }

                        },
                        throwable -> {
                            createPatientState.postValue(ContentState.ERROR);
                            createErrorMessage.postValue(ErrorModel.showErrorBody(throwable));
                        }
                );
    }

    public void editPatient(int patientId,
                            Map<String, RequestBody> requestBody,
                            MultipartBody.Part img) {
        createPatientState.postValue(ContentState.LOADING);
        model.editPatient(patientId, img, requestBody)
                .subscribe(
                        data -> {
                            if (data != null) {
                                createPatientState.postValue(ContentState.CONTENT);
                                createPatient.postValue(data.getData());
                            } else {
                                createPatientState.postValue(ContentState.EMPTY);
                            }

                        },
                        throwable -> {
                            createPatientState.postValue(ContentState.ERROR);
                            createErrorMessage.postValue(ErrorModel.showErrorBody(throwable));
                        }
                );
    }

}
