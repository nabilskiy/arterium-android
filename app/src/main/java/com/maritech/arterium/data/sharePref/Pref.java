package com.maritech.arterium.data.sharePref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maritech.arterium.data.models.DrugProgramModel;
import com.maritech.arterium.data.models.ProfileResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ujujzk on 16.08.2017
 * Softensy Digital Studio
 * softensiteam@gmail.com
 */

public class Pref {

    private static final String AUTH_TOKEN = "User";
    private static final String FIRST_LAUNCH = "FirstLaunch";
    private static final String DEVICE_UUID = "DeviceUUID";
    private static final String USER_DATA = "UserData";
    private static final String DRUG_PROGRAM_LAST_UPDATE = "drugProgramLastUpdate";
    private static final String DRUG_PROGRAM_ID = "drugProgramKey";
    private static final String DRUG_PROGRAM_LIST = "drugProgramListKey";

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

    public void setDeviceUUID(Context context, String uuid) {
        SharedPreferences.Editor prefsEditor = getPrefs(context).edit();
        prefsEditor.putString(DEVICE_UUID, uuid);
        prefsEditor.apply();
    }

    public String getDeviceUUID(Context context) {
        return getPrefs(context).getString(DEVICE_UUID, "");
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

    public void setDrugProgramId(Context context, int id) {
        SharedPreferences.Editor prefsEditor = getPrefs(context).edit();
        prefsEditor.putInt(DRUG_PROGRAM_ID, id);
        prefsEditor.apply();
    }

    public long getDrugProgramLastMillis(Context context) {
        return getPrefs(context).getLong(DRUG_PROGRAM_LAST_UPDATE, 0);
    }

    public void setDrugProgramLastMillis(Context context, long millis) {
        SharedPreferences.Editor prefsEditor = getPrefs(context).edit();
        prefsEditor.putLong(DRUG_PROGRAM_LAST_UPDATE, millis);
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
}
