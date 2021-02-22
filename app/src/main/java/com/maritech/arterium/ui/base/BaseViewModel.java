package com.maritech.arterium.ui.base;

import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends ViewModel {

    protected CompositeDisposable disposable = new CompositeDisposable();

    public BaseViewModel() {
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }

}
