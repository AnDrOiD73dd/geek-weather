package ru.android73dd.geek.weather.ui.details;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;


public class DetailsViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    private final String cityName;
    private Application application;

    /**
     * Creates a {@code AndroidViewModelFactory}
     * @param application an application to pass in {@link AndroidViewModel}
     * @param cityName
     */
    public DetailsViewModelFactory(@NonNull Application application, String cityName) {
        super(application);
        this.application = application;
        this.cityName = cityName;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailsViewModel(application, cityName);
    }
}
