package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.google.android.gms.auth.api.signin.internal.zzk;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultStore;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzl;
import dalvik.annotation.Signature;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzqw extends GoogleApiClient implements com.google.android.gms.internal.zzrf.zza {
    private final int CF;
    private final GoogleApiAvailability CH;
    final com.google.android.gms.common.api.Api.zza<? extends zzach, zzaci> CI;
    private final Lock DS;
    private final zzl EC;
    private zzrf ED = null;
    final Queue<com.google.android.gms.internal.zzqk.zza<?, ?>> EE = new LinkedList();
    private volatile boolean EF;
    private long EG = 120000;
    private long EH = 5000;
    private final zza EI;
    zzrc EJ;
    final Map<zzc<?>, zze> EK;
    Set<Scope> EL = new HashSet();
    private final zzrn EM = new zzrn();
    private final ArrayList<zzqn> EN;
    private Integer EO = null;
    Set<zzsa> EP = null;
    final zzsb EQ;
    private final com.google.android.gms.common.internal.zzl.zza ER = new C08411(this);
    final zzg Ep;
    final Map<Api<?>, Integer> Eq;
    private final Context mContext;
    private final Looper zzaih;

    /* compiled from: dalvik_source_com.waze.apk */
    class C08411 implements com.google.android.gms.common.internal.zzl.zza {
        final /* synthetic */ zzqw ES;

        C08411(zzqw $r1) throws  {
            this.ES = $r1;
        }

        public boolean isConnected() throws  {
            return this.ES.isConnected();
        }

        public Bundle zzapt() throws  {
            return null;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    final class zza extends Handler {
        final /* synthetic */ zzqw ES;

        zza(zzqw $r1, Looper $r2) throws  {
            this.ES = $r1;
            super($r2);
        }

        public void handleMessage(Message $r1) throws  {
            switch ($r1.what) {
                case 1:
                    this.ES.zzatl();
                    return;
                case 2:
                    this.ES.resume();
                    return;
                default:
                    String str = "GoogleApiClientImpl";
                    Log.w(str, "Unknown message id: " + $r1.what);
                    return;
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    static class zzb extends com.google.android.gms.internal.zzrc.zza {
        private WeakReference<zzqw> EW;

        zzb(zzqw $r1) throws  {
            this.EW = new WeakReference($r1);
        }

        public void zzasl() throws  {
            zzqw $r3 = (zzqw) this.EW.get();
            if ($r3 != null) {
                $r3.resume();
            }
        }
    }

    public zzqw(@Signature({"(", "Landroid/content/Context;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/GoogleApiAvailability;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;II", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;)V"}) Context $r1, @Signature({"(", "Landroid/content/Context;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/GoogleApiAvailability;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;II", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;)V"}) Lock $r2, @Signature({"(", "Landroid/content/Context;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/GoogleApiAvailability;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;II", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;)V"}) Looper $r3, @Signature({"(", "Landroid/content/Context;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/GoogleApiAvailability;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;II", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;)V"}) zzg $r4, @Signature({"(", "Landroid/content/Context;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/GoogleApiAvailability;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;II", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;)V"}) GoogleApiAvailability $r5, @Signature({"(", "Landroid/content/Context;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/GoogleApiAvailability;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;II", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;)V"}) com.google.android.gms.common.api.Api.zza<? extends zzach, zzaci> $r6, @Signature({"(", "Landroid/content/Context;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/GoogleApiAvailability;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;II", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;)V"}) Map<Api<?>, Integer> $r7, @Signature({"(", "Landroid/content/Context;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/GoogleApiAvailability;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;II", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;)V"}) List<ConnectionCallbacks> $r8, @Signature({"(", "Landroid/content/Context;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/GoogleApiAvailability;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;II", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;)V"}) List<OnConnectionFailedListener> $r9, @Signature({"(", "Landroid/content/Context;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/GoogleApiAvailability;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;II", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;)V"}) Map<zzc<?>, zze> $r10, @Signature({"(", "Landroid/content/Context;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/GoogleApiAvailability;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;II", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;)V"}) int $i0, @Signature({"(", "Landroid/content/Context;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/GoogleApiAvailability;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;II", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;)V"}) int $i1, @Signature({"(", "Landroid/content/Context;", "Ljava/util/concurrent/locks/Lock;", "Landroid/os/Looper;", "Lcom/google/android/gms/common/internal/zzg;", "Lcom/google/android/gms/common/GoogleApiAvailability;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/GoogleApiClient$ConnectionCallbacks;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/common/api/GoogleApiClient$OnConnectionFailedListener;", ">;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;II", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/internal/zzqn;", ">;)V"}) ArrayList<zzqn> $r11) throws  {
        GoogleApiClient googleApiClient = this;
        this.mContext = $r1;
        this.DS = $r2;
        this.EC = new zzl($r3, this.ER);
        this.zzaih = $r3;
        this.EI = new zza(this, $r3);
        this.CH = $r5;
        this.CF = $i0;
        if (this.CF >= 0) {
            this.EO = Integer.valueOf($i1);
        }
        this.Eq = $r7;
        this.EK = $r10;
        this.EN = $r11;
        Map $r72 = this.EK;
        this.EQ = new zzsb($r72);
        for (ConnectionCallbacks $r23 : $r8) {
            this.EC.registerConnectionCallbacks($r23);
        }
        for (OnConnectionFailedListener $r24 : $r9) {
            this.EC.registerConnectionFailedListener($r24);
        }
        this.Ep = $r4;
        this.CI = $r6;
    }

    private void resume() throws  {
        this.DS.lock();
        try {
            if (isResuming()) {
                zzatk();
            }
            this.DS.unlock();
        } catch (Throwable th) {
            this.DS.unlock();
        }
    }

    public static int zza(@Signature({"(", "Ljava/lang/Iterable", "<", "Lcom/google/android/gms/common/api/Api$zze;", ">;Z)I"}) Iterable<zze> $r0, @Signature({"(", "Ljava/lang/Iterable", "<", "Lcom/google/android/gms/common/api/Api$zze;", ">;Z)I"}) boolean $z0) throws  {
        boolean $z1 = false;
        boolean $z2 = false;
        for (zze $r3 : $r0) {
            if ($r3.zzaec()) {
                $z2 = true;
            }
            if ($r3.zzaer()) {
                $z1 = true;
            }
        }
        return $z2 ? $z1 ? $z0 ? 2 : 1 : 1 : 3;
    }

    private void zza(final GoogleApiClient $r1, final zzrw $r2, final boolean $z0) throws  {
        zzso.Ks.zzj($r1).setResultCallback(new ResultCallback<Status>(this) {
            final /* synthetic */ zzqw ES;

            public /* synthetic */ void onResult(@NonNull Result $r1) throws  {
                zzv((Status) $r1);
            }

            public void zzv(@NonNull Status $r1) throws  {
                zzk.zzbc(this.ES.mContext).zzafd();
                if ($r1.isSuccess() && this.ES.isConnected()) {
                    this.ES.reconnect();
                }
                $r2.zzc($r1);
                if ($z0) {
                    $r1.disconnect();
                }
            }
        });
    }

    private void zzatk() throws  {
        this.EC.zzawu();
        this.ED.connect();
    }

    private void zzatl() throws  {
        this.DS.lock();
        try {
            if (zzatn()) {
                zzatk();
            }
            this.DS.unlock();
        } catch (Throwable th) {
            this.DS.unlock();
        }
    }

    private void zzb(@NonNull zzrg $r1) throws  {
        if (this.CF >= 0) {
            zzqi.zza($r1).zzhs(this.CF);
            return;
        }
        throw new IllegalStateException("Called stopAutoManage but automatic lifecycle management is not enabled.");
    }

    private void zzhv(int $i0) throws  {
        if (this.EO == null) {
            this.EO = Integer.valueOf($i0);
        } else if (this.EO.intValue() != $i0) {
            String $r5 = String.valueOf(zzhw($i0));
            String $r6 = String.valueOf(zzhw(this.EO.intValue()));
            throw new IllegalStateException(new StringBuilder((String.valueOf($r5).length() + 51) + String.valueOf($r6).length()).append("Cannot use sign-in mode: ").append($r5).append(". Mode was already set to ").append($r6).toString());
        }
        if (this.ED == null) {
            Map $r9 = this.EK;
            Object obj = null;
            Object obj2 = null;
            for (zze $r13 : $r9.values()) {
                if ($r13.zzaec()) {
                    obj2 = 1;
                }
                if ($r13.zzaer()) {
                    obj = 1;
                }
            }
            switch (this.EO.intValue()) {
                case 1:
                    if (obj2 == null) {
                        throw new IllegalStateException("SIGN_IN_MODE_REQUIRED cannot be used on a GoogleApiClient that does not contain any authenticated APIs. Use connect() instead.");
                    } else if (obj != null) {
                        throw new IllegalStateException("Cannot use SIGN_IN_MODE_REQUIRED with GOOGLE_SIGN_IN_API. Use connect(SIGN_IN_MODE_OPTIONAL) instead.");
                    }
                    break;
                case 2:
                    if (obj2 != null) {
                        this.ED = zzqo.zza(this.mContext, this, this.DS, this.zzaih, this.CH, this.EK, this.Ep, this.Eq, this.CI, this.EN);
                        return;
                    }
                    break;
                case 3:
                    break;
                default:
                    break;
            }
            this.ED = new zzqy(this.mContext, this, this.DS, this.zzaih, this.CH, this.EK, this.Ep, this.Eq, this.CI, this.EN, this);
        }
    }

    static String zzhw(int $i0) throws  {
        switch ($i0) {
            case 1:
                return "SIGN_IN_MODE_REQUIRED";
            case 2:
                return "SIGN_IN_MODE_OPTIONAL";
            case 3:
                return "SIGN_IN_MODE_NONE";
            default:
                return "UNKNOWN";
        }
    }

    public ConnectionResult blockingConnect() throws  {
        boolean $z0 = true;
        zzab.zza(Looper.myLooper() != Looper.getMainLooper(), (Object) "blockingConnect must not be called on the UI thread");
        this.DS.lock();
        try {
            if (this.CF >= 0) {
                if (this.EO == null) {
                    $z0 = false;
                }
                zzab.zza($z0, (Object) "Sign-in mode should have been set explicitly by auto-manage.");
            } else if (this.EO == null) {
                this.EO = Integer.valueOf(zza(this.EK.values(), false));
            } else if (this.EO.intValue() == 2) {
                throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            zzhv(this.EO.intValue());
            this.EC.zzawu();
            ConnectionResult $r7 = this.ED.blockingConnect();
            return $r7;
        } finally {
            this.DS.unlock();
        }
    }

    public ConnectionResult blockingConnect(long $l0, @NonNull TimeUnit $r1) throws  {
        boolean $z0 = false;
        if (Looper.myLooper() != Looper.getMainLooper()) {
            $z0 = true;
        }
        zzab.zza($z0, (Object) "blockingConnect must not be called on the UI thread");
        zzab.zzb((Object) $r1, (Object) "TimeUnit must not be null");
        this.DS.lock();
        try {
            if (this.EO == null) {
                this.EO = Integer.valueOf(zza(this.EK.values(), false));
            } else if (this.EO.intValue() == 2) {
                throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            zzhv(this.EO.intValue());
            this.EC.zzawu();
            ConnectionResult $r10 = this.ED.blockingConnect($l0, $r1);
            return $r10;
        } finally {
            this.DS.unlock();
        }
    }

    public PendingResult<Status> clearDefaultAccountAndReconnect() throws  {
        zzab.zza(isConnected(), (Object) "GoogleApiClient is not connected yet.");
        zzab.zza(this.EO.intValue() != 2, (Object) "Cannot use clearDefaultAccountAndReconnect with GOOGLE_SIGN_IN_API");
        final zzrw $r2 = new zzrw((GoogleApiClient) this);
        if (this.EK.containsKey(zzso.ca)) {
            zza(this, $r2, false);
            return $r2;
        }
        final AtomicReference $r5 = new AtomicReference();
        Builder $r8 = new Builder(this.mContext).addApi(zzso.API).addConnectionCallbacks(new ConnectionCallbacks(this) {
            final /* synthetic */ zzqw ES;

            public void onConnected(Bundle bundle) throws  {
                this.ES.zza((GoogleApiClient) $r5.get(), $r2, true);
            }

            public void onConnectionSuspended(int i) throws  {
            }
        }).addOnConnectionFailedListener(new OnConnectionFailedListener(this) {
            final /* synthetic */ zzqw ES;

            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) throws  {
                $r2.zzc(new Status(8));
            }
        });
        zza $r11 = this.EI;
        GoogleApiClient $r12 = $r8.setHandler($r11).build();
        $r5.set($r12);
        $r12.connect();
        return $r2;
    }

    public void connect() throws  {
        boolean $z0 = false;
        this.DS.lock();
        try {
            if (this.CF >= 0) {
                if (this.EO != null) {
                    $z0 = true;
                }
                zzab.zza($z0, (Object) "Sign-in mode should have been set explicitly by auto-manage.");
            } else if (this.EO == null) {
                this.EO = Integer.valueOf(zza(this.EK.values(), false));
            } else if (this.EO.intValue() == 2) {
                throw new IllegalStateException("Cannot call connect() when SignInMode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            connect(this.EO.intValue());
        } finally {
            this.DS.unlock();
        }
    }

    public void connect(int $i0) throws  {
        boolean $z0 = true;
        this.DS.lock();
        if (!($i0 == 3 || $i0 == 1 || $i0 == 2)) {
            $z0 = false;
        }
        try {
            zzab.zzb($z0, "Illegal sign-in mode: " + $i0);
            zzhv($i0);
            zzatk();
        } finally {
            this.DS.unlock();
        }
    }

    public void disconnect() throws  {
        this.DS.lock();
        try {
            this.EQ.release();
            if (this.ED != null) {
                this.ED.disconnect();
            }
            this.EM.release();
            for (com.google.android.gms.internal.zzqk.zza $r8 : this.EE) {
                $r8.zza(null);
                $r8.cancel();
            }
            this.EE.clear();
            if (this.ED != null) {
                zzatn();
                this.EC.zzawt();
                this.DS.unlock();
            }
        } finally {
            this.DS.unlock();
        }
    }

    public void dump(String $r1, FileDescriptor $r2, PrintWriter $r3, String[] $r4) throws  {
        $r3.append($r1).append("mContext=").println(this.mContext);
        $r3.append($r1).append("mResuming=").print(this.EF);
        $r3.append(" mWorkQueue.size()=").print(this.EE.size());
        this.EQ.dump($r3);
        if (this.ED != null) {
            this.ED.dump($r1, $r2, $r3, $r4);
        }
    }

    @NonNull
    public ConnectionResult getConnectionResult(@NonNull @Signature({"(", "Lcom/google/android/gms/common/api/Api", "<*>;)", "Lcom/google/android/gms/common/ConnectionResult;"}) Api<?> $r1) throws  {
        this.DS.lock();
        try {
            if (!isConnected() && !isResuming()) {
                throw new IllegalStateException("Cannot invoke getConnectionResult unless GoogleApiClient is connected");
            } else if (this.EK.containsKey($r1.zzarl())) {
                ConnectionResult $r8 = this.ED.getConnectionResult($r1);
                if ($r8 != null) {
                    this.DS.unlock();
                    return $r8;
                } else if (isResuming()) {
                    $r8 = ConnectionResult.BB;
                    return $r8;
                } else {
                    Log.i("GoogleApiClientImpl", zzatp());
                    Log.wtf("GoogleApiClientImpl", String.valueOf($r1.getName()).concat(" requested in getConnectionResult is not connected but is not present in the failed  connections map"), new Exception());
                    $r8 = new ConnectionResult(8, null);
                    this.DS.unlock();
                    return $r8;
                }
            } else {
                throw new IllegalArgumentException(String.valueOf($r1.getName()).concat(" was never registered with GoogleApiClient"));
            }
        } finally {
            this.DS.unlock();
        }
    }

    public Context getContext() throws  {
        return this.mContext;
    }

    public Looper getLooper() throws  {
        return this.zzaih;
    }

    public int getSessionId() throws  {
        return System.identityHashCode(this);
    }

    public boolean hasConnectedApi(@NonNull @Signature({"(", "Lcom/google/android/gms/common/api/Api", "<*>;)Z"}) Api<?> $r1) throws  {
        zze $r5 = (zze) this.EK.get($r1.zzarl());
        return $r5 != null && $r5.isConnected();
    }

    public boolean isConnected() throws  {
        return this.ED != null && this.ED.isConnected();
    }

    public boolean isConnecting() throws  {
        return this.ED != null && this.ED.isConnecting();
    }

    public boolean isConnectionCallbacksRegistered(@NonNull ConnectionCallbacks $r1) throws  {
        return this.EC.isConnectionCallbacksRegistered($r1);
    }

    public boolean isConnectionFailedListenerRegistered(@NonNull OnConnectionFailedListener $r1) throws  {
        return this.EC.isConnectionFailedListenerRegistered($r1);
    }

    boolean isResuming() throws  {
        return this.EF;
    }

    public void reconnect() throws  {
        disconnect();
        connect();
    }

    public void registerConnectionCallbacks(@NonNull ConnectionCallbacks $r1) throws  {
        this.EC.registerConnectionCallbacks($r1);
    }

    public void registerConnectionFailedListener(@NonNull OnConnectionFailedListener $r1) throws  {
        this.EC.registerConnectionFailedListener($r1);
    }

    public void stopAutoManage(@NonNull FragmentActivity $r1) throws  {
        zzb(new zzrg($r1));
    }

    public void unregisterConnectionCallbacks(@NonNull ConnectionCallbacks $r1) throws  {
        this.EC.unregisterConnectionCallbacks($r1);
    }

    public void unregisterConnectionFailedListener(@NonNull OnConnectionFailedListener $r1) throws  {
        this.EC.unregisterConnectionFailedListener($r1);
    }

    @NonNull
    public <C extends zze> C zza(@NonNull @Signature({"<C::", "Lcom/google/android/gms/common/api/Api$zze;", ">(", "Lcom/google/android/gms/common/api/Api$zzc", "<TC;>;)TC;"}) zzc<C> $r1) throws  {
        Object $r4 = (zze) this.EK.get($r1);
        zzab.zzb($r4, (Object) "Appropriate Api was not requested.");
        return $r4;
    }

    public void zza(ResultStore $r1) throws  {
        this.EQ.zza($r1);
    }

    public void zza(zzsa $r1) throws  {
        this.DS.lock();
        try {
            if (this.EP == null) {
                this.EP = new HashSet();
            }
            this.EP.add($r1);
        } finally {
            this.DS.unlock();
        }
    }

    public boolean zza(@NonNull @Signature({"(", "Lcom/google/android/gms/common/api/Api", "<*>;)Z"}) Api<?> $r1) throws  {
        return this.EK.containsKey($r1.zzarl());
    }

    public boolean zza(zzru $r1) throws  {
        return this.ED != null && this.ED.zza($r1);
    }

    public void zzarx() throws  {
        if (this.ED != null) {
            this.ED.zzarx();
        }
    }

    void zzatm() throws  {
        if (!isResuming()) {
            this.EF = true;
            if (this.EJ == null) {
                this.EJ = this.CH.zza(this.mContext.getApplicationContext(), new zzb(this));
            }
            this.EI.sendMessageDelayed(this.EI.obtainMessage(1), this.EG);
            this.EI.sendMessageDelayed(this.EI.obtainMessage(2), this.EH);
        }
    }

    boolean zzatn() throws  {
        if (!isResuming()) {
            return false;
        }
        this.EF = false;
        this.EI.removeMessages(2);
        this.EI.removeMessages(1);
        if (this.EJ != null) {
            this.EJ.unregister();
            this.EJ = null;
        }
        return true;
    }

    boolean zzato() throws  {
        boolean $z1 = false;
        this.DS.lock();
        try {
            if (this.EP == null) {
                return false;
            }
            if (!this.EP.isEmpty()) {
                $z1 = true;
            }
            this.DS.unlock();
            return $z1;
        } finally {
            this.DS.unlock();
        }
    }

    String zzatp() throws  {
        StringWriter $r3 = new StringWriter();
        dump("", null, new PrintWriter($r3), null);
        return $r3.toString();
    }

    <C extends zze> C zzb(@Signature({"<C::", "Lcom/google/android/gms/common/api/Api$zze;", ">(", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;)TC;"}) zzc<?> $r1) throws  {
        Object $r4 = (zze) this.EK.get($r1);
        zzab.zzb($r4, (Object) "Appropriate Api was not requested.");
        return $r4;
    }

    public void zzb(zzsa $r1) throws  {
        this.DS.lock();
        try {
            if (this.EP == null) {
                Log.wtf("GoogleApiClientImpl", "Attempted to remove pending transform when no transforms are registered.", new Exception());
            } else if (!this.EP.remove($r1)) {
                Log.wtf("GoogleApiClientImpl", "Failed to remove pending transform - this may lead to memory leaks!", new Exception());
            } else if (!zzato()) {
                this.ED.zzass();
            }
            this.DS.unlock();
        } catch (Throwable th) {
            this.DS.unlock();
        }
    }

    public <A extends com.google.android.gms.common.api.Api.zzb, R extends Result, T extends com.google.android.gms.internal.zzqk.zza<R, A>> T zzc(@NonNull @Signature({"<A::", "Lcom/google/android/gms/common/api/Api$zzb;", "R::", "Lcom/google/android/gms/common/api/Result;", "T:", "Lcom/google/android/gms/internal/zzqk$zza", "<TR;TA;>;>(TT;)TT;"}) T $r1) throws  {
        zzab.zzb($r1.zzarl() != null, (Object) "This task can not be enqueued (it's probably a Batch or malformed)");
        boolean $z0 = this.EK.containsKey($r1.zzarl());
        String $r5 = $r1.zzars() != null ? $r1.zzars().getName() : "the API";
        zzab.zzb($z0, new StringBuilder(String.valueOf($r5).length() + 65).append("GoogleApiClient is not configured to use ").append($r5).append(" required for this call.").toString());
        this.DS.lock();
        try {
            com.google.android.gms.internal.zzqk.zza $r12;
            if (this.ED == null) {
                this.EE.add($r1);
                return $r12;
            }
            $r12 = this.ED.zzc($r1);
            this.DS.unlock();
            return $r12;
        } finally {
            this.DS.unlock();
        }
    }

    public <A extends com.google.android.gms.common.api.Api.zzb, T extends com.google.android.gms.internal.zzqk.zza<? extends Result, A>> T zzd(@NonNull @Signature({"<A::", "Lcom/google/android/gms/common/api/Api$zzb;", "T:", "Lcom/google/android/gms/internal/zzqk$zza", "<+", "Lcom/google/android/gms/common/api/Result;", "TA;>;>(TT;)TT;"}) T $r1) throws  {
        zzab.zzb($r1.zzarl() != null, (Object) "This task can not be executed (it's probably a Batch or malformed)");
        boolean $z0 = this.EK.containsKey($r1.zzarl());
        String $r5 = $r1.zzars() != null ? $r1.zzars().getName() : "the API";
        zzab.zzb($z0, new StringBuilder(String.valueOf($r5).length() + 65).append("GoogleApiClient is not configured to use ").append($r5).append(" required for this call.").toString());
        this.DS.lock();
        try {
            if (this.ED == null) {
                throw new IllegalStateException("GoogleApiClient is not connected yet.");
            } else if (isResuming()) {
                this.EE.add($r1);
                while (!this.EE.isEmpty()) {
                    com.google.android.gms.internal.zzqk.zza $r14 = (com.google.android.gms.internal.zzqk.zza) this.EE.remove();
                    zzsb com_google_android_gms_internal_zzsb = this.EQ;
                    zzsb $r15 = com_google_android_gms_internal_zzsb;
                    com_google_android_gms_internal_zzsb.zzg($r14);
                    $r14.zzag(Status.CS);
                }
                return $r1;
            } else {
                $r1 = this.ED.zzd($r1);
                this.DS.unlock();
                return $r1;
            }
        } finally {
            this.DS.unlock();
        }
    }

    public void zzd(ConnectionResult $r1) throws  {
        if (!this.CH.isPlayServicesPossiblyUpdating(this.mContext, $r1.getErrorCode())) {
            zzatn();
        }
        if (!isResuming()) {
            this.EC.zzm($r1);
            this.EC.zzawt();
        }
    }

    public void zze(int $i0, boolean $z0) throws  {
        if ($i0 == 1 && !$z0) {
            zzatm();
        }
        this.EQ.zzaus();
        this.EC.zzio($i0);
        this.EC.zzawt();
        if ($i0 == 2) {
            zzatk();
        }
    }

    public void zzr(Bundle $r1) throws  {
        while (!this.EE.isEmpty()) {
            zzd((com.google.android.gms.internal.zzqk.zza) this.EE.remove());
        }
        this.EC.zzt($r1);
    }

    public <L> zzrm<L> zzw(@NonNull @Signature({"<", "L:Ljava/lang/Object;", ">(T", "L;", ")", "Lcom/google/android/gms/internal/zzrm", "<T", "L;", ">;"}) L $r1) throws  {
        this.DS.lock();
        try {
            zzrm $r2 = this.EM.zzb($r1, this.zzaih);
            return $r2;
        } finally {
            this.DS.unlock();
        }
    }
}
