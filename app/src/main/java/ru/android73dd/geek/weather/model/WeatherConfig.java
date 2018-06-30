package ru.android73dd.geek.weather.model;

import java.util.Set;

public class WeatherConfig {

    private Set<String> citiesSet;
    private boolean showHumidity;
    private boolean showWind;
    private boolean showProbabilityOfPrecipitation;

    private WeatherConfig(Set<String> citiesSet, boolean showHumidity, boolean showWind,
                          boolean showProbabilityOfPrecipitation) {
        this.citiesSet = citiesSet;
        this.showHumidity = showHumidity;
        this.showWind = showWind;
        this.showProbabilityOfPrecipitation = showProbabilityOfPrecipitation;
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

    public static class Builder {

        private Set<String> citiesSet;
        private boolean showHumidity;
        private boolean showWind;
        private boolean showProbabilityOfPrecipitation;

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

        public WeatherConfig create() {
            return new WeatherConfig(citiesSet, showHumidity, showWind, showProbabilityOfPrecipitation);
        }
    }
}
