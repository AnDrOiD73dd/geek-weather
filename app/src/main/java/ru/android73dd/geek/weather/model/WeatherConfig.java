package ru.android73dd.geek.weather.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class WeatherConfig implements Parcelable {

    private Set<String> citiesSet;
    private String cityName;
    private boolean showHumidity;
    private boolean showWind;
    private boolean showProbabilityOfPrecipitation;

    private WeatherConfig(Set<String> citiesSet, String cityName, boolean showHumidity,
                          boolean showWind, boolean showProbabilityOfPrecipitation) {
        this.citiesSet = citiesSet;
        this.cityName = cityName;
        this.showHumidity = showHumidity;
        this.showWind = showWind;
        this.showProbabilityOfPrecipitation = showProbabilityOfPrecipitation;
    }

    public Set<String> getCitiesSet() {
        return citiesSet;
    }

    public String getCityName() {
        return cityName;
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

    public void setCitiesSet(Set<String> citiesSet) {
        this.citiesSet = citiesSet;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setShowHumidity(boolean showHumidity) {
        this.showHumidity = showHumidity;
    }

    public void setShowWind(boolean showWind) {
        this.showWind = showWind;
    }

    public void setShowProbabilityOfPrecipitation(boolean showProbabilityOfPrecipitation) {
        this.showProbabilityOfPrecipitation = showProbabilityOfPrecipitation;
    }

    public static class Builder {

        private Set<String> citiesSet;
        private String cityName;
        private boolean showHumidity;
        private boolean showWind;
        private boolean showProbabilityOfPrecipitation;

        public Builder setCitiesSet(Set<String> citiesSet) {
            this.citiesSet = citiesSet;
            return this;
        }

        public Builder setCityName(String cityName) {
            this.cityName = cityName;
            return this;
        }

        public Builder setShowHumidity(boolean showHumidity) {
            this.showHumidity = showHumidity;
            return this;
        }

        public Builder setShowWind(boolean showWind) {
            this.showWind = showWind;
            return this;
        }

        public Builder setShowProbabilityOfPrecipitation(boolean showProbabilityOfPrecipitation) {
            this.showProbabilityOfPrecipitation = showProbabilityOfPrecipitation;
            return this;
        }

        public WeatherConfig create() {
            return new WeatherConfig(citiesSet, cityName, showHumidity, showWind, showProbabilityOfPrecipitation);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String[] citiesArray = new String[0];
        citiesSet.toArray(citiesArray);
        dest.writeStringArray(citiesArray);
        dest.writeString(this.cityName);
        dest.writeByte(this.showHumidity ? (byte) 1 : (byte) 0);
        dest.writeByte(this.showWind ? (byte) 1 : (byte) 0);
        dest.writeByte(this.showProbabilityOfPrecipitation ? (byte) 1 : (byte) 0);
    }

    protected WeatherConfig(Parcel in) {
        String[] citiesArray = in.createStringArray();
        this.citiesSet = new HashSet<>(Arrays.asList(citiesArray));
        this.cityName = in.readString();
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
}
