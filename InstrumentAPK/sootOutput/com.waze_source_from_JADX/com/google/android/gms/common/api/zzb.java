package com.google.android.gms.common.api;

import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.internal.zzqh;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class zzb implements Result {
    private final ArrayMap<zzqh<?>, ConnectionResult> Cg;
    private final Status cp;

    public zzb(@Signature({"(", "Lcom/google/android/gms/common/api/Status;", "Landroid/support/v4/util/ArrayMap", "<", "Lcom/google/android/gms/internal/zzqh", "<*>;", "Lcom/google/android/gms/common/ConnectionResult;", ">;)V"}) Status $r1, @Signature({"(", "Lcom/google/android/gms/common/api/Status;", "Landroid/support/v4/util/ArrayMap", "<", "Lcom/google/android/gms/internal/zzqh", "<*>;", "Lcom/google/android/gms/common/ConnectionResult;", ">;)V"}) ArrayMap<zzqh<?>, ConnectionResult> $r2) throws  {
        this.cp = $r1;
        this.Cg = $r2;
    }

    public Status getStatus() throws  {
        return this.cp;
    }
}
