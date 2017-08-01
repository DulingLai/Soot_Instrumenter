package com.google.android.apps.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.util.HashMap;

/* compiled from: dalvik_source_com.waze.apk */
public class AnalyticsReceiver extends BroadcastReceiver {
    private static final String INSTALL_ACTION = "com.android.vending.INSTALL_REFERRER";

    static String formatReferrer(String $r0) throws  {
        int $i0;
        String[] $r1 = $r0.split("&");
        HashMap $r2 = new HashMap();
        for (String split : $r1) {
            String[] $r3 = split.split("=");
            if ($r3.length != 2) {
                break;
            }
            $r2.put($r3[0], $r3[1]);
        }
        boolean $z0 = $r2.get("utm_campaign") != null;
        boolean $z1 = $r2.get("utm_medium") != null;
        boolean $z2 = $r2.get("utm_source") != null;
        if ($z0 && $z1 && $z2) {
            $r6 = new String[7][];
            $r6[0] = new String[]{"utmcid", (String) $r2.get("utm_id")};
            $r6[1] = new String[]{"utmcsr", (String) $r2.get("utm_source")};
            $r6[2] = new String[]{"utmgclid", (String) $r2.get("gclid")};
            $r6[3] = new String[]{"utmccn", (String) $r2.get("utm_campaign")};
            $r6[4] = new String[]{"utmcmd", (String) $r2.get("utm_medium")};
            $r6[5] = new String[]{"utmctr", (String) $r2.get("utm_term")};
            $r6[6] = new String[]{"utmcct", (String) $r2.get("utm_content")};
            StringBuilder stringBuilder = new StringBuilder();
            $z0 = true;
            for ($i0 = 0; $i0 < $r6.length; $i0++) {
                if ($r6[$i0][1] != null) {
                    $r0 = $r6[$i0][1].replace("+", "%20").replace(" ", "%20");
                    if ($z0) {
                        $z0 = false;
                    } else {
                        stringBuilder.append("|");
                    }
                    stringBuilder.append($r6[$i0][0]).append("=").append($r0);
                }
            }
            return stringBuilder.toString();
        }
        Log.w(GoogleAnalyticsTracker.LOG_TAG, "Badly formatted referrer missing campaign, name or source");
        return null;
    }

    public void onReceive(Context $r1, Intent $r2) throws  {
        String $r3 = $r2.getStringExtra(QueryParams.REFERRER);
        if (INSTALL_ACTION.equals($r2.getAction()) && $r3 != null) {
            $r3 = formatReferrer($r3);
            if ($r3 != null) {
                new PersistentEventStore(new DataBaseHelper($r1)).setReferrer($r3);
                Log.d(GoogleAnalyticsTracker.LOG_TAG, "Stored referrer:" + $r3);
                return;
            }
            Log.w(GoogleAnalyticsTracker.LOG_TAG, "Badly formatted referrer, ignored");
        }
    }
}
