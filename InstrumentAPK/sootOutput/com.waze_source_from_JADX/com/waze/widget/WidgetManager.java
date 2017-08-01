package com.waze.widget;

import android.app.Service;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import com.abaltatech.mcp.weblink.core.WLTypes;
import com.waze.config.WazeHistory;
import com.waze.config.WazeLang;
import com.waze.config.WazePreferences;
import com.waze.config.WazeUserPreferences;
import com.waze.widget.routing.RouteScore;
import com.waze.widget.routing.RouteScoreType;
import com.waze.widget.routing.RoutingManager;
import com.waze.widget.routing.RoutingResponse;
import com.waze.widget.rt.RealTimeManager;

public class WidgetManager {
    private static Destination dest;
    static LocationListener mGpslocListener = null;
    private static Handler mHandler = new Handler();
    static LocationManager mLocationManager;
    static LocationListener mNetlocListener = null;
    private static Service mService;

    public static void init(Service service) {
        mService = service;
        mLocationManager = (LocationManager) service.getSystemService(WLTypes.VEHICLEDATA_ATTRIBUTE_LOCATION);
        loadWazeConfig();
    }

    public static void loadWazeConfig() {
        WazeAppWidgetLog.m45d("loadWazeConfig");
        WazePreferences.load();
        WazeUserPreferences.load();
        WazeHistory.load(mService);
        WazeLang.load(WazeUserPreferences.getProperty("System.Language"));
    }

    public static Boolean hasHomeOrWork() {
        loadWazeConfig();
        boolean z = (WazeHistory.getEntry(WazeLang.getLang("Home")) == null && WazeHistory.getEntry(WazeLang.getLang("Work")) == null) ? false : true;
        return Boolean.valueOf(z);
    }

    public static void executeRequest(Location from) {
        loadWazeConfig();
        if (from == null) {
            WazeAppWidgetService.setState(262144, new StatusData("No Location", -1, RouteScoreType.NONE, null));
            WazeAppWidgetLog.m47w("last Known Location is null");
            return;
        }
        dest = DestinationSelector.getDestination(from);
        WazeAppWidgetLog.m45d("DestinationSelector selected " + dest.getName());
        if (dest.getType() == DestinationType.NA) {
            WazeAppWidgetService.setState(524288, new StatusData("No Destination", -1, RouteScoreType.NONE, null));
        } else if (dest.getType() != DestinationType.NONE) {
            if (WazeAppWidgetPreferences.RoutingServerAuthenticationNeeded().booleanValue()) {
                RealTimeManager.getInstance().authenticate();
            }
            new RoutingManager().RoutingRequest(from, dest.getLocation());
        }
    }

    public static void executeResponse(final RoutingResponse rrsp) {
        mHandler.post(new Runnable() {
            public void run() {
                if (rrsp == null) {
                    WazeAppWidgetService.setState(1048576, new StatusData(WidgetManager.dest.getName(), -1, RouteScoreType.NONE, null));
                    return;
                }
                WazeAppWidgetService.setState(1, new StatusData(WidgetManager.dest.getName(), rrsp.getTime() / 60, RouteScore.getScore(rrsp.getTime(), rrsp.getAveragetTime()), rrsp));
            }
        });
    }

    public static void onLocation(Location loc) {
        if (mGpslocListener != null) {
            mLocationManager.removeUpdates(mGpslocListener);
            mGpslocListener = null;
        }
        if (mNetlocListener != null) {
            mLocationManager.removeUpdates(mNetlocListener);
            mNetlocListener = null;
        }
        WazeAppWidgetService.stopRefreshMonitor();
        executeRequest(loc);
    }

    public static void RouteRequest() {
        if (ContextCompat.checkSelfPermission(mService.getApplication().getApplicationContext(), "android.permission.ACCESS_FINE_LOCATION") == 0) {
            WazeAppWidgetLog.m45d("RouteRequest...");
            loadWazeConfig();
            if (!RealTimeManager.getInstance().hasUserName().booleanValue()) {
                WazeAppWidgetLog.m46e("No valide user credentials found");
                WazeAppWidgetService.setState(524288, new StatusData("No Destination", -1, RouteScoreType.NONE, null));
                WazeAppWidgetService.stopRefreshMonitor();
            } else if (mService == null) {
                WazeAppWidgetLog.m47w("service is null");
                WazeAppWidgetService.setState(262144, new StatusData("No Location", -1, RouteScoreType.NONE, null));
                WazeAppWidgetService.stopRefreshMonitor();
            } else {
                if (mLocationManager == null) {
                    mLocationManager = (LocationManager) mService.getSystemService(WLTypes.VEHICLEDATA_ATTRIBUTE_LOCATION);
                }
                if (mLocationManager == null) {
                    WazeAppWidgetService.setState(262144, new StatusData("No Location", -1, RouteScoreType.NONE, null));
                    WazeAppWidgetLog.m47w("locationManager is null");
                    WazeAppWidgetService.stopRefreshMonitor();
                    return;
                }
                Location lastKnownLocation = mLocationManager.getLastKnownLocation("network");
                if (lastKnownLocation != null) {
                    executeRequest(lastKnownLocation);
                    WazeAppWidgetService.stopRefreshMonitor();
                } else if (mGpslocListener == null) {
                    WazeAppWidgetLog.m45d("lastKnowLocation is null, activating GPS");
                    if (mGpslocListener == null) {
                        if (mLocationManager.isProviderEnabled("gps")) {
                            mGpslocListener = new WazeAppWidgetLocationListener();
                            mLocationManager.requestLocationUpdates("gps", 0, 0.0f, mGpslocListener);
                        } else {
                            WazeAppWidgetLog.m45d("GPS_PROVIDER is disabled. Not registring loction listener");
                        }
                    }
                    if (mNetlocListener != null) {
                        return;
                    }
                    if (mLocationManager.isProviderEnabled("network")) {
                        mNetlocListener = new WazeAppWidgetLocationListener();
                        mLocationManager.requestLocationUpdates("network", 0, 0.0f, mNetlocListener);
                        return;
                    }
                    WazeAppWidgetLog.m45d("NETWORK_PROVIDER is disabled. Not registring loction listener");
                } else {
                    WazeAppWidgetLog.m45d("lastKnowLocation is null, GPS already activated");
                }
            }
        }
    }
}
