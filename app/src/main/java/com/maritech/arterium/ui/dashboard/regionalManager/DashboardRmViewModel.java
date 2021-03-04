package com.maritech.arterium.ui.dashboard.regionalManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardRmViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DashboardRmViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard Rm fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
