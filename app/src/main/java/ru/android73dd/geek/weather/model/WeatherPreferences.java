package ru.android73dd.geek.weather.model;

import java.util.Set;

public class WeatherPreferences {

    private Set<String> citiesSet;
    private boolean showHumidity;
    private boolean showWind;
    private boolean showProbabilityOfPrecipitation;
    private String temperatureUnit;
    private String windSpeedUnit;

    private WeatherPreferences(Set<String> citiesSet, boolean showHumidity, boolean showWind,
                               boolean showProbabilityOfPrecipitation, String temperatureUnit,
                               String windSpeedUnit) {
        this.citiesSet = citiesSet;
        this.showHumidity = showHumidity;
        this.showWind = showWind;
        this.showProbabilityOfPrecipitation = showProbabilityOfPrecipitation;
        this.temperatureUnit = temperatureUnit;
        this.windSpeedUnit = windSpeedUnit;
    }

    public Set<String> getCitiesSet() {
        return citiesSet;
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

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public String getWindSpeedUnit() {
        return windSpeedUnit;
    }

    public void setCitiesSet(Set<String> citiesSet) {
        this.citiesSet = citiesSet;
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

    public void setTemperatureUnit(String temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    public void setWindSpeedUnit(String windSpeedUnit) {
        this.windSpeedUnit = windSpeedUnit;
    }

    public static class Builder {

        private Set<String> citiesSet;
        private boolean showHumidity;
        private boolean showWind;
        private boolean showProbabilityOfPrecipitation;
        private String temperatureUnit;
        private String windSpeedUnit;

        public Builder setCitiesSet(Set<String> citiesSet) {
            this.citiesSet = citiesSet;
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

        public Builder setTemperatureUnit(String temperatureUnit) {
            this.temperatureUnit = temperatureUnit;
            return this;
        }

        public Builder setWindSpeedUnit(String windSpeedUnit) {
            this.windSpeedUnit = windSpeedUnit;
            return this;
        }

        public WeatherPreferences create() {
            return new WeatherPreferences(citiesSet, showHumidity, showWind,
                    showProbabilityOfPrecipitation, temperatureUnit, windSpeedUnit);
        }
    }
}
