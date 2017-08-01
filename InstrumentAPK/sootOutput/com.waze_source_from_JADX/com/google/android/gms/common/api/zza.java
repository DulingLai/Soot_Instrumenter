package com.google.android.gms.common.api;

import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.internal.zzqh;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class zza extends zzb {
    private final ConnectionResult Cf;

    public zza(@Signature({"(", "Lcom/google/android/gms/common/api/Status;", "Landroid/support/v4/util/ArrayMap", "<", "Lcom/google/android/gms/internal/zzqh", "<*>;", "Lcom/google/android/gms/common/ConnectionResult;", ">;)V"}) Status $r1, @Signature({"(", "Lcom/google/android/gms/common/api/Status;", "Landroid/support/v4/util/ArrayMap", "<", "Lcom/google/android/gms/internal/zzqh", "<*>;", "Lcom/google/android/gms/common/ConnectionResult;", ">;)V"}) ArrayMap<zzqh<?>, ConnectionResult> $r2) throws  {
        super($r1, $r2);
        this.Cf = (ConnectionResult) $r2.get($r2.keyAt(0));
    }
}
