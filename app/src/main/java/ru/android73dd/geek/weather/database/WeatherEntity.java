package ru.android73dd.geek.weather.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.android73dd.geek.weather.model.openweathermap.OpenWeatherMapModel;

@Entity(indices = {@Index(value = {"city_name"}, unique = true)})
public class WeatherEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "city_name")
    private String cityName;
    @Nullable
    @ColumnInfo(name = "temperature")
    private float temperature;
    @Nullable
    @ColumnInfo(name = "humidity")
    private int humidity;
    @Nullable
    @ColumnInfo(name = "wind_speed")
    private float windSpeed;

    public WeatherEntity(@NonNull String cityName, float temperature, int humidity, float windSpeed) {
        this.cityName = cityName;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }

    @NonNull
    public String getCityName() {
        return cityName;
    }

    public void setCityName(@NonNull String cityName) {
        this.cityName = cityName;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public static WeatherEntity map(OpenWeatherMapModel openWeatherMapModel) {
        if (openWeatherMapModel == null) {
            return null;
        }
        float temperature = openWeatherMapModel.getMain().getTemp();
        int humidity = openWeatherMapModel.getMain().getHumidity();
        float wind = openWeatherMapModel.getWind().getSpeed();
        return new WeatherEntity(openWeatherMapModel.getName(), temperature, humidity, wind);
    }
}