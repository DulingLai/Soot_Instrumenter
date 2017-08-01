package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.internal.zzae.zza;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/* compiled from: dalvik_source_com.waze.apk */
public class zzar extends zzaq {
    private static final String TAG = zzar.class.getSimpleName();

    protected zzar(Context $r1, String $r2, boolean $z0) throws  {
        super($r1, $r2, $z0);
    }

    public static zzar zza(String $r0, Context $r1, boolean $z0) throws  {
        zzaq.zza($r1, $z0);
        return new zzar($r1, $r0, $z0);
    }

    protected List<Callable<Void>> zzb(@Signature({"(", "Lcom/google/android/gms/internal/zzax;", "Lcom/google/android/gms/internal/zzae$zza;", ")", "Ljava/util/List", "<", "Ljava/util/concurrent/Callable", "<", "Ljava/lang/Void;", ">;>;"}) zzax $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzax;", "Lcom/google/android/gms/internal/zzae$zza;", ")", "Ljava/util/List", "<", "Ljava/util/concurrent/Callable", "<", "Ljava/lang/Void;", ">;>;"}) zza $r2) throws  {
        if ($r1.zzcd() == null || !this.zzafn) {
            return super.zzb($r1, $r2);
        }
        int $i0 = $r1.zzat();
        ArrayList $r3 = new ArrayList();
        $r3.addAll(super.zzb($r1, $r2));
        $r3.add(new zzbh($r1, zzav.zzbl(), zzav.zzbm(), $r2, $i0, 24));
        return $r3;
    }
}
