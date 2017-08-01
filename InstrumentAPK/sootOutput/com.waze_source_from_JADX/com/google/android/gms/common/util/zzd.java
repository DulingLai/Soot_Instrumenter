package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.android.gms.internal.zztc;

/* compiled from: dalvik_source_com.waze.apk */
public class zzd {
    public static int zza(PackageInfo $r0) throws  {
        if ($r0 == null) {
            return -1;
        }
        if ($r0.applicationInfo == null) {
            return -1;
        }
        Bundle $r2 = $r0.applicationInfo.metaData;
        return $r2 != null ? $r2.getInt("com.google.android.gms.version", -1) : -1;
    }

    public static int zzp(Context $r0, String $r1) throws  {
        return zza(zzq($r0, $r1));
    }

    @Nullable
    public static PackageInfo zzq(Context $r0, String $r1) throws  {
        try {
            return zztc.zzcl($r0).getPackageInfo($r1, 128);
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    @TargetApi(12)
    public static boolean zzr(Context $r0, String $r1) throws  {
        if (!zzr.zzazb()) {
            return false;
        }
        if ("com.google.android.gms".equals($r1) && zzzz()) {
            return false;
        }
        try {
            return (zztc.zzcl($r0).getApplicationInfo($r1, 0).flags & 2097152) != 0;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static boolean zzzz() throws  {
        return false;
    }
}
