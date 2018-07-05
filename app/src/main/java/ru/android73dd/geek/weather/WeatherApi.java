package ru.android73dd.geek.weather;

import android.arch.persistence.room.Room;
import android.content.Context;

import ru.android73dd.geek.weather.database.WeatherDatabase;

public class WeatherApi {

    private static final String DATABASE_NAME = "WeatherDatabase";

    private static WeatherApi instance;
    private WeatherDatabase database;

    public static WeatherApi getInstance() {
        WeatherApi localInstance = instance;
        if (localInstance == null) {
            synchronized (WeatherApi.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new WeatherApi();
                }
            }
        }
        return localInstance;
    }

    public void initDb(Context context) {
        database =  Room.databaseBuilder(context, WeatherDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .build();
    }

    public WeatherDatabase getDatabase() {
        return database;
    }
}
