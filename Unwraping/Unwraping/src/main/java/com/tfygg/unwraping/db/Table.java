package com.tfygg.unwraping.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tfygg on 2016/11/20.
 */

public abstract class Table {

    private static final String DB_NAME = "stock.db";

    protected String tableName;

    private final Context mContext;

    protected SQLiteDatabase.CursorFactory mFactory;

    protected Map<String, String> tableContent;

    private SQLiteDatabase mDataBase;

    protected DatabaseHelper dbHelper;

    protected String mColumnIdInMedia;

    private boolean mIsInit;

    protected int _DATA_CACHE_VERSION_ = 1;

    public Table(Context ctx, String tableName) {
        this.tableName = tableName;
        this.mContext = ctx;
        this.tableContent = new HashMap<String, String>();
        mIsInit = false;
        this.mFactory = null;
        mColumnIdInMedia = null;
        this.getClass().getSimpleName().toLowerCase();
    }

    protected abstract void updateVersion();

    public void init() {
        if (mIsInit) {
            Log.d("table", "table exist");
            return;
        }
        Log.d("table", "create table");
        updateVersion();

        dbHelper = new DatabaseHelper(mContext, DB_NAME, mFactory,
                _DATA_CACHE_VERSION_);

        this.setTableAttr();

        Log.d("tableName", tableName);
        dbHelper.setTableAttr(tableName, tableContent);
        mDataBase = dbHelper.getWritableDatabase();

        Log.d("table", "mIsInit");
        mIsInit = true;

    }

    protected abstract void setTableAttr();

    public boolean insert(ContentValues values) {
        Log.d("db", "insert");
        if (mDataBase == null) {
            return false;
        }

        mDataBase.insert(this.tableName, null, values);
        return true;
    }

    public boolean delete(String whereClause) {

        if (mDataBase == null) {
            return false;
        }

        mDataBase.delete(this.tableName, whereClause, null);
        return true;
    }

    public boolean update(ContentValues values, String whereClause) {
        if (mDataBase == null) {
            return false;
        }

        mDataBase.update(this.tableName, values, whereClause, null);
        return true;
    }

    private Cursor query(String columnName, int id) {
        if (mDataBase == null) {
            return null;
        }

        if (mColumnIdInMedia == null ||
                mColumnIdInMedia.equalsIgnoreCase("")) {
            mColumnIdInMedia = DatabaseHelper.COLUMN_ID;
        }

        Cursor cursor = mDataBase.query(this.tableName, new String[]{columnName, mColumnIdInMedia}, null, null, null,
                null, "_id asc");

        int _idIndex = cursor.getColumnIndex(mColumnIdInMedia);

        if (_idIndex != -1) {
            for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
                if (id == cursor.getInt(_idIndex)) {
                    return cursor;
                }
            }
        }

        cursor.close();
        return null;

    }

    public String queryString(String columnName, int id) {
        Cursor cursor = query(columnName, id);

        String value = null;

        if (cursor != null) {
            value = cursor.getString(0);
            cursor.close();
        }

        return value;
    }

    public int queryInteger(String columnName, int id) {
        Cursor cursor = query(columnName, id);

        int value = -1;

        if (cursor != null) {
            value = cursor.getInt(0);
            cursor.close();
        }

        return value;
    }

    public double queryDouble(String columnName, int id) {
        Cursor cursor = query(columnName, id);

        double value = -1;

        if (cursor != null) {
            value = cursor.getDouble(0);
            cursor.close();
        }

        return value;
    }

    public void Release() {

        if (this.mDataBase != null) {
            this.mDataBase.close();
            this.mDataBase = null;
        }

        this.mIsInit = false;
    }

}
