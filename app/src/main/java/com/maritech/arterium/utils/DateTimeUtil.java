package com.maritech.arterium.utils;

import android.content.Context;

import com.maritech.arterium.data.sharePref.Pref;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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


    public static String getCurrentMonth() {
        SimpleDateFormat dateFormat = new SimpleDateFormat( "dd MMMM", Locale.getDefault() );
        return dateFormat.format( new Date() );
    }



}
