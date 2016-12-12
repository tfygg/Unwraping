package com.tfygg.unwraping.db;

import android.content.Context;

/**
 * Created by tfygg on 2016/11/20.
 */

public class StockTable extends Table {


    public static final String COLUMN_DATE = "date";

    public static final String COLUMN_OPEN = "open";

    public static final String COLUMN_HIGH = "high";

    public static final String COLUMN_CLOSE = "close";

    public static final String COLUMN_LOW = "low";

    public static final String COLUMN_VOLUME = "volume";

    public static final String COLUMN_ID_IMAGE = "_id_image";

    private int version;

    public StockTable(Context ctx, String tableName, int version) {
        super(ctx, tableName);
        this.version = version;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void setTableAttr() {
        // TODO Auto-generated method stub
        this.mColumnIdInMedia = COLUMN_ID_IMAGE;

        this.tableContent.clear();

        this.tableContent.put(COLUMN_DATE, "VARCHAR");
        this.tableContent.put(COLUMN_OPEN, "VARCHAR");
        this.tableContent.put(COLUMN_HIGH, "VARCHAR");
        this.tableContent.put(COLUMN_CLOSE, "VARCHAR");
        this.tableContent.put(COLUMN_LOW, "VARCHAR");
        this.tableContent.put(COLUMN_VOLUME, "VARCHAR");
    }

    @Override
    protected void updateVersion() {
        // TODO Auto-generated method stub
        _DATA_CACHE_VERSION_ = version;
    }
}
