package com.waze;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.util.Log;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;

public final class ConnEventReceiver extends BroadcastReceiver {
    static boolean bIsFirstTime = true;

    public void onReceive(Context aContext, Intent $r2) throws  {
        Boolean $r3 = Boolean.valueOf(!$r2.getBooleanExtra("noConnectivity", false));
        NetworkInfo $r5 = (NetworkInfo) $r2.getParcelableExtra("networkInfo");
        if ($r5 != null) {
            Log.i(Logger.TAG("ConnEventReceiver"), "Received event: " + $r2.getAction() + ". Connectivity: " + $r3 + ". Type: " + $r5.getTypeName() + " ( " + $r5.getType() + " )" + ". State: " + $r5.getState().toString() + ". Connected: " + $r5.isConnected());
            if (AppService.IsAppRunning()) {
                NativeManager.getInstance().SetNetInfo($r5);
                if (!$r5.isConnected()) {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_NETWORK_STATE, "MODE|MCC|MNC", "NA||");
                } else if ($r5.getType() == 0) {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_NETWORK_STATE, "MODE|MCC|MNC", "CELL|" + AppService.getAppResources().getConfiguration().mcc + "|" + AppService.getAppResources().getConfiguration().mnc);
                } else if ($r5.getType() == 1) {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_NETWORK_STATE, "MODE|MCC|MNC", "WIFI||");
                } else if ($r5.getType() == 6) {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_NETWORK_STATE, "MODE|MCC|MNC", "WIMAX||");
                }
            }
            if (AppService.IsAppRunning() && !bIsFirstTime && ($r5.getType() == 0 || $r5.getType() == 1 || $r5.getType() == 6)) {
                AppService.getNativeManager().ConnectivityChanged($r5.isConnected(), $r5.getType(), $r5.getTypeName());
            } else {
                bIsFirstTime = false;
            }
        }
    }
}
