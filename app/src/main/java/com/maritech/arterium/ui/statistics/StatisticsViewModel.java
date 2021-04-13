package com.maritech.arterium.ui.statistics;

import com.maritech.arterium.common.ContentState;
import com.maritech.arterium.data.models.StatisticsResponse;
import com.maritech.arterium.data.network.ArteriumDataProvider;
import com.maritech.arterium.data.network.DataProvider;
import com.maritech.arterium.ui.base.BaseViewModel;
import com.maritech.arterium.ui.base.SingleLiveEvent;

public class StatisticsViewModel extends BaseViewModel {

    public SingleLiveEvent<StatisticsResponse.StatisticsModel> responseLiveData =
            new SingleLiveEvent<>();
    public SingleLiveEvent<ContentState> contentState = new SingleLiveEvent<>();
    public SingleLiveEvent<String> errorMessage = new SingleLiveEvent<>();

    private final DataProvider model;

    public StatisticsViewModel() {
        model = ArteriumDataProvider.getInstance();
    }

    public void getStatistic(int doctorId,
                             String from,
                             String to,
                             int force,
                             int drugProgramId) {
        if (doctorId < 0)
            getStatistics(from, to, force, drugProgramId);
        else getStatisticById(doctorId, from, to, force, drugProgramId);
    }

    private void getStatisticById(int doctorId,
                                  String from,
                                  String to,
                                  int force,
                                  int drugProgramId) {
        contentState.postValue(ContentState.LOADING);
        model.getStatisticByDoctorId(doctorId, from, to, force, drugProgramId)
                .subscribe(
                        data -> {
                            if (data != null && data.getData() != null) {
                                contentState.postValue(ContentState.CONTENT);
                                responseLiveData.postValue(data.getData());
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

    private void getStatistics(String from,
                               String to,
                               int force,
                               int drugProgramId) {

        contentState.postValue(ContentState.LOADING);
        model.getStatistics(from, to, force, drugProgramId)
                .subscribe(
                        data -> {
                            if (data != null && data.getData() != null) {
                                contentState.postValue(ContentState.CONTENT);
                                responseLiveData.postValue(data.getData());
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
