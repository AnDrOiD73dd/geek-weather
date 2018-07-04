package ru.android73dd.geek.weather.model;

import java.util.Objects;

import ru.android73dd.geek.weather.R;
import ru.android73dd.geek.weather.model.openweathermap.OpenWeatherMapModel;

public class WeatherSimpleEntry {

    private static String unknown = "N/A";
    private String cityName;
    private int statusPic;
    private String temperature;
    private String humidity;
    private String wind;
    private String probabilityOfPrecipitation;

    public WeatherSimpleEntry(String cityName, int statusPic, String temperature, String humidity, String wind, String probabilityOfPrecipitation) {
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

    public String getWindSpeed() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherSimpleEntry that = (WeatherSimpleEntry) o;
        return statusPic == that.statusPic &&
                Objects.equals(cityName, that.cityName) &&
                Objects.equals(temperature, that.temperature) &&
                Objects.equals(humidity, that.humidity) &&
                Objects.equals(wind, that.wind) &&
                Objects.equals(probabilityOfPrecipitation, that.probabilityOfPrecipitation);
    }

    @Override
    public int hashCode() {

        return Objects.hash(cityName, statusPic, temperature, humidity, wind, probabilityOfPrecipitation);
    }

    public static WeatherSimpleEntry createDefault(String cityName) {
        return new WeatherSimpleEntry(cityName, R.drawable.weather_sunny, unknown, unknown, unknown,
                unknown);
    }

    public static WeatherSimpleEntry map(OpenWeatherMapModel openWeatherMapModel) {
        if (openWeatherMapModel == null) {
            return null;
        }
        String temperature = Float.toString(openWeatherMapModel.getMain().getTemp());
        String humidity = Integer.toString(openWeatherMapModel.getMain().getHumidity());
        String wind = Float.toString(openWeatherMapModel.getWind().getSpeed());
        return new WeatherSimpleEntry(openWeatherMapModel.getName(), R.drawable.weather_sunny,
                temperature, humidity, wind, unknown);
    }
}
