package com.maritech.arterium.utils;

import android.content.Context;

import com.maritech.arterium.data.sharePref.Pref;

import java.util.Locale;

public class DateTimeUtil {

    public static Locale getLocale(Context context) {
        String lang = Pref.getInstance().getLanguage(context);

        if (lang.equalsIgnoreCase("en")) {
            return Locale.US;
        } else if (lang.equalsIgnoreCase("ru")) {
            return new Locale("ru");
        } else {
            return new Locale("uk");
        }
    }

}
