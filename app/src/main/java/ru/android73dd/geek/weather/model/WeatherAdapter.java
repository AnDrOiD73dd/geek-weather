package ru.android73dd.geek.weather.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.android73dd.geek.weather.R;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private List<Weather> dataSource;
    private OnItemClickListener itemClickListener;

    public WeatherAdapter(List<Weather> dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Weather item = dataSource.get(position);
        holder.cityName.setText(item.getCityName());
        holder.statusPic.setImageResource(item.getStatusPic());
        holder.temperature.setText(item.getTemperature());
        holder.humidity.setText(item.getHumidity());
        holder.wind.setText(item.getWind());
        holder.probabilityOfPrecipitation.setText(item.getProbabilityOfPrecipitation());
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public void SetOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView cityName;
        private ImageView statusPic;
        private TextView temperature;
        private TextView humidity;
        private TextView wind;
        private TextView probabilityOfPrecipitation;

        public ViewHolder(View itemView) {
            super(itemView);

            cityName = itemView.findViewById(R.id.tv_city_name);
            statusPic = itemView.findViewById(R.id.iv_status);
            temperature = itemView.findViewById(R.id.tv_temperature_value);
            humidity = itemView.findViewById(R.id.tv_humidity_value);
            wind = itemView.findViewById(R.id.tv_wind_value);
            probabilityOfPrecipitation = itemView.findViewById(R.id.tv_probability_of_precipitation_value);
        }
    }
}
