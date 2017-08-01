package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzg;
import dalvik.annotation.Signature;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/* compiled from: dalvik_source_com.waze.apk */
public class zzqy implements zzrf {
    final com.google.android.gms.common.api.Api.zza<? extends zzach, zzaci> CI;
    final zzqw DI;
    private final Lock DS;
    final Map<zzc<?>, zze> EK;
    private final Condition EX;
    private final zzb EY;
    final Map<zzc<?>, ConnectionResult> EZ = new HashMap();
    private final GoogleApiAvailabilityLight Eb;
    final zzg Ep;
    final Map<Api<?>, Integer> Eq;
    private volatile zzqx Fa;
    private ConnectionResult Fb = null;
    int Fc;
    final com.google.android.gms.internal.zzrf.zza Fd;
    private final Context mContext;

    /* compiled from: dalvik_source_com.waze.apk */
    static abstract class zza {
        private final zzqx Fe;

        protected zza(zzqx $r1) throws  {
            this.Fe = $r1;
        }

        protected abstract void zzatc() throws ;

        public final void zzd(zzqy $r1) throws  {
            $r1.DS.lock();
            try {
                if ($r1.Fa == this.Fe) {
                    zzatc();
                    $r1.DS.unlock();
                }
            } finally {
                $r1.DS.unlock();
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    final class zzb extends Handler {
        final /* synthetic */ zzqy Ff;

        zzb(zzqy $r1, Looper $r2) throws  {
            this.Ff = $r1;
            super($r2);
        }

        public void handleMessage(Message $r1) throws  {
            switch ($r1.what) {
                case 1:
                    ((zza) $r1.obj).zzd(this.Ff);
                    return;
                case 2:
                    throw ((RuntimeException) $r1.obj);
                default:
                    String str = "GACStateManager";
                    Log.w(str, "Unknown message id: " + $r1.what);
                    return;
            }
        }
    }

    public zzqy(@Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Lcom/google/android/gms/internal/zzrf$zza;", ")V"}) Context $r1, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Lcom/google/android/gms/internal/zzrf$zza;", ")V"}) zzqw $r2, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Lcom/google/android/gms/internal/zzrf$zza;", ")V"}) Lock $r3, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Lcom/google/android/gms/internal/zzrf$zza;", ")V"}) Looper $r4, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Lcom/google/android/gms/internal/zzrf$zza;", ")V"}) GoogleApiAvailabilityLight $r5, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Lcom/google/android/gms/internal/zzrf$zza;", ")V"}) Map<zzc<?>, zze> $r6, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Lcom/google/android/gms/internal/zzrf$zza;", ")V"}) zzg $r7, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Lcom/google/android/gms/internal/zzrf$zza;", ")V"}) Map<Api<?>, Integer> $r8, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Lcom/google/android/gms/internal/zzrf$zza;", ")V"}) com.google.android.gms.common.api.Api.zza<? extends zzach, zzaci> $r9, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Lcom/google/android/gms/internal/zzrf$zza;", ")V"}) ArrayList<zzqn> $r10, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/internal/zzqw;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;", "Lcom/google/android/gms/internal/zzrf$zza;", ")V"}) com.google.android.gms.internal.zzrf.zza $r11) throws  {
        this.mContext = $r1;
        this.DS = $r3;
        this.Eb = $r5;
        this.EK = $r6;
        this.Ep = $r7;
        this.Eq = $r8;
        this.CI = $r9;
        this.DI = $r2;
        this.Fd = $r11;
        Iterator $r13 = $r10.iterator();
        while ($r13.hasNext()) {
            ((zzqn) $r13.next()).zza(this);
        }
        this.EY = new zzb(this, $r4);
        this.EX = $r3.newCondition();
        this.Fa = new zzqv(this);
    }

    public ConnectionResult blockingConnect() throws  {
        connect();
        while (isConnecting()) {
            try {
                this.EX.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
        }
        return isConnected() ? ConnectionResult.BB : this.Fb != null ? this.Fb : new ConnectionResult(13, null);
    }

    public ConnectionResult blockingConnect(long $l0, TimeUnit $r1) throws  {
        connect();
        $l0 = $r1.toNanos($l0);
        while (isConnecting()) {
            if ($l0 <= 0) {
                try {
                    disconnect();
                    return new ConnectionResult(14, null);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return new ConnectionResult(15, null);
                }
            }
            $l0 = this.EX.awaitNanos($l0);
        }
        return isConnected() ? ConnectionResult.BB : this.Fb != null ? this.Fb : new ConnectionResult(13, null);
    }

    public void connect() throws  {
        this.Fa.connect();
    }

    public void disconnect() throws  {
        if (this.Fa.disconnect()) {
            this.EZ.clear();
        }
    }

    public void dump(String $r1, FileDescriptor $r2, PrintWriter $r3, String[] $r4) throws  {
        String $r5 = String.valueOf($r1).concat("  ");
        $r3.append($r1).append("mState=").println(this.Fa);
        for (Api $r12 : this.Eq.keySet()) {
            $r3.append($r1).append($r12.getName()).println(":");
            ((zze) this.EK.get($r12.zzarl())).dump($r5, $r2, $r3, $r4);
        }
    }

    @Nullable
    public ConnectionResult getConnectionResult(@NonNull @Signature({"(", "Lcom/google/android/gms/common/api/Api", "<*>;)", "Lcom/google/android/gms/common/ConnectionResult;"}) Api<?> $r1) throws  {
        zzc $r2 = $r1.zzarl();
        if (this.EK.containsKey($r2)) {
            if (((zze) this.EK.get($r2)).isConnected()) {
                return ConnectionResult.BB;
            }
            if (this.EZ.containsKey($r2)) {
                return (ConnectionResult) this.EZ.get($r2);
            }
        }
        return null;
    }

    public boolean isConnected() throws  {
        return this.Fa instanceof zzqt;
    }

    public boolean isConnecting() throws  {
        return this.Fa instanceof zzqu;
    }

    public void onConnected(@Nullable Bundle $r1) throws  {
        this.DS.lock();
        try {
            this.Fa.onConnected($r1);
        } finally {
            this.DS.unlock();
        }
    }

    public void onConnectionSuspended(int $i0) throws  {
        this.DS.lock();
        try {
            this.Fa.onConnectionSuspended($i0);
        } finally {
            this.DS.unlock();
        }
    }

    public void zza(@NonNull @Signature({"(", "Lcom/google/android/gms/common/ConnectionResult;", "Lcom/google/android/gms/common/api/Api", "<*>;I)V"}) ConnectionResult $r1, @NonNull @Signature({"(", "Lcom/google/android/gms/common/ConnectionResult;", "Lcom/google/android/gms/common/api/Api", "<*>;I)V"}) Api<?> $r2, @Signature({"(", "Lcom/google/android/gms/common/ConnectionResult;", "Lcom/google/android/gms/common/api/Api", "<*>;I)V"}) int $i0) throws  {
        this.DS.lock();
        try {
            this.Fa.zza($r1, $r2, $i0);
        } finally {
            this.DS.unlock();
        }
    }

    void zza(zza $r1) throws  {
        this.EY.sendMessage(this.EY.obtainMessage(1, $r1));
    }

    public boolean zza(zzru com_google_android_gms_internal_zzru) throws  {
        return false;
    }

    public void zzarx() throws  {
    }

    public void zzass() throws  {
        if (isConnected()) {
            ((zzqt) this.Fa).zzatb();
        }
    }

    void zzatq() throws  {
        this.DS.lock();
        try {
            this.Fa = new zzqu(this, this.Ep, this.Eq, this.Eb, this.CI, this.DS, this.mContext);
            this.Fa.begin();
            Condition $r9 = this.EX;
            $r9.signalAll();
        } finally {
            this.DS.unlock();
        }
    }

    void zzatr() throws  {
        this.DS.lock();
        try {
            this.DI.zzatn();
            this.Fa = new zzqt(this);
            this.Fa.begin();
            this.EX.signalAll();
        } finally {
            this.DS.unlock();
        }
    }

    void zzats() throws  {
        for (zze disconnect : this.EK.values()) {
            disconnect.disconnect();
        }
    }

    void zzb(RuntimeException $r1) throws  {
        this.EY.sendMessage(this.EY.obtainMessage(2, $r1));
    }

    public <A extends com.google.android.gms.common.api.Api.zzb, R extends Result, T extends com.google.android.gms.internal.zzqk.zza<R, A>> T zzc(@NonNull @Signature({"<A::", "Lcom/google/android/gms/common/api/Api$zzb;", "R::", "Lcom/google/android/gms/common/api/Result;", "T:", "Lcom/google/android/gms/internal/zzqk$zza", "<TR;TA;>;>(TT;)TT;"}) T $r1) throws  {
        $r1.zzasn();
        return this.Fa.zzc($r1);
    }

    public <A extends com.google.android.gms.common.api.Api.zzb, T extends com.google.android.gms.internal.zzqk.zza<? extends Result, A>> T zzd(@NonNull @Signature({"<A::", "Lcom/google/android/gms/common/api/Api$zzb;", "T:", "Lcom/google/android/gms/internal/zzqk$zza", "<+", "Lcom/google/android/gms/common/api/Result;", "TA;>;>(TT;)TT;"}) T $r1) throws  {
        $r1.zzasn();
        return this.Fa.zzd($r1);
    }

    void zzi(ConnectionResult $r1) throws  {
        this.DS.lock();
        try {
            this.Fb = $r1;
            this.Fa = new zzqv(this);
            this.Fa.begin();
            this.EX.signalAll();
        } finally {
            this.DS.unlock();
        }
    }
}
