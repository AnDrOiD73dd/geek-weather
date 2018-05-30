package ru.android73dd.geek.weather.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import ru.android73dd.geek.weather.R;
import ru.android73dd.geek.weather.repository.SettingsRepositoryImpl;

public class WeatherDetailsActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        WeatherDetailsFragment details = (WeatherDetailsFragment) getFragmentManager().findFragmentById(R.id.weather_container);
        if (details == null) {
            details = WeatherDetailsFragment.newInstance(SettingsRepositoryImpl.getInstance().getSettings(this));
            getFragmentManager().beginTransaction().add(R.id.weather_container, details).commit();
        } else {
            getFragmentManager().beginTransaction().replace(R.id.weather_container, details).commit();
        }
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
                return true;
        }
        return false;
    }
}
