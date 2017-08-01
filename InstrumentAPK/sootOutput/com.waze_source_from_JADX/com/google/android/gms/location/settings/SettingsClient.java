package com.google.android.gms.location.settings;

import android.accounts.Account;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/* compiled from: dalvik_source_com.waze.apk */
public class SettingsClient {
    @Deprecated
    public static final String LOCATION_HISTORY_ACTION = "com.google.android.gms.location.settings.LOCATION_HISTORY";

    public static Intent getLocationHistoryIntent(Account $r0) throws  {
        Intent $r1 = new Intent(LOCATION_HISTORY_ACTION);
        if ($r0 == null) {
            return $r1;
        }
        $r1.putExtra("account", $r0);
        return $r1;
    }

    public static void launchGoogleLocationSettings(Context $r0) throws  {
        zzz($r0, "com.google.android.gms.location.settings.GOOGLE_LOCATION_SETTINGS");
    }

    private static void zzz(Context $r0, String $r1) throws  {
        Intent $r2 = new Intent($r1);
        $r2.setFlags(268435456);
        $r2.setPackage("com.google.android.gms");
        try {
            $r0.startActivity($r2);
        } catch (ActivityNotFoundException e) {
            $r1 = String.valueOf($r2);
            Log.e("GCoreLocationSettings", new StringBuilder(String.valueOf($r1).length() + 40).append("Problem while starting settings activity").append($r1).toString());
        }
    }
}
