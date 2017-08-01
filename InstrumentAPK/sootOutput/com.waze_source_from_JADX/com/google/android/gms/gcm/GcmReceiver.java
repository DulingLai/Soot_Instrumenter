package com.google.android.gms.gcm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build.VERSION;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Base64;
import android.util.Log;
import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import com.waze.strings.DisplayStrings;

/* compiled from: dalvik_source_com.waze.apk */
public class GcmReceiver extends WakefulBroadcastReceiver {
    private static String aqP = "gcm.googleapis.com/refresh";

    private void zzg(Context $r1, Intent $r2) throws  {
        if (isOrderedBroadcast()) {
            setResultCode(DisplayStrings.DS_NO_NETWORK_CONNECTION__UNABLE_TO_REPORT);
        }
        zzh($r1, $r2);
        try {
            ComponentName $r3;
            if ($r1.checkCallingOrSelfPermission("android.permission.WAKE_LOCK") == 0) {
                $r3 = WakefulBroadcastReceiver.startWakefulService($r1, $r2);
            } else {
                $r3 = $r1.startService($r2);
                Log.d("GcmReceiver", "Missing wake lock permission, service start may be delayed");
            }
            if ($r3 == null) {
                Log.e("GcmReceiver", "Error while delivering the message: ServiceIntent not found.");
                if (isOrderedBroadcast()) {
                    setResultCode(404);
                }
            } else if (isOrderedBroadcast()) {
                setResultCode(-1);
            }
        } catch (SecurityException $r4) {
            Log.e("GcmReceiver", "Error while delivering the message to the serviceIntent", $r4);
            if (isOrderedBroadcast()) {
                setResultCode(401);
            }
        }
    }

    private void zzh(Context $r1, Intent $r2) throws  {
        ResolveInfo $r4 = $r1.getPackageManager().resolveService($r2, 0);
        if ($r4 == null || $r4.serviceInfo == null) {
            Log.e("GcmReceiver", "Failed to resolve target intent service, skipping classname enforcement");
            return;
        }
        ServiceInfo $r5 = $r4.serviceInfo;
        if (!$r1.getPackageName().equals($r5.packageName) || $r5.name == null) {
            String $r6 = String.valueOf($r5.packageName);
            String $r7 = String.valueOf($r5.name);
            Log.e("GcmReceiver", new StringBuilder((String.valueOf($r6).length() + 94) + String.valueOf($r7).length()).append("Error resolving target intent service, skipping classname enforcement. Resolved service was: ").append($r6).append("/").append($r7).toString());
            return;
        }
        $r6 = $r5.name;
        if ($r6.startsWith(FileUploadSession.SEPARATOR)) {
            $r7 = String.valueOf($r1.getPackageName());
            $r6 = String.valueOf($r6);
            $r6 = $r6.length() != 0 ? $r7.concat($r6) : new String($r7);
        }
        if (Log.isLoggable("GcmReceiver", 3)) {
            $r7 = "Restricting intent to a specific service: ";
            String $r9 = String.valueOf($r6);
            Log.d("GcmReceiver", $r9.length() != 0 ? $r7.concat($r9) : new String("Restricting intent to a specific service: "));
        }
        $r2.setClassName($r1.getPackageName(), $r6);
    }

    public void onReceive(Context $r1, Intent $r2) throws  {
        $r2.setComponent(null);
        $r2.setPackage($r1.getPackageName());
        if (VERSION.SDK_INT <= 18) {
            $r2.removeCategory($r1.getPackageName());
        }
        String $r3 = $r2.getStringExtra("from");
        if ("com.google.android.c2dm.intent.REGISTRATION".equals($r2.getAction()) || "google.com/iid".equals($r3) || aqP.equals($r3)) {
            $r2.setAction("com.google.android.gms.iid.InstanceID");
        }
        $r3 = $r2.getStringExtra("gcm.rawData64");
        if ($r3 != null) {
            $r2.putExtra("rawData", Base64.decode($r3, 0));
            $r2.removeExtra("gcm.rawData64");
        }
        if ("com.google.android.c2dm.intent.RECEIVE".equals($r2.getAction())) {
            onStartWakefulService($r1, $r2);
        } else {
            zzg($r1, $r2);
        }
        if (isOrderedBroadcast() && getResultCode() == 0) {
            setResultCode(-1);
        }
    }

    public void onStartWakefulService(Context $r1, Intent $r2) throws  {
        zzg($r1, $r2);
    }
}
