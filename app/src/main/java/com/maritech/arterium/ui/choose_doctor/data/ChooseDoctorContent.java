package com.maritech.arterium.ui.choose_doctor.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.maritech.arterium.R;

public class ChooseDoctorContent implements Parcelable {
    private String name;
    private int photo;
    private String numberPhone;
    private String city;
    private Boolean isSelected = false;


    public ChooseDoctorContent(String name, int photo, String numberPhone, String city) {
        this.name = name;
        this.photo = photo;
        this.numberPhone = numberPhone;
        this.city = city;
    }

    protected ChooseDoctorContent(Parcel in) {
        name = in.readString();
        photo = in.readInt();
        numberPhone = in.readString();
        city = in.readString();
        byte tmpIsSelected = in.readByte();
        isSelected = tmpIsSelected == 0 ? null : tmpIsSelected == 1;
    }

    public static final Creator<ChooseDoctorContent> CREATOR = new Creator<ChooseDoctorContent>() {
        @Override
        public ChooseDoctorContent createFromParcel(Parcel in) {
            return new ChooseDoctorContent(in);
        }

        @Override
        public ChooseDoctorContent[] newArray(int size) {
            return new ChooseDoctorContent[size];
        }
    };

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;

        if (photo == 0) {
            this.photo = R.drawable.ic_default_avatar;
        }
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(photo);
        dest.writeString(numberPhone);
        dest.writeString(city);
        dest.writeByte((byte) (isSelected == null ? 0 : isSelected ? 1 : 2));
    }
}
