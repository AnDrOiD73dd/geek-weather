package ru.android73dd.geek.weather.ui.settings;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v7.app.ActionBar;

import java.util.List;

import ru.android73dd.geek.weather.R;
import ru.android73dd.geek.weather.model.WeatherPreferences;
import ru.android73dd.geek.weather.repository.SettingsRepositoryImpl;
import ru.android73dd.geek.weather.ui.settings.AppCompatPreferenceActivity;
import ru.android73dd.geek.weather.utils.Logger;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatPreferenceActivity {

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            preference.setSummary(stringValue);
            return true;
        }
    };

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setupActionBar();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || InterfacePreferenceFragment.class.getName().equals(fragmentName)
                || NotificationsPreferenceFragment.class.getName().equals(fragmentName);
    }

    /**
     * This fragment shows interface preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class InterfacePreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        private static final String PREF_HUMIDITY = "pref_show_humidity";
        private static final String PREF_WIND = "pref_show_wind";
        private static final String PREF_TEMPERATURE_UNITS = "pref_interface_temperature_unit";
        private static final String PREF_WIND_UNITS = "pref_interface_wind_unit";

        private SwitchPreference prefHumidity;
        private SwitchPreference prefWind;
        private ListPreference prefTemperatureUnitList;
        private ListPreference prefWindUnitList;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_interface);

            prefHumidity = (SwitchPreference) findPreference(PREF_HUMIDITY);
            prefWind = (SwitchPreference) findPreference(PREF_WIND);
            prefTemperatureUnitList = (ListPreference) findPreference(PREF_TEMPERATURE_UNITS);
            prefWindUnitList = (ListPreference) findPreference(PREF_WIND_UNITS);

            WeatherPreferences weatherPreferences = SettingsRepositoryImpl.getInstance().getSettings(getActivity());
            prefHumidity.setChecked(weatherPreferences.isShowHumidity());
            prefWind.setChecked(weatherPreferences.isShowWind());
            prefTemperatureUnitList.setValue(weatherPreferences.getTemperatureUnit());
            prefWindUnitList.setValue(weatherPreferences.getWindSpeedUnit());
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            switch (key) {
                case PREF_HUMIDITY:
                    WeatherPreferences weatherPreferences = SettingsRepositoryImpl.getInstance().getSettings(getActivity());
                    weatherPreferences.setShowHumidity(prefHumidity.isChecked());
                    SettingsRepositoryImpl.getInstance().saveSettings(getActivity(), weatherPreferences);
                    break;
                case PREF_WIND:
                    weatherPreferences = SettingsRepositoryImpl.getInstance().getSettings(getActivity());
                    weatherPreferences.setShowWind(prefWind.isChecked());
                    SettingsRepositoryImpl.getInstance().saveSettings(getActivity(), weatherPreferences);
                    break;
                case PREF_TEMPERATURE_UNITS:
                    weatherPreferences = SettingsRepositoryImpl.getInstance().getSettings(getActivity());
                    weatherPreferences.setTemperatureUnit(prefTemperatureUnitList.getValue());
                    SettingsRepositoryImpl.getInstance().saveSettings(getActivity(), weatherPreferences);
                    break;
                case PREF_WIND_UNITS:
                    weatherPreferences = SettingsRepositoryImpl.getInstance().getSettings(getActivity());
                    weatherPreferences.setWindSpeedUnit(prefWindUnitList.getValue());
                    SettingsRepositoryImpl.getInstance().saveSettings(getActivity(), weatherPreferences);
                    break;
                default:
                    Logger.d("Unknown key = " + key);
                    break;
            }
        }
    }

    /**
     * This fragment shows notifications preferences only.
     * This is stub, not implemented yet
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class NotificationsPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_notifications);
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            switch (key) {
                default:
                    Logger.d("Unknown key = " + key);
                    break;
            }
        }
    }
}
