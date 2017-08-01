package com.waze.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import com.waze.Logger;
import com.waze.NativeManager;

public class VoiceActivator {
    private static long DOUBLE_WAVE_ACTIVATION_PERIOD = 2000;
    public static final VoiceActivator instance = new VoiceActivator();
    private static SensorEventListener listener = new C26081();
    private static long mActivationStartTime = 0;
    private static boolean mIsTwice = false;
    private SensorManager sensorManager;

    static class C26081 implements SensorEventListener {
        C26081() {
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == 8 && event.values[0] < event.sensor.getMaximumRange() && VoiceActivator.isActivated()) {
                NativeManager.getInstance().asrActivated();
            }
        }
    }

    public static void activate(Context context, boolean twice) {
        mIsTwice = twice;
        instance.sensorManager = (SensorManager) context.getSystemService("sensor");
        instance.sensorManager.registerListener(listener, instance.sensorManager.getDefaultSensor(8), 2);
    }

    public static void deactivate(Context context) {
        instance.sensorManager = (SensorManager) context.getSystemService("sensor");
        instance.sensorManager.unregisterListener(listener);
    }

    private static boolean isActivated() {
        long timestamp_ms = SystemClock.elapsedRealtime();
        long delta = timestamp_ms - mActivationStartTime;
        Logger.d_("VoiceActivator", "Activation delta: " + delta);
        if (!mIsTwice || (mActivationStartTime != 0 && delta <= DOUBLE_WAVE_ACTIVATION_PERIOD)) {
            mActivationStartTime = 0;
            return true;
        }
        mActivationStartTime = timestamp_ms;
        return false;
    }
}
