package com.maritech.arterium.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LevelsResponse implements Parcelable {

    @SerializedName("data")
    @Expose
    private List<LevelModel> data = null;

    public final static Creator<LevelsResponse> CREATOR = new Creator<LevelsResponse>() {
        public LevelsResponse createFromParcel(Parcel in) {
            return new LevelsResponse(in);
        }

        public LevelsResponse[] newArray(int size) {
            return (new LevelsResponse[size]);
        }
    };

    protected LevelsResponse(Parcel in) {
        in.readList(this.data, (DrugProgramModel.class.getClassLoader()));
    }

    public LevelsResponse() {
    }

    public List<LevelModel> getData() {
        return data;
    }

    public void setData(List<LevelModel> data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(data);
    }

    public int describeContents() {
        return 0;
    }

}
