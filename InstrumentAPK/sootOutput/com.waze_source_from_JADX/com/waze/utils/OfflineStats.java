package com.waze.utils;

import android.content.Context;
import com.waze.widget.rt.RealTimeManager;

public class OfflineStats {
    public static void SendStats(Context mContext, String sEventName, String[] sValue) {
        RealTimeManager.getInstance().SendStat(mContext, sEventName, sValue);
    }

    public static void SendStats(Context mContext, String sEventName, String[] sValue, boolean storeIfFailed) {
        RealTimeManager.getInstance().SendStat(mContext, sEventName, sValue, storeIfFailed);
    }

    public static void SendAdsStats(Context mContext, String sEventName) {
        RealTimeManager.getInstance().SendAdsStat(mContext, sEventName);
    }
}
