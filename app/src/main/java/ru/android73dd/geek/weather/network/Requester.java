package ru.android73dd.geek.weather.network;

import android.os.AsyncTask;

import ru.android73dd.geek.weather.model.Weather;

public class Requester extends AsyncTask<String, Void, Weather> {

    private final WeatherRequestListener listener;

    Requester(WeatherRequestListener listener) {
        this.listener = listener;
    }

    @Override
    protected Weather doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPostExecute(Weather weather) {
        listener.onWeatherReceived(weather);
    }
}
