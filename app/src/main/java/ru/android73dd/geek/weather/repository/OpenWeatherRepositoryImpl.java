package ru.android73dd.geek.weather.repository;

import java.util.concurrent.ConcurrentHashMap;

import ru.android73dd.geek.weather.model.openweathermap.OpenWeatherMapModel;

public class OpenWeatherRepositoryImpl implements OpenWeatherRepository {

    private ConcurrentHashMap<String, OpenWeatherMapModel> weatherMap;

    private static OpenWeatherRepositoryImpl instance;

    private OpenWeatherRepositoryImpl() {
        weatherMap = new ConcurrentHashMap<>();
    }

    public static OpenWeatherRepositoryImpl getInstance() {
        OpenWeatherRepositoryImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (OpenWeatherRepositoryImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new OpenWeatherRepositoryImpl();
                }
            }
        }
        return localInstance;
    }


    @Override
    public void add(OpenWeatherMapModel instance) {
        weatherMap.put(instance.getName(), instance);
    }

    @Override
    public void remove(OpenWeatherMapModel instance) {
        weatherMap.remove(instance.getName());
    }

    @Override
    public void update(OpenWeatherMapModel instance) {
        weatherMap.put(instance.getName(), instance);
    }

    @Override
    public OpenWeatherMapModel getByCityName(String cityName) {
        return weatherMap.get(cityName);
    }

    @Override
    public ConcurrentHashMap<String, OpenWeatherMapModel> getAll() {
        return weatherMap;
    }

    @Override
    public int getSize() {
        return weatherMap.size();
    }

    @Override
    public void clear() {
        weatherMap.clear();
    }
}
