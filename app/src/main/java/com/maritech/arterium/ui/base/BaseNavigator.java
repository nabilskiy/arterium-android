package com.maritech.arterium.ui.base;

import androidx.fragment.app.FragmentActivity;

import java.lang.ref.WeakReference;


public class BaseNavigator {

    WeakReference<FragmentActivity> activity;

    void attach(FragmentActivity activity) {
        this.activity = new WeakReference(activity);
    }

    void release() {
        this.activity = new WeakReference<FragmentActivity>(null);
    }

    public void back() {
        this.activity.get().onBackPressed();
    }
}