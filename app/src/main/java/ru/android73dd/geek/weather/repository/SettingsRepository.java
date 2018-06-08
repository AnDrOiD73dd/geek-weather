package ru.android73dd.geek.weather.repository;

import android.content.Context;

import ru.android73dd.geek.weather.model.WeatherConfig;

public interface SettingsRepository {

    void saveSettings(Context context, WeatherConfig weatherConfig);
    WeatherConfig getSettings(Context context);

    void addCity(Context context, String cityName);
}
