package com.google.android.gms.internal;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.internal.zzae.zza;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/* compiled from: dalvik_source_com.waze.apk */
public class zzap extends zzaq {
    private static final String TAG = zzap.class.getSimpleName();
    private Info zzafm;

    protected zzap(Context $r1) throws  {
        super($r1, "");
    }

    public static zzap zze(Context $r0) throws  {
        zzaq.zza($r0, true);
        return new zzap($r0);
    }

    public String zza(String $r1, String $r2) throws  {
        return zzak.zza($r1, $r2, true);
    }

    public void zza(Info $r1) throws  {
        this.zzafm = $r1;
    }

    protected void zza(zzax $r1, zza $r2) throws  {
        if (!$r1.zzch()) {
            zza(zzb($r1, $r2));
        } else if (this.zzafm != null) {
            String $r4 = this.zzafm.getId();
            if (!TextUtils.isEmpty($r4)) {
                $r2.zzef = zzay.zzo($r4);
                $r2.zzeg = Integer.valueOf(5);
                $r2.zzeh = Boolean.valueOf(this.zzafm.isLimitAdTrackingEnabled());
            }
            this.zzafm = null;
        }
    }

    protected List<Callable<Void>> zzb(@Signature({"(", "Lcom/google/android/gms/internal/zzax;", "Lcom/google/android/gms/internal/zzae$zza;", ")", "Ljava/util/List", "<", "Ljava/util/concurrent/Callable", "<", "Ljava/lang/Void;", ">;>;"}) zzax $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzax;", "Lcom/google/android/gms/internal/zzae$zza;", ")", "Ljava/util/List", "<", "Ljava/util/concurrent/Callable", "<", "Ljava/lang/Void;", ">;>;"}) zza $r2) throws  {
        ArrayList $r3 = new ArrayList();
        if ($r1.zzcd() == null) {
            return $r3;
        }
        int $i0 = $r1.zzat();
        $r3.add(new zzbh($r1, zzav.zzbl(), zzav.zzbm(), $r2, $i0, 24));
        return $r3;
    }

    protected zza zzd(Context context) throws  {
        return null;
    }
}
