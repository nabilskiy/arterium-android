package com.maritech.arterium.ui.pharmacies;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.maritech.arterium.common.ContentState;
import com.maritech.arterium.data.models.PharmacyModel;
import com.maritech.arterium.data.models.PharmacyResponse;
import com.maritech.arterium.data.network.ArteriumDataProvider;
import com.maritech.arterium.data.network.DataProvider;
import com.maritech.arterium.ui.base.SingleLiveEvent;
import java.util.ArrayList;

public class PharmaciesViewModel extends ViewModel {

    public MutableLiveData<PharmacyResponse> responseLiveData = new MutableLiveData<>();
    public SingleLiveEvent<ContentState> contentState = new SingleLiveEvent<>();
    public SingleLiveEvent<String> errorMessage = new SingleLiveEvent<>();
    public MutableLiveData<Integer> selectedPharmacyPosition = new MutableLiveData<>();

    public MutableLiveData<ArrayList<PharmacyModel>> modelList = new MutableLiveData<>();

    private final DataProvider model;

    public PharmaciesViewModel() {
        model = ArteriumDataProvider.getInstance();
    }

    public void getPharmacies() {
        contentState.postValue(ContentState.LOADING);

        model.getPharmacies()
                .subscribe(data -> {
                            if (data != null && data.getData() != null &&
                                    !data.getData().isEmpty()) {
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