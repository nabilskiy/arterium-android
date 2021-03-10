package com.maritech.arterium.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientResponse {

    @SerializedName("data")
    @Expose
    private PatientModel data;

    public PatientModel getData() {
        return data;
    }

    public void setData(PatientModel data) {
        this.data = data;
    }

}