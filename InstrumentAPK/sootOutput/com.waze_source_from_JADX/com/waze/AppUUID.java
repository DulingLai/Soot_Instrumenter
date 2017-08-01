package com.waze;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.UUID;

public class AppUUID {
    private static final String PREFS_NAME = "com.waze.appuid";
    private static final String PREFS_PROPERTY_UUID = "UUID";

    public static synchronized String getInstallationUUID(Context $r0) throws  {
        String $r3;
        synchronized (AppUUID.class) {
            try {
                SharedPreferences $r1 = $r0.getSharedPreferences(PREFS_NAME, 0);
                String $r2 = $r1.getString(PREFS_PROPERTY_UUID, "");
                $r3 = $r2;
                if ($r2.equals("")) {
                    Logger.d_("AGA DEBUG", "Generating UUID");
                    $r2 = UUID.randomUUID().toString();
                    $r3 = $r2;
                    Editor $r5 = $r1.edit();
                    $r5.putString(PREFS_PROPERTY_UUID, $r2);
                    $r5.commit();
                }
            } catch (Throwable th) {
                Class cls = AppUUID.class;
            }
        }
        return $r3;
    }
}
