package com.waze.widget;

import com.waze.widget.routing.RouteScoreType;
import com.waze.widget.routing.RoutingResponse;

public class StatusData {
    public static final int STATUS_DATA_OK = 4;
    public static final int STATUS_DRIVE_REQUEST = 128;
    public static final int STATUS_ERR_GENERAL = 65536;
    public static final int STATUS_ERR_NO_DESTINATION = 524288;
    public static final int STATUS_ERR_NO_LOCATION = 262144;
    public static final int STATUS_ERR_NO_LOGIN = 131072;
    public static final int STATUS_ERR_REFRESH_TIMEOUT = 2097152;
    public static final int STATUS_ERR_ROUTE_SERVER = 1048576;
    public static final int STATUS_MASK_ERROR = 65536;
    public static final int STATUS_NEW_WIDGET = 8;
    public static final int STATUS_REFRESH_REQUEST = 32;
    public static final int STATUS_REFRESH_REQUEST_INFO = 64;
    public static final int STATUS_REFRESH_TEST_TRUE = 16;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_UNDEFINED = 2;
    private String mDestDescription;
    private RoutingResponse mRoutingResponse;
    private RouteScoreType mScore;
    private long mTimeStamp;
    private int mTimeToDest;

    public StatusData() {
        this.mDestDescription = "Home";
        this.mTimeToDest = 0;
        this.mTimeStamp = 0;
        this.mScore = RouteScoreType.NONE;
    }

    public StatusData(String aDestDescription, int aTimeToDest, RouteScoreType score, RoutingResponse routingRsp) {
        this.mDestDescription = "Home";
        this.mTimeToDest = 0;
        this.mTimeStamp = 0;
        this.mScore = RouteScoreType.NONE;
        this.mTimeStamp = System.currentTimeMillis();
        this.mDestDescription = aDestDescription;
        this.mTimeToDest = aTimeToDest;
        this.mScore = score;
        this.mRoutingResponse = routingRsp;
    }

    public StatusData(String aDestDescription, int aTimeToDest, long aTimeStamp) {
        this.mDestDescription = "Home";
        this.mTimeToDest = 0;
        this.mTimeStamp = 0;
        this.mScore = RouteScoreType.NONE;
        this.mTimeStamp = aTimeStamp;
        this.mDestDescription = aDestDescription;
        this.mTimeToDest = aTimeToDest;
    }

    public StatusData(StatusData aStatusData) {
        this.mDestDescription = "Home";
        this.mTimeToDest = 0;
        this.mTimeStamp = 0;
        this.mScore = RouteScoreType.NONE;
        copy(aStatusData);
    }

    public void copy(StatusData aData) {
        if (aData != null) {
            this.mDestDescription = aData.mDestDescription;
            this.mTimeToDest = aData.mTimeToDest;
            this.mTimeStamp = aData.mTimeStamp;
            this.mScore = aData.mScore;
            this.mRoutingResponse = aData.mRoutingResponse;
        }
    }

    public RoutingResponse getRoutingRespnse() {
        return this.mRoutingResponse;
    }

    public void setTimeStamp(long aTimeStamp) {
        this.mTimeStamp = aTimeStamp;
    }

    public String destination() {
        return this.mDestDescription;
    }

    public int timeToDest() {
        return this.mTimeToDest;
    }

    public long timeStamp() {
        return this.mTimeStamp;
    }

    public RouteScoreType score() {
        return this.mScore;
    }
}
