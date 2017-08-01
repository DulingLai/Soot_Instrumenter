package com.waze.widget.routing;

import com.waze.config.WazePreferences;
import com.waze.config.WazeUserPreferences;

public class RoutingUserOptions {
    public static boolean avoidPrimaries() {
        return WazeUserPreferences.getProperty("Routing.Avoid primaries", "no").equalsIgnoreCase("yes");
    }

    public static int avoidTrails() {
        String avoidPrimaries = WazeUserPreferences.getProperty("Routing.Avoid trails", "Don't allow");
        if (avoidPrimaries.equalsIgnoreCase("Allow")) {
            return 1;
        }
        if (avoidPrimaries.equalsIgnoreCase("Don't allow")) {
            return 0;
        }
        return 2;
    }

    public static boolean preferSameStreet() {
        return WazePreferences.getProperty("Routing.Prefer same street", "no").equalsIgnoreCase("yes");
    }

    public static boolean userTraffic() {
        return true;
    }

    public static boolean allowUnkownDirections() {
        return WazeUserPreferences.getProperty("Routing.Allow unknown directions", "yes").equalsIgnoreCase("yes");
    }

    public static boolean avoidTolls() {
        return WazeUserPreferences.getProperty("Routing.Avoid tolls", "no").equalsIgnoreCase("yes");
    }

    public static boolean avoidDangerousTurns() {
        return WazeUserPreferences.getProperty("Routing.Avoid dangerous turns", "no").equalsIgnoreCase("yes");
    }

    public static boolean allowVehicleTypes() {
        return WazeUserPreferences.getProperty("Routing.Allow vehicle types", "no").equalsIgnoreCase("yes");
    }

    public static boolean preferUnkownDirections() {
        return WazeUserPreferences.getProperty("Routing.Prefer unknown directions", "no").equalsIgnoreCase("yes");
    }

    public static boolean avoidPalestinianRoads() {
        return WazeUserPreferences.getProperty("Routing.Avoid Palestinian Roads", "yes").equalsIgnoreCase("yes");
    }

    public static RoutingType routeType() {
        if (WazeUserPreferences.getProperty("Routing.Type", "Fastest").equalsIgnoreCase("Fastest")) {
            return RoutingType.HISTORIC_TIME;
        }
        return RoutingType.DISTANCE;
    }
}
