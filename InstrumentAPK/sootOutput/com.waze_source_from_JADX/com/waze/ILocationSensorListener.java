package com.waze;

import android.app.PendingIntent;
import android.location.Location;
import com.waze.ifs.async.RunnableExecutor;
import com.waze.view.map.SpeedometerView;

public interface ILocationSensorListener {
    void RegisterCompass() throws ;

    void RemoveProximityAlert(PendingIntent pendingIntent) throws ;

    void SetProximityAlert(PendingIntent pendingIntent, double d, double d2, float f, long j) throws ;

    void UnregisterCompass() throws ;

    Location getLastLocation() throws ;

    boolean gpsProviderEnabled() throws ;

    void initSpeedometer(SpeedometerView speedometerView) throws ;

    void onLogin() throws ;

    void playSpeedometerSound() throws ;

    void registerLocListener(RunnableExecutor runnableExecutor) throws ;

    void registerSensors() throws ;

    void start() throws ;

    void stop() throws ;

    void unregisterLocListener(RunnableExecutor runnableExecutor) throws ;

    void unregisterSensors() throws ;
}
