package com.waze.main.navigate;

import java.io.Serializable;

public class LocationData implements Serializable {
    private static final long serialVersionUID = 1;
    public String destinationName;
    public String downloadUrl;
    public String emailLocationUrlPrefix;
    public String locationEta;
    public String locationHash;
    public String locationName;
    public int locationX;
    public int locationY;
    public String mCity = null;
    public String mStreet = null;
    public String mVenueId;
    public String smsLocationUrlPrefix;

    public LocationData(String $r1, String $r2, String $r3, String $r4, String $r5, String $r6, String $r7, String $r8) throws  {
        this.locationName = $r1;
        this.destinationName = $r2;
        this.locationEta = $r3;
        this.smsLocationUrlPrefix = $r4;
        this.emailLocationUrlPrefix = $r5;
        this.downloadUrl = $r6;
        this.locationHash = $r7;
        this.mVenueId = $r8;
    }

    public LocationData(String $r1, String $r2, String $r3, String $r4, String $r5, String $r6, String $r7, int $i0, int $i1, String $r8, String $r9, String $r10) throws  {
        this.locationName = $r1;
        this.destinationName = $r2;
        this.locationEta = $r3;
        this.smsLocationUrlPrefix = $r4;
        this.emailLocationUrlPrefix = $r5;
        this.downloadUrl = $r6;
        this.locationHash = $r7;
        this.locationX = $i0;
        this.locationY = $i1;
        this.mCity = $r8;
        this.mStreet = $r9;
        this.mVenueId = $r10;
    }
}
