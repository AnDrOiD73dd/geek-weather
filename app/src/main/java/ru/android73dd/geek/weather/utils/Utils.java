package ru.android73dd.geek.weather.utils;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.text.format.DateFormat;

import java.util.Date;

public class Utils {

    public static final String DEFAULT_SIMPLE_DATE_FORMAT = "EEEE, dd MMMM yyyy, hh:mm:ss";

    public static String getCurrentTime(String simpleDateFormat) {
        String res;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat(simpleDateFormat);
            res = format.format(calendar.getTime());
        } else {
            Date date = new java.util.Date();
            res = DateFormat.format(simpleDateFormat, date).toString();
        }
        return res;
    }
}
