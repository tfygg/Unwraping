package com.tfygg.unwraping.sharedperfences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by tfygg .
 */
public class PersistProvider {

    private static PersistProvider mIntsnace;
    private SharedPreferences mSp;
    private SharedPreferences.Editor mEditor;

    private PersistProvider(Context context) {
        mSp = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mSp.edit();
    }

    public static PersistProvider getInstance(Context context) {
        if (mIntsnace == null) {
            mIntsnace = new PersistProvider(context.getApplicationContext());
        }
        return mIntsnace;
    }

    public void persist(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.apply();
    }

    public void persist(String key, String value) {
        mEditor.putString(key, value);
        mEditor.apply();
    }

    public void persist(String key, float value) {
        mEditor.putFloat(key, value);
        mEditor.apply();
    }

    public void persist(String key, double value) {
        mEditor.putFloat(key, (float) value);
        mEditor.apply();
    }

    public void persist(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.apply();
    }

    public String getStringPersist(String key, String defaultValue) {
        return mSp.getString(key, defaultValue);
    }

    public int getIntPersist(String key, int defaultValue) {
        return mSp.getInt(key, defaultValue);
    }

    public float getFloatPersist(String key, float defaultValue) {
        return mSp.getFloat(key, defaultValue);
    }

    public boolean getBooleanPersist(String key, boolean defaultValue) {
        return mSp.getBoolean(key, defaultValue);
    }

    public void clear() {
        mEditor.clear();
        mEditor.commit();
    }

}