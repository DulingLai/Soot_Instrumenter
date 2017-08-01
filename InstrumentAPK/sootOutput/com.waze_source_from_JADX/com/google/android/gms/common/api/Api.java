package com.google.android.gms.common.api;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzq;
import dalvik.annotation.Signature;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public final class Api<O extends ApiOptions> {
    private final zza<?, O> Cb;
    private final zzh<?, O> Cc = null;
    private final zzf<?> Cd;
    private final zzi<?> Ce;
    private final String mName;

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zzd<T extends zzb, O> {
        public int getPriority() throws  {
            return ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        }

        public List<Scope> zzt(@Signature({"(TO;)", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/Scope;", ">;"}) O o) throws  {
            return Collections.emptyList();
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza<T extends zze, O> extends zzd<T, O> {
        public abstract T zza(@Signature({"(", "Landroid/content/Context;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "TO;", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ")TT;"}) Context context, @Signature({"(", "Landroid/content/Context;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "TO;", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ")TT;"}) Looper looper, @Signature({"(", "Landroid/content/Context;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "TO;", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ")TT;"}) com.google.android.gms.common.internal.zzg com_google_android_gms_common_internal_zzg, @Signature({"(", "Landroid/content/Context;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "TO;", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ")TT;"}) O o, @Signature({"(", "Landroid/content/Context;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "TO;", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ")TT;"}) ConnectionCallbacks connectionCallbacks, @Signature({"(", "Landroid/content/Context;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "TO;", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ")TT;"}) OnConnectionFailedListener onConnectionFailedListener) throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface ApiOptions {

        /* compiled from: dalvik_source_com.waze.apk */
        public interface HasOptions extends ApiOptions {
        }

        /* compiled from: dalvik_source_com.waze.apk */
        public interface NotRequiredOptions extends ApiOptions {
        }

        /* compiled from: dalvik_source_com.waze.apk */
        public interface Optional extends HasOptions, NotRequiredOptions {
        }

        /* compiled from: dalvik_source_com.waze.apk */
        public static final class NoOptions implements NotRequiredOptions {
            private NoOptions() throws  {
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface zzb {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface zze extends zzb {
        void disconnect() throws ;

        void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) throws ;

        boolean isConnected() throws ;

        boolean isConnecting() throws ;

        void zza(com.google.android.gms.common.internal.zzd.zzf com_google_android_gms_common_internal_zzd_zzf) throws ;

        void zza(@Signature({"(", "Lcom/google/android/gms/common/internal/zzq;", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;)V"}) zzq com_google_android_gms_common_internal_zzq, @Signature({"(", "Lcom/google/android/gms/common/internal/zzq;", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;)V"}) Set<Scope> set) throws ;

        boolean zzaec() throws ;

        boolean zzaer() throws ;

        Intent zzaes() throws ;

        boolean zzarn() throws ;

        @Nullable
        IBinder zzaro() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class zzc<C extends zzb> {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzf<C extends zze> extends zzc<C> {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface zzg<T extends IInterface> extends zzb {
        void zza(@Signature({"(ITT;)V"}) int i, @Signature({"(ITT;)V"}) T t) throws ;

        T zzbc(@Signature({"(", "Landroid/os/IBinder;", ")TT;"}) IBinder iBinder) throws ;

        String zzrg() throws ;

        String zzrh() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zzh<T extends zzg, O> extends zzd<T, O> {
        public abstract int zzarp() throws ;

        public abstract T zzv(@Signature({"(TO;)TT;"}) O o) throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzi<C extends zzg> extends zzc<C> {
    }

    public <C extends zze> Api(@Signature({"<C::", "Lcom/google/android/gms/common/api/Api$zze;", ">(", "Ljava/lang/String;", "Lcom/google/android/gms/common/api/Api$zza", "<TC;TO;>;", "Lcom/google/android/gms/common/api/Api$zzf", "<TC;>;)V"}) String $r1, @Signature({"<C::", "Lcom/google/android/gms/common/api/Api$zze;", ">(", "Ljava/lang/String;", "Lcom/google/android/gms/common/api/Api$zza", "<TC;TO;>;", "Lcom/google/android/gms/common/api/Api$zzf", "<TC;>;)V"}) zza<C, O> $r2, @Signature({"<C::", "Lcom/google/android/gms/common/api/Api$zze;", ">(", "Ljava/lang/String;", "Lcom/google/android/gms/common/api/Api$zza", "<TC;TO;>;", "Lcom/google/android/gms/common/api/Api$zzf", "<TC;>;)V"}) zzf<C> $r3) throws  {
        zzab.zzb((Object) $r2, (Object) "Cannot construct an Api with a null ClientBuilder");
        zzab.zzb((Object) $r3, (Object) "Cannot construct an Api with a null ClientKey");
        this.mName = $r1;
        this.Cb = $r2;
        this.Cd = $r3;
        this.Ce = null;
    }

    public String getName() throws  {
        return this.mName;
    }

    public zzd<?, O> zzari() throws  {
        return zzarm() ? null : this.Cb;
    }

    public zza<?, O> zzarj() throws  {
        zzab.zza(this.Cb != null, (Object) "This API was constructed with a SimpleClientBuilder. Use getSimpleClientBuilder");
        return this.Cb;
    }

    public zzh<?, O> zzark() throws  {
        zzab.zza(false, (Object) "This API was constructed with a ClientBuilder. Use getClientBuilder");
        return null;
    }

    public zzc<?> zzarl() throws  {
        if (this.Cd != null) {
            return this.Cd;
        }
        throw new IllegalStateException("This API was constructed with null client keys. This should not be possible.");
    }

    public boolean zzarm() throws  {
        return false;
    }
}
