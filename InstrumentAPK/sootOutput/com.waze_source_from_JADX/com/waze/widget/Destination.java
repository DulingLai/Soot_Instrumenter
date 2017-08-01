package com.waze.widget;

import android.location.Location;

public class Destination {
    private Location mDestLocation;
    private String mDestName;
    private DestinationType mDestType;

    public Destination(DestinationType type, String name, Location location) {
        this.mDestType = type;
        this.mDestName = name;
        this.mDestLocation = location;
    }

    public String getName() {
        return this.mDestName;
    }

    public DestinationType getType() {
        return this.mDestType;
    }

    public Location getLocation() {
        return this.mDestLocation;
    }

    public void setName(String name) {
        this.mDestName = name;
    }

    public void setType(DestinationType type) {
        this.mDestType = type;
    }

    public void setLocation(Location location) {
        this.mDestLocation = location;
    }
}
