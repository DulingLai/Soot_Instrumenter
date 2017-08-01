package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzg;
import dalvik.annotation.Signature;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/* compiled from: dalvik_source_com.waze.apk */
final class zzqo implements zzrf {
    private final zzqw DI;
    private final zzqy DJ;
    private final zzqy DK;
    private final Map<zzc<?>, zzqy> DL;
    private final Set<zzru> DM = Collections.newSetFromMap(new WeakHashMap());
    private final zze DN;
    private Bundle DO;
    private ConnectionResult DP = null;
    private ConnectionResult DQ = null;
    private boolean DR = false;
    private final Lock DS;
    private int DT = 0;
    private final Context mContext;
    private final Looper zzaih;

    /* compiled from: dalvik_source_com.waze.apk */
    class C08341 implements Runnable {
        final /* synthetic */ zzqo DU;

        C08341(zzqo $r1) throws  {
            this.DU = $r1;
        }

        public void run() throws  {
            this.DU.DS.lock();
            try {
                this.DU.zzasv();
            } finally {
                this.DU.DS.unlock();
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private class zza implements com.google.android.gms.internal.zzrf.zza {
        final /* synthetic */ zzqo DU;

        private zza(zzqo $r1) throws  {
            this.DU = $r1;
        }

        public void zzd(@NonNull ConnectionResult $r1) throws  {
            this.DU.DS.lock();
            try {
                this.DU.DP = $r1;
                this.DU.zzasv();
            } finally {
                this.DU.DS.unlock();
            }
        }

        public void zze(int $i0, boolean $z0) throws  {
            this.DU.DS.lock();
            try {
                if (this.DU.DR || this.DU.DQ == null || !this.DU.DQ.isSuccess()) {
                    this.DU.DR = false;
                    this.DU.zzd($i0, $z0);
                    return;
                }
                this.DU.DR = true;
                this.DU.DK.onConnectionSuspended($i0);
                this.DU.DS.unlock();
            } finally {
                this.DU.DS.unlock();
            }
        }

        public void zzr(@Nullable Bundle $r1) throws  {
            this.DU.DS.lock();
            try {
                this.DU.zzq($r1);
                this.DU.DP = ConnectionResult.BB;
                this.DU.zzasv();
            } finally {
                this.DU.DS.unlock();
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private class zzb implements com.google.android.gms.internal.zzrf.zza {
        final /* synthetic */ zzqo DU;

        private zzb(zzqo $r1) throws  {
            this.DU = $r1;
        }

        public void zzd(@NonNull ConnectionResult $r1) throws  {
            this.DU.DS.lock();
            try {
                this.DU.DQ = $r1;
                this.DU.zzasv();
            } finally {
                this.DU.DS.unlock();
            }
        }

        public void zze(int $i0, boolean $z0) throws  {
            this.DU.DS.lock();
            try {
                if (this.DU.DR) {
                    this.DU.DR = false;
                    this.DU.zzd($i0, $z0);
                    return;
                }
                this.DU.DR = true;
                this.DU.DJ.onConnectionSuspended($i0);
                this.DU.DS.unlock();
            } finally {
                this.DU.DS.unlock();
            }
        }

        public void zzr(@Nullable Bundle bundle) throws  {
            this.DU.DS.lock();
            try {
                this.DU.DQ = ConnectionResult.BB;
                this.DU.zzasv();
            } finally {
                this.DU.DS.unlock();
            }
        }
    }

    private zzqo(@Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Lcom/google/android/gms/common/api/Api$zze;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;)V"}) Context $r1, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Lcom/google/android/gms/common/api/Api$zze;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;)V"}) zzqw $r2, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Lcom/google/android/gms/common/api/Api$zze;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;)V"}) Lock $r3, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Lcom/google/android/gms/common/api/Api$zze;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;)V"}) Looper $r4, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Lcom/google/android/gms/common/api/Api$zze;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;)V"}) GoogleApiAvailabilityLight $r5, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Lcom/google/android/gms/common/api/Api$zze;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;)V"}) Map<zzc<?>, zze> $r6, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Lcom/google/android/gms/common/api/Api$zze;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;)V"}) Map<zzc<?>, zze> $r7, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Lcom/google/android/gms/common/api/Api$zze;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;)V"}) zzg $r8, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Lcom/google/android/gms/common/api/Api$zze;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;)V"}) com.google.android.gms.common.api.Api.zza<? extends zzach, zzaci> $r9, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Lcom/google/android/gms/common/api/Api$zze;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;)V"}) zze $r10, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Lcom/google/android/gms/common/api/Api$zze;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;)V"}) ArrayList<zzqn> $r11, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Lcom/google/android/gms/common/api/Api$zze;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;)V"}) ArrayList<zzqn> $r12, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Lcom/google/android/gms/common/api/Api$zze;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;)V"}) Map<Api<?>, Integer> $r13, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Lcom/google/android/gms/common/api/Api$zze;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;)V"}) Map<Api<?>, Integer> $r14) throws  {
        zzqo com_google_android_gms_internal_zzqo = this;
        this.mContext = $r1;
        this.DI = $r2;
        this.DS = $r3;
        this.zzaih = $r4;
        this.DN = $r10;
        zzqo com_google_android_gms_internal_zzqo2 = this;
        this.DJ = new zzqy($r1, this.DI, $r3, $r4, $r5, $r7, null, $r14, null, $r12, new zza());
        com_google_android_gms_internal_zzqo2 = this;
        this.DK = new zzqy($r1, this.DI, $r3, $r4, $r5, $r6, $r8, $r13, $r9, $r11, new zzb());
        ArrayMap arrayMap = new ArrayMap();
        for (zzc $r23 : $r7.keySet()) {
            arrayMap.put($r23, this.DJ);
        }
        for (zzc $r232 : $r6.keySet()) {
            arrayMap.put($r232, this.DK);
        }
        this.DL = Collections.unmodifiableMap(arrayMap);
    }

    public static zzqo zza(@Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;)", "Lcom/google/android/gms/internal/zzqo;"}) Context $r0, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;)", "Lcom/google/android/gms/internal/zzqo;"}) zzqw $r1, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;)", "Lcom/google/android/gms/internal/zzqo;"}) Lock $r2, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;)", "Lcom/google/android/gms/internal/zzqo;"}) Looper $r3, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;)", "Lcom/google/android/gms/internal/zzqo;"}) GoogleApiAvailabilityLight $r4, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;)", "Lcom/google/android/gms/internal/zzqo;"}) Map<zzc<?>, zze> $r5, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;)", "Lcom/google/android/gms/internal/zzqo;"}) zzg $r6, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;)", "Lcom/google/android/gms/internal/zzqo;"}) Map<Api<?>, Integer> $r7, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;)", "Lcom/google/android/gms/internal/zzqo;"}) com.google.android.gms.common.api.Api.zza<? extends zzach, zzaci> $r8, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;)", "Lcom/google/android/gms/internal/zzqo;"}) ArrayList<zzqn> $r9) throws  {
        zze $r16 = null;
        ArrayMap arrayMap = new ArrayMap();
        arrayMap = new ArrayMap();
        for (Entry $r20 : $r5.entrySet()) {
            zze $r21 = (zze) $r20.getValue();
            if ($r21.zzaer()) {
                $r16 = $r21;
            }
            if ($r21.zzaec()) {
                arrayMap.put((zzc) $r20.getKey(), $r21);
            } else {
                arrayMap.put((zzc) $r20.getKey(), $r21);
            }
        }
        zzab.zza(!arrayMap.isEmpty(), (Object) "CompositeGoogleApiClient should not be used without any APIs that require sign-in.");
        arrayMap = new ArrayMap();
        arrayMap = new ArrayMap();
        for (Api $r23 : $r7.keySet()) {
            zzc $r22 = $r23.zzarl();
            if (arrayMap.containsKey($r22)) {
                arrayMap.put($r23, (Integer) $r7.get($r23));
            } else if (arrayMap.containsKey($r22)) {
                arrayMap.put($r23, (Integer) $r7.get($r23));
            } else {
                throw new IllegalStateException("Each API in the apiTypeMap must have a corresponding client in the clients map.");
            }
        }
        ArrayList arrayList = new ArrayList();
        arrayList = new ArrayList();
        Iterator $r18 = $r9.iterator();
        while ($r18.hasNext()) {
            zzqn $r26 = (zzqn) $r18.next();
            if (arrayMap.containsKey($r26.zn)) {
                arrayList.add($r26);
            } else {
                if (arrayMap.containsKey($r26.zn)) {
                    arrayList.add($r26);
                } else {
                    throw new IllegalStateException("Each ClientCallbacks must have a corresponding API in the apiTypeMap");
                }
            }
        }
        return new zzqo($r0, $r1, $r2, $r3, $r4, arrayMap, arrayMap, $r6, $r8, $r16, arrayList, arrayList, arrayMap, arrayMap);
    }

    private void zzasu() throws  {
        this.DQ = null;
        this.DP = null;
        this.DJ.connect();
        this.DK.connect();
    }

    private void zzasv() throws  {
        if (zzc(this.DP)) {
            if (zzc(this.DQ) || zzasy()) {
                zzasw();
            } else if (this.DQ == null) {
            } else {
                if (this.DT == 1) {
                    zzasx();
                    return;
                }
                zzb(this.DQ);
                this.DJ.disconnect();
            }
        } else if (this.DP != null && zzc(this.DQ)) {
            this.DK.disconnect();
            zzb(this.DP);
        } else if (this.DP != null && this.DQ != null) {
            ConnectionResult $r1 = this.DP;
            if (this.DK.Fc < this.DJ.Fc) {
                $r1 = this.DQ;
            }
            zzb($r1);
        }
    }

    private void zzasw() throws  {
        switch (this.DT) {
            case 1:
                break;
            case 2:
                this.DI.zzr(this.DO);
                break;
            default:
                Log.wtf("CompositeGAC", "Attempted to call success callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new AssertionError());
                break;
        }
        zzasx();
        this.DT = 0;
    }

    private void zzasx() throws  {
        for (zzru zzaeq : this.DM) {
            zzaeq.zzaeq();
        }
        this.DM.clear();
    }

    private boolean zzasy() throws  {
        return this.DQ != null && this.DQ.getErrorCode() == 4;
    }

    @Nullable
    private PendingIntent zzasz() throws  {
        return this.DN == null ? null : PendingIntent.getActivity(this.mContext, this.DI.getSessionId(), this.DN.zzaes(), 134217728);
    }

    private void zzb(ConnectionResult $r1) throws  {
        switch (this.DT) {
            case 1:
                break;
            case 2:
                this.DI.zzd($r1);
                break;
            default:
                Log.wtf("CompositeGAC", "Attempted to call failure callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new Exception());
                break;
        }
        zzasx();
        this.DT = 0;
    }

    private static boolean zzc(ConnectionResult $r0) throws  {
        return $r0 != null && $r0.isSuccess();
    }

    private void zzd(int $i0, boolean $z0) throws  {
        this.DI.zze($i0, $z0);
        this.DQ = null;
        this.DP = null;
    }

    private boolean zze(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zza", "<+", "Lcom/google/android/gms/common/api/Result;", "+", "Lcom/google/android/gms/common/api/Api$zzb;", ">;)Z"}) com.google.android.gms.internal.zzqk.zza<? extends Result, ? extends com.google.android.gms.common.api.Api.zzb> $r1) throws  {
        zzc $r2 = $r1.zzarl();
        zzab.zzb(this.DL.containsKey($r2), (Object) "GoogleApiClient is not configured to use the API required for this call.");
        return ((zzqy) this.DL.get($r2)).equals(this.DK);
    }

    private void zzq(Bundle $r1) throws  {
        if (this.DO == null) {
            this.DO = $r1;
        } else if ($r1 != null) {
            this.DO.putAll($r1);
        }
    }

    public ConnectionResult blockingConnect() throws  {
        throw new UnsupportedOperationException();
    }

    public ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit) throws  {
        throw new UnsupportedOperationException();
    }

    public void connect() throws  {
        this.DT = 2;
        this.DR = false;
        zzasu();
    }

    public void disconnect() throws  {
        this.DQ = null;
        this.DP = null;
        this.DT = 0;
        this.DJ.disconnect();
        this.DK.disconnect();
        zzasx();
    }

    public void dump(String $r1, FileDescriptor $r2, PrintWriter $r3, String[] $r4) throws  {
        $r3.append($r1).append("authClient").println(":");
        this.DK.dump(String.valueOf($r1).concat("  "), $r2, $r3, $r4);
        $r3.append($r1).append("anonClient").println(":");
        this.DJ.dump(String.valueOf($r1).concat("  "), $r2, $r3, $r4);
    }

    @Nullable
    public ConnectionResult getConnectionResult(@NonNull @Signature({"(", "Lcom/google/android/gms/common/api/Api", "<*>;)", "Lcom/google/android/gms/common/ConnectionResult;"}) Api<?> $r1) throws  {
        return ((zzqy) this.DL.get($r1.zzarl())).equals(this.DK) ? zzasy() ? new ConnectionResult(4, zzasz()) : this.DK.getConnectionResult($r1) : this.DJ.getConnectionResult($r1);
    }

    public boolean isConnected() throws  {
        boolean $z0 = true;
        this.DS.lock();
        try {
            if (!(this.DJ.isConnected() && (zzast() || zzasy() || this.DT == 1))) {
                $z0 = false;
            }
            this.DS.unlock();
            return $z0;
        } catch (Throwable th) {
            this.DS.unlock();
        }
    }

    public boolean isConnecting() throws  {
        this.DS.lock();
        try {
            boolean $z0 = this.DT == 2;
            this.DS.unlock();
            return $z0;
        } catch (Throwable th) {
            this.DS.unlock();
        }
    }

    public boolean zza(zzru $r1) throws  {
        this.DS.lock();
        try {
            if ((isConnecting() || isConnected()) && !zzast()) {
                this.DM.add($r1);
                if (this.DT == 0) {
                    this.DT = 1;
                }
                this.DQ = null;
                this.DK.connect();
                return true;
            }
            this.DS.unlock();
            return false;
        } finally {
            this.DS.unlock();
        }
    }

    public void zzarx() throws  {
        this.DS.lock();
        try {
            boolean $z0 = isConnecting();
            this.DK.disconnect();
            this.DQ = new ConnectionResult(4);
            if ($z0) {
                new Handler(this.zzaih).post(new C08341(this));
            } else {
                zzasx();
            }
            this.DS.unlock();
        } catch (Throwable th) {
            this.DS.unlock();
        }
    }

    public void zzass() throws  {
        this.DJ.zzass();
        this.DK.zzass();
    }

    public boolean zzast() throws  {
        return this.DK.isConnected();
    }

    public <A extends com.google.android.gms.common.api.Api.zzb, R extends Result, T extends com.google.android.gms.internal.zzqk.zza<R, A>> T zzc(@NonNull @Signature({"<A::", "Lcom/google/android/gms/common/api/Api$zzb;", "R::", "Lcom/google/android/gms/common/api/Result;", "T:", "Lcom/google/android/gms/internal/zzqk$zza", "<TR;TA;>;>(TT;)TT;"}) T $r1) throws  {
        if (!zze((com.google.android.gms.internal.zzqk.zza) $r1)) {
            return this.DJ.zzc((com.google.android.gms.internal.zzqk.zza) $r1);
        }
        if (!zzasy()) {
            return this.DK.zzc((com.google.android.gms.internal.zzqk.zza) $r1);
        }
        $r1.zzag(new Status(4, null, zzasz()));
        return $r1;
    }

    public <A extends com.google.android.gms.common.api.Api.zzb, T extends com.google.android.gms.internal.zzqk.zza<? extends Result, A>> T zzd(@NonNull @Signature({"<A::", "Lcom/google/android/gms/common/api/Api$zzb;", "T:", "Lcom/google/android/gms/internal/zzqk$zza", "<+", "Lcom/google/android/gms/common/api/Result;", "TA;>;>(TT;)TT;"}) T $r1) throws  {
        if (!zze((com.google.android.gms.internal.zzqk.zza) $r1)) {
            return this.DJ.zzd($r1);
        }
        if (!zzasy()) {
            return this.DK.zzd($r1);
        }
        $r1.zzag(new Status(4, null, zzasz()));
        return $r1;
    }
}
