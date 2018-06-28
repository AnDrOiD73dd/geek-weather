package ru.android73dd.geek.weather.network;

public interface WeatherRequester {

    void getWeather(int cityId);
    void getWeather(String cityName);
    void getWeather(String cityName, int countryCode);
    void getWeather(int zipCode, int countryCode);
    void getWeather(float lat, float lon);

}
