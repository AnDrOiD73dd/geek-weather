package ru.android73dd.geek.weather.ui.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TreeSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.android73dd.geek.weather.R;
import ru.android73dd.geek.weather.WeatherApi;
import ru.android73dd.geek.weather.database.WeatherEntity;
import ru.android73dd.geek.weather.model.WeatherPreferences;
import ru.android73dd.geek.weather.model.WeatherSimpleEntry;
import ru.android73dd.geek.weather.model.openweathermap.OpenWeatherMapModel;
import ru.android73dd.geek.weather.network.openweathermap.OpenWeatherRequester;
import ru.android73dd.geek.weather.repository.SettingsRepositoryImpl;
import ru.android73dd.geek.weather.utils.Logger;
import ru.android73dd.geek.weather.utils.Utils;


public class CitiesViewModel extends AndroidViewModel implements LifecycleObserver {

    public final static String ACTION_LOAD_ERROR = "ru.android73dd.geek.weather.load_error";
    public final static String KEY_LOAD_ERROR_TEXT = "klet";

    private MutableLiveData<List<WeatherSimpleEntry>> citiesList = new MutableLiveData<>();
    private Observer<List<WeatherEntity>> dbEventListener = new Observer<List<WeatherEntity>>() {

        @Override
        public void onChanged(@Nullable List<WeatherEntity> weatherEntities) {
            if (weatherEntities != null && weatherEntities.size() > 0) {
                List<WeatherSimpleEntry> newData = mapAll(weatherEntities);
                citiesList.setValue(newData);
            }
        }
    };

    public CitiesViewModel(@NonNull Application application) {
        super(application);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void subscribeDbEvents() {
        WeatherApi.getDatabase(getApplication()).weatherDao().getAll().observeForever(dbEventListener);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void unsubscribeDbEvents() {
        WeatherApi.getDatabase(getApplication()).weatherDao().getAll().removeObserver(dbEventListener);
    }

    public LiveData<List<WeatherSimpleEntry>> getCitiesData() {
        return citiesList;
    }

    private boolean isExist(String cityName) {
        for (WeatherSimpleEntry item : Objects.requireNonNull(citiesList.getValue())) {
            if (item.getCityName().equals(cityName)) {
                return true;
            }
        }
        return false;
    }

    private int getPosition(String cityName) {
        for (int i = 0; i < Objects.requireNonNull(citiesList.getValue()).size() - 1; i++) {
            if (citiesList.getValue().get(i).getCityName().equals(cityName)) {
                return i;
            }
        }
        return -1;
    }

    private List<WeatherSimpleEntry> mapAll(List<WeatherEntity> weatherEntities) {
        List<WeatherSimpleEntry> res = new ArrayList<>();
        for (WeatherEntity item : weatherEntities) {
            res.add(Utils.setDataUnits(getApplication(), WeatherSimpleEntry.map(item), getWeatherConfig()));
        }
        return res;
    }

    private WeatherPreferences getWeatherConfig() {
        return SettingsRepositoryImpl.getInstance().getSettings(getApplication());
    }

    private void loadAllWeather() {
        for (WeatherSimpleEntry item : Objects.requireNonNull(citiesList.getValue())) {
            loadItemWeather(item);
        }
    }

    void loadItemWeather(WeatherSimpleEntry item) {
        final String cityName = item.getCityName();
        OpenWeatherRequester openWeatherRequester = new OpenWeatherRequester();
        openWeatherRequester.getWeather(cityName, new Callback<OpenWeatherMapModel>() {

            String error = String.format("%s: %s", getApplication().getString(R.string.error_load_data), cityName);

            @Override
            public void onResponse(Call<OpenWeatherMapModel> call, Response<OpenWeatherMapModel> response) {
                if (response.code() == 200) {
                    OpenWeatherMapModel openWeatherMapModel = response.body();
                    if (openWeatherMapModel != null) {
                        WeatherApi.getDatabase(getApplication()).weatherDao().insert(WeatherEntity.map(openWeatherMapModel));
                    }
                }
                else {
                    notifyListeners(error);
                }
            }

            @Override
            public void onFailure(Call<OpenWeatherMapModel> call, Throwable t) {
                Logger.d(t.toString());
                notifyListeners(error);
            }
        });
    }

    private void notifyListeners(String error) {
        Intent intent = new Intent(ACTION_LOAD_ERROR);
        intent.putExtra(KEY_LOAD_ERROR_TEXT, error);
        getApplication().sendBroadcast(intent);
    }
}
