package ru.android73dd.geek.weather.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.android73dd.geek.weather.model.WeatherConfig;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class WeatherDetailsActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        if (savedInstanceState == null) {
            WeatherConfig weatherConfig = getIntent().getParcelableExtra(WeatherSetUpFragment.KEY_WEATHER_CONFIG);
            WeatherDetailsFragment details = WeatherDetailsFragment.newInstance(weatherConfig);
            getFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
        }
    }
}
