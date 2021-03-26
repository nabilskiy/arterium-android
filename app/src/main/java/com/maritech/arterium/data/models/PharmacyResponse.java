package com.maritech.arterium.data.models;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PharmacyResponse implements Parcelable {

    @SerializedName("data")
    @Expose
    private ArrayList<PharmacyModel> data = null;
    public final static Parcelable.Creator<PharmacyResponse> CREATOR = new Creator<PharmacyResponse>() {
        public PharmacyResponse createFromParcel(Parcel in) {
            return new PharmacyResponse(in);
        }

        public PharmacyResponse[] newArray(int size) {
            return (new PharmacyResponse[size]);
        }

    };

    protected PharmacyResponse(Parcel in) {
        in.readList(this.data, (PharmacyModel.class.getClassLoader()));
    }

    public PharmacyResponse() {
    }

    public ArrayList<PharmacyModel> getData() {
        return data;
    }

    public void setData(ArrayList<PharmacyModel> data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(data);
    }

    public int describeContents() {
        return 0;
    }

}