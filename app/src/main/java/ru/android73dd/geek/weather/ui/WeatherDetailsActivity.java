package ru.android73dd.geek.weather.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import ru.android73dd.geek.weather.R;
import ru.android73dd.geek.weather.model.WeatherConfig;
import ru.android73dd.geek.weather.repository.SettingsRepositoryImpl;

public class WeatherDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_CITY_NAME = "ecn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String cityName = getIntent().getExtras().getString(EXTRA_CITY_NAME, "");

        WeatherDetailsFragment details = (WeatherDetailsFragment) getFragmentManager().findFragmentById(R.id.weather_container);
        if (details == null) {
            WeatherConfig weatherConfig = SettingsRepositoryImpl.getInstance().getSettings(this);
            // TODO replace it
            weatherConfig.setCityName(cityName);
            details = WeatherDetailsFragment.newInstance(weatherConfig);
            getFragmentManager().beginTransaction().add(R.id.weather_container, details).commit();
        } else {
            getFragmentManager().beginTransaction().replace(R.id.weather_container, details).commit();
        }
    }

    public static Intent getIntent(Context context, String cityName) {
        Intent intent = new Intent(context, WeatherDetailsActivity.class);
        intent.putExtra(EXTRA_CITY_NAME, cityName);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
        }
        return false;
    }
}
