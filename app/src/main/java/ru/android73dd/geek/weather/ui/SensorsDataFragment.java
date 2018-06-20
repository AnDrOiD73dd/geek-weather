package ru.android73dd.geek.weather.ui;

import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.android73dd.geek.weather.R;
import ru.android73dd.geek.weather.utils.SensorUtils;

public class SensorsDataFragment extends BaseFragment {

    private TextView tvCityName;
    private TextView tvTemperatureValue;
    private TextView tvHumidityValue;
    private SensorEventListener temperatureListener;
    private SensorEventListener humidityListener;
    private String unitPercentage;
    private String unitCesium;

    public static SensorsDataFragment newInstance() {
        SensorsDataFragment fragment = new SensorsDataFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_sensors_data, container, false);
        tvCityName = view.findViewById(R.id.tv_city_name);
        tvTemperatureValue = view.findViewById(R.id.tv_temperature_value);
        tvHumidityValue = view.findViewById(R.id.tv_humidity_value);

        Resources resources = getResources();
        unitPercentage = resources.getString(R.string.unit_percentage);
        unitCesium = resources.getString(R.string.unit_cesium);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setValues();
        subscribeToSensors();
    }

    @Override
    public void onPause() {
        super.onPause();
        SensorUtils.unsubscribeTemperatureSensor(getActivity(), temperatureListener);
        SensorUtils.unsubscribeHumiditySensor(getActivity(), humidityListener);
    }

    private void setValues() {
        tvCityName.setText("???");
        tvTemperatureValue.setText("0");
        tvHumidityValue.setText("0");
    }

    private void subscribeToSensors() {
        temperatureListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(String.valueOf(event.values[0])).append(unitCesium);
                tvTemperatureValue.setText(stringBuilder);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        SensorUtils.subscribeTemperatureSensor(getActivity(), temperatureListener);
        humidityListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(String.valueOf(event.values[0])).append(unitPercentage);
                tvHumidityValue.setText(stringBuilder);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        SensorUtils.subscribeHumiditySensor(getActivity(), humidityListener);
    }
}
