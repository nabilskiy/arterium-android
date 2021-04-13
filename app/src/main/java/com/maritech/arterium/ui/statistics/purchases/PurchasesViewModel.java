package com.maritech.arterium.ui.statistics.purchases;

import com.maritech.arterium.common.ContentState;
import com.maritech.arterium.data.models.PurchasesResponse;
import com.maritech.arterium.data.network.ArteriumDataProvider;
import com.maritech.arterium.data.network.DataProvider;
import com.maritech.arterium.ui.base.BaseViewModel;
import com.maritech.arterium.ui.base.SingleLiveEvent;

public class PurchasesViewModel extends BaseViewModel {

    public SingleLiveEvent<PurchasesResponse> responseLiveData = new SingleLiveEvent<>();
    public SingleLiveEvent<ContentState> contentState = new SingleLiveEvent<>();
    public SingleLiveEvent<String> errorMessage = new SingleLiveEvent<>();

    private final DataProvider model;

    public PurchasesViewModel() {
        model = ArteriumDataProvider.getInstance();
    }

    public void getPurchases(String startDate,
                             String endDate,
                             int drugProgram) {

        contentState.postValue(ContentState.LOADING);
        model.getDoctorsSales(startDate, endDate, drugProgram)
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

    public void getPurchasesByDoctorId(int doctorId,
                                       String startDate,
                                       String endDate,
                                       int drugProgram) {
        contentState.postValue(ContentState.LOADING);
        model.getDoctorsSalesByDoctorId(doctorId, startDate, endDate, drugProgram)
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
}
