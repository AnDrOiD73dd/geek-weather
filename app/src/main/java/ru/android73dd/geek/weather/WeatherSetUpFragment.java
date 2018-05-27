package ru.android73dd.geek.weather;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
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

public class WeatherSetUpFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    public static final String KEY_WEATHER_CONFIG = "weather config";

    private TextInputEditText etCityName;
    private AppCompatCheckBox cbTemperature;
    private AppCompatCheckBox cbHumidity;
    private AppCompatCheckBox cbWind;
    private AppCompatCheckBox cbProbabilityOfPrecipitation;
    private Button btShowWeather;

    private boolean isWeatherDetailsOnScreen = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Logger.d("onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Logger.d("onCreateView");
        return inflater.inflate(R.layout.fragment_weather_set_up, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.d("onActivityCreated");
        Activity activity = getActivity();
        etCityName = activity.findViewById(R.id.et_city_name);
        etCityName.addTextChangedListener(new TextWatcher() {
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
        });
        cbTemperature = activity.findViewById(R.id.cb_temperature);
        cbHumidity = activity.findViewById(R.id.cb_humidity);
        cbWind = activity.findViewById(R.id.cb_wind);
        cbProbabilityOfPrecipitation = activity.findViewById(R.id.cb_probability_of_precipitation);
        btShowWeather = activity.findViewById(R.id.bt_show_weather);

        View weatherDetails = getActivity().findViewById(R.id.weather_details_container);
        if (weatherDetails != null && weatherDetails.getVisibility() == View.VISIBLE) {
            isWeatherDetailsOnScreen = true;
            btShowWeather.setVisibility(View.GONE);
            cbTemperature.setOnCheckedChangeListener(this);
            cbHumidity.setOnCheckedChangeListener(this);
            cbWind.setOnCheckedChangeListener(this);
            cbProbabilityOfPrecipitation.setOnCheckedChangeListener(this);
        } else {
            isWeatherDetailsOnScreen = false;
            btShowWeather.setOnClickListener(this);
        }

        // Если это не повторное создание, то восстановим текущую позицию
        WeatherConfig currentConfig;
        if (savedInstanceState != null) {
            currentConfig = savedInstanceState.getParcelable(KEY_WEATHER_CONFIG);
        } else {
            currentConfig = getCurrentConfig();
        }

        if (isWeatherDetailsOnScreen) {
            showWeatherDetails(currentConfig);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Logger.d("onStart");
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Logger.d("onViewStateRestored");
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.d("onPause");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.d("onSaveInstanceState");
        outState.putParcelable(KEY_WEATHER_CONFIG, getCurrentConfig());
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.d("onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.d("onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d("onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Logger.d("onDetach");
    }

    private WeatherConfig getCurrentConfig() {
        String cityName = etCityName.getText().toString().trim();
        if (cityName.isEmpty()) {
            return new WeatherConfig(cityName, false, false,
                    false, false);
        }
        return new WeatherConfig(cityName, cbTemperature.isChecked(), cbHumidity.isChecked(),
                cbWind.isChecked(), cbProbabilityOfPrecipitation.isChecked());
    }

    // Показать герб. Ecли возможно, то показать рядом со списком,
    // если нет, то открыть второе активити
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
        Logger.d("onCheckedChanged " + buttonView + " " + isChecked);
        showWeatherDetails(getCurrentConfig());
    }
}
