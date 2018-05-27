package ru.android73dd.geek.weather;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class WeatherConfig implements Parcelable {

    private String cityName;
    private boolean showTemperature;
    private boolean showHumidity;
    private boolean showWind;
    private boolean showProbabilityOfPrecipitation;

    public WeatherConfig(String cityName, boolean showTemperature, boolean showHumidity, boolean showWind, boolean showProbabilityOfPrecipitation) {
        this.cityName = cityName;
        this.showTemperature = showTemperature;
        this.showHumidity = showHumidity;
        this.showWind = showWind;
        this.showProbabilityOfPrecipitation = showProbabilityOfPrecipitation;
    }

    public String getCityName() {
        return cityName;
    }

    public boolean isShowTemperature() {
        return showTemperature;
    }

    public boolean isShowHumidity() {
        return showHumidity;
    }

    public boolean isShowWind() {
        return showWind;
    }

    public boolean isShowProbabilityOfPrecipitation() {
        return showProbabilityOfPrecipitation;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cityName);
        dest.writeByte(this.showTemperature ? (byte) 1 : (byte) 0);
        dest.writeByte(this.showHumidity ? (byte) 1 : (byte) 0);
        dest.writeByte(this.showWind ? (byte) 1 : (byte) 0);
        dest.writeByte(this.showProbabilityOfPrecipitation ? (byte) 1 : (byte) 0);
    }

    protected WeatherConfig(Parcel in) {
        this.cityName = in.readString();
        this.showTemperature = in.readByte() != 0;
        this.showHumidity = in.readByte() != 0;
        this.showWind = in.readByte() != 0;
        this.showProbabilityOfPrecipitation = in.readByte() != 0;
    }

    public static final Creator<WeatherConfig> CREATOR = new Creator<WeatherConfig>() {
        @Override
        public WeatherConfig createFromParcel(Parcel source) {
            return new WeatherConfig(source);
        }

        @Override
        public WeatherConfig[] newArray(int size) {
            return new WeatherConfig[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherConfig that = (WeatherConfig) o;
        return showTemperature == that.showTemperature &&
                showHumidity == that.showHumidity &&
                showWind == that.showWind &&
                showProbabilityOfPrecipitation == that.showProbabilityOfPrecipitation &&
                Objects.equals(cityName, that.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityName, showTemperature, showHumidity, showWind, showProbabilityOfPrecipitation);
    }
}
