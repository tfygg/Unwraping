package com.tfygg.unwraping.entity;

/**
 * Created by tfygg on 2016/11/19.
 */

public class StockNow {
    private String id;

    private String name;
    private String open;
    private String closeYesterday;
    private String now;

    private String volume;

    private String date;
    private String time;

    public StockNow(String id, String str) {
        this.id = id;

        String[] leftRight = str.split("=");
        String right = leftRight[1].replaceAll("\"", "");
        String[] strArray = right.split("\\,");
        name = strArray[0];
        open = strArray[1];
        closeYesterday = strArray[2];
        now = strArray[3];

        volume = strArray[8];

        date = strArray[30];
        time = strArray[31];
    }

    public StockNow(String str) {

        String[] leftRight = str.split("=");
        String right = leftRight[1].replaceAll("\"", "");
        String[] strArray = right.split("\\,");
        name = strArray[0];
        open = strArray[1];
        closeYesterday = strArray[2];
        now = strArray[3];

        volume = strArray[8];
        date = strArray[30];
        time = strArray[31];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getCloseYesterday() {
        return closeYesterday;
    }

    public void setCloseYesterday(String closeYesterday) {
        this.closeYesterday = closeYesterday;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
