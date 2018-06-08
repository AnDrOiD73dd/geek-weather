package ru.android73dd.geek.weather.repository;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

import ru.android73dd.geek.weather.model.WeatherConfig;

public class SettingsRepositoryImpl implements SettingsRepository {

    private static final String KEY_WEATHER_CITIES_LIST = "kwcl";
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
//        if (this.weatherConfig == null) {
//            this.weatherConfig = new WeatherConfig.Builder()
//                    .setCitiesSet(weatherConfig.getCitiesSet())
//                    .setCityName(weatherConfig.getCityName())
//                    .setShowHumidity(weatherConfig.isShowHumidity())
//                    .setShowWind(weatherConfig.isShowWind())
//                    .setShowProbabilityOfPrecipitation(weatherConfig.isShowProbabilityOfPrecipitation())
//                    .create();
//        } else {
//            this.weatherConfig = weatherConfig;
//        }
        this.weatherConfig = weatherConfig;

        PrefUtils.getEditor(context)
                .putStringSet(KEY_WEATHER_CITIES_LIST, weatherConfig.getCitiesSet())
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
        SharedPreferences prefs = PrefUtils.getPrefs(context);
        Set<String> citiesSet = prefs.getStringSet(KEY_WEATHER_CITIES_LIST, new HashSet<String>());
        String cityName = prefs.getString(KEY_WEATHER_CITY, "Saratov");
        boolean showHumidity = prefs.getBoolean(KEY_WEATHER_HUMIDITY, true);
        boolean showWind = prefs.getBoolean(KEY_WEATHER_WIND, true);
        boolean showProbabilityOfPrecipitation = prefs.getBoolean(KEY_WEATHER_PROBABILITY_OF_PRECIPITATION, true);
        this.weatherConfig = new WeatherConfig.Builder()
                .setCitiesSet(citiesSet)
                .setCityName(cityName)
                .setShowHumidity(showHumidity)
                .setShowWind(showWind)
                .setShowProbabilityOfPrecipitation(showProbabilityOfPrecipitation)
                .create();

        return this.weatherConfig;
    }
}
