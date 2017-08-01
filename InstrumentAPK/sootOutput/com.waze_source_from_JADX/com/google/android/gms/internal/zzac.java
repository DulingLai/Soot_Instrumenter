package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.http.AndroidHttpClient;
import android.os.Build.VERSION;
import java.io.File;

/* compiled from: dalvik_source_com.waze.apk */
public class zzac {
    public static zzl zza(Context $r0) throws  {
        return zza($r0, null);
    }

    public static zzl zza(Context $r0, zzy $r2) throws  {
        File $r1 = r15;
        File r15 = new File($r0.getCacheDir(), "volley");
        String $r4 = "volley/0";
        try {
            String $r5 = $r0.getPackageName();
            int $i0 = $r0.getPackageManager().getPackageInfo($r5, 0).versionCode;
            $r4 = new StringBuilder(String.valueOf($r5).length() + 12).append($r5).append("/").append($i0).toString();
        } catch (NameNotFoundException e) {
        }
        if ($r2 == null) {
            if (VERSION.SDK_INT >= 9) {
                $r2 = r0;
                zzz com_google_android_gms_internal_zzz = new zzz();
            } else {
                Object $r22 = r0;
                zzw com_google_android_gms_internal_zzw = new zzw(AndroidHttpClient.newInstance($r4));
            }
        }
        zzl $r11 = r0;
        zzl com_google_android_gms_internal_zzl = new zzl(new zzv($r1), new zzt($r2));
        $r11.start();
        return $r11;
    }
}
