package com.maritech.arterium.ui.statistics.purchases;

import com.maritech.arterium.common.PurchasesType;
import com.maritech.arterium.ui.base.BaseViewModel;
import com.maritech.arterium.ui.base.SingleLiveEvent;

public class StatSharedViewModel extends BaseViewModel {

    public SingleLiveEvent<Boolean> reload = new SingleLiveEvent<>();
    public SingleLiveEvent<String[]> dates = new SingleLiveEvent<>();
    public SingleLiveEvent<PurchasesType> purchasesFilter = new SingleLiveEvent<>();
    public SingleLiveEvent<String> searchQuery = new SingleLiveEvent<>();

}
