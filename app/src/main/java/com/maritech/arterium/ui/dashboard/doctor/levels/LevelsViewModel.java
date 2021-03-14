package com.maritech.arterium.ui.dashboard.doctor.levels;

import com.maritech.arterium.common.ContentState;
import com.maritech.arterium.data.models.LevelModel;
import com.maritech.arterium.data.network.ArteriumDataProvider;
import com.maritech.arterium.data.network.DataProvider;
import com.maritech.arterium.ui.base.BaseViewModel;
import com.maritech.arterium.ui.base.SingleLiveEvent;

import java.util.List;

public class LevelsViewModel extends BaseViewModel {

    public SingleLiveEvent<List<LevelModel>> responseLiveData = new SingleLiveEvent<>();
    public SingleLiveEvent<ContentState> contentState = new SingleLiveEvent<>();
    public SingleLiveEvent<String> errorMessage = new SingleLiveEvent<>();

    private final DataProvider model;

    public LevelsViewModel() {
        model = ArteriumDataProvider.getInstance();
    }

    public void getLevels(int programId) {
        contentState.postValue(ContentState.LOADING);
        model.getLevels(programId)
                .subscribe(
                        data -> {
                            if (data != null &&
                                    data.getData() != null &&
                                    !data.getData().isEmpty()) {

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
