package com.tfygg.unwraping.db;

import android.content.SharedPreferences;
import android.os.PersistableBundle;

import com.tfygg.unwraping.StockApplication;
import com.tfygg.unwraping.sharedperfences.PersistProvider;

/**
 * Created by tfy on 2016/11/23.
 */

public class DbVersionManager {

    private PersistProvider persistProvider;
    private String DbVersion = "Db Version";

    private int version;

    private static DbVersionManager mInstance = new DbVersionManager();

    private DbVersionManager() {
        persistProvider = PersistProvider.getInstance(StockApplication.getContent());
        version = persistProvider.getIntPersist(DbVersion, 1);
    }

    public static DbVersionManager getInstance() {
        return mInstance;
    }

    public int updateVersion() {
        version = version + 1;
        persistProvider.persist(DbVersion, version);
        return version;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
