package com.waze.widget;

import com.facebook.appevents.AppEventsConstants;
import com.waze.config.WazePreferences;

public class WazeAppWidgetPreferences {
    public static Boolean RoutingServerAuthenticationNeeded() {
        String value = WazePreferences.getProperty("Widget.Authentication", "no");
        boolean z = value != null && value.equalsIgnoreCase("yes");
        return Boolean.valueOf(z);
    }

    public static long AllowRefreshTimer() {
        Long defaultTimer = Long.valueOf(10);
        String value = WazePreferences.getProperty("Widget.Allow Refresh Timer", defaultTimer.toString());
        if (value == null) {
            return defaultTimer.longValue();
        }
        return (Long.parseLong(value) * 60) * 1000;
    }

    public static String getRoutingServerUrl() {
        return WazePreferences.getProperty("Widget.Routing Server URL", "");
    }

    public static Boolean debugEnabled() {
        return Boolean.valueOf(WazePreferences.getProperty("General.Log level", AppEventsConstants.EVENT_PARAM_VALUE_YES).equalsIgnoreCase(AppEventsConstants.EVENT_PARAM_VALUE_YES));
    }

    public static String SecuredServerUrl() {
        return WazePreferences.getProperty("Realtime.Web-Service Secured Address");
    }

    public static String SecuredCarpoolServerUrl() {
        return WazePreferences.getProperty("Carpool.Web-Service Secured Address");
    }

    public static String ServerUrl() {
        return WazePreferences.getProperty("Realtime.Web-Service Address");
    }

    public static boolean isWebServiceSecuredEnabled() {
        String value = WazePreferences.getProperty("Realtime.Web-Service Secure Enabled", "yes");
        return value != null && value.equalsIgnoreCase("yes");
    }

    public static int getStartRange() {
        return Integer.parseInt(WazePreferences.getProperty("Widget.Start Range", "-60"));
    }

    public static int getEndRange() {
        return Integer.parseInt(WazePreferences.getProperty("Widget.End Range", "60"));
    }
}
