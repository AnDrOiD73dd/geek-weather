package ru.android73dd.geek.weather.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import static android.content.Context.SENSOR_SERVICE;

public class SensorUtils {

    public static SensorManager getSensorManager(Context context) {
        return (SensorManager) context.getSystemService(SENSOR_SERVICE);
    }

    private static Sensor getSensor(SensorManager sensorManager, int sensorType) {
        return sensorManager.getDefaultSensor(sensorType);
    }

    public static Sensor getTemperatureSensor(SensorManager sensorManager) {
        return getSensor(sensorManager, Sensor.TYPE_AMBIENT_TEMPERATURE);
    }

    public static Sensor getHumiditySensor(SensorManager sensorManager) {
        return getSensor(sensorManager, Sensor.TYPE_RELATIVE_HUMIDITY);
    }

    private static boolean subscribeSensor(Context context, SensorEventListener listener, int sensorType) {
        SensorManager sensorManager = getSensorManager(context);
        Sensor sensor = getSensor(sensorManager, sensorType);
        if (sensor == null) {
            return false;
        }
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        return true;
    }

    public static boolean subscribeTemperatureSensor(Context context, SensorEventListener listener) {
        return subscribeSensor(context, listener, Sensor.TYPE_AMBIENT_TEMPERATURE);
    }

    public static boolean subscribeHumiditySensor(Context context, SensorEventListener listener) {
        return subscribeSensor(context, listener, Sensor.TYPE_RELATIVE_HUMIDITY);
    }

    private static void unsibscribeSensor(Context context, SensorEventListener listener, int sensorType) {
        SensorManager sensorManager = getSensorManager(context);
        Sensor sensor = getSensor(sensorManager, sensorType);
        sensorManager.unregisterListener(listener, sensor);
    }

    public static void unsubscribeTemperatureSensor(Context context, SensorEventListener listener) {
        unsibscribeSensor(context, listener, Sensor.TYPE_AMBIENT_TEMPERATURE);
    }

    public static void unsubscribeHumiditySensor(Context context, SensorEventListener listener) {
        unsibscribeSensor(context, listener, Sensor.TYPE_RELATIVE_HUMIDITY);
    }
}
