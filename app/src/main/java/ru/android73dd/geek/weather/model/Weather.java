package ru.android73dd.geek.weather.model;

import android.content.Context;

import ru.android73dd.geek.weather.R;

public class Weather {

    private String cityName;
    private int statusPic;
    private String temperature;
    private String humidity;
    private String wind;
    private String probabilityOfPrecipitation;

    public Weather(String cityName, int statusPic, String temperature, String humidity, String wind, String probabilityOfPrecipitation) {
        this.cityName = cityName;
        this.statusPic = statusPic;
        this.temperature = temperature;
        this.humidity = humidity;
        this.wind = wind;
        this.probabilityOfPrecipitation = probabilityOfPrecipitation;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getStatusPic() {
        return statusPic;
    }

    public void setStatusPic(int statusPic) {
        this.statusPic = statusPic;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getProbabilityOfPrecipitation() {
        return probabilityOfPrecipitation;
    }

    public void setProbabilityOfPrecipitation(String probabilityOfPrecipitation) {
        this.probabilityOfPrecipitation = probabilityOfPrecipitation;
    }

    public static Weather createDefault(Context context, String cityName) {
        return new Weather(cityName, R.drawable.weather_sunny,
                "+22" + context.getResources().getString(R.string.unit_cesium),
                " 40%",
                " 3-5" + context.getResources().getString(R.string.unit_wind_speed),
                " 20%");
    }
}
