package com.tfygg.unwraping.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    public static String getDay(int count) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, count);
        date = calendar.getTime();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(date);

        return time;
    }

}
