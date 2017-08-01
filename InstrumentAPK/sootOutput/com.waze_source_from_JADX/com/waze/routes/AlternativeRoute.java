package com.waze.routes;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class AlternativeRoute implements Parcelable {
    public static final Creator<AlternativeRoute> CREATOR = new C25641();
    public boolean closure;
    public String description;
    public int distance;
    public int distanceRound;
    public int distanceTenths;
    public String distanceUnits;
    public boolean ferry;
    public int id;
    public boolean preferred;
    public int routeColor;
    public int time;
    public boolean toll;

    static class C25641 implements Creator<AlternativeRoute> {
        C25641() {
        }

        public AlternativeRoute createFromParcel(Parcel in) {
            return new AlternativeRoute(in);
        }

        public AlternativeRoute[] newArray(int size) {
            return new AlternativeRoute[size];
        }
    }

    public AlternativeRoute(int id, int distance, int time, String description, int distanceRound, int distanceTenths, String distanceUnits, boolean preferred, boolean toll, boolean ferry, int routeColor) {
        this.id = id;
        this.distance = distance;
        this.time = time;
        this.description = description;
        this.distanceRound = distanceRound;
        this.distanceTenths = distanceTenths;
        this.distanceUnits = distanceUnits;
        this.preferred = preferred;
        this.toll = toll;
        this.ferry = ferry;
        this.routeColor = routeColor;
        this.closure = false;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i;
        int i2 = 1;
        dest.writeInt(this.distance);
        dest.writeInt(this.time);
        dest.writeString(this.description);
        dest.writeInt(this.distanceRound);
        dest.writeInt(this.distanceTenths);
        dest.writeString(this.distanceUnits);
        dest.writeInt(this.id);
        if (this.preferred) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeInt(i);
        if (this.toll) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeInt(i);
        if (this.closure) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeInt(i);
        if (!this.ferry) {
            i2 = 0;
        }
        dest.writeInt(i2);
    }

    public AlternativeRoute(Parcel in) {
        boolean z;
        boolean z2 = true;
        this.distance = in.readInt();
        this.time = in.readInt();
        this.description = in.readString();
        this.distanceRound = in.readInt();
        this.distanceTenths = in.readInt();
        this.distanceUnits = in.readString();
        this.id = in.readInt();
        if (in.readInt() != 0) {
            z = true;
        } else {
            z = false;
        }
        this.preferred = z;
        if (in.readInt() != 0) {
            z = true;
        } else {
            z = false;
        }
        this.toll = z;
        if (in.readInt() != 0) {
            z = true;
        } else {
            z = false;
        }
        this.closure = z;
        if (in.readInt() == 0) {
            z2 = false;
        }
        this.ferry = z2;
    }
}
