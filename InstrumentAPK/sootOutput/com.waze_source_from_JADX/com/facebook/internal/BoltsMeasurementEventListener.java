package com.facebook.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import com.facebook.appevents.AppEventsLogger;

public class BoltsMeasurementEventListener extends BroadcastReceiver {
    private static final String BOLTS_MEASUREMENT_EVENT_PREFIX = "bf_";
    private static final String MEASUREMENT_EVENT_ARGS_KEY = "event_args";
    private static final String MEASUREMENT_EVENT_NAME_KEY = "event_name";
    private static final String MEASUREMENT_EVENT_NOTIFICATION_NAME = "com.parse.bolts.measurement_event";
    private static BoltsMeasurementEventListener _instance;
    private Context applicationContext;

    private BoltsMeasurementEventListener(Context $r1) throws  {
        this.applicationContext = $r1.getApplicationContext();
    }

    private void open() throws  {
        LocalBroadcastManager.getInstance(this.applicationContext).registerReceiver(this, new IntentFilter("com.parse.bolts.measurement_event"));
    }

    private void close() throws  {
        LocalBroadcastManager.getInstance(this.applicationContext).unregisterReceiver(this);
    }

    public static BoltsMeasurementEventListener getInstance(Context $r0) throws  {
        if (_instance != null) {
            return _instance;
        }
        _instance = new BoltsMeasurementEventListener($r0);
        _instance.open();
        return _instance;
    }

    protected void finalize() throws Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    public void onReceive(Context $r1, Intent $r2) throws  {
        AppEventsLogger $r4 = AppEventsLogger.newLogger($r1);
        String $r6 = BOLTS_MEASUREMENT_EVENT_PREFIX + $r2.getStringExtra("event_name");
        Bundle $r7 = $r2.getBundleExtra("event_args");
        Bundle $r3 = new Bundle();
        for (String $r11 : $r7.keySet()) {
            $r3.putString($r11.replaceAll("[^0-9a-zA-Z _-]", "-").replaceAll("^[ -]*", "").replaceAll("[ -]*$", ""), (String) $r7.get($r11));
        }
        $r4.logEvent($r6, $r3);
    }
}
