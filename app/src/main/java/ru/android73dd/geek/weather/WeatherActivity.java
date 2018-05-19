package ru.android73dd.geek.weather;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

public class WeatherActivity extends AppCompatActivity {

    private TextView tvCityName;
    private TextView tvDate;
    private TextView tvTempValue;
    private TextView tvHumidityValue;
    private TextView tvWindValue;
    private TextView tvProbabilityOfPrecipitationValue;
    private LinearLayout llTemperature;
    private LinearLayout llHumidity;
    private LinearLayout llWind;
    private LinearLayout llProbabilityOfPrecipitation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        tvCityName = findViewById(R.id.tv_city_value);
        tvDate = findViewById(R.id.tv_date);
        tvTempValue = findViewById(R.id.tv_temperature_value);
        tvHumidityValue = findViewById(R.id.tv_humidity_value);
        tvWindValue = findViewById(R.id.tv_wind_value);
        tvProbabilityOfPrecipitationValue = findViewById(R.id.tv_probability_of_precipitation_value);
        llTemperature = findViewById(R.id.ll_temperature);
        llHumidity = findViewById(R.id.ll_humidity);
        llWind = findViewById(R.id.ll_wind);
        llProbabilityOfPrecipitation = findViewById(R.id.ll_probability_of_precipitation);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        updateUI(extras.getString(WelcomeActivity.KEY_CITY_NAME),
                extras.getBoolean(WelcomeActivity.KEY_PARAM_TEMPERATURE, true),
                extras.getBoolean(WelcomeActivity.KEY_PARAM_HUMIDITY, true),
                extras.getBoolean(WelcomeActivity.KEY_PARAM_WIND, true),
                extras.getBoolean(WelcomeActivity.KEY_PARAM_PROBABILITY_OF_PRECIPITATION, true));
    }

    private void updateUI(String cityName, boolean showTemp, boolean showHumidity, boolean showWind, boolean showProbabilityOfPrecipitation) {
        tvCityName.setText(cityName);
        tvDate.setText(getCurrentTime());
        llTemperature.setVisibility(showTemp ? View.VISIBLE : View.GONE);
        llHumidity.setVisibility(showHumidity ? View.VISIBLE : View.GONE);
        llWind.setVisibility(showWind ? View.VISIBLE : View.GONE);
        llProbabilityOfPrecipitation.setVisibility(showProbabilityOfPrecipitation ? View.VISIBLE : View.GONE);
    }

    private String getCurrentTime() {
        String res;
        String sdf = "EEEE, dd MMMM yyyy, hh:mm:ss";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Calendar calendar = Calendar.getInstance();
//            res = String.format(Locale.getDefault(), "%1$tA %1$tb %1$td %1$tY, %1$tI:%1$tM %1$Tp", calendar);
            SimpleDateFormat format = new SimpleDateFormat(sdf);
            res = format.format(calendar.getTime());
        } else {
            android.text.format.DateFormat df = new android.text.format.DateFormat();
            Date date = new java.util.Date();
            res = df.format(sdf, date).toString();
        }
        return res;
    }

}
