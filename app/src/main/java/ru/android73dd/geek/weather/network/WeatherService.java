package ru.android73dd.geek.weather.network;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import ru.android73dd.geek.weather.model.WeatherSimpleEntry;

public class WeatherService extends Service {

    public static final String KEY_WEATHER = "weather";
    public static final String KEY_LISTENER = "weather listener";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.hasExtra(KEY_WEATHER) && intent.hasExtra(KEY_LISTENER)) {
            Bundle extras = intent.getExtras();
            WeatherRequestListener listener = extras.getParcelable(KEY_LISTENER);
            WeatherSimpleEntry weatherSimpleEntry = extras.getParcelable(KEY_WEATHER);
            new OpenWeatherRequester(listener).getWeather(weatherSimpleEntry.getCityName());
        }
        return START_NOT_STICKY;
    }
}
