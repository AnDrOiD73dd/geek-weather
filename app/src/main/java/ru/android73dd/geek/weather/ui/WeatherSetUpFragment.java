package ru.android73dd.geek.weather.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;

import ru.android73dd.geek.weather.R;
import ru.android73dd.geek.weather.model.WeatherConfig;

public class WeatherSetUpFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, TextWatcher {

    public static final String KEY_WEATHER_CONFIG = "weather config";

    private TextInputEditText etCityName;
    private AppCompatCheckBox cbHumidity;
    private AppCompatCheckBox cbWind;
    private AppCompatCheckBox cbProbabilityOfPrecipitation;
    private Button btShowWeather;

    private boolean isWeatherDetailsOnScreen = false;
    private TextWatcher textWatcher;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_set_up, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        WeatherConfig currentConfig;
        if (savedInstanceState != null) {
            currentConfig = savedInstanceState.getParcelable(KEY_WEATHER_CONFIG);
            if (currentConfig == null) {
                currentConfig = new WeatherConfig("", false, false, false);
            }
        } else {
            currentConfig = getCurrentConfig();
        }

        if (isWeatherDetailsOnScreen) {
            showWeatherDetails(currentConfig);
        }
    }

    private void initViews() {
        Activity activity = getActivity();
        etCityName = activity.findViewById(R.id.et_city_name);
        etCityName.addTextChangedListener(this);
        cbHumidity = activity.findViewById(R.id.cb_humidity);
        cbWind = activity.findViewById(R.id.cb_wind);
        cbProbabilityOfPrecipitation = activity.findViewById(R.id.cb_probability_of_precipitation);
        btShowWeather = activity.findViewById(R.id.bt_show_weather);

        View weatherDetails = getActivity().findViewById(R.id.weather_details_container);
        if (weatherDetails != null && weatherDetails.getVisibility() == View.VISIBLE) {
            isWeatherDetailsOnScreen = true;
            btShowWeather.setVisibility(View.GONE);
            cbHumidity.setOnCheckedChangeListener(this);
            cbWind.setOnCheckedChangeListener(this);
            cbProbabilityOfPrecipitation.setOnCheckedChangeListener(this);
        } else {
            isWeatherDetailsOnScreen = false;
            btShowWeather.setOnClickListener(this);
        }
    }

    private WeatherConfig getCurrentConfig() {
        String cityName = etCityName.getText().toString().trim();
        if (cityName.isEmpty()) {
            return new WeatherConfig(cityName, false, false,
                    false);
        }
        return new WeatherConfig(cityName, cbHumidity.isChecked(), cbWind.isChecked(),
                cbProbabilityOfPrecipitation.isChecked());
    }

    private void showWeatherDetails(WeatherConfig parcel) {
        if (isWeatherDetailsOnScreen) {
            WeatherDetailsFragment detail = (WeatherDetailsFragment)
                    getFragmentManager().findFragmentById(R.id.weather_details_container);
            if (detail == null || !detail.getWeatherConfig().equals(parcel)) {
                detail = WeatherDetailsFragment.newInstance(parcel);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.weather_details_container, detail);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        }
        else {
            Intent intent = new Intent(getActivity(), WeatherDetailsActivity.class);
            intent.putExtra(KEY_WEATHER_CONFIG, parcel);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_show_weather:
                showWeatherDetails(getCurrentConfig());
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        showWeatherDetails(getCurrentConfig());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String cityName = s.toString().trim();
        if (btShowWeather.getVisibility() != View.GONE) {
            btShowWeather.setEnabled(!cityName.isEmpty());
        } else {
            showWeatherDetails(getCurrentConfig());
        }
    }
}
