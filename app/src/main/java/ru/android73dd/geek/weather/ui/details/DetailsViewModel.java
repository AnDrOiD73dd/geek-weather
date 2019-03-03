package ru.android73dd.geek.weather.ui.details;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.android73dd.geek.weather.WeatherApi;
import ru.android73dd.geek.weather.database.WeatherEntity;
import ru.android73dd.geek.weather.model.WeatherPreferences;
import ru.android73dd.geek.weather.model.WeatherSimpleEntry;
import ru.android73dd.geek.weather.repository.SettingsRepositoryImpl;
import ru.android73dd.geek.weather.utils.Utils;


public class DetailsViewModel extends AndroidViewModel implements LifecycleObserver {

    private String cityName;

    private MutableLiveData<WeatherSimpleEntry> weatherData = new MutableLiveData<>();
    private Observer<WeatherEntity> dbEventListener = new Observer<WeatherEntity>() {
        @Override
        public void onChanged(@Nullable WeatherEntity weatherEntity) {
            weatherData.setValue(Utils.setDataUnits(getApplication(), WeatherSimpleEntry.map(weatherEntity), getWeatherConfig()));
        }
    };

    public DetailsViewModel(@NonNull Application application, String cityName) {
        super(application);
        this.cityName = cityName;
    }

    public LiveData<WeatherSimpleEntry> getData() {
        return weatherData;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void subscribeDbEvents() {
        WeatherApi.getDatabase(getApplication()).weatherDao().getWeatherByCityName(cityName).observeForever(dbEventListener);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void unsubscribeDbEvents() {
        WeatherApi.getDatabase(getApplication()).weatherDao().getWeatherByCityName(cityName).removeObserver(dbEventListener);
    }

    private WeatherPreferences getWeatherConfig() {
        return SettingsRepositoryImpl.getInstance().getSettings(getApplication());
    }
}
