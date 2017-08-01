package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.internal.zzqk.zza;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzqx {
    void begin() throws ;

    void connect() throws ;

    boolean disconnect() throws ;

    void onConnected(Bundle bundle) throws ;

    void onConnectionSuspended(int i) throws ;

    void zza(@Signature({"(", "Lcom/google/android/gms/common/ConnectionResult;", "Lcom/google/android/gms/common/api/Api", "<*>;I)V"}) ConnectionResult connectionResult, @Signature({"(", "Lcom/google/android/gms/common/ConnectionResult;", "Lcom/google/android/gms/common/api/Api", "<*>;I)V"}) Api<?> api, @Signature({"(", "Lcom/google/android/gms/common/ConnectionResult;", "Lcom/google/android/gms/common/api/Api", "<*>;I)V"}) int i) throws ;

    <A extends zzb, R extends Result, T extends zza<R, A>> T zzc(@Signature({"<A::", "Lcom/google/android/gms/common/api/Api$zzb;", "R::", "Lcom/google/android/gms/common/api/Result;", "T:", "Lcom/google/android/gms/internal/zzqk$zza", "<TR;TA;>;>(TT;)TT;"}) T t) throws ;

    <A extends zzb, T extends zza<? extends Result, A>> T zzd(@Signature({"<A::", "Lcom/google/android/gms/common/api/Api$zzb;", "T:", "Lcom/google/android/gms/internal/zzqk$zza", "<+", "Lcom/google/android/gms/common/api/Result;", "TA;>;>(TT;)TT;"}) T t) throws ;
}
