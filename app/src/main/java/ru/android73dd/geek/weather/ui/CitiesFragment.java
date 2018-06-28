package ru.android73dd.geek.weather.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.List;

import ru.android73dd.geek.weather.R;
import ru.android73dd.geek.weather.model.Weather;
import ru.android73dd.geek.weather.model.WeatherAdapter;
import ru.android73dd.geek.weather.model.WeatherConfig;
import ru.android73dd.geek.weather.repository.SettingsRepositoryImpl;
import ru.android73dd.geek.weather.utils.DataSourceBuilder;
import ru.android73dd.geek.weather.utils.Logger;

public class CitiesFragment extends BaseFragment implements View.OnClickListener, WeatherAdapter.OnItemClickListener {

    private OnFragmentInteractionListener listener;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private List<Weather> dataSource;
    private WeatherAdapter adapter;

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
        listener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add_city:
                showAddCityDialog(v);
                break;
            default:
                Logger.d("Unknown view = " + v.getId());
                break;
        }
    }

    private void showAddCityDialog(View v) {
        final EditText input = new EditText(v.getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext())
                .setView(input)
                .setTitle(R.string.dialog_add_city_title)
                .setPositiveButton(R.string.dialog_add_city_button_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String cityName = input.getText().toString().trim();
                        if (!cityName.isEmpty()) {
                            SettingsRepositoryImpl.getInstance().addCity(getActivity(), cityName);
                            dataSource.add(Weather.createDefault(getContext(), cityName));
                            adapter.notifyDataSetChanged();
                        }
                    }
                })
                .setNegativeButton(R.string.dialog_add_city_button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    @Override
    public void onItemClick(View view, int position) {
        listener.onItemClicked(dataSource.get(position).getCityName());
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
        adapter = new WeatherAdapter(dataSource, getWeatherConfig());
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }
}
