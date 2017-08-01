package com.abaltatech.mcp.weblink.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class AppUtils {
    private static final String TAG = "WLAppUtils";

    public static boolean canActivateApp(String $r0, Context $r1) throws  {
        if ($r0 == null) {
            return false;
        }
        if ($r0.isEmpty()) {
            return false;
        }
        return $r1.getPackageManager().queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse($r0)), 0).size() > 0;
    }

    public static boolean canLaunchApp(String $r0, Context $r1) throws  {
        if ($r0 == null) {
            return false;
        }
        if ($r0.isEmpty()) {
            return false;
        }
        PackageManager $r2 = $r1.getPackageManager();
        Intent $r3 = $r2.getLaunchIntentForPackage($r0);
        Intent $r4 = $r3;
        if ($r3 == null && $r0.endsWith("://")) {
            $r4 = $r2.getLaunchIntentForPackage($r0.substring(0, $r0.length() - 3));
        }
        return $r4 != null;
    }

    public static boolean activateApp(String $r0, Context $r1, boolean $z0) throws  {
        Log.d(TAG, "activateApp: " + $r0);
        if ($r1 == null) {
            return false;
        }
        if ($r0 == null) {
            return false;
        }
        Intent $r5 = $r1.getPackageManager().getLaunchIntentForPackage($r0);
        Intent $r6 = $r5;
        if ($r5 == null && $r0.endsWith("://")) {
            $r6 = $r1.getPackageManager().getLaunchIntentForPackage($r0.substring(0, $r0.length() - 3));
        }
        int $i0;
        if (canActivateApp($r0, $r1)) {
            $r5 = new Intent("android.intent.action.VIEW", Uri.parse($r0));
            $i0 = 196608;
            if ($z0) {
                $i0 = 196608 | 268435456;
            }
            $r5.setFlags($i0);
            try {
                $r1.startActivity($r5);
                Log.d(TAG, "activateApp: Application started - " + $r0);
                return false;
            } catch (Exception $r8) {
                Log.e(TAG, "Failed to activate " + $r0, $r8);
                return false;
            }
        } else if (canLaunchApp($r0, $r1)) {
            $i0 = 196608;
            if ($z0) {
                $i0 = 196608 | 268435456;
            }
            $r6.setFlags($i0);
            try {
                $r1.startActivity($r6);
                Log.d(TAG, "activateApp: Application started - " + $r0);
                return false;
            } catch (Exception $r9) {
                Log.e(TAG, "Failed to activate " + $r0, $r9);
                return false;
            }
        } else {
            Toast.makeText($r1, "Application unavailable - " + $r0, 1).show();
            Log.e(TAG, "activateApp: Application unavailable - " + $r0);
            return false;
        }
    }

    public static final boolean bindService(Context $r0, String $r1, ServiceConnection $r2) throws  {
        Intent $r4 = new Intent($r1);
        ResolveInfo $r7 = $r0.getPackageManager().resolveService($r4, 0);
        if ($r7 == null) {
            return false;
        }
        ServiceInfo $r5 = $r7.serviceInfo;
        $r4.setComponent(new ComponentName($r5.packageName, $r5.name));
        return $r0.bindService($r4, $r2, 1);
    }

    public static String getAppName(Context $r0) throws  {
        PackageManager $r1 = $r0.getPackageManager();
        ApplicationInfo $r2 = null;
        String $r3 = null;
        try {
            $r3 = $r0.getApplicationInfo().packageName;
            $r2 = $r1.getApplicationInfo($r3, 0);
        } catch (NameNotFoundException e) {
        }
        if ($r2 == null || $r2.className == null) {
            return $r3;
        }
        return $r2.className;
    }
}
