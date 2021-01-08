package com.maritech.arterium.ui.dashboard.data;

import java.text.DateFormat;
import java.util.Locale;

public class PatientPurchasesContent {

    String name;
    String data;
    //Data data;
    //DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.ENGLISH);


    public PatientPurchasesContent(String name, String data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
