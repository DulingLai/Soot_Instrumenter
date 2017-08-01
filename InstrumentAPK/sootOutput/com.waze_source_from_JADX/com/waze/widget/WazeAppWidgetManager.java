package com.waze.widget;

import android.content.Context;

public class WazeAppWidgetManager {
    private static WazeAppWidgetManager mInstance = null;
    private Context mContext = null;

    public static WazeAppWidgetManager getInstance() {
        return mInstance;
    }

    public static WazeAppWidgetManager create(Context aContext) {
        if (mInstance == null) {
            mInstance = new WazeAppWidgetManager(aContext);
        }
        return mInstance;
    }

    public static void refreshHandler(Context aContext) {
        WazeAppWidgetLog.m45d("refreshHandler");
        WidgetManager.RouteRequest();
    }

    public void RouteRequestCallback(int aStatusCode, String aErrDesc, String aDestDescription, int aTimeToDest) {
    }

    public static void setRequestInProcess(boolean aValue) {
    }

    public static void shutDownApp() {
    }

    public void RequestRefresh() {
        WazeAppWidgetService.requestRefresh(this.mContext);
    }

    private WazeAppWidgetManager(Context aContext) {
        this.mContext = aContext;
    }
}
