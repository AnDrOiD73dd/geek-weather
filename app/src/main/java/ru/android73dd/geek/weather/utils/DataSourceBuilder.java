package ru.android73dd.geek.weather.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ru.android73dd.geek.weather.model.WeatherSimpleEntry;
import ru.android73dd.geek.weather.model.WeatherConfig;
import ru.android73dd.geek.weather.repository.SettingsRepositoryImpl;

public class DataSourceBuilder {

    private List<WeatherSimpleEntry> dataSource;
    private Context context;

    public DataSourceBuilder(Context context) {
        this.dataSource = new ArrayList<>();
        this.context = context;
    }

    public List<WeatherSimpleEntry> build() {
        WeatherConfig weatherConfig = SettingsRepositoryImpl.getInstance().getSettings(context);
        for (String s: weatherConfig.getCitiesSet()) {
            dataSource.add(WeatherSimpleEntry.createDefault(context, s));
        }
        return dataSource;
    }
}
