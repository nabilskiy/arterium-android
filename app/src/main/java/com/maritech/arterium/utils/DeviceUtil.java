package com.maritech.arterium.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;

public class DeviceUtil {
    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context context) {
        //TODO
//        return Settings.Secure.getString(
//               context.getContentResolver(), Settings.Secure.ANDROID_ID
//       );

        return "383b5b4f-dbe9-439c-a6ea-88e73f81b25f";
    }
}
