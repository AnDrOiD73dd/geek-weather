package ru.android73dd.geek.weather.network;

import android.os.AsyncTask;

import ru.android73dd.geek.weather.model.WeatherSimpleEntry;

public class Requester extends AsyncTask<String, Void, WeatherSimpleEntry> {

    private final WeatherRequestListener listener;

    Requester(WeatherRequestListener listener) {
        this.listener = listener;
    }

    @Override
    protected WeatherSimpleEntry doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPostExecute(WeatherSimpleEntry weatherSimpleEntry) {
        listener.onWeatherReceived(weatherSimpleEntry);
    }
}
