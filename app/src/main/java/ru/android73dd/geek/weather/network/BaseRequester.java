package ru.android73dd.geek.weather.network;

import retrofit2.Callback;
import ru.android73dd.geek.weather.model.openweathermap.OpenWeatherMapModel;

public interface BaseRequester {

    void getWeather(int cityId);
    void getWeather(String cityName, Callback<OpenWeatherMapModel> listener);
    void getWeather(String cityName, int countryCode);
    void getWeather(int zipCode, int countryCode);
    void getWeather(float lat, float lon);

}
