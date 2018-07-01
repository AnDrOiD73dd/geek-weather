package ru.android73dd.geek.weather.network.openweathermap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.android73dd.geek.weather.model.openweathermap.WeatherRequest;

public interface OpenWeather {
    @GET("data/2.5/weather")
    Call<WeatherRequest> loadWeather(@Query("q") String cityCountry, @Query("appid") String keyApi);
}
