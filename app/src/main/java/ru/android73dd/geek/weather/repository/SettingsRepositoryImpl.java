package ru.android73dd.geek.weather.repository;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

import ru.android73dd.geek.weather.R;
import ru.android73dd.geek.weather.model.WeatherPreferences;

public class SettingsRepositoryImpl implements SettingsRepository {

    private static final String KEY_WEATHER_CITIES_LIST = "kwcl";

    private static final String KEY_WEATHER_HUMIDITY = "kwh";
    private static final String KEY_WEATHER_WIND = "kww";
    private static final String KEY_WEATHER_PROBABILITY_OF_PRECIPITATION = "kwpop";

    private static final String KEY_INTERFACE_TEMPERATURE_UNIT = "kitu";

    private static SettingsRepositoryImpl instance;
    private WeatherPreferences weatherPreferences;

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
    public void saveSettings(Context context, WeatherPreferences weatherPreferences) {
        this.weatherPreferences = weatherPreferences;
        PrefUtils.getEditor(context)
                .putStringSet(KEY_WEATHER_CITIES_LIST, weatherPreferences.getCitiesSet())
                .putBoolean(KEY_WEATHER_HUMIDITY, weatherPreferences.isShowHumidity())
                .putBoolean(KEY_WEATHER_WIND, weatherPreferences.isShowWind())
                .putBoolean(KEY_WEATHER_PROBABILITY_OF_PRECIPITATION, weatherPreferences.isShowProbabilityOfPrecipitation())
                .putString(KEY_INTERFACE_TEMPERATURE_UNIT, weatherPreferences.getTemperatureUnit())
                .apply();
        // TODO notify observers
    }

    @Override
    public WeatherPreferences getSettings(Context context) {
        if (this.weatherPreferences != null) {
            return this.weatherPreferences;
        }
        SharedPreferences prefs = PrefUtils.getPrefs(context);
        Set<String> citiesSet = prefs.getStringSet(KEY_WEATHER_CITIES_LIST, new HashSet<String>());
        boolean showHumidity = prefs.getBoolean(KEY_WEATHER_HUMIDITY, true);
        boolean showWind = prefs.getBoolean(KEY_WEATHER_WIND, true);
        boolean showProbabilityOfPrecipitation = prefs.getBoolean(KEY_WEATHER_PROBABILITY_OF_PRECIPITATION, true);
        String temperatureUnit = prefs.getString(KEY_INTERFACE_TEMPERATURE_UNIT, context.getString(R.string.unit_cesium));
        this.weatherPreferences = new WeatherPreferences.Builder()
                .setCitiesSet(citiesSet)
                .setShowHumidity(showHumidity)
                .setShowWind(showWind)
                .setShowProbabilityOfPrecipitation(showProbabilityOfPrecipitation)
                .setTemperatureUnit(temperatureUnit)
                .create();

        return this.weatherPreferences;
    }

    @Override
    public void addCity(Context context, String cityName) {
        SharedPreferences prefs = PrefUtils.getPrefs(context);
        Set<String> citiesSet = new HashSet<>(prefs.getStringSet(KEY_WEATHER_CITIES_LIST, new HashSet<String>()));
        citiesSet.add(cityName);
        this.weatherPreferences.setCitiesSet(citiesSet);
        PrefUtils.getEditor(context).putStringSet(KEY_WEATHER_CITIES_LIST, weatherPreferences.getCitiesSet()).apply();
        // TODO notify observers
    }
}
