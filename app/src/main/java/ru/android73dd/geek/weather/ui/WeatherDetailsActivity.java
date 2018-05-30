package ru.android73dd.geek.weather.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
            WeatherDetailsFragment details = new WeatherDetailsFragment();
            details.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
        }
    }
}
