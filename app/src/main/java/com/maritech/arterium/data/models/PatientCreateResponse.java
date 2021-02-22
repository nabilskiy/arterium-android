package com.maritech.arterium.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientCreateResponse {

    @SerializedName("data")
    @Expose
    private PatientCreateModel data;

    public PatientCreateModel getData() {
        return data;
    }

    public void setData(PatientCreateModel data) {
        this.data = data;
    }
}