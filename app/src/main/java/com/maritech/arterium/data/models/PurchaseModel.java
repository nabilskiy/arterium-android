package com.maritech.arterium.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PurchaseModel implements Parcelable {

    @SerializedName("patient_id")
    @Expose
    private int patientId;
    @SerializedName("patient_name")
    @Expose
    private String patientName;
    @SerializedName("sold_sum")
    @Expose
    private String soldSum;
    @SerializedName("is_primary")
    @Expose
    private Boolean isPrimary;
    @SerializedName("timestamp")
    @Expose
    private long timestamp;

    public final static Parcelable.Creator<PurchaseModel> CREATOR = new Creator<PurchaseModel>() {
        public PurchaseModel createFromParcel(Parcel in) {
            return new PurchaseModel(in);
        }

        public PurchaseModel[] newArray(int size) {
            return (new PurchaseModel[size]);
        }
    };

    protected PurchaseModel(Parcel in) {
        this.patientId = ((int) in.readValue((Integer.class.getClassLoader())));
        this.patientName = ((String) in.readValue((String.class.getClassLoader())));
        this.soldSum = ((String) in.readValue((String.class.getClassLoader())));
        this.isPrimary = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.timestamp = ((long) in.readValue((Integer.class.getClassLoader())));
    }

    public PurchaseModel() {
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getSoldSum() {
        return soldSum;
    }

    public void setSoldSum(String soldSum) {
        this.soldSum = soldSum;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(patientId);
        dest.writeValue(patientName);
        dest.writeValue(soldSum);
        dest.writeValue(isPrimary);
        dest.writeValue(timestamp);
    }

    public int describeContents() {
        return 0;
    }
}