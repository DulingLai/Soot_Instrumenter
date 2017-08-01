package com.google.android.gms.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.zzc;
import com.google.android.gms.internal.zzqk.zza;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class zzrb<O extends ApiOptions> extends zzqs {
    private final zzc<O> FB;

    public zzrb(@Signature({"(", "Lcom/google/android/gms/common/api/zzc", "<TO;>;)V"}) zzc<O> $r1) throws  {
        super("Method is not supported by connectionless client. APIs supporting connectionless client must not call this method.");
        this.FB = $r1;
    }

    public Looper getLooper() throws  {
        return this.FB.getLooper();
    }

    public void zza(zzsa com_google_android_gms_internal_zzsa) throws  {
        this.FB.zzarq();
    }

    public void zzb(zzsa com_google_android_gms_internal_zzsa) throws  {
        this.FB.zzarr();
    }

    public <A extends zzb, R extends Result, T extends zza<R, A>> T zzc(@NonNull @Signature({"<A::", "Lcom/google/android/gms/common/api/Api$zzb;", "R::", "Lcom/google/android/gms/common/api/Result;", "T:", "Lcom/google/android/gms/internal/zzqk$zza", "<TR;TA;>;>(TT;)TT;"}) T $r1) throws  {
        return this.FB.zza((zza) $r1);
    }

    public <A extends zzb, T extends zza<? extends Result, A>> T zzd(@NonNull @Signature({"<A::", "Lcom/google/android/gms/common/api/Api$zzb;", "T:", "Lcom/google/android/gms/internal/zzqk$zza", "<+", "Lcom/google/android/gms/common/api/Result;", "TA;>;>(TT;)TT;"}) T $r1) throws  {
        return this.FB.zzb((zza) $r1);
    }
}
