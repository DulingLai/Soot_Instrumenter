package com.waze.widget;

import android.location.Location;
import com.waze.config.WazeHistory;
import com.waze.config.WazeLang;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DestinationSelector {
    public static Destination getDestination(Location currentLocation) {
        Calendar calendar = new GregorianCalendar();
        Destination dest = new Destination(DestinationType.NONE, null, null);
        String nameHome = "";
        String nameWork = "";
        if (currentLocation == null) {
            WazeAppWidgetLog.m45d("currentLocation is null");
        } else {
            nameHome = WazeLang.getLang("Home");
            Location locationHome = WazeHistory.getEntry(nameHome);
            if (locationHome == null) {
                nameHome = WazeLang.getLang("home");
                locationHome = WazeHistory.getEntry(nameHome);
            }
            if (locationHome == null) {
                nameHome = "Home";
                locationHome = WazeHistory.getEntry(nameHome);
            }
            if (locationHome == null) {
                nameHome = "home";
                locationHome = WazeHistory.getEntry(nameHome);
            }
            nameWork = WazeLang.getLang("Work");
            Location locationWork = WazeHistory.getEntry(nameWork);
            if (locationWork == null) {
                nameWork = WazeLang.getLang("work");
                locationWork = WazeHistory.getEntry(nameWork);
            }
            if (locationWork == null) {
                nameWork = "Work";
                locationWork = WazeHistory.getEntry(nameWork);
            }
            if (locationWork == null) {
                nameWork = "work";
                locationWork = WazeHistory.getEntry(nameWork);
            }
            if (locationHome == null && locationWork == null) {
                WazeAppWidgetLog.m45d("No Home & Work");
                dest.setType(DestinationType.NA);
            } else if (calendar.get(9) == 0) {
                if (locationWork == null) {
                    WazeAppWidgetLog.m45d("No Work");
                    dest.setType(DestinationType.NA);
                } else if (locationWork.distanceTo(currentLocation) > 1000.0f) {
                    WazeAppWidgetLog.m45d("getDestination - selecting Work");
                    dest.setType(DestinationType.WORK);
                    dest.setLocation(locationWork);
                    dest.setName(nameWork);
                } else if (locationHome != null) {
                    WazeAppWidgetLog.m45d("Too Close to work selecting home");
                    dest.setType(DestinationType.HOME);
                    dest.setLocation(locationHome);
                    dest.setName(nameHome);
                } else if (locationHome == null) {
                    WazeAppWidgetLog.m45d("No Home");
                    dest.setType(DestinationType.NA);
                }
            } else if (locationHome == null) {
                WazeAppWidgetLog.m45d("No Home");
                dest.setType(DestinationType.NA);
            } else if (locationHome.distanceTo(currentLocation) > 1000.0f) {
                dest.setType(DestinationType.HOME);
                dest.setLocation(locationHome);
                dest.setName(nameHome);
            } else if (locationWork != null) {
                dest.setType(DestinationType.WORK);
                dest.setLocation(locationWork);
                dest.setName(nameWork);
            }
        }
        return dest;
    }
}
