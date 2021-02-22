package com.maritech.arterium.data.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorResponse implements Parcelable {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("tag")
    @Expose
    private String tag;

    public final static Parcelable.Creator<ErrorResponse> CREATOR = new Creator<ErrorResponse>() {
        public ErrorResponse createFromParcel(Parcel in) {
            return new ErrorResponse(in);
        }

        public ErrorResponse[] newArray(int size) {
            return (new ErrorResponse[size]);
        }
    };

    protected ErrorResponse(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.tag = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ErrorResponse() {
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(tag);
    }

    public int describeContents() {
        return 0;
    }

}