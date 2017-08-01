package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.google.android.gms.common.GoogleSignatureVerifier;
import com.google.android.gms.internal.zztc;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzx {
    @TargetApi(19)
    public static boolean zza(Context $r0, int $i0, String $r1) throws  {
        return zztc.zzcl($r0).zzg($i0, $r1);
    }

    public static boolean zzd(Context $r0, int $i0) throws  {
        if (!zza($r0, $i0, "com.google.android.gms")) {
            return false;
        }
        try {
            return GoogleSignatureVerifier.getInstance($r0).zza($r0.getPackageManager(), $r0.getPackageManager().getPackageInfo("com.google.android.gms", 64));
        } catch (NameNotFoundException e) {
            if (!Log.isLoggable("UidVerifier", 3)) {
                return false;
            }
            Log.d("UidVerifier", "Package manager can't find google play services package, defaulting to false");
            return false;
        }
    }
}
