package com.waze.widget;

import android.util.Log;
import java.util.Date;

public class WazeAppWidgetLog {
    public static final String LOG_TAG = "WAZE WIDGET";

    public static void m45d(String msg) {
        if (WazeAppWidgetPreferences.debugEnabled().booleanValue()) {
            Log.d(LOG_TAG, "[" + new Date().toLocaleString() + "] - " + "[DEBUG] - " + msg);
        }
    }

    public static void m47w(String msg) {
        Log.w(LOG_TAG, "[" + new Date().toLocaleString() + "] - " + "[WARNING] - " + msg);
    }

    public static void m46e(String msg) {
        Log.e(LOG_TAG, "[" + new Date().toLocaleString() + "] - " + "[ERROR] - " + msg);
    }
}
