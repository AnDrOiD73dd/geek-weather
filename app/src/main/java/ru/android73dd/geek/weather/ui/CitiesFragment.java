package ru.android73dd.geek.weather.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.android73dd.geek.weather.R;
import ru.android73dd.geek.weather.model.Weather;
import ru.android73dd.geek.weather.model.WeatherAdapter;
import ru.android73dd.geek.weather.model.WeatherConfig;
import ru.android73dd.geek.weather.repository.SettingsRepositoryImpl;
import ru.android73dd.geek.weather.utils.DataSourceBuilder;
import ru.android73dd.geek.weather.utils.Logger;

public class CitiesFragment extends Fragment implements View.OnClickListener, WeatherAdapter.OnItemClickListener {

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private List<Weather> dataSource;

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
            mListener = (OnFragmentInteractionListener) context;
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

        fab = layout.findViewById(R.id.fab_add_city);
        fab.setOnClickListener(this);
        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        setupAdapter();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add_city:
                break;
            default:
                Logger.d("Unknown view = " + v.getId());
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        mListener.onItemClicked(dataSource.get(position).getCityName());
    }

    public WeatherConfig getWeatherConfig() {
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
        DataSourceBuilder builder = new DataSourceBuilder(getActivity());
        dataSource = builder.build();
        final WeatherAdapter adapter = new WeatherAdapter(dataSource, getWeatherConfig());
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }
}
