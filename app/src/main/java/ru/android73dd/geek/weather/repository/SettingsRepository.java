package ru.android73dd.geek.weather.repository;

import android.content.Context;

import ru.android73dd.geek.weather.model.WeatherPreferences;

public interface SettingsRepository {

    void saveSettings(Context context, WeatherPreferences weatherPreferences);
    WeatherPreferences getSettings(Context context);

    void addCity(Context context, String cityName);
}
