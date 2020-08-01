package com.noobcode.task_behtaar.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/*
* SHARED PREFERENCES CLASS TO UPDATE AND RETRIEVE LAST TIME DATA WAS SAVED
* */

public class SharedPreferencesHelper {
    public static final String PREF_TIME = "Pref_time";
    private static SharedPreferencesHelper instance = null;
    private SharedPreferences prefs;

    private SharedPreferencesHelper(Context context){
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreferencesHelper getInstance(Context context){
        if(instance == null){
            instance = new SharedPreferencesHelper(context);
        }
        return instance;
    }

    public void saveUpdateTime(long time){
        prefs.edit().putLong(PREF_TIME, time).apply();
    }

    public long getUpdateTime(){
        return prefs.getLong(PREF_TIME, 0);
    }
}
