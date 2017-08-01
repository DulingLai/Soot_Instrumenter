package com.waze.main.navigate;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import com.abaltatech.mcp.weblink.core.WLTypes;
import com.waze.AppService;
import com.waze.GeoFencingService;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsEvents;
import com.waze.messages.QuestionData;
import com.waze.utils.OfflineStats;

public class GeoFencingReceiver extends BroadcastReceiver {
    public static boolean IsEntered = false;

    public void onReceive(Context $r1, Intent $r2) throws  {
        Boolean $r3 = Boolean.valueOf($r2.getBooleanExtra("entering", false));
        String $r5 = $r2.getExtras().getString("TYPE", null);
        if ($r5 != null) {
            if (!AppService.IsAppRunning()) {
                String $r7 = $r2.getExtras().getString("Destination", "");
                String $r8 = $r2.getExtras().getString("VenueID", "");
                double d = ((double) $r2.getExtras().getInt("DEST_LAT", 0)) * 9.999999974752427E-7d;
                double d2 = ((double) $r2.getExtras().getInt("DEST_LON", 0)) * 9.999999974752427E-7d;
                long $l0 = System.currentTimeMillis();
                boolean $z0 = NativeManager.IsAppStarted();
                String[] $r6 = new String[2];
                $r6[0] = "LON|LAT|TIME|DEST_LON|DEST_LAT|DEST_VENUE_ID|VISIBLE|DEST_NAME|TYPE";
                $r6[1] = "0|0|" + $l0 + "|" + d2 + "|" + d + "|" + $r8 + "|" + ($z0 ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_TRUE : AnalyticsEvents.ANALYTICS_EVENT_VALUE_FALSE) + "|" + $r7 + "|" + $r5;
                if ($r5.equals("NEW_USER")) {
                    QuestionData.SetGeoFencingWakeUpFlag($r1, 0);
                } else {
                    QuestionData.SetGeoFencingWakeUpFlag($r1, 1);
                }
                OfflineStats.SendStats($r1, AnalyticsEvents.ANALYTICS_EVENT_ORIGIN_DEPART, $r6, true);
            }
            ((LocationManager) $r1.getSystemService(WLTypes.VEHICLEDATA_ATTRIBUTE_LOCATION)).removeProximityAlert(PendingIntent.getBroadcast($r1, 1, $r2, 0));
        } else if (!$r3.booleanValue()) {
            OfflineStats.SendStats($r1, AnalyticsEvents.ANALYTICS_EVENT_EXIT_RADIUS, new String[]{"TIME", Long.toString(System.currentTimeMillis())});
            OfflineStats.SendAdsStats($r1, "ADS_EXITED_RADIUS");
        } else if (!IsEntered) {
            IsEntered = true;
            OfflineStats.SendStats($r1, AnalyticsEvents.ANALYTICS_EVENT_ENTERED_RADIUS, new String[]{"TIME", Long.toString(System.currentTimeMillis())});
            OfflineStats.SendAdsStats($r1, "ADS_ENTERED_RADIUS");
            GeoFencingService.start($r1);
        }
    }
}
