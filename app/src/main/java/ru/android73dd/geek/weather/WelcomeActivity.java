package ru.android73dd.geek.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String KEY_CITY_NAME = "city name";
    public static final String KEY_PARAM_TEMPERATURE = "temperature";
    public static final String KEY_PARAM_HUMIDITY = "humidity";
    public static final String KEY_PARAM_WIND = "wind";
    public static final String KEY_PARAM_PROBABILITY_OF_PRECIPITATION = "probability of precipitation";

    private TextInputEditText etCityName;
    private AppCompatCheckBox cbTemperature;
    private AppCompatCheckBox cbHumidity;
    private AppCompatCheckBox cbWind;
    private AppCompatCheckBox cbProbabilityOfPrecipitation;
    private Button btShowWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        etCityName = findViewById(R.id.et_city_name);
        etCityName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btShowWeather.setEnabled(!s.toString().isEmpty());
            }
        });
        cbTemperature = findViewById(R.id.cb_temperature);
        cbHumidity = findViewById(R.id.cb_humidity);
        cbWind = findViewById(R.id.cb_wind);
        cbProbabilityOfPrecipitation = findViewById(R.id.cb_probability_of_precipitation);
        btShowWeather = findViewById(R.id.bt_show_weather);
        btShowWeather.setOnClickListener(this);

        if (savedInstanceState != null)
            parseBundle(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        buildBundle(outState);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_show_weather) {
            showWeather();
        }
    }

    private void showWeather() {
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(buildIntent(intent));
    }

    private Intent buildIntent(Intent intent) {
        intent.putExtra(KEY_CITY_NAME, etCityName.getText().toString().trim());
        intent.putExtra(KEY_PARAM_TEMPERATURE, cbTemperature.isChecked());
        intent.putExtra(KEY_PARAM_HUMIDITY, cbHumidity.isChecked());
        intent.putExtra(KEY_PARAM_WIND, cbWind.isChecked());
        intent.putExtra(KEY_PARAM_PROBABILITY_OF_PRECIPITATION, cbProbabilityOfPrecipitation.isChecked());
        return intent;
    }

    private Bundle buildBundle(Bundle bundle) {
        bundle.putString(KEY_CITY_NAME, etCityName.getText().toString().trim());
        bundle.putBoolean(KEY_PARAM_TEMPERATURE, cbTemperature.isChecked());
        bundle.putBoolean(KEY_PARAM_HUMIDITY, cbHumidity.isChecked());
        bundle.putBoolean(KEY_PARAM_WIND, cbWind.isChecked());
        bundle.putBoolean(KEY_PARAM_PROBABILITY_OF_PRECIPITATION, cbProbabilityOfPrecipitation.isChecked());
        return bundle;
    }

    private void parseBundle(Bundle bundle) {
        etCityName.setText(bundle.getString(KEY_CITY_NAME, ""));
        cbTemperature.setChecked(bundle.getBoolean(KEY_PARAM_TEMPERATURE, true));
        cbHumidity.setChecked(bundle.getBoolean(KEY_PARAM_HUMIDITY, true));
        cbWind.setChecked(bundle.getBoolean(KEY_PARAM_WIND, true));
        cbProbabilityOfPrecipitation.setChecked(bundle.getBoolean(KEY_PARAM_PROBABILITY_OF_PRECIPITATION, true));
    }
}
