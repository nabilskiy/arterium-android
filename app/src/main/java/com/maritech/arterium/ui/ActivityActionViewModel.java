package com.maritech.arterium.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ActivityActionViewModel extends ViewModel {

    public MutableLiveData<Boolean> onBackPress = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> onRecreate = new MutableLiveData<>(false);

}
