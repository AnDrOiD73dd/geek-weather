package ru.android73dd.geek.weather.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.android73dd.geek.weather.R;
import ru.android73dd.geek.weather.WeatherApi;
import ru.android73dd.geek.weather.database.WeatherEntity;
import ru.android73dd.geek.weather.model.WeatherAdapter;
import ru.android73dd.geek.weather.model.WeatherPreferences;
import ru.android73dd.geek.weather.model.WeatherSimpleEntry;
import ru.android73dd.geek.weather.model.openweathermap.OpenWeatherMapModel;
import ru.android73dd.geek.weather.network.openweathermap.OpenWeatherRequester;
import ru.android73dd.geek.weather.repository.SettingsRepositoryImpl;
import ru.android73dd.geek.weather.ui.dialog.AddCityDialogFragment;
import ru.android73dd.geek.weather.utils.Logger;

public class CitiesFragment extends BaseFragment implements View.OnClickListener,
        WeatherAdapter.OnItemClickListener, AddCityDialogFragment.ActionListener {

    private OnFragmentInteractionListener listener;
    private RecyclerView recyclerView;
    private List<WeatherSimpleEntry> dataSource;
    private WeatherAdapter adapter;
    private AddCityDialogFragment addCityDialog;

    public CitiesFragment() {
        // Required empty public constructor
    }

    public static CitiesFragment newInstance() {
        return new CitiesFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_cities, container, false);

        recyclerView = layout.findViewById(R.id.rv_cities_list);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        FloatingActionButton fab = layout.findViewById(R.id.fab_add_city);
        fab.setOnClickListener(this);
        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        setupAdapter();
        loadAllWeather();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        if (addCityDialog != null) {
            addCityDialog.setListener(null);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add_city:
                showAddCityDialogFragment();
                break;
            default:
                Logger.d("Unknown view = " + v.getId());
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        WeatherSimpleEntry weatherSimpleEntry = dataSource.get(position);
        listener.onItemClicked(weatherSimpleEntry.getCityName());
    }

    public WeatherPreferences getWeatherConfig() {
        return SettingsRepositoryImpl.getInstance().getSettings(getActivity());
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onItemClicked(String cityName);
    }

    private void setupAdapter() {
//        DataSourceBuilder builder = new DataSourceBuilder(getActivity());
//        dataSource = builder.build();
        List<WeatherEntity> weatherEntities = WeatherApi.getDatabase(getContext()).weatherDao().getAll();
        List<WeatherSimpleEntry> weatherSimpleEntries = new ArrayList<>();
        for (WeatherEntity entity : weatherEntities) {
            WeatherSimpleEntry weatherSimpleEntry = WeatherSimpleEntry.map(entity);
            weatherSimpleEntry = setDataUnits(weatherSimpleEntry);
            weatherSimpleEntries.add(weatherSimpleEntry);
        }
        dataSource = weatherSimpleEntries;
        adapter = new WeatherAdapter(dataSource, getWeatherConfig());
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    public void showAddCityDialogFragment() {
        FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        if (addCityDialog == null) {
            if (manager != null) {
                addCityDialog = new AddCityDialogFragment();
                addCityDialog.setListener(this);
                addCityDialog.show(manager, "Add city dialog");
            }
        }
        else {
            addCityDialog.show(manager, "Add city dialog");
        }
    }

    @Override
    public void onTextChanged(String s) {
        // TODO: Implement auto complete city name
    }

    @Override
    public void onAddClick(String s) {
        addCityDialog.dismiss();
        final String cityName = s.trim();
        if (!cityName.isEmpty()) {
            WeatherSimpleEntry weatherSimpleEntry = WeatherSimpleEntry.createDefault(cityName);
            WeatherApi.getDatabase(getContext()).weatherDao().insert(WeatherEntity.map(weatherSimpleEntry));
            dataSource.add(weatherSimpleEntry);
            adapter.notifyDataSetChanged();
            loadItemWeather(weatherSimpleEntry);
        }
    }

    private void loadAllWeather() {
        for (WeatherSimpleEntry item : dataSource) {
            loadItemWeather(item);
        }
    }

    private void loadItemWeather(WeatherSimpleEntry item) {
        final String cityName = item.getCityName();
        final int position = dataSource.indexOf(item);
//        if (item.equals(WeatherSimpleEntry.createDefault(cityName))) {
//            OpenWeatherMapModel openWeatherMapModel = OpenWeatherRepositoryImpl.getInstance().getByCityName(cityName);
//            if (openWeatherMapModel != null) {
//                dataSource.set(position, WeatherSimpleEntry.map(openWeatherMapModel));
//                adapter.notifyDataSetChanged();
//                return;
//            }
            WeatherEntity weatherEntity =  WeatherApi.getDatabase(getContext()).weatherDao().getWeatherByCityName(cityName);
            if (weatherEntity != null) {
                WeatherSimpleEntry weatherSimpleEntry = WeatherSimpleEntry.map(weatherEntity);
                weatherSimpleEntry = setDataUnits(weatherSimpleEntry);
                dataSource.set(position, weatherSimpleEntry);
                adapter.notifyDataSetChanged();
//                return;
            }
            OpenWeatherRequester openWeatherRequester = new OpenWeatherRequester();
            openWeatherRequester.getWeather(cityName, new Callback<OpenWeatherMapModel>() {

                String cityName = dataSource.get(position).getCityName();
                String error = String.format("%s: %s", getContext().getString(R.string.error_load_data), cityName);

                @Override
                public void onResponse(Call<OpenWeatherMapModel> call, Response<OpenWeatherMapModel> response) {
                    if (response.code() == 200) {
                        OpenWeatherMapModel openWeatherMapModel = response.body();
                        if (openWeatherMapModel != null) {
//                            OpenWeatherRepositoryImpl.getInstance().add(openWeatherMapModel);
                            WeatherApi.getDatabase(getContext()).weatherDao()
                                    .update(WeatherEntity.map(openWeatherMapModel));
                            WeatherSimpleEntry weatherSimpleEntry = WeatherSimpleEntry.map(openWeatherMapModel);
                            weatherSimpleEntry = setDataUnits(weatherSimpleEntry);
                            dataSource.set(position, weatherSimpleEntry);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    else {
                        showToast(error);
                    }
                }

                @Override
                public void onFailure(Call<OpenWeatherMapModel> call, Throwable t) {
                    Logger.d(t.toString());
                    showToast(error);
                }
            });
//        }
    }

    private void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
    }

    private WeatherSimpleEntry setDataUnits(WeatherSimpleEntry item) {
        String temperatureUnit = getWeatherConfig().getTemperatureUnit();
        double currentTemp = Double.valueOf(item.getTemperature());
        if (temperatureUnit.equals(getContext().getString(R.string.unit_cesium))) {
            double temp = currentTemp - 273.15;
            item.setTemperature(String.format(Locale.getDefault(), "%.0f %s", temp, temperatureUnit));
        }
        else if (temperatureUnit.equals(getContext().getString(R.string.unit_fahrenheit))) {
            double temp = 1.8 * (currentTemp - 273) + 32;
            item.setTemperature(String.format(Locale.getDefault(), "%.0f %s", temp, temperatureUnit));
        }

        String windSpeedUnit = getWeatherConfig().getWindSpeedUnit();
        double currentWindSpeed = Double.valueOf(item.getWindSpeed());
        if (windSpeedUnit.equals(getContext().getString(R.string.unit_meter_second))) {
            item.setWind(String.format(Locale.getDefault(), "%.0f %s", currentWindSpeed, windSpeedUnit));
        }
        else if (windSpeedUnit.equals(getContext().getString(R.string.unit_miles_hour))) {
            // TODO convert
            item.setWind(String.format(Locale.getDefault(), "%.0f %s", currentWindSpeed, getContext().getString(R.string.unit_meter_second)));
        }

        item.setHumidity(String.format(Locale.getDefault(), "%s %s", item.getHumidity(), getContext().getString(R.string.unit_percentage)));
        return item;
    }
}
