package weather.geekbrains.android73dd.ru.weather;

import android.util.Log;

import java.util.Locale;

/**
 * Custom Log system with black jack
 */
public class Logger {
    public static final String TAG = "Weather";

    private static final boolean PRINT_FILE_NAME = true;

    public static void v(String message) {
        Log.v(TAG, getLocation() + message);
    }

    public static void d(String message){
        Log.d(TAG, getLocation() + message);
    }

    public static void e(String message) {
        Log.e(TAG, getLocation() + message);
    }

    public static void w(String message) {
        Log.w(TAG, getLocation() + message);
    }

    public static void i(String message) {
        Log.i(TAG, getLocation() + message);
    }

    public static void e(Throwable e) {
        Log.e(TAG, "Exception: ", e);
    }

    private static String getLocation() {
        if (!PRINT_FILE_NAME)
            return "";

        final String logClassName = Logger.class.getName();
        final StackTraceElement[] traces = Thread.currentThread().getStackTrace();
        boolean found = false;

        for (StackTraceElement trace : traces) {
            if (found) {
                if (!trace.getClassName().startsWith(logClassName)) {
                    return String.format(Locale.getDefault(), "[%s:%d]", trace.getFileName().substring(0, trace.getFileName().length() - 5),
                            trace.getLineNumber());
                }
            } else if (trace.getClassName().startsWith(logClassName)) {
                found = true;
            }

        }

        return "[]";
    }
}
