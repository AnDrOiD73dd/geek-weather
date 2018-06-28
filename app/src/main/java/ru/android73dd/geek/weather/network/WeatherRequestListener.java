package ru.android73dd.geek.weather.network;

import ru.android73dd.geek.weather.model.Weather;

public interface WeatherRequestListener {
    void onWeatherReceived(Weather weather);
}
