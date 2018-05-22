package ru.android73dd.geek.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WeatherActivity extends AppCompatActivity {

    private static final String KEY_DATE_TIME = "current date time";

    private TextView tvCityName;
    private TextView tvDate;
    private TextView tvTempValue;
    private TextView tvHumidityValue;
    private TextView tvWindValue;
    private TextView tvProbabilityOfPrecipitationValue;
    private LinearLayout llTemperature;
    private LinearLayout llHumidity;
    private LinearLayout llWind;
    private LinearLayout llProbabilityOfPrecipitation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        tvCityName = findViewById(R.id.tv_city_value);
        tvDate = findViewById(R.id.tv_date);
        tvTempValue = findViewById(R.id.tv_temperature_value);
        tvHumidityValue = findViewById(R.id.tv_humidity_value);
        tvWindValue = findViewById(R.id.tv_wind_value);
        tvProbabilityOfPrecipitationValue = findViewById(R.id.tv_probability_of_precipitation_value);
        llTemperature = findViewById(R.id.ll_temperature);
        llHumidity = findViewById(R.id.ll_humidity);
        llWind = findViewById(R.id.ll_wind);
        llProbabilityOfPrecipitation = findViewById(R.id.ll_probability_of_precipitation);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if (bundle != null) parseBundle(bundle);
        } else {
            parseBundle(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putAll(getIntent().getExtras());
        outState.putString(KEY_DATE_TIME, tvDate.getText().toString());
    }

    private void updateUI(String cityName, String date, boolean showTemp, boolean showHumidity, boolean showWind, boolean showProbabilityOfPrecipitation) {
        tvCityName.setText(cityName);
        tvDate.setText(date);
        llTemperature.setVisibility(showTemp ? View.VISIBLE : View.GONE);
        llHumidity.setVisibility(showHumidity ? View.VISIBLE : View.GONE);
        llWind.setVisibility(showWind ? View.VISIBLE : View.GONE);
        llProbabilityOfPrecipitation.setVisibility(showProbabilityOfPrecipitation ? View.VISIBLE : View.GONE);
    }

    private void parseBundle(Bundle bundle) {
        updateUI(bundle.getString(WelcomeActivity.KEY_CITY_NAME),
                bundle.getString(KEY_DATE_TIME, Utils.getCurrentTime(Utils.DEFAULT_SIMPLE_DATE_FORMAT)),
                bundle.getBoolean(WelcomeActivity.KEY_PARAM_TEMPERATURE, true),
                bundle.getBoolean(WelcomeActivity.KEY_PARAM_HUMIDITY, true),
                bundle.getBoolean(WelcomeActivity.KEY_PARAM_WIND, true),
                bundle.getBoolean(WelcomeActivity.KEY_PARAM_PROBABILITY_OF_PRECIPITATION, true));
    }
}
