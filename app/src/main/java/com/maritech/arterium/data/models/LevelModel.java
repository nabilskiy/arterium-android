package com.maritech.arterium.data.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LevelModel implements Parcelable {

    @SerializedName("drug_program_id")
    @Expose
    private int drugProgramId;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("condition_from")
    @Expose
    private int conditionFrom;
    @SerializedName("condition_to")
    @Expose
    private int conditionTo;
    @SerializedName("primary_pay")
    @Expose
    private String primaryPay;
    @SerializedName("secondary_pay")
    @Expose
    private String secondaryPay;

    public final static Parcelable.Creator<LevelModel> CREATOR = new Creator<LevelModel>() {
        public LevelModel createFromParcel(Parcel in) {
            return new LevelModel(in);
        }

        public LevelModel[] newArray(int size) {
            return (new LevelModel[size]);
        }
    };

    protected LevelModel(Parcel in) {
        this.drugProgramId = ((int) in.readValue((int.class.getClassLoader())));
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.conditionFrom = ((int) in.readValue((int.class.getClassLoader())));
        this.conditionTo = ((int) in.readValue((int.class.getClassLoader())));
        this.primaryPay = ((String) in.readValue((String.class.getClassLoader())));
        this.secondaryPay = ((String) in.readValue((String.class.getClassLoader())));
    }

    public LevelModel() {
    }

    public int getDrugProgramId() {
        return drugProgramId;
    }

    public void setDrugProgramId(int drugProgramId) {
        this.drugProgramId = drugProgramId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getConditionFrom() {
        return conditionFrom;
    }

    public void setConditionFrom(int conditionFrom) {
        this.conditionFrom = conditionFrom;
    }

    public int getConditionTo() {
        return conditionTo;
    }

    public void setConditionTo(int conditionTo) {
        this.conditionTo = conditionTo;
    }

    public String getPrimaryPay() {
        return primaryPay;
    }

    public void setPrimaryPay(String primaryPay) {
        this.primaryPay = primaryPay;
    }

    public String getSecondaryPay() {
        return secondaryPay;
    }

    public void setSecondaryPay(String secondaryPay) {
        this.secondaryPay = secondaryPay;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(drugProgramId);
        dest.writeValue(code);
        dest.writeValue(conditionFrom);
        dest.writeValue(conditionTo);
        dest.writeValue(primaryPay);
        dest.writeValue(secondaryPay);
    }

    public int describeContents() {
        return 0;
    }

}