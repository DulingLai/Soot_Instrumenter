package com.google.android.gms.auth.api.credentials.internal;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzab;

/* compiled from: dalvik_source_com.waze.apk */
public class zzb {
    public static String zzen(String $r0) throws  {
        boolean $z0 = false;
        zzab.zzb(!TextUtils.isEmpty($r0), (Object) "account type cannot be empty");
        String $r2 = Uri.parse($r0).getScheme();
        if ("http".equalsIgnoreCase($r2) || "https".equalsIgnoreCase($r2)) {
            $z0 = true;
        }
        zzab.zzb($z0, (Object) "Account type must be an http or https URI");
        return $r0;
    }
}
