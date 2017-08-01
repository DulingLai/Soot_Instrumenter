package com.google.android.gms.common;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzo;
import com.google.android.gms.common.util.zzh;

/* compiled from: dalvik_source_com.waze.apk */
public class GoogleApiAvailabilityLight {
    private static final GoogleApiAvailabilityLight BF = new GoogleApiAvailabilityLight();
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = GooglePlayServicesUtilLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;

    GoogleApiAvailabilityLight() throws  {
    }

    public static GoogleApiAvailabilityLight getInstance() throws  {
        return BF;
    }

    private String zzo(@Nullable Context $r1, @Nullable String $r2) throws  {
        StringBuilder $r3 = new StringBuilder();
        $r3.append("gcore_");
        $r3.append(GOOGLE_PLAY_SERVICES_VERSION_CODE);
        $r3.append("-");
        if (!TextUtils.isEmpty($r2)) {
            $r3.append($r2);
        }
        $r3.append("-");
        if ($r1 != null) {
            $r3.append($r1.getPackageName());
        }
        $r3.append("-");
        if ($r1 != null) {
            try {
                $r3.append($r1.getPackageManager().getPackageInfo($r1.getPackageName(), 0).versionCode);
            } catch (NameNotFoundException e) {
            }
        }
        return $r3.toString();
    }

    public int getApkVersion(Context $r1) throws  {
        return GooglePlayServicesUtilLight.getApkVersion($r1);
    }

    public int getClientVersion(Context $r1) throws  {
        return GooglePlayServicesUtilLight.getClientVersion($r1);
    }

    @Nullable
    @Deprecated
    public Intent getErrorResolutionIntent(@Deprecated int $i0) throws  {
        return getErrorResolutionIntent(null, $i0, null);
    }

    @Nullable
    public Intent getErrorResolutionIntent(Context $r1, int $i0, @Nullable String $r2) throws  {
        switch ($i0) {
            case 1:
            case 2:
                return zzo.zzak("com.google.android.gms", zzo($r1, $r2));
            case 3:
                return zzo.zzgv("com.google.android.gms");
            case 42:
                return zzo.zzawx();
            default:
                return null;
        }
    }

    @Nullable
    public PendingIntent getErrorResolutionPendingIntent(Context $r1, int $i0, int $i1) throws  {
        return getErrorResolutionPendingIntent($r1, $i0, $i1, null);
    }

    @Nullable
    public PendingIntent getErrorResolutionPendingIntent(Context $r1, int $i1, int $i0, @Nullable String $r2) throws  {
        if (zzh.zzcf($r1) && $i1 == 2) {
            $i1 = 42;
        }
        Intent $r3 = getErrorResolutionIntent($r1, $i1, $r2);
        return $r3 == null ? null : PendingIntent.getActivity($r1, $i0, $r3, 268435456);
    }

    public String getErrorString(int $i0) throws  {
        return GooglePlayServicesUtilLight.getErrorString($i0);
    }

    @Nullable
    public String getOpenSourceSoftwareLicenseInfo(Context $r1) throws  {
        return GooglePlayServicesUtilLight.getOpenSourceSoftwareLicenseInfo($r1);
    }

    public int isGooglePlayServicesAvailable(Context $r1) throws  {
        int $i0 = GooglePlayServicesUtilLight.isGooglePlayServicesAvailable($r1);
        return GooglePlayServicesUtilLight.isPlayServicesPossiblyUpdating($r1, $i0) ? 18 : $i0;
    }

    public boolean isPlayServicesPossiblyUpdating(Context $r1, int $i0) throws  {
        return GooglePlayServicesUtilLight.isPlayServicesPossiblyUpdating($r1, $i0);
    }

    public boolean isPlayStorePossiblyUpdating(Context $r1, int $i0) throws  {
        return GooglePlayServicesUtilLight.isPlayStorePossiblyUpdating($r1, $i0);
    }

    public boolean isUserResolvableError(int $i0) throws  {
        return GooglePlayServicesUtilLight.isUserRecoverableError($i0);
    }

    public void zzbm(Context $r1) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        GooglePlayServicesUtilLight.zzbb($r1);
    }

    public void zzbn(Context $r1) throws  {
        GooglePlayServicesUtilLight.zzbn($r1);
    }

    public boolean zzn(Context $r1, String $r2) throws  {
        return GooglePlayServicesUtilLight.zzn($r1, $r2);
    }
}
