package com.maritech.arterium.ui.patients;

import com.maritech.arterium.common.ContentState;
import com.maritech.arterium.data.models.PatientCreateModel;
import com.maritech.arterium.data.models.PatientsResponse;
import com.maritech.arterium.data.network.ArteriumDataProvider;
import com.maritech.arterium.data.network.DataProvider;
import com.maritech.arterium.ui.base.BaseViewModel;
import com.maritech.arterium.ui.base.SingleLiveEvent;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class PatientsViewModel extends BaseViewModel {

    public SingleLiveEvent<PatientsResponse> responseLiveData = new SingleLiveEvent<>();
    public SingleLiveEvent<ContentState> contentState = new SingleLiveEvent<>();
    public SingleLiveEvent<String> errorMessage = new SingleLiveEvent<>();
    public SingleLiveEvent<PatientCreateModel> createPatient = new SingleLiveEvent<>();
    public SingleLiveEvent<ContentState> createPatientState = new SingleLiveEvent<>();
    public SingleLiveEvent<String> createErrorMessage = new SingleLiveEvent<>();
    public SingleLiveEvent<ResponseBody> imageResponse = new SingleLiveEvent<>();
    public SingleLiveEvent<ContentState> imageState = new SingleLiveEvent<>();
    public SingleLiveEvent<String> imageErrorMessage = new SingleLiveEvent<>();

    private final DataProvider model;

    public PatientsViewModel() {
        model = ArteriumDataProvider.getInstance();
    }

    public void getPatients(String startDate,
                            String endDate,
                            int drugProgram,
                            String search) {

        contentState.postValue(ContentState.LOADING);
        model.getPatients(0, startDate, endDate, drugProgram, search)
                .subscribe(
                        data -> {
                            if (data != null && data.getData() != null && !data.getData().isEmpty()) {
                                contentState.postValue(ContentState.CONTENT);
                                responseLiveData.postValue(data);
                            } else {
                                contentState.postValue(ContentState.EMPTY);
                            }

                        },
                        throwable -> {
                            contentState.postValue(ContentState.ERROR);
                            errorMessage.postValue(throwable.getMessage());
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
                            imageErrorMessage.postValue(throwable.getMessage());
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
                            createErrorMessage.postValue(throwable.getMessage());
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
                            createErrorMessage.postValue(throwable.getMessage());
                        }
                );
    }

}
