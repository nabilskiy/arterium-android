package com.maritech.arterium.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatisticsResponse implements Parcelable {

    @SerializedName("data")
    @Expose
    private StatisticsModel data;

    public final static Parcelable.Creator<StatisticsResponse> CREATOR = new Creator<StatisticsResponse>() {
        public StatisticsResponse createFromParcel(Parcel in) {
            return new StatisticsResponse(in);
        }

        public StatisticsResponse[] newArray(int size) {
            return (new StatisticsResponse[size]);
        }

    };

    protected StatisticsResponse(Parcel in) {
        this.data = ((StatisticsModel) in.readValue((StatisticsModel.class.getClassLoader())));
    }

    public StatisticsResponse() {
    }

    public StatisticsModel getData() {
        return data;
    }

    public void setData(StatisticsModel data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(data);
    }

    public int describeContents() {
        return 0;
    }

    public static class StatisticsModel implements Parcelable {

        @SerializedName("last_update_at")
        @Expose
        private long lastUpdateAt;
        @SerializedName("total")
        @Expose
        private Integer total;
        @SerializedName("primary_count")
        @Expose
        private Integer primaryCount;
        @SerializedName("secondary_count")
        @Expose
        private Integer secondaryCount;

        public final Parcelable.Creator<StatisticsModel> CREATOR = new Creator<StatisticsModel>() {

            public StatisticsModel createFromParcel(Parcel in) {
                return new StatisticsModel(in);
            }

            public StatisticsModel[] newArray(int size) {
                return (new StatisticsModel[size]);
            }

        };

        protected StatisticsModel(Parcel in) {
            this.lastUpdateAt = ((Integer) in.readValue((Integer.class.getClassLoader())));
            this.total = ((Integer) in.readValue((Integer.class.getClassLoader())));
            this.primaryCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
            this.secondaryCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        }

        public StatisticsModel() {
        }

        public long getLastUpdateAt() {
            return lastUpdateAt;
        }

        public void setLastUpdateAt(long lastUpdateAt) {
            this.lastUpdateAt = lastUpdateAt;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getPrimaryCount() {
            return primaryCount;
        }

        public void setPrimaryCount(Integer primaryCount) {
            this.primaryCount = primaryCount;
        }

        public Integer getSecondaryCount() {
            return secondaryCount;
        }

        public void setSecondaryCount(Integer secondaryCount) {
            this.secondaryCount = secondaryCount;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(lastUpdateAt);
            dest.writeValue(total);
            dest.writeValue(primaryCount);
            dest.writeValue(secondaryCount);
        }

        public int describeContents() {
            return 0;
        }

    }
}