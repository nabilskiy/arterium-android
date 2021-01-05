package com.maritech.arterium.ui.choose_mp.data;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;

import com.maritech.arterium.R;

public class ChooseMpContent {
    private String name;
    private int photo;
    private String numberPhone;
    private String city;


    public ChooseMpContent(String name, int photo, String numberPhone, String city) {
        this.name = name;
        this.photo = photo;
        this.numberPhone = numberPhone;
        this.city = city;
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
}
