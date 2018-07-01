package ru.android73dd.geek.weather.network;

import retrofit2.Callback;
import ru.android73dd.geek.weather.model.openweathermap.WeatherRequest;

public interface BaseRequester {

    void getWeather(int cityId);
    void getWeather(String cityName, Callback<WeatherRequest> listener);
    void getWeather(String cityName, int countryCode);
    void getWeather(int zipCode, int countryCode);
    void getWeather(float lat, float lon);

}
