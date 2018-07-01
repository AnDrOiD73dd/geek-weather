package ru.android73dd.geek.weather.network.openweathermap;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.android73dd.geek.weather.model.openweathermap.OpenWeatherMapModel;
import ru.android73dd.geek.weather.network.BaseRequester;

public class OpenWeatherRequester implements BaseRequester {

    private static final String URL = "http://api.openweathermap.org/";
    private static final String API_KEY = "9a4ecc5cbd6ef1bc2f39ddff378a16a8";

    private OpenWeather openWeather;

    public OpenWeatherRequester() {
        initRetrofit();
    }

    @Override
    public void getWeather(int cityId) {
    }

    @Override
    public void getWeather(String cityName, Callback<OpenWeatherMapModel> listener) {
        requestRetrofit(cityName, listener);
    }

    @Override
    public void getWeather(String cityName, int countryCode) {
    }

    @Override
    public void getWeather(int zipCode, int countryCode) {
    }

    @Override
    public void getWeather(float lat, float lon) {
    }

    private void initRetrofit() {
        Retrofit retrofit;
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        openWeather = retrofit.create(OpenWeather.class);
    }

    private void requestRetrofit(String cityName, Callback<OpenWeatherMapModel> listener){
        openWeather.loadWeather(cityName, API_KEY).enqueue(listener);
    }
}