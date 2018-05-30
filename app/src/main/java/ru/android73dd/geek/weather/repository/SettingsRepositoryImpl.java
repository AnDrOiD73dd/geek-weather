package ru.android73dd.geek.weather.repository;

import android.content.Context;

import ru.android73dd.geek.weather.model.WeatherConfig;

public class SettingsRepositoryImpl implements SettingsRepository {

    private static final String KEY_WEATHER_CITY = "kwc";
    private static final String KEY_WEATHER_HUMIDITY = "kwh";
    private static final String KEY_WEATHER_WIND = "kww";
    private static final String KEY_WEATHER_PROBABILITY_OF_PRECIPITATION = "kwpop";

    private static SettingsRepositoryImpl instance;
    private WeatherConfig weatherConfig;

    private SettingsRepositoryImpl() {
    }

    public static SettingsRepositoryImpl getInstance() {
        SettingsRepositoryImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (SettingsRepositoryImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SettingsRepositoryImpl();
                }
            }
        }
        return localInstance;
    }


    @Override
    public void saveSettings(Context context, WeatherConfig weatherConfig) {
        if (this.weatherConfig == null) {
            this.weatherConfig = new WeatherConfig(weatherConfig.getCityName(),
                    weatherConfig.isShowHumidity(), weatherConfig.isShowWind(),
                    weatherConfig.isShowProbabilityOfPrecipitation());
        } else {
            this.weatherConfig = weatherConfig;
        }

        PrefUtils.getEditor(context)
                .putString(KEY_WEATHER_CITY, weatherConfig.getCityName())
                .putBoolean(KEY_WEATHER_HUMIDITY, weatherConfig.isShowHumidity())
                .putBoolean(KEY_WEATHER_WIND, weatherConfig.isShowWind())
                .putBoolean(KEY_WEATHER_PROBABILITY_OF_PRECIPITATION, weatherConfig.isShowProbabilityOfPrecipitation())
                .commit();

//        sendNotify(weatherConfig);
    }

    @Override
    public WeatherConfig getSettings(Context context) {
        if (this.weatherConfig != null) {
            return this.weatherConfig;
        }

        String cityName = PrefUtils.getPrefs(context).getString(KEY_WEATHER_CITY, "Saratov");
        boolean showHumidity = PrefUtils.getPrefs(context).getBoolean(KEY_WEATHER_HUMIDITY, true);
        boolean showWind = PrefUtils.getPrefs(context).getBoolean(KEY_WEATHER_WIND, true);;
        boolean showProbabilityOfPrecipitation = PrefUtils.getPrefs(context).getBoolean(KEY_WEATHER_PROBABILITY_OF_PRECIPITATION, true);
        this.weatherConfig = new WeatherConfig(cityName, showHumidity, showWind, showProbabilityOfPrecipitation);

        return this.weatherConfig;
    }
}
