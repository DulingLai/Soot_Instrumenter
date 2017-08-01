package com.waze.autocomplete;

public class PlaceData {
    public String mAdsAdvilJson;
    public boolean mAdsIsServer;
    public String mAdsUrl;
    public boolean mIsAds;
    public int mLocX;
    public int mLocY;
    public int mLocalIndex;
    public String mReference;
    public String mResponse;
    public String mSearchTerm;
    public String mSecondaryTitle;
    public String mTitle;
    public String mVenueContext;
    public String mVenueId;
    public boolean mWasAdReported;
    public int mfeature;

    public PlaceData() throws  {
        this.mLocX = -1;
        this.mLocY = -1;
        this.mVenueId = null;
        this.mReference = null;
        this.mLocalIndex = -1;
        this.mAdsUrl = null;
        this.mTitle = null;
        this.mSecondaryTitle = null;
        this.mIsAds = false;
        this.mWasAdReported = false;
        this.mfeature = 0;
        this.mResponse = null;
        this.mVenueContext = null;
        this.mAdsAdvilJson = null;
        this.mAdsIsServer = false;
    }

    public PlaceData(String $r1, String $r2, String $r3, String $r4, String $r5) throws  {
        this.mLocX = -1;
        this.mLocY = -1;
        this.mVenueId = $r1;
        this.mTitle = $r2;
        this.mSecondaryTitle = $r3 + " " + $r4;
        this.mReference = null;
        this.mIsAds = true;
        this.mWasAdReported = false;
        this.mLocalIndex = -1;
        this.mAdsUrl = $r5;
        this.mfeature = 0;
        this.mResponse = null;
        this.mVenueContext = null;
        this.mAdsAdvilJson = null;
        this.mAdsIsServer = false;
    }

    public boolean hasLocation() throws  {
        return this.mLocX > 0 && this.mLocY > 0;
    }
}
