package com.waze;

import android.app.PendingIntent;
import android.location.Location;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import com.waze.main.navigate.GeoFencingReceiver;

public final class NativeLocListener extends LocationSensorListener implements LocationListener {
    public NativeLocListener() throws  {
        boolean $z1;
        boolean $z0 = true;
        int $i0 = super.init();
        if (($i0 & 8) != 0) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        this.mResetGps = $z1;
        if (($i0 & 16) == 0) {
            $z0 = false;
        }
        this.mClearCache = $z0;
    }

    public void start() throws  {
        try {
            stop();
            if (checkPermissions()) {
                Logger.m36d("Starting location listener");
                if (this.mLocationManager.getProvider("network") != null) {
                    this.mLocationManager.requestLocationUpdates("network", 0, 0.0f, this);
                }
                if (this.mLocationManager.getProvider("gps") != null) {
                    if (this.mResetGps) {
                        Bundle $r1 = new Bundle();
                        this.mLocationManager.sendExtraCommand("gps", "force_xtra_injection", $r1);
                        this.mLocationManager.sendExtraCommand("gps", "force_time_injection", $r1);
                    }
                    if (this.mClearCache) {
                        NetworkInfo $r8 = ((ConnectivityManager) AppService.getAppContext().getSystemService("connectivity")).getActiveNetworkInfo();
                        if ($r8 != null && $r8.isConnected()) {
                            this.mLocationManager.sendExtraCommand("gps", "delete_aiding_data", null);
                        }
                    }
                    this.mLocationManager.requestLocationUpdates("gps", 0, 0.0f, this);
                }
                this.mStarted = true;
                this.mStatus = 3;
                this.mHasGps = false;
                this.mLastGpsFixTime = System.currentTimeMillis();
                super.start();
            }
        } catch (Exception $r2) {
            Logger.m38e("Error starting location listener" + $r2.getMessage());
        }
    }

    public void stop() throws  {
        if (this.mStarted) {
            Logger.m36d("Stopping location listener");
            this.mLocationManager.removeUpdates(this);
            this.mStarted = false;
        }
        super.stop();
    }

    public void SetProximityAlert(PendingIntent $r1, double $d0, double $d1, float $f0, long $l0) throws  {
        GeoFencingReceiver.IsEntered = false;
        this.mLocationManager.addProximityAlert($d1, $d0, $f0, $l0, $r1);
        this.mProximityAdded = true;
    }

    public void RemoveProximityAlert(PendingIntent $r1) throws  {
        if (this.mProximityAdded) {
            try {
                this.mLocationManager.removeProximityAlert($r1);
            } catch (IllegalArgumentException e) {
                Logger.m38e("IllegalArgumentException for removeProximityAlert");
            }
            this.mProximityAdded = false;
        }
    }

    public void onStatusChanged(String $r1, int $i0, Bundle extras) throws  {
        Logger.m36d("onStatusChanged: " + $r1 + " status " + $i0);
        if ($i0 == 2) {
            this.mIsavailable = true;
            if ($r1.equals("gps")) {
                this.mStatus |= 2;
            }
            if ($r1.equals("network")) {
                this.mStatus |= 1;
            }
        } else {
            this.mIsavailable = false;
            if ($r1.equals("gps")) {
                this.mStatus &= -3;
            }
            if ($r1.equals("network")) {
                this.mStatus &= -2;
            }
        }
        if (ContextCompat.checkSelfPermission(AppService.getAppContext(), "android.permission.ACCESS_FINE_LOCATION") != 0 && ContextCompat.checkSelfPermission(AppService.getAppContext(), "android.permission.ACCESS_COARSE_LOCATION") == 0) {
        }
    }

    public void onProviderDisabled(String provider) throws  {
        Logger.m36d("onProviderDisabled");
    }

    public void onProviderEnabled(String provider) throws  {
        Logger.m36d("onProviderEnabled");
    }

    protected synchronized void setLastLocation(Location $r1, long $l0) throws  {
        if ($r1.getProvider().equals("gps")) {
            Logger.m36d("setLastLocation: mLastGpsFixTime updated to " + $l0);
            this.mLastGpsFixTime = $l0;
        }
        super.setLastLocation($r1, $l0);
    }

    protected int setLocationAccuracy(int $i0, Location $r1, NativeLocation $r2) throws  {
        byte $b1 = (byte) 0;
        if ($r1.getProvider().equals("network")) {
            $b1 = (byte) 1;
            if (($i0 & 1) == 0) {
                $r2.mAccuracy = -1;
            }
        }
        if ($r1.getProvider().equals("gps")) {
            $b1 = (byte) 0;
            if (($i0 & 2) == 0) {
                $r2.mAccuracy = -1;
            }
        }
        if (VERSION.SDK_INT < 18 || !this.mbLoggedIn || this.mbReportedMockLocation || !$r1.isFromMockProvider()) {
            return $b1;
        }
        Logger.m36d("Location is from mock provider: " + $r1.getProvider());
        setGpsFakeStatusNTV();
        this.mbReportedMockLocation = true;
        return $b1;
    }
}
