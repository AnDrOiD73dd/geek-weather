package ru.android73dd.geek.weather;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        overallLog("onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        overallLog("onStart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        overallLog("onRestoreInstanceState");
    }

    @Override
    protected void onResume() {
        super.onResume();
        overallLog("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        overallLog("onPause");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        overallLog("onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        overallLog("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        overallLog("onDestroy");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void writeToLogcat(String message) {
        Logger.d(message);
    }

    private void overallLog(String message) {
        writeToLogcat(message);
        showToast(message);
    }
}
