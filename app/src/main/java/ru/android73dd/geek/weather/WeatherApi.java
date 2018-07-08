package ru.android73dd.geek.weather;

import android.arch.persistence.room.Room;
import android.content.Context;

import ru.android73dd.geek.weather.database.WeatherDatabase;

public class WeatherApi {

    private static final String DATABASE_NAME = "WeatherDatabase";

    private static WeatherDatabase database;

    public static WeatherDatabase getDatabase(Context context) {
        if (database == null) {
            database =  Room.databaseBuilder(context, WeatherDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return database;
    }
}
