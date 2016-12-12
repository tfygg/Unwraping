package com.tfygg.unwraping;

import android.app.Application;
import android.content.Context;

/**
 * Created by Tong FY on 2016/11/11.
 */
public class StockApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContent() {
        return mContext;
    }

}
