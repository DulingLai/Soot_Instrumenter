package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import com.waze.strings.DisplayStrings;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzh {
    private static Boolean MJ;
    private static Boolean MK;
    private static Boolean ML;
    private static Boolean MM;

    public static boolean zzb(Resources $r0) throws  {
        boolean $z0 = false;
        if ($r0 == null) {
            return false;
        }
        if (MJ == null) {
            boolean $z1 = ($r0.getConfiguration().screenLayout & 15) > 3;
            if ((zzr.zzaza() && $z1) || zzc($r0)) {
                $z0 = true;
            }
            MJ = Boolean.valueOf($z0);
        }
        return MJ.booleanValue();
    }

    @TargetApi(13)
    private static boolean zzc(Resources $r0) throws  {
        if (MK == null) {
            Configuration $r2 = $r0.getConfiguration();
            boolean $z0 = zzr.zzazc() && ($r2.screenLayout & 15) <= 3 && $r2.smallestScreenWidthDp >= DisplayStrings.DS_SENDING_REPORT_FAILED__PLEASE_RESEND_LATER;
            MK = Boolean.valueOf($z0);
        }
        return MK.booleanValue();
    }

    @TargetApi(20)
    public static boolean zzcf(Context $r0) throws  {
        if (ML == null) {
            boolean $z0 = zzr.zzazi() && $r0.getPackageManager().hasSystemFeature("android.hardware.type.watch");
            ML = Boolean.valueOf($z0);
        }
        return ML.booleanValue();
    }

    @TargetApi(21)
    public static boolean zzcg(Context $r0) throws  {
        if (MM == null) {
            boolean $z0 = zzr.zzazk() && $r0.getPackageManager().hasSystemFeature("cn.google");
            MM = Boolean.valueOf($z0);
        }
        return MM.booleanValue();
    }
}
