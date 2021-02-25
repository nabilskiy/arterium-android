package com.maritech.arterium.data.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DrugProgramsResponse implements Parcelable {

    @SerializedName("data")
    @Expose
    private List<DrugProgramModel> data = null;

    public final static Parcelable.Creator<DrugProgramsResponse> CREATOR = new Creator<DrugProgramsResponse>() {
        public DrugProgramsResponse createFromParcel(Parcel in) {
            return new DrugProgramsResponse(in);
        }

        public DrugProgramsResponse[] newArray(int size) {
            return (new DrugProgramsResponse[size]);
        }
    };

    protected DrugProgramsResponse(Parcel in) {
        in.readList(this.data, (DrugProgramModel.class.getClassLoader()));
    }

    public DrugProgramsResponse() {
    }

    public List<DrugProgramModel> getData() {
        return data;
    }

    public void setData(List<DrugProgramModel> data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(data);
    }

    public int describeContents() {
        return 0;
    }

}
