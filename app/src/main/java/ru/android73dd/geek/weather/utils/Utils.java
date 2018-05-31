package ru.android73dd.geek.weather.utils;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;

import java.util.Date;

public class Utils {

    public static final String DEFAULT_SIMPLE_DATE_FORMAT = "EEEE, dd MMMM yyyy, hh:mm:ss";

    public static String getCurrentTime(String simpleDateFormat) {
        String res;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Calendar calendar = Calendar.getInstance();
//            res = String.format(Locale.getDefault(), "%1$tA %1$tb %1$td %1$tY, %1$tI:%1$tM %1$Tp", calendar);
            SimpleDateFormat format = new SimpleDateFormat(simpleDateFormat);
            res = format.format(calendar.getTime());
        } else {
            android.text.format.DateFormat df = new android.text.format.DateFormat();
            Date date = new java.util.Date();
            res = df.format(simpleDateFormat, date).toString();
        }
        return res;
    }
}
