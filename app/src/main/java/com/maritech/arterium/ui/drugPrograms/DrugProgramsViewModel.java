package com.maritech.arterium.ui.drugPrograms;

import com.maritech.arterium.common.ContentState;
import com.maritech.arterium.data.models.DrugProgramModel;
import com.maritech.arterium.data.network.ArteriumDataProvider;
import com.maritech.arterium.data.network.DataProvider;
import com.maritech.arterium.ui.base.BaseViewModel;
import com.maritech.arterium.ui.base.SingleLiveEvent;

import java.util.List;

public class DrugProgramsViewModel extends BaseViewModel {

    public SingleLiveEvent<List<DrugProgramModel>> responseLiveData = new SingleLiveEvent<>();
    public SingleLiveEvent<ContentState> contentState = new SingleLiveEvent<>();
    public SingleLiveEvent<String> errorMessage = new SingleLiveEvent<>();

    private final DataProvider model;

    public DrugProgramsViewModel() {
        model = ArteriumDataProvider.getInstance();
    }

    public void getDrugPrograms() {
        contentState.postValue(ContentState.LOADING);
        model.getDrugPrograms()
                .subscribe(
                        data -> {
                            if (data != null) {
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
