package com.maritech.arterium.ui.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel<T> extends ViewModel {

    public MutableLiveData<T> responseLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();
    public MutableLiveData<String> error = new MutableLiveData<>();

    protected CompositeDisposable disposable = new CompositeDisposable();

    public BaseViewModel() {
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }

}
