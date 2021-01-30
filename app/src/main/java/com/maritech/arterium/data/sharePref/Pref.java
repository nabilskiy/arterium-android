package com.maritech.arterium.data.sharePref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

/**
 * Created by ujujzk on 16.08.2017
 * Softensy Digital Studio
 * softensiteam@gmail.com
 */

public class Pref {

    private static final String PREF_KEY_USER = "User";
    //================================== SINGLETON ==========================================

    private static volatile Pref instance;

    private Pref() {
        //Prevent form the reflection api.
        if (instance != null) {
            throw new AssertionError("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static Pref getInstance() {
        if (instance == null) {
            synchronized (Pref.class) {
                if (instance == null) {
                    instance = new Pref();
                }
            }
        }
        return instance;
    }

    //Make singleton from serialize and deserialize operation.
    protected Pref readResolve() {
        return getInstance();
    }


    //====================================================================================

    private SharedPreferences getPrefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }


    public void saveAuthtoken(Context context, String authtoken) {
        SharedPreferences.Editor prefsEditor = getPrefs(context).edit();
        prefsEditor.putString(PREF_KEY_USER, authtoken);
        prefsEditor.apply();
    }

    public String getUser(Context context) {
        Gson gson = new Gson();
        return getPrefs(context).getString(PREF_KEY_USER, "");
    }



}
