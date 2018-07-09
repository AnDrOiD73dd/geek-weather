package ru.android73dd.geek.weather.ui.details;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.android73dd.geek.weather.R;
import ru.android73dd.geek.weather.WeatherApi;
import ru.android73dd.geek.weather.database.WeatherEntity;
import ru.android73dd.geek.weather.model.WeatherPreferences;
import ru.android73dd.geek.weather.model.WeatherSimpleEntry;
import ru.android73dd.geek.weather.repository.SettingsRepositoryImpl;
import ru.android73dd.geek.weather.utils.Utils;

public class WeatherDetailsFragment extends Fragment {

    private static final String KEY_CITY_NAME = "kcn";

    private TextView tvCityName;
    private TextView tvDate;
    private ImageView ivStatus;
    private TextView tvTempValue;
    private TextView tvHumidityValue;
    private TextView tvWindValue;
    private LinearLayout llTemperature;
    private LinearLayout llHumidity;
    private LinearLayout llWind;
    private DetailsViewModel detailsViewModel;
    private String cityName;

    public static WeatherDetailsFragment newInstance(String cityName) {
        WeatherDetailsFragment detailsFragment = new WeatherDetailsFragment();
        Bundle args = new Bundle();
        args.putString(KEY_CITY_NAME, cityName);
        detailsFragment.setArguments(args);
        return detailsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_details, container, false);
        initViews(layout);

        cityName = getArguments().getString(KEY_CITY_NAME);
        ViewModelProvider.AndroidViewModelFactory factory = new DetailsViewModelFactory(getActivity().getApplication(), cityName);
        detailsViewModel = ViewModelProviders.of(getActivity(), factory).get(DetailsViewModel.class);
        detailsViewModel.getData().observe(this, new Observer<WeatherSimpleEntry>() {
                    @Override
                    public void onChanged(@Nullable WeatherSimpleEntry weatherSimpleEntry) {
                        setValues(weatherSimpleEntry);
                    }
                });

        getLifecycle().addObserver(detailsViewModel);

        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateUI(getWeatherConfig());
    }

    public WeatherPreferences getWeatherConfig() {
        return SettingsRepositoryImpl.getInstance().getSettings(getActivity());
    }

    private void updateUI(WeatherPreferences weatherPreferences) {
        updateUI(cityName, Utils.getCurrentTime(Utils.DEFAULT_SIMPLE_DATE_FORMAT),
                weatherPreferences.isShowHumidity(), weatherPreferences.isShowWind());
    }

    private void updateUI(String cityName, String date, boolean showHumidity, boolean showWind) {
        tvCityName.setText(cityName);
        tvDate.setVisibility(cityName.isEmpty() ? View.GONE : View.VISIBLE);
        tvDate.setText(date);
        ivStatus.setVisibility(cityName.isEmpty() ? View.GONE : View.VISIBLE);
        llTemperature.setVisibility(cityName.isEmpty() ? View.GONE : View.VISIBLE);
        llHumidity.setVisibility(showHumidity ? View.VISIBLE : View.GONE);
        llWind.setVisibility(showWind ? View.VISIBLE : View.GONE);
    }

    private void initViews(View layout) {
        tvCityName = layout.findViewById(R.id.tv_city_value);
        tvDate = layout.findViewById(R.id.tv_date);
        ivStatus = layout.findViewById(R.id.iv_status);
        tvTempValue = layout.findViewById(R.id.tv_temperature_value);
        tvHumidityValue = layout.findViewById(R.id.tv_humidity_value);
        tvWindValue = layout.findViewById(R.id.tv_wind_value);
        llTemperature = layout.findViewById(R.id.ll_temperature);
        llHumidity = layout.findViewById(R.id.ll_humidity);
        llWind = layout.findViewById(R.id.ll_wind);
    }

    private void setValues(WeatherSimpleEntry weatherSimpleEntry) {
        tvCityName.setText(weatherSimpleEntry.getCityName());
        tvDate.setText(Utils.getCurrentTime(Utils.DEFAULT_SIMPLE_DATE_FORMAT));
        tvTempValue.setText(weatherSimpleEntry .getTemperature());
        tvHumidityValue.setText(weatherSimpleEntry.getHumidity());
        tvWindValue.setText(weatherSimpleEntry.getWindSpeed());
    }
}
