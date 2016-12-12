package com.tfygg.unwraping.entity;

/**
 * Created by Tong FY on 2016/11/11.
 */
public class StockDb {
    private String date;
    private String open;
    private String high;
    private String low;
    private String close;
    private String volume;


    public StockDb(String str) {
        String[] strArray = str.split("\\,");
        date = strArray[0];
        open = strArray[1];
        high = strArray[2];
        close = strArray[3];
        low = strArray[4];
        volume = strArray[5];
    }

    @Override
    public String toString() {
        return "StockDb [date=" + date + ", open=" + open + ", high=" + high
                + ", low=" + low + ", close=" + close + ", volume=" + volume
                + "]";
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

}
