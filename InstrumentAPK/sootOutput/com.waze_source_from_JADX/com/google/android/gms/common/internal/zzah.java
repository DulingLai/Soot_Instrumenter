package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.Api.zzg;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class zzah<T extends IInterface> extends zzk<T> {
    private final zzg<T> Km;

    public zzah(@Signature({"(", "Landroid/content/Context;", "Landroid/os/Looper;", "I", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/Api$zzg", "<TT;>;)V"}) Context $r1, @Signature({"(", "Landroid/content/Context;", "Landroid/os/Looper;", "I", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/Api$zzg", "<TT;>;)V"}) Looper $r2, @Signature({"(", "Landroid/content/Context;", "Landroid/os/Looper;", "I", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/Api$zzg", "<TT;>;)V"}) int $i0, @Signature({"(", "Landroid/content/Context;", "Landroid/os/Looper;", "I", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/Api$zzg", "<TT;>;)V"}) ConnectionCallbacks $r3, @Signature({"(", "Landroid/content/Context;", "Landroid/os/Looper;", "I", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/Api$zzg", "<TT;>;)V"}) OnConnectionFailedListener $r4, @Signature({"(", "Landroid/content/Context;", "Landroid/os/Looper;", "I", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/Api$zzg", "<TT;>;)V"}) zzg $r5, @Signature({"(", "Landroid/content/Context;", "Landroid/os/Looper;", "I", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/Api$zzg", "<TT;>;)V"}) zzg<T> $r6) throws  {
        super($r1, $r2, $i0, $r5, $r3, $r4);
        this.Km = $r6;
    }

    public zzg<T> zzaxk() throws  {
        return this.Km;
    }

    protected T zzbc(@Signature({"(", "Landroid/os/IBinder;", ")TT;"}) IBinder $r1) throws  {
        return this.Km.zzbc($r1);
    }

    protected void zzc(@Signature({"(ITT;)V"}) int $i0, @Signature({"(ITT;)V"}) T $r1) throws  {
        this.Km.zza($i0, $r1);
    }

    protected String zzrg() throws  {
        return this.Km.zzrg();
    }

    protected String zzrh() throws  {
        return this.Km.zzrh();
    }
}
