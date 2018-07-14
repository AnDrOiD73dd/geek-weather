package ru.android73dd.geek.weather;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

public class WeatherApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
