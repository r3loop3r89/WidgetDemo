package com.shra1.widgetdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesStorage {
    private static SharedPreferencesStorage INSTANCE = null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SharedPreferencesStorage(Context mCtx) {
        sharedPreferences = mCtx.getSharedPreferences(
                mCtx.getPackageName() + "." + this.getClass().getName(),
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static SharedPreferencesStorage getInstance(Context mCtx) {
        if (INSTANCE == null) {
            INSTANCE = new SharedPreferencesStorage(mCtx);
        }
        return INSTANCE;
    }

    public boolean getEnableDisableNotificationStatus() {
        return sharedPreferences.getBoolean("EnableDisableNotificationStatus", false);
    }

    public void setEnableDisableNotificationStatus(boolean EnableDisableNotificationStatus) {
        editor.putBoolean("EnableDisableNotificationStatus", EnableDisableNotificationStatus);
        editor.commit();
    }
}
