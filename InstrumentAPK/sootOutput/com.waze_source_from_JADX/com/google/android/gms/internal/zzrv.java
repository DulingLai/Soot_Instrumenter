package com.google.android.gms.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzqk.zzb;
import com.google.android.gms.internal.zzre.zza;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class zzrv extends zza {
    private final zzb<Status> Ga;

    public zzrv(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zzb", "<", "Lcom/google/android/gms/common/api/Status;", ">;)V"}) zzb<Status> $r1) throws  {
        this.Ga = $r1;
    }

    public void zzv(Status $r1) throws  {
        this.Ga.setResult($r1);
    }
}
