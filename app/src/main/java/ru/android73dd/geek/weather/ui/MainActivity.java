package ru.android73dd.geek.weather.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import ru.android73dd.geek.weather.R;
import ru.android73dd.geek.weather.model.Weather;
import ru.android73dd.geek.weather.model.WeatherAdapter;
import ru.android73dd.geek.weather.model.WeatherConfig;
import ru.android73dd.geek.weather.repository.SettingsRepositoryImpl;
import ru.android73dd.geek.weather.utils.DataSourceBuilder;

public class MainActivity extends AppCompatActivity{

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupAdapter();
    }

    public WeatherConfig getWeatherConfig() {
        return SettingsRepositoryImpl.getInstance().getSettings(this);
    }

    private void setupAdapter() {
        DataSourceBuilder builder = new DataSourceBuilder(this);
        final List<Weather> dataSource = builder.build();
        final WeatherAdapter adapter = new WeatherAdapter(dataSource, getWeatherConfig());
        recyclerView.setAdapter(adapter);
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
