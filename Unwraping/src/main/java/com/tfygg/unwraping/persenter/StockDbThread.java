package com.tfygg.unwraping.persenter;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tfygg.unwraping.StockApplication;
import com.tfygg.unwraping.db.DbVersionManager;
import com.tfygg.unwraping.db.StockTable;
import com.tfygg.unwraping.db.Table;
import com.tfygg.unwraping.entity.StockDb;
import com.tfygg.unwraping.http.HttpConnection;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Tong FY on 2016/11/15.
 */
public class StockDbThread extends Thread {
    private static final String TAG = "StockDbThread";

    private String stockId;
    private StockDb stockDb;
    private String tableName;
    private Table stockTable;
    private Handler handler;
    private Context mContext;

    private HttpConnection httpConnection;
    private String stockNow;
    private BufferedReader reader;
    private DbVersionManager dbVersionManager = DbVersionManager.getInstance();

    public StockDbThread(String id, Handler handler) {
        stockId = id;
        this.handler = handler;
        mContext = StockApplication.getContent();
    }

    @Override
    public void run() {

        httpConnection = new HttpConnection();
        stockNow = httpConnection.queryStockNow(stockId);
        Log.i("StockDbThread", stockNow);

        Message msg = handler.obtainMessage();
        Bundle b = new Bundle();
        b.putString("stockNow", stockNow);
        msg.setData(b);
        msg.sendToTarget();

        tableName = getTableName();
        if (tableName == null)
            return;


        stockTable = new StockTable(mContext, tableName, dbVersionManager.updateVersion());
        stockTable.init();

        reader = httpConnection.queryStock(stockId);
        String line;
        try {
            while (null != (line = reader.readLine())) {
                Log.d("line", line);
                saveToDb(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        stockTable.Release();
    }

    private void saveToDb(String line) {
        Log.d("saveToDb", "saveToDb");
        stockDb = new StockDb(line);


        ContentValues values = new ContentValues();
        values.put(StockTable.COLUMN_DATE, stockDb.getDate());
        values.put(StockTable.COLUMN_OPEN, stockDb.getOpen());
        values.put(StockTable.COLUMN_HIGH, stockDb.getHigh());
        values.put(StockTable.COLUMN_CLOSE, stockDb.getClose());
        values.put(StockTable.COLUMN_LOW, stockDb.getLow());
        values.put(StockTable.COLUMN_VOLUME, stockDb.getVolume());
        stockTable.insert(values);


    }

    @Nullable
    private String getTableName() {
        String tableName;
        if (stockId.endsWith("000001")) {
            tableName = "sh" + stockId;
        } else if (stockId.startsWith("6")) {
            tableName = "sh" + stockId;
        } else if (stockId.startsWith("0") || stockId.startsWith("3")) {
            tableName = "sz" + stockId;
        } else {
            return null;
        }
        return tableName;
    }

}
