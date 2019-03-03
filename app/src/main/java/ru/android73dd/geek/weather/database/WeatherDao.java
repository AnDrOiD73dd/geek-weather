package ru.android73dd.geek.weather.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface WeatherDao {

    @Query("SELECT * FROM WeatherEntity")
    LiveData<List<WeatherEntity>> getAll();

    @Query("SELECT * FROM WeatherEntity WHERE city_name LIKE :cityName")
    LiveData<WeatherEntity> getWeatherByCityName(String cityName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WeatherEntity entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<WeatherEntity> entities);

    @Update
    void update(WeatherEntity entity);

    @Delete
    void delete(WeatherEntity entity);

    @Query("DELETE FROM WeatherEntity WHERE city_name LIKE :cityName")
    void delete(String cityName);
}