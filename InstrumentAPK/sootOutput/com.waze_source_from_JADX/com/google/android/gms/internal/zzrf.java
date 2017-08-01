package com.google.android.gms.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Result;
import dalvik.annotation.Signature;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzrf {

    /* compiled from: dalvik_source_com.waze.apk */
    public interface zza {
        void zzd(ConnectionResult connectionResult) throws ;

        void zze(int i, boolean z) throws ;

        void zzr(Bundle bundle) throws ;
    }

    ConnectionResult blockingConnect() throws ;

    ConnectionResult blockingConnect(long j, TimeUnit timeUnit) throws ;

    void connect() throws ;

    void disconnect() throws ;

    void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) throws ;

    @Nullable
    ConnectionResult getConnectionResult(@NonNull @Signature({"(", "Lcom/google/android/gms/common/api/Api", "<*>;)", "Lcom/google/android/gms/common/ConnectionResult;"}) Api<?> api) throws ;

    boolean isConnected() throws ;

    boolean isConnecting() throws ;

    boolean zza(zzru com_google_android_gms_internal_zzru) throws ;

    void zzarx() throws ;

    void zzass() throws ;

    <A extends zzb, R extends Result, T extends com.google.android.gms.internal.zzqk.zza<R, A>> T zzc(@NonNull @Signature({"<A::", "Lcom/google/android/gms/common/api/Api$zzb;", "R::", "Lcom/google/android/gms/common/api/Result;", "T:", "Lcom/google/android/gms/internal/zzqk$zza", "<TR;TA;>;>(TT;)TT;"}) T t) throws ;

    <A extends zzb, T extends com.google.android.gms.internal.zzqk.zza<? extends Result, A>> T zzd(@NonNull @Signature({"<A::", "Lcom/google/android/gms/common/api/Api$zzb;", "T:", "Lcom/google/android/gms/internal/zzqk$zza", "<+", "Lcom/google/android/gms/common/api/Result;", "TA;>;>(TT;)TT;"}) T t) throws ;
}
