package com.waze;

import android.app.PendingIntent;
import android.location.Location;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class FusedLocationListener extends LocationSensorListener implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
    private static final long FASTEST_INTERVAL = 0;
    private static final long INTERVAL = 1000;
    private GoogleApiClient mGoogleApiClient = new Builder(AppService.getAppContext(), this, this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
    private LocationRequest mLocationRequest;

    protected int setLocationAccuracy(int aStatus, Location aLoc, NativeLocation loc) throws  {
        return 0;
    }

    public FusedLocationListener() throws  {
        Logger.m36d("Creating Fused location listener");
        super.init();
        createLocationRequest();
    }

    public void onConnectionFailed(ConnectionResult $r1) throws  {
        Logger.m36d("FusedLocationListener Connection failed: " + $r1.toString());
        this.mIsavailable = false;
    }

    public void onConnected(Bundle arg0) throws  {
        Logger.m36d("FusedLocationListener Connected");
        this.mLastLocation = LocationServices.FusedLocationApi.getLastLocation(this.mGoogleApiClient);
        startLocationUpdates();
    }

    public void onConnectionSuspended(int arg0) throws  {
        Logger.m36d("FusedLocationListener Suspended");
    }

    public void SetProximityAlert(PendingIntent $r1, double $d0, double $d1, float $f0, long $l0) throws  {
        Logger.m36d("FusedLocationListener prox alert " + $r1.toString());
        try {
            String $r5;
            if (VERSION.SDK_INT > 16) {
                $r5 = $r1.getCreatorPackage();
                Logger.m36d("SDK is " + VERSION.SDK_INT + "; using getCreatorPackage() method in Fuse location");
            } else {
                $r5 = $r1.getTargetPackage();
                Logger.m36d("SDK is " + VERSION.SDK_INT + "; using getTargetPackage() method in Fuse location");
            }
            Geofence $r8 = new Geofence.Builder().setRequestId($r5).setCircularRegion($d1, $d0, $f0).setExpirationDuration($l0).setTransitionTypes(3).build();
            GeofencingRequest.Builder $r2 = new GeofencingRequest.Builder();
            $r2.setInitialTrigger(1);
            $r2.addGeofence($r8);
            GeofencingRequest $r9 = $r2.build();
            LocationServices.GeofencingApi.addGeofences(this.mGoogleApiClient, $r9, $r1);
            this.mProximityAdded = true;
        } catch (NoSuchMethodError e) {
            Logger.m38e("Method android.app.PendingIntent.getCreatorPackage does not exist in current SDK: " + VERSION.SDK_INT + "; Proximity alert cannot be enabled");
        }
    }

    public void RemoveProximityAlert(PendingIntent $r1) throws  {
        if (this.mProximityAdded) {
            Logger.m36d("FusedLocationListener remove prox alert " + $r1.toString());
            try {
                LocationServices.GeofencingApi.removeGeofences(this.mGoogleApiClient, $r1);
                this.mProximityAdded = false;
            } catch (Exception $r2) {
                Logger.m39e("An exception occurred while trying to remove proximity alert; SDK: " + VERSION.SDK_INT, $r2);
            }
        }
    }

    public void start() throws  {
        Logger.m36d("FusedLocationListener start");
        try {
            stop();
            if (checkPermissions()) {
                this.mGoogleApiClient.connect();
                this.mStarted = true;
                this.mStatus = 3;
                this.mHasGps = false;
                this.mLastGpsFixTime = System.currentTimeMillis();
                super.start();
                Logger.m36d("Starting location listener");
            }
        } catch (Exception $r1) {
            Logger.ee("Error starting location listener", $r1);
        }
    }

    public void stop() throws  {
        Logger.m36d("stop");
        if (this.mStarted) {
            Logger.m36d("Stopping location listener");
            this.mGoogleApiClient.disconnect();
            this.mStarted = false;
        }
        super.stop();
    }

    protected void startLocationUpdates() throws  {
        Logger.m36d("FusedLocationListener start loc updates");
        LocationServices.FusedLocationApi.requestLocationUpdates(this.mGoogleApiClient, this.mLocationRequest, (LocationListener) this);
        Logger.m36d("Location update started ..............: ");
        this.mIsavailable = true;
    }

    protected void stopLocationUpdates() throws  {
        Logger.m36d("FusedLocationListener stop loc updates");
        LocationServices.FusedLocationApi.removeLocationUpdates(this.mGoogleApiClient, (LocationListener) this);
        Logger.m36d("Location update stopped .......................");
        this.mIsavailable = false;
    }

    protected void createLocationRequest() throws  {
        Logger.m36d("FusedLocationListener create loc req");
        this.mLocationRequest = new LocationRequest();
        this.mLocationRequest.setInterval(INTERVAL);
        this.mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        this.mLocationRequest.setPriority(100);
    }

    protected synchronized void setLastLocation(Location $r1, long $l0) throws  {
        this.mLastGpsFixTime = $l0;
        super.setLastLocation($r1, $l0);
    }
}
