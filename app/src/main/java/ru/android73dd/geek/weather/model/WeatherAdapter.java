package ru.android73dd.geek.weather.model;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.android73dd.geek.weather.R;
import ru.android73dd.geek.weather.model.openweathermap.OpenWeatherMapModel;
import ru.android73dd.geek.weather.network.openweathermap.OpenWeatherRequester;
import ru.android73dd.geek.weather.repository.OpenWeatherRepositoryImpl;
import ru.android73dd.geek.weather.utils.Logger;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private List<WeatherSimpleEntry> dataSource;
    private WeatherPreferences weatherPreferences;
    private OnItemClickListener itemClickListener;

    public WeatherAdapter(List<WeatherSimpleEntry> dataSource, WeatherPreferences weatherPreferences) {
        this.dataSource = dataSource;
        this.weatherPreferences = weatherPreferences;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_cities_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView cityName;
        private ImageView statusPic;
        private TextView temperature;
        private TextView humidity;
        private TextView wind;
        private TextView probabilityOfPrecipitation;
        private LinearLayout llHumidity;
        private LinearLayout llWind;
        private LinearLayout llProbabilityOfPrecipitation;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });

            cityName = itemView.findViewById(R.id.tv_city_name);
            statusPic = itemView.findViewById(R.id.iv_status);
            temperature = itemView.findViewById(R.id.tv_temperature_value);
            humidity = itemView.findViewById(R.id.tv_humidity_value);
            wind = itemView.findViewById(R.id.tv_wind_value);
            probabilityOfPrecipitation = itemView.findViewById(R.id.tv_probability_of_precipitation_value);

            llHumidity = itemView.findViewById(R.id.ll_humidity);
            llWind = itemView.findViewById(R.id.ll_wind);
            llProbabilityOfPrecipitation = itemView.findViewById(R.id.ll_probability_of_precipitation);
        }

        void bind(int position) {
            if (getLayoutPosition() != RecyclerView.NO_POSITION) {
                WeatherSimpleEntry item = dataSource.get(position);
                setData(item);
                updateValues(item, position);
            }
        }

        private void setData(WeatherSimpleEntry item) {
            cityName.setText(item.getCityName());
            statusPic.setImageResource(item.getStatusPic());
            temperature.setText(item.getTemperature());

            humidity.setText(item.getHumidity());
            llHumidity.setVisibility(weatherPreferences.isShowHumidity() ? View.VISIBLE : View.GONE);

            wind.setText(item.getWind());
            llWind.setVisibility(weatherPreferences.isShowWind() ? View.VISIBLE : View.GONE);

            probabilityOfPrecipitation.setText(item.getProbabilityOfPrecipitation());
            llProbabilityOfPrecipitation.setVisibility(weatherPreferences.isShowProbabilityOfPrecipitation() ? View.VISIBLE : View.GONE);
        }

        private void updateValues(WeatherSimpleEntry item, final int position) {
            final String cityName = item.getCityName();
            if (item.equals(WeatherSimpleEntry.createDefault(cityName))) {
                OpenWeatherMapModel openWeatherMapModel = OpenWeatherRepositoryImpl.getInstance().getByCityName(cityName);
                if (openWeatherMapModel != null) {
                    dataSource.set(position, WeatherSimpleEntry.map(openWeatherMapModel));
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            notifyDataSetChanged();
                        }
                    });
                    return;
                }
                OpenWeatherRequester openWeatherRequester = new OpenWeatherRequester();
                openWeatherRequester.getWeather(cityName, new Callback<OpenWeatherMapModel>() {
                    @Override
                    public void onResponse(Call<OpenWeatherMapModel> call, Response<OpenWeatherMapModel> response) {
                        if (response.code() == 200) {
                            OpenWeatherMapModel openWeatherMapModel = response.body();
                            if (openWeatherMapModel != null) {
                                OpenWeatherRepositoryImpl.getInstance().add(openWeatherMapModel);
                                dataSource.set(position, WeatherSimpleEntry.map(openWeatherMapModel));
                                notifyDataSetChanged();
                            }
                        }
                        else {
                            Snackbar.make(itemView, R.string.error_load_data, Snackbar.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<OpenWeatherMapModel> call, Throwable t) {
                        Logger.d(t.toString());
                        Snackbar.make(itemView, R.string.error_load_data, Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        }
    }
}
