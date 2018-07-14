package ru.android73dd.geek.weather.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ru.android73dd.geek.weather.R;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private List<WeatherSimpleEntry> dataSource;
    private WeatherPreferences weatherPreferences;
    private OnItemClickListener itemClickListener;

    public WeatherAdapter(List<WeatherSimpleEntry> dataSource, WeatherPreferences weatherPreferences) {
        this.dataSource = dataSource;
        this.weatherPreferences = weatherPreferences;
    }

    public void setDataSource(List<WeatherSimpleEntry> dataSource) {
        this.dataSource = dataSource;
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
        private LinearLayout llHumidity;
        private LinearLayout llWind;

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

            llHumidity = itemView.findViewById(R.id.ll_humidity);
            llWind = itemView.findViewById(R.id.ll_wind);
        }

        void bind(int position) {
            if (getLayoutPosition() != RecyclerView.NO_POSITION) {
                WeatherSimpleEntry item = dataSource.get(position);
                setData(item);
            }
        }

        private void setData(WeatherSimpleEntry item) {
            cityName.setText(item.getCityName());
            statusPic.setImageResource(item.getStatusPic());
            temperature.setText(item.getTemperature());

            humidity.setText(item.getHumidity());
            llHumidity.setVisibility(weatherPreferences.isShowHumidity() ? View.VISIBLE : View.GONE);

            wind.setText(item.getWindSpeed());
            llWind.setVisibility(weatherPreferences.isShowWind() ? View.VISIBLE : View.GONE);
        }
    }
}
