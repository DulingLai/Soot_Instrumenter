package com.waze.widget.routing;

import android.location.Location;
import com.waze.analytics.AnalyticsEvents;
import com.waze.widget.WazeAppWidgetLog;
import com.waze.widget.WazeAppWidgetPreferences;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class RoutingRequest {
    private static int fromRange = -1;
    private static int toRange = -1;
    private Location mFrom;
    private String mKey;
    private RoutingType mRouteType;
    private String mSession;
    private Location mTo;
    private Map<RoutingOption, Boolean> options;

    public RoutingRequest(Location from, Location to, RoutingType type, String sessionId, String key) {
        this.mFrom = from;
        this.mTo = to;
        this.mRouteType = type;
        this.mSession = sessionId;
        this.mKey = key;
        fromRange = WazeAppWidgetPreferences.getStartRange();
        toRange = WazeAppWidgetPreferences.getEndRange();
    }

    public void setRouteType(RoutingType type) {
        this.mRouteType = type;
    }

    public void addOption(RoutingOption key, boolean value) {
        if (this.options == null) {
            this.options = new HashMap();
        }
        this.options.put(key, Boolean.valueOf(value));
    }

    public static int getNumberOfRecords() {
        if (fromRange == -1) {
            fromRange = WazeAppWidgetPreferences.getStartRange();
        }
        if (toRange == -1) {
            toRange = WazeAppWidgetPreferences.getEndRange();
        }
        return (Math.abs(fromRange) / 10) + (Math.abs(toRange) / 10);
    }

    public static int getNowLocation() {
        if (fromRange == -1) {
            fromRange = WazeAppWidgetPreferences.getEndRange();
        }
        return Math.abs(fromRange) / 10;
    }

    public String buildCmd() {
        String str = null;
        if (this.mTo == null) {
            WazeAppWidgetLog.m46e("RoutingRequest.buildCmd [mTo is null]");
        } else if (this.mFrom == null) {
            WazeAppWidgetLog.m46e("RoutingRequest.buildCmd [mFrom is null]");
        } else {
            str = "?from=x:" + this.mFrom.getLongitude() + "+y:" + this.mFrom.getLatitude() + "+bd:true" + "&to=x:" + this.mTo.getLongitude() + "+y:" + this.mTo.getLatitude() + "+bd:true" + "&type=" + this.mRouteType.name() + "&returnGeometries=false" + "&returnInstructions=false" + "&timeout=60000" + "&nPaths=3" + "&returnJSON=true" + "&graph=" + fromRange + "," + toRange;
            if (this.mSession != null) {
                str = str + "&session=" + this.mSession;
            }
            if (this.mKey != null) {
                str = str + "&token=" + this.mKey;
            }
            if (this.options != null) {
                str = str + "&options=";
                for (Entry<RoutingOption, Boolean> e : this.options.entrySet()) {
                    str = str + ((RoutingOption) e.getKey()).name() + ":";
                    if (((Boolean) e.getValue()).booleanValue()) {
                        str = str + AnalyticsEvents.ANALYTICS_EVENT_VALUE_T;
                    } else {
                        str = str + AnalyticsEvents.ANALYTICS_EVENT_VALUE_F;
                    }
                    str = str + ",";
                }
            }
        }
        return str;
    }

    public String getOriginAndDest() {
        if (this.mTo == null) {
            WazeAppWidgetLog.m46e("RoutingRequest.getOriginAndDest [mTo is null]");
            return null;
        } else if (this.mFrom != null) {
            return "?from=x:" + this.mFrom.getLongitude() + "+y:" + this.mFrom.getLatitude() + "&to=x:" + this.mTo.getLongitude() + "+y:" + this.mTo.getLatitude();
        } else {
            WazeAppWidgetLog.m46e("RoutingRequest.getOriginAndDest [mFrom is null]");
            return null;
        }
    }
}
