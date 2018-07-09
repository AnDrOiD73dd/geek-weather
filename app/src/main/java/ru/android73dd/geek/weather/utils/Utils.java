package ru.android73dd.geek.weather.utils;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.text.format.DateFormat;

import java.util.Date;
import java.util.Locale;

import ru.android73dd.geek.weather.R;
import ru.android73dd.geek.weather.model.WeatherPreferences;
import ru.android73dd.geek.weather.model.WeatherSimpleEntry;
import ru.android73dd.geek.weather.model.openweathermap.Weather;


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

    public static WeatherSimpleEntry setDataUnits(Context context, WeatherSimpleEntry item, WeatherPreferences weatherPreferences) {
        String temperatureUnit = weatherPreferences.getTemperatureUnit();
        double currentTemp = Double.valueOf(item.getTemperature());
        if (temperatureUnit.equals(context.getString(R.string.unit_cesium))) {
            double temp = currentTemp - 273.15;
            item.setTemperature(String.format(Locale.getDefault(), "%.0f %s", temp, temperatureUnit));
        }
        else if (temperatureUnit.equals(context.getString(R.string.unit_fahrenheit))) {
            double temp = 1.8 * (currentTemp - 273) + 32;
            item.setTemperature(String.format(Locale.getDefault(), "%.0f %s", temp, temperatureUnit));
        }

        String windSpeedUnit = weatherPreferences.getWindSpeedUnit();
        double currentWindSpeed = Double.valueOf(item.getWindSpeed());
        if (windSpeedUnit.equals(context.getString(R.string.unit_meter_second))) {
            item.setWind(String.format(Locale.getDefault(), "%.0f %s", currentWindSpeed, windSpeedUnit));
        }
        else if (windSpeedUnit.equals(context.getString(R.string.unit_miles_hour))) {
            // TODO convert
            item.setWind(String.format(Locale.getDefault(), "%.0f %s", currentWindSpeed,
                    context.getString(R.string.unit_meter_second)));
        }

        item.setHumidity(String.format(Locale.getDefault(), "%s %s", item.getHumidity(),
                context.getString(R.string.unit_percentage)));
        return item;
    }
}
