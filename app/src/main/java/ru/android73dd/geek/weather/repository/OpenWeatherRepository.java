package ru.android73dd.geek.weather.repository;

import java.util.concurrent.ConcurrentHashMap;

import ru.android73dd.geek.weather.model.openweathermap.OpenWeatherMapModel;

public interface OpenWeatherRepository {

    void add(OpenWeatherMapModel instance);
    void remove(OpenWeatherMapModel instance);
    void update(OpenWeatherMapModel instance);
    OpenWeatherMapModel getByCityName(String cityName);
    ConcurrentHashMap<String, OpenWeatherMapModel> getAll();
    int getSize();
    void clear();
}
