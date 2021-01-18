package com.maritech.arterium.ui.dashboardDoctor.data;

public class PatientPurchasesContent {

    String name;
    String data;
    String message;
    //Data data;
    //DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.ENGLISH);

    public PatientPurchasesContent(String name, String data, String message) {
        this.name = name;
        this.data = data;
        this.message = message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
