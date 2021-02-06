package com.maritech.arterium.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileResponse {
    @SerializedName("data")
    @Expose
    private Profile data;

    public Profile getData() {
        return data;
    }

    public void setData(Profile data) {
        this.data = data;
    }
}