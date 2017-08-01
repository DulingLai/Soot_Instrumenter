package com.google.android.gms.common.internal;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.Uri.Builder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/* compiled from: dalvik_source_com.waze.apk */
public class zzo {
    private static final Uri JX = Uri.parse("http://plus.google.com/");
    private static final Uri JY = JX.buildUpon().appendPath("circles").appendPath("find").build();

    public static boolean zza(PackageManager $r0, Intent $r1) throws  {
        return $r0.resolveActivity($r1, 65536) != null;
    }

    private static Uri zzaj(String $r0, @Nullable String $r1) throws  {
        Builder $r3 = Uri.parse("market://details").buildUpon().appendQueryParameter("id", $r0);
        if (!TextUtils.isEmpty($r1)) {
            $r3.appendQueryParameter("pcampaignid", $r1);
        }
        return $r3.build();
    }

    public static Intent zzak(String $r0, @Nullable String $r1) throws  {
        Intent $r2 = new Intent("android.intent.action.VIEW");
        $r2.setData(zzaj($r0, $r1));
        $r2.setPackage("com.android.vending");
        $r2.addFlags(524288);
        return $r2;
    }

    public static Intent zzawx() throws  {
        Intent $r0 = new Intent("com.google.android.clockwork.home.UPDATE_ANDROID_WEAR_ACTION");
        $r0.setPackage("com.google.android.wearable.app");
        return $r0;
    }

    public static Intent zzgv(String $r0) throws  {
        Uri $r1 = Uri.fromParts("package", $r0, null);
        Intent $r2 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        $r2.setData($r1);
        return $r2;
    }
}
