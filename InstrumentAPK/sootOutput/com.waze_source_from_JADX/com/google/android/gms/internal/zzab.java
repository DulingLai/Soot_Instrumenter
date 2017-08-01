package com.google.android.gms.internal;

import com.google.android.gms.internal.zzm.zza;
import com.google.android.gms.internal.zzm.zzb;
import dalvik.annotation.Signature;
import java.io.UnsupportedEncodingException;

/* compiled from: dalvik_source_com.waze.apk */
public class zzab extends zzk<String> {
    private final zzb<String> zzcf;

    public zzab(@Signature({"(I", "Ljava/lang/String;", "Lcom/google/android/gms/internal/zzm$zzb", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/internal/zzm$zza;", ")V"}) int $i0, @Signature({"(I", "Ljava/lang/String;", "Lcom/google/android/gms/internal/zzm$zzb", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/internal/zzm$zza;", ")V"}) String $r1, @Signature({"(I", "Ljava/lang/String;", "Lcom/google/android/gms/internal/zzm$zzb", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/internal/zzm$zza;", ")V"}) zzb<String> $r2, @Signature({"(I", "Ljava/lang/String;", "Lcom/google/android/gms/internal/zzm$zzb", "<", "Ljava/lang/String;", ">;", "Lcom/google/android/gms/internal/zzm$zza;", ")V"}) zza $r3) throws  {
        super($i0, $r1, $r3);
        this.zzcf = $r2;
    }

    protected zzm<String> zza(@Signature({"(", "Lcom/google/android/gms/internal/zzi;", ")", "Lcom/google/android/gms/internal/zzm", "<", "Ljava/lang/String;", ">;"}) zzi $r1) throws  {
        String $r2;
        try {
            $r2 = new String($r1.data, zzx.zza($r1.headers));
        } catch (UnsupportedEncodingException e) {
            $r2 = new String($r1.data);
        }
        return zzm.zza($r2, zzx.zzb($r1));
    }

    protected /* synthetic */ void zza(Object $r1) throws  {
        zzi((String) $r1);
    }

    protected void zzi(String $r1) throws  {
        this.zzcf.zzb($r1);
    }
}
