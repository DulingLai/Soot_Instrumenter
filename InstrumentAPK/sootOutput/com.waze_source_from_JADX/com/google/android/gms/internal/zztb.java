package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import com.google.android.gms.common.util.zzr;

/* compiled from: dalvik_source_com.waze.apk */
public class zztb {
    protected final Context mContext;

    public zztb(Context $r1) throws  {
        this.mContext = $r1;
    }

    public ApplicationInfo getApplicationInfo(String $r1, int $i0) throws NameNotFoundException {
        return this.mContext.getPackageManager().getApplicationInfo($r1, $i0);
    }

    public PackageInfo getPackageInfo(String $r1, int $i0) throws NameNotFoundException {
        return this.mContext.getPackageManager().getPackageInfo($r1, $i0);
    }

    @TargetApi(19)
    public boolean zzg(int $i0, String $r1) throws  {
        if (zzr.zzazh()) {
            try {
                ((AppOpsManager) this.mContext.getSystemService("appops")).checkPackage($i0, $r1);
                return true;
            } catch (SecurityException e) {
                return false;
            }
        }
        String[] $r6 = this.mContext.getPackageManager().getPackagesForUid($i0);
        if ($r1 == null) {
            return false;
        }
        if ($r6 == null) {
            return false;
        }
        for (String $r7 : $r6) {
            if ($r1.equals($r7)) {
                return true;
            }
        }
        return false;
    }
}
