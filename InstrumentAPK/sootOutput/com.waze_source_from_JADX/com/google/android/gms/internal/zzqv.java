package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.internal.zzqk.zza;
import dalvik.annotation.Signature;
import java.util.Collections;

/* compiled from: dalvik_source_com.waze.apk */
public class zzqv implements zzqx {
    private final zzqy DY;

    public zzqv(zzqy $r1) throws  {
        this.DY = $r1;
    }

    public void begin() throws  {
        this.DY.zzats();
        this.DY.DI.EL = Collections.emptySet();
    }

    public void connect() throws  {
        this.DY.zzatq();
    }

    public boolean disconnect() throws  {
        return true;
    }

    public void onConnected(Bundle bundle) throws  {
    }

    public void onConnectionSuspended(int i) throws  {
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/common/ConnectionResult;", "Lcom/google/android/gms/common/api/Api", "<*>;I)V"}) ConnectionResult connectionResult, @Signature({"(", "Lcom/google/android/gms/common/ConnectionResult;", "Lcom/google/android/gms/common/api/Api", "<*>;I)V"}) Api<?> api, @Signature({"(", "Lcom/google/android/gms/common/ConnectionResult;", "Lcom/google/android/gms/common/api/Api", "<*>;I)V"}) int i) throws  {
    }

    public <A extends zzb, R extends Result, T extends zza<R, A>> T zzc(@Signature({"<A::", "Lcom/google/android/gms/common/api/Api$zzb;", "R::", "Lcom/google/android/gms/common/api/Result;", "T:", "Lcom/google/android/gms/internal/zzqk$zza", "<TR;TA;>;>(TT;)TT;"}) T $r1) throws  {
        this.DY.DI.EE.add($r1);
        return $r1;
    }

    public <A extends zzb, T extends zza<? extends Result, A>> T zzd(@Signature({"<A::", "Lcom/google/android/gms/common/api/Api$zzb;", "T:", "Lcom/google/android/gms/internal/zzqk$zza", "<+", "Lcom/google/android/gms/common/api/Result;", "TA;>;>(TT;)TT;"}) T t) throws  {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }
}
