package ru.android73dd.geek.weather.network;

import ru.android73dd.geek.weather.model.WeatherSimpleEntry;

public interface WeatherRequestListener {
    void onWeatherReceived(WeatherSimpleEntry weatherSimpleEntry);
}
