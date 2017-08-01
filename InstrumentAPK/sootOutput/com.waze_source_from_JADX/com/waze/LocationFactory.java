package com.waze;

import android.location.Location;
import com.waze.config.ConfigValues;
import java.util.concurrent.atomic.AtomicBoolean;

public class LocationFactory {
    private static AtomicBoolean mInitialized = new AtomicBoolean(false);
    private static ILocationSensorListener mInstance = null;

    public static ILocationSensorListener getInstance() throws  {
        if (mInitialized.compareAndSet(false, true)) {
            boolean $z0 = ConfigValues.getBoolValue(83);
            if ($z0 && Utils.checkIsGooglePlayServicesAvailable()) {
                Logger.m41i("Google play services is available, using FusedLocationListener ");
                mInstance = new FusedLocationListener();
            } else {
                if ($z0) {
                    Logger.m43w("Google play services is NOT available, using NativeLocListener");
                } else {
                    Logger.m43w("Waze configuration disables FUSE location services, using NativeLocListener");
                }
                mInstance = new NativeLocListener();
            }
        }
        return mInstance;
    }

    public static NativeLocation getNativeLocation(Location $r0) throws  {
        return LocationSensorListener.GetNativeLocation($r0);
    }
}
