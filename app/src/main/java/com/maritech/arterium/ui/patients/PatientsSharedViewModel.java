package com.maritech.arterium.ui.patients;

import com.maritech.arterium.common.PurchasesType;
import com.maritech.arterium.ui.base.BaseViewModel;
import com.maritech.arterium.ui.base.SingleLiveEvent;

public class PatientsSharedViewModel extends BaseViewModel {

    public SingleLiveEvent<String[]> dates = new SingleLiveEvent<>();
    public SingleLiveEvent<PurchasesType> purchasesFilter = new SingleLiveEvent<>();
    public SingleLiveEvent<String> searchQuery = new SingleLiveEvent<>();
    public SingleLiveEvent<String> filter = new SingleLiveEvent<>();

}
