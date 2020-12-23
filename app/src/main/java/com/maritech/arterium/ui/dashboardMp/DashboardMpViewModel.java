package com.maritech.arterium.ui.dashboardMp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardMpViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DashboardMpViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard Mp fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}