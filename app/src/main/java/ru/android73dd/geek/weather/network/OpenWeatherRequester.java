package ru.android73dd.geek.weather.network;

public class OpenWeatherRequester implements WeatherRequester {

    private WeatherRequestListener listener;

    public OpenWeatherRequester(WeatherRequestListener listener) {
        this.listener = listener;
    }

    @Override
    public void getWeather(int cityId) {
        startRequestStub();    }

    @Override
    public void getWeather(String cityName) {
        startRequestStub();
    }

    @Override
    public void getWeather(String cityName, int countryCode) {
        startRequestStub();
    }

    @Override
    public void getWeather(int zipCode, int countryCode) {
        startRequestStub();
    }

    @Override
    public void getWeather(float lat, float lon) {
        startRequestStub();
    }

    private void startRequestStub() {
        String stubUri = "";
        Requester requester = new Requester(listener);
        requester.execute(stubUri);
    }
}
