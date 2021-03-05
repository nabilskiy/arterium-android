package com.maritech.arterium.data.sharePref;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maritech.arterium.data.models.DrugProgramModel;
import com.maritech.arterium.data.models.ProfileResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by ujujzk on 16.08.2017
 * Softensy Digital Studio
 * softensiteam@gmail.com
 */

public class Pref {

    private static final String AUTH_TOKEN = "User";
    private static final String FIRST_LAUNCH = "FirstLaunch";
    private static final String USER_DATA = "UserData";
    private static final String DRUG_PROGRAM_LAST_UPDATE = "drugProgramLastUpdate";
    private static final String DRUG_PROGRAM_ID = "drugProgramKey";
    private static final String DRUG_PROGRAM_LIST = "drugProgramListKey";
    private static final String PIN_CODE_KEY = "pinCodeKey";
    private static final String PIN_CODE_ENABLE_KEY = "pinCodeEnableKey";
    private static final String BIO_ENABLE_KEY = "bioEnableKey";
    private static final String LANGUAGE = "language";

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

    public void setAuthToken(Context context, String authtoken) {
        SharedPreferences.Editor prefsEditor = getPrefs(context).edit();
        prefsEditor.putString(AUTH_TOKEN, authtoken);
        prefsEditor.apply();
    }

    public String getAuthToken(Context context) {
        return getPrefs(context).getString(AUTH_TOKEN, "");
    }

    public void setUserFirstLaunch(Context context, boolean isFirstLaunch) {
        SharedPreferences.Editor prefsEditor = getPrefs(context).edit();
        prefsEditor.putBoolean(FIRST_LAUNCH, isFirstLaunch);
        prefsEditor.apply();
    }

    public boolean isUserFirstLaunch(Context context) {
        return getPrefs(context).getBoolean(FIRST_LAUNCH, true);
    }

    public void setUserProfile(Context context, ProfileResponse profileResponse) {
        SharedPreferences.Editor prefsEditor = getPrefs(context).edit();
        Gson gson = new Gson();
        String json;
        if (profileResponse != null) {
            json = gson.toJson(profileResponse);
        } else {
            json = "";
        }
        prefsEditor.putString(USER_DATA, json);
        prefsEditor.apply();
    }

    public ProfileResponse getUserProfile(Context context) {
        String json = getPrefs(context).getString(USER_DATA, "");
        Gson gson = new Gson();
        return gson.fromJson(json, ProfileResponse.class);
    }

    public long getDrugProgramLastMillis(Context context) {
        return getPrefs(context).getLong(DRUG_PROGRAM_LAST_UPDATE, 0);
    }

    public void setDrugProgramLastMillis(Context context, long millis) {
        SharedPreferences.Editor prefsEditor = getPrefs(context).edit();
        prefsEditor.putLong(DRUG_PROGRAM_LAST_UPDATE, millis);
        prefsEditor.apply();
    }

    public void setDrugProgramId(Context context, int id) {
        SharedPreferences.Editor prefsEditor = getPrefs(context).edit();
        prefsEditor.putInt(DRUG_PROGRAM_ID, id);
        prefsEditor.apply();
    }

    public int getDrugProgramId(Context context) {
        return getPrefs(context).getInt(DRUG_PROGRAM_ID, 1);
    }

    public void setDrugProgramList(Context context, List<DrugProgramModel> models) {
        SharedPreferences.Editor prefsEditor = getPrefs(context).edit();
        Gson gson = new Gson();
        String json;
        if (models != null && models.size() > 0) {
            json = gson.toJson(models);
        } else {
            json = "";
        }
        prefsEditor.putString(DRUG_PROGRAM_LIST, json);
        prefsEditor.apply();
    }

    public ArrayList<DrugProgramModel> getDrugProgramList(Context context) {
        String str = getPrefs(context).getString(DRUG_PROGRAM_LIST, "");
        Type itemsListType = new TypeToken<ArrayList<DrugProgramModel>>() {}.getType();

        return new Gson().fromJson(str,itemsListType);
    }

    public void setPinCode(Context context, String pinCode) {
        SharedPreferences.Editor prefsEditor = getPrefs(context).edit();
        prefsEditor.putString(PIN_CODE_KEY, pinCode);
        prefsEditor.apply();
    }

    public String getPinCode(Context context) {
        return getPrefs(context).getString(PIN_CODE_KEY, "");
    }

    public void setPinCodeEnable(Context context, boolean enable) {
        SharedPreferences.Editor prefsEditor = getPrefs(context).edit();
        prefsEditor.putBoolean(PIN_CODE_ENABLE_KEY, enable);
        prefsEditor.apply();
    }

    public boolean isPinCodeEnabled(Context context) {
        return getPrefs(context).getBoolean(PIN_CODE_ENABLE_KEY, false);
    }

    public void setFingerPrintEnable(Context context, boolean enable) {
        SharedPreferences.Editor prefsEditor = getPrefs(context).edit();
        prefsEditor.putBoolean(BIO_ENABLE_KEY, enable);
        prefsEditor.apply();
    }

    public boolean isFingerPrintEnabled(Context context) {
        return getPrefs(context).getBoolean(BIO_ENABLE_KEY, false);
    }

    public void setLanguage(Context context, String lang) {
        SharedPreferences.Editor prefsEditor = getPrefs(context).edit();
        prefsEditor.putString(LANGUAGE, lang);
        prefsEditor.apply();
    }

    public String getLanguage(Context context) {
        return getPrefs(context).getString(LANGUAGE, "ua");
    }
}
