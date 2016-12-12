package com.tfygg.unwraping.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by tfygg on 2016/11/20.
 */

public class DatabaseHelper extends MySQLiteOpenHelper {

    public static final String COLUMN_ID = "_id";

    private String tableName;
    private Map<String, String> tableContent;

    DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory,
                   int version) {
        super(context, name, cursorFactory, version);
        this.tableName = null;
        this.tableContent = new HashMap<String, String>();
    }

    public void setTableAttr(String tableName, Map<String, String> tableContent) {
        this.tableName = tableName;
        this.tableContent = tableContent;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        if (this.tableName == null ||
                this.tableContent.size() == 0) {
            return;
        }

        String sql = "create table if not exists ";
        sql += this.tableName;
        sql += " (";
        sql += COLUMN_ID;
        sql += " INTEGER PRIMARY KEY, ";

        Set set = tableContent.entrySet();
        Iterator iterator = set.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            index++;
            Map.Entry mapEntry = (Map.Entry) iterator.next();
            if (mapEntry.getValue() != null) {
                sql += (String) mapEntry.getKey();
                sql += " ";
                sql += (String) mapEntry.getValue();

                if (index < this.tableContent.size()) {
                    sql += ",";
                }
            }
        }

        sql += ")";
        Log.d("sql", sql);
//        sql = "CREATE TABLE person (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, age SMALLINT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        this.onCreate(db);
    }


}
