package ru.android73dd.geek.weather.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ru.android73dd.geek.weather.model.Weather;
import ru.android73dd.geek.weather.model.WeatherConfig;
import ru.android73dd.geek.weather.repository.SettingsRepositoryImpl;

public class DataSourceBuilder {

    private List<Weather> dataSource;
    private Context context;

    public DataSourceBuilder(Context context) {
        this.dataSource = new ArrayList<>();
        this.context = context;
    }

    public List<Weather> build() {
        WeatherConfig weatherConfig = SettingsRepositoryImpl.getInstance().getSettings(context);
        for (String s: weatherConfig.getCitiesSet()) {
            dataSource.add(Weather.createDefault(context, s));
        }
        return dataSource;
    }
}
