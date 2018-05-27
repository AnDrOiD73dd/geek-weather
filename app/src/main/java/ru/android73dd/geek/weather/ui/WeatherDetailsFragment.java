package ru.android73dd.geek.weather.ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.android73dd.geek.weather.Logger;
import ru.android73dd.geek.weather.R;
import ru.android73dd.geek.weather.Utils;
import ru.android73dd.geek.weather.model.WeatherConfig;

public class WeatherDetailsFragment extends Fragment {

    private TextView tvCityName;
    private TextView tvDate;
    private ImageView ivStatus;
    private TextView tvTempValue;
    private TextView tvHumidityValue;
    private TextView tvWindValue;
    private TextView tvProbabilityOfPrecipitationValue;
    private LinearLayout llTemperature;
    private LinearLayout llHumidity;
    private LinearLayout llWind;
    private LinearLayout llProbabilityOfPrecipitation;

    public static WeatherDetailsFragment newInstance(WeatherConfig weatherConfig) {
        WeatherDetailsFragment f = new WeatherDetailsFragment();

        // передача параметра
        Bundle args = new Bundle();
        args.putParcelable(WeatherSetUpFragment.KEY_WEATHER_CONFIG, weatherConfig);
        f.setArguments(args);
        return f;
    }

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
        View layout = inflater.inflate(R.layout.fragment_weather_details, container, false);

        tvCityName = layout.findViewById(R.id.tv_city_value);
        tvDate = layout.findViewById(R.id.tv_date);
        ivStatus = layout.findViewById(R.id.iv_status);
        tvTempValue = layout.findViewById(R.id.tv_temperature_value);
        tvHumidityValue = layout.findViewById(R.id.tv_humidity_value);
        tvWindValue = layout.findViewById(R.id.tv_wind_value);
        tvProbabilityOfPrecipitationValue = layout.findViewById(R.id.tv_probability_of_precipitation_value);
        llTemperature = layout.findViewById(R.id.ll_temperature);
        llHumidity = layout.findViewById(R.id.ll_humidity);
        llWind = layout.findViewById(R.id.ll_wind);
        llProbabilityOfPrecipitation = layout.findViewById(R.id.ll_probability_of_precipitation);

        WeatherConfig weatherConfig = getWeatherConfig();
        updateUI(weatherConfig.getCityName(), Utils.getCurrentTime(Utils.DEFAULT_SIMPLE_DATE_FORMAT),
                weatherConfig.isShowTemperature(), weatherConfig.isShowHumidity(),
                weatherConfig.isShowWind(), weatherConfig.isShowProbabilityOfPrecipitation());
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.d("onActivityCreated");
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

    public WeatherConfig getWeatherConfig() {
        return getArguments().getParcelable(WeatherSetUpFragment.KEY_WEATHER_CONFIG);
    }

    private void updateUI(String cityName, String date, boolean showTemp, boolean showHumidity, boolean showWind, boolean showProbabilityOfPrecipitation) {
        tvCityName.setText(cityName);
        tvDate.setVisibility(cityName.isEmpty() ? View.GONE : View.VISIBLE);
        tvDate.setText(date);
        ivStatus.setVisibility(cityName.isEmpty() ? View.GONE : View.VISIBLE);
        llTemperature.setVisibility(showTemp ? View.VISIBLE : View.GONE);
        llHumidity.setVisibility(showHumidity ? View.VISIBLE : View.GONE);
        llWind.setVisibility(showWind ? View.VISIBLE : View.GONE);
        llProbabilityOfPrecipitation.setVisibility(showProbabilityOfPrecipitation ? View.VISIBLE : View.GONE);
    }
}
