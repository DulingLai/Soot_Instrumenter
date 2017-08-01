package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.BinderThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.signin.internal.SignInResponse;
import dalvik.annotation.Signature;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;

/* compiled from: dalvik_source_com.waze.apk */
public class zzqu implements zzqx {
    private final com.google.android.gms.common.api.Api.zza<? extends zzach, zzaci> CI;
    private final Lock DS;
    private final zzqy DY;
    private final GoogleApiAvailabilityLight Eb;
    private ConnectionResult Ec;
    private int Ed;
    private int Ee = 0;
    private int Ef;
    private final Bundle Eg = new Bundle();
    private final Set<com.google.android.gms.common.api.Api.zzc> Eh = new HashSet();
    private zzach Ei;
    private int Ej;
    private boolean Ek;
    private boolean El;
    private zzq Em;
    private boolean En;
    private boolean Eo;
    private final zzg Ep;
    private final Map<Api<?>, Integer> Eq;
    private ArrayList<Future<?>> Er = new ArrayList();
    private final Context mContext;

    /* compiled from: dalvik_source_com.waze.apk */
    class C08371 implements Runnable {
        final /* synthetic */ zzqu Es;

        C08371(zzqu $r1) throws  {
            this.Es = $r1;
        }

        public void run() throws  {
            this.Es.Eb.zzbn(this.Es.mContext);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zza implements com.google.android.gms.common.internal.zzd.zzf {
        private final int DG;
        private final WeakReference<zzqu> Et;
        private final Api<?> zn;

        public zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqu;", "Lcom/google/android/gms/common/api/Api", "<*>;I)V"}) zzqu $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqu;", "Lcom/google/android/gms/common/api/Api", "<*>;I)V"}) Api<?> $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqu;", "Lcom/google/android/gms/common/api/Api", "<*>;I)V"}) int $i0) throws  {
            this.Et = new WeakReference($r1);
            this.zn = $r2;
            this.DG = $i0;
        }

        public void zzh(@NonNull ConnectionResult $r1) throws  {
            boolean $z0 = false;
            zzqu $r4 = (zzqu) this.Et.get();
            if ($r4 != null) {
                if (Looper.myLooper() == $r4.DY.DI.getLooper()) {
                    $z0 = true;
                }
                zzab.zza($z0, (Object) "onReportServiceBinding must be called on the GoogleApiClient handler thread");
                $r4.DS.lock();
                try {
                    if ($r4.zzht(0)) {
                        if (!$r1.isSuccess()) {
                            $r4.zzb($r1, this.zn, this.DG);
                        }
                        if ($r4.zzatd()) {
                            $r4.zzate();
                        }
                        $r4.DS.unlock();
                    }
                } finally {
                    $r4.DS.unlock();
                }
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private abstract class zzf implements Runnable {
        final /* synthetic */ zzqu Es;

        private zzf(zzqu $r1) throws  {
            this.Es = $r1;
        }

        @WorkerThread
        public void run() throws  {
            this.Es.DS.lock();
            try {
                if (!Thread.interrupted()) {
                    zzatc();
                    this.Es.DS.unlock();
                }
            } catch (RuntimeException $r3) {
                this.Es.DY.zzb($r3);
            } finally {
                this.Es.DS.unlock();
            }
        }

        @WorkerThread
        protected abstract void zzatc() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private class zzb extends zzf {
        final /* synthetic */ zzqu Es;
        private final Map<com.google.android.gms.common.api.Api.zze, zza> Eu;

        public zzb(@Signature({"(", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zze;", "Lcom/google/android/gms/internal/zzqu$zza;", ">;)V"}) zzqu $r1, @Signature({"(", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zze;", "Lcom/google/android/gms/internal/zzqu$zza;", ">;)V"}) Map<com.google.android.gms.common.api.Api.zze, zza> $r2) throws  {
            this.Es = $r1;
            super();
            this.Eu = $r2;
        }

        @WorkerThread
        public void zzatc() throws  {
            boolean $z0 = true;
            int $i0 = 0;
            boolean $z1 = true;
            boolean $z2 = false;
            for (com.google.android.gms.common.api.Api.zze $r5 : this.Eu.keySet()) {
                if (!$r5.zzarn()) {
                    $z1 = false;
                } else if (((zza) this.Eu.get($r5)).DG == 0) {
                    $z2 = true;
                    break;
                } else {
                    $z2 = true;
                }
            }
            $z0 = $z2;
            $z2 = false;
            if ($z0) {
                zzqu $r7 = this.Es;
                GoogleApiAvailabilityLight $r8 = $r7.Eb;
                $r7 = this.Es;
                $i0 = $r8.isGooglePlayServicesAvailable($r7.mContext);
            }
            if ($i0 == 0 || !($z2 || $z1)) {
                $r7 = this.Es;
                if ($r7.Ek) {
                    $r7 = this.Es;
                    zzqu $r72 = $r7;
                    $r7.Ei.connect();
                }
                for (com.google.android.gms.common.api.Api.zze $r52 : this.Eu.keySet()) {
                    com.google.android.gms.common.internal.zzd.zzf $r14 = (com.google.android.gms.common.internal.zzd.zzf) this.Eu.get($r52);
                    if (!$r52.zzarn() || $i0 == 0) {
                        $r52.zza($r14);
                    } else {
                        $r7 = this.Es;
                        final com.google.android.gms.common.internal.zzd.zzf com_google_android_gms_common_internal_zzd_zzf = $r14;
                        $r7.DY.zza(new zza(this, this.Es) {
                            final /* synthetic */ zzb Ew;

                            public void zzatc() throws  {
                                com_google_android_gms_common_internal_zzd_zzf.zzh(new ConnectionResult(16, null));
                            }
                        });
                    }
                }
                return;
            }
            ConnectionResult connectionResult = new ConnectionResult($i0, null);
            $r7 = this.Es;
            final ConnectionResult connectionResult2 = connectionResult;
            $r7.DY.zza(new zza(this, this.Es) {
                final /* synthetic */ zzb Ew;

                public void zzatc() throws  {
                    this.Ew.Es.zzg(connectionResult2);
                }
            });
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private class zzc extends zzf {
        final /* synthetic */ zzqu Es;
        private final ArrayList<com.google.android.gms.common.api.Api.zze> Ey;

        public zzc(@Signature({"(", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/common/api/Api$zze;", ">;)V"}) zzqu $r1, @Signature({"(", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/common/api/Api$zze;", ">;)V"}) ArrayList<com.google.android.gms.common.api.Api.zze> $r2) throws  {
            this.Es = $r1;
            super();
            this.Ey = $r2;
        }

        @WorkerThread
        public void zzatc() throws  {
            this.Es.DY.DI.EL = this.Es.zzatj();
            Iterator $r6 = this.Ey.iterator();
            while ($r6.hasNext()) {
                ((com.google.android.gms.common.api.Api.zze) $r6.next()).zza(this.Es.Em, this.Es.DY.DI.EL);
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zzd extends com.google.android.gms.signin.internal.zzb {
        private final WeakReference<zzqu> Et;

        zzd(zzqu $r1) throws  {
            this.Et = new WeakReference($r1);
        }

        @BinderThread
        public void zzb(final SignInResponse $r1) throws  {
            final zzqu $r5 = (zzqu) this.Et.get();
            if ($r5 != null) {
                $r5.DY.zza(new zza(this, $r5) {
                    final /* synthetic */ zzd EB;

                    public void zzatc() throws  {
                        $r5.zza($r1);
                    }
                });
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private class zze implements ConnectionCallbacks, OnConnectionFailedListener {
        final /* synthetic */ zzqu Es;

        private zze(zzqu $r1) throws  {
            this.Es = $r1;
        }

        public void onConnected(Bundle bundle) throws  {
            this.Es.Ei.zza(new zzd(this.Es));
        }

        public void onConnectionFailed(@NonNull ConnectionResult $r1) throws  {
            this.Es.DS.lock();
            try {
                if (this.Es.zzf($r1)) {
                    this.Es.zzath();
                    this.Es.zzate();
                } else {
                    this.Es.zzg($r1);
                }
                this.Es.DS.unlock();
            } catch (Throwable th) {
                this.Es.DS.unlock();
            }
        }

        public void onConnectionSuspended(int i) throws  {
        }
    }

    public zzqu(@Signature({"(", "Lcom/google/android/gms/internal/zzqy;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/concurrent/locks/Lock;", "Landroid/content/Context;", ")V"}) zzqy $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqy;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/concurrent/locks/Lock;", "Landroid/content/Context;", ")V"}) zzg $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqy;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/concurrent/locks/Lock;", "Landroid/content/Context;", ")V"}) Map<Api<?>, Integer> $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzqy;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/concurrent/locks/Lock;", "Landroid/content/Context;", ")V"}) GoogleApiAvailabilityLight $r4, @Signature({"(", "Lcom/google/android/gms/internal/zzqy;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/concurrent/locks/Lock;", "Landroid/content/Context;", ")V"}) com.google.android.gms.common.api.Api.zza<? extends zzach, zzaci> $r5, @Signature({"(", "Lcom/google/android/gms/internal/zzqy;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/concurrent/locks/Lock;", "Landroid/content/Context;", ")V"}) Lock $r6, @Signature({"(", "Lcom/google/android/gms/internal/zzqy;", "Lcom/google/android/gms/common/internal/zzg;", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api", "<*>;", "Ljava/lang/Integer;", ">;", "Lcom/google/android/gms/common/GoogleApiAvailabilityLight;", "Lcom/google/android/gms/common/api/Api$zza", "<+", "Lcom/google/android/gms/internal/zzach;", "Lcom/google/android/gms/internal/zzaci;", ">;", "Ljava/util/concurrent/locks/Lock;", "Landroid/content/Context;", ")V"}) Context $r7) throws  {
        this.DY = $r1;
        this.Ep = $r2;
        this.Eq = $r3;
        this.Eb = $r4;
        this.CI = $r5;
        this.DS = $r6;
        this.mContext = $r7;
    }

    private void zza(SignInResponse $r1) throws  {
        if (zzht(0)) {
            ConnectionResult $r2 = $r1.zzaxe();
            if ($r2.isSuccess()) {
                ResolveAccountResponse $r3 = $r1.zzcng();
                $r2 = $r3.zzaxe();
                if ($r2.isSuccess()) {
                    this.El = true;
                    this.Em = $r3.zzaxd();
                    this.En = $r3.zzaxf();
                    this.Eo = $r3.zzaxg();
                    zzate();
                    return;
                }
                String $r4 = String.valueOf($r2);
                Log.wtf("GoogleApiClientConnecting", new StringBuilder(String.valueOf($r4).length() + 48).append("Sign-in succeeded with resolve account failure: ").append($r4).toString(), new Exception());
                zzg($r2);
            } else if (zzf($r2)) {
                zzath();
                zzate();
            } else {
                zzg($r2);
            }
        }
    }

    private boolean zza(int $i0, int $i1, ConnectionResult $r1) throws  {
        return ($i1 != 1 || zze($r1)) ? this.Ec == null || $i0 < this.Ed : false;
    }

    private boolean zzatd() throws  {
        this.Ef--;
        if (this.Ef > 0) {
            return false;
        }
        if (this.Ef < 0) {
            Log.i("GoogleApiClientConnecting", this.DY.DI.zzatp());
            Log.wtf("GoogleApiClientConnecting", "GoogleApiClient received too many callbacks for the given step. Clients may be in an unexpected state; GoogleApiClient will now disconnect.", new Exception());
            zzg(new ConnectionResult(8, null));
            return false;
        } else if (this.Ec == null) {
            return true;
        } else {
            this.DY.Fc = this.Ed;
            zzg(this.Ec);
            return false;
        }
    }

    private void zzate() throws  {
        if (this.Ef == 0) {
            if (!this.Ek || this.El) {
                zzatf();
            }
        }
    }

    private void zzatf() throws  {
        ArrayList $r1 = new ArrayList();
        this.Ee = 1;
        this.Ef = this.DY.EK.size();
        for (com.google.android.gms.common.api.Api.zzc $r7 : this.DY.EK.keySet()) {
            if (!this.DY.EZ.containsKey($r7)) {
                $r1.add((com.google.android.gms.common.api.Api.zze) this.DY.EK.get($r7));
            } else if (zzatd()) {
                zzatg();
            }
        }
        if (!$r1.isEmpty()) {
            this.Er.add(zzqz.zzatt().submit(new zzc(this, $r1)));
        }
    }

    private void zzatg() throws  {
        this.DY.zzatr();
        zzqz.zzatt().execute(new C08371(this));
        if (this.Ei != null) {
            if (this.En) {
                this.Ei.zza(this.Em, this.Eo);
            }
            zzbl(false);
        }
        for (com.google.android.gms.common.api.Api.zzc $r10 : this.DY.EZ.keySet()) {
            ((com.google.android.gms.common.api.Api.zze) this.DY.EK.get($r10)).disconnect();
        }
        Bundle $r12 = this.Eg;
        Bundle $r122 = $r12.isEmpty() ? null : this.Eg;
        com.google.android.gms.internal.zzrf.zza com_google_android_gms_internal_zzrf_zza = this.DY.Fd;
        com.google.android.gms.internal.zzrf.zza $r13 = com_google_android_gms_internal_zzrf_zza;
        com_google_android_gms_internal_zzrf_zza.zzr($r122);
    }

    private void zzath() throws  {
        this.Ek = false;
        this.DY.DI.EL = Collections.emptySet();
        for (com.google.android.gms.common.api.Api.zzc $r7 : this.Eh) {
            if (!this.DY.EZ.containsKey($r7)) {
                this.DY.EZ.put($r7, new ConnectionResult(17, null));
            }
        }
    }

    private void zzati() throws  {
        Iterator $r2 = this.Er.iterator();
        while ($r2.hasNext()) {
            ((Future) $r2.next()).cancel(true);
        }
        this.Er.clear();
    }

    private Set<Scope> zzatj() throws  {
        if (this.Ep == null) {
            return Collections.emptySet();
        }
        HashSet $r1 = new HashSet(this.Ep.zzawg());
        Map $r4 = this.Ep.zzawi();
        for (Api $r7 : $r4.keySet()) {
            if (!this.DY.EZ.containsKey($r7.zzarl())) {
                $r1.addAll(((com.google.android.gms.common.internal.zzg.zza) $r4.get($r7)).fN);
            }
        }
        return $r1;
    }

    private void zzb(@Signature({"(", "Lcom/google/android/gms/common/ConnectionResult;", "Lcom/google/android/gms/common/api/Api", "<*>;I)V"}) ConnectionResult $r1, @Signature({"(", "Lcom/google/android/gms/common/ConnectionResult;", "Lcom/google/android/gms/common/api/Api", "<*>;I)V"}) Api<?> $r2, @Signature({"(", "Lcom/google/android/gms/common/ConnectionResult;", "Lcom/google/android/gms/common/api/Api", "<*>;I)V"}) int $i0) throws  {
        if ($i0 != 2) {
            int $i1 = $r2.zzari().getPriority();
            if (zza($i1, $i0, $r1)) {
                this.Ec = $r1;
                this.Ed = $i1;
            }
        }
        this.DY.EZ.put($r2.zzarl(), $r1);
    }

    private void zzbl(boolean $z0) throws  {
        if (this.Ei != null) {
            if (this.Ei.isConnected() && $z0) {
                this.Ei.zzcmv();
            }
            this.Ei.disconnect();
            this.Em = null;
        }
    }

    private boolean zze(ConnectionResult $r1) throws  {
        return $r1.hasResolution() ? true : this.Eb.getErrorResolutionIntent($r1.getErrorCode()) != null;
    }

    private boolean zzf(ConnectionResult $r1) throws  {
        return this.Ej != 2 ? this.Ej == 1 && !$r1.hasResolution() : true;
    }

    private void zzg(ConnectionResult $r1) throws  {
        zzati();
        zzbl(!$r1.hasResolution());
        this.DY.zzi($r1);
        this.DY.Fd.zzd($r1);
    }

    private boolean zzht(int $i0) throws  {
        if (this.Ee == $i0) {
            return true;
        }
        Log.i("GoogleApiClientConnecting", this.DY.DI.zzatp());
        String $r3 = String.valueOf(this);
        Log.i("GoogleApiClientConnecting", new StringBuilder(String.valueOf($r3).length() + 23).append("Unexpected callback in ").append($r3).toString());
        String str = "GoogleApiClientConnecting";
        Log.i(str, "mRemainingConnections=" + this.Ef);
        $r3 = String.valueOf(zzhu(this.Ee));
        String $r5 = String.valueOf(zzhu($i0));
        Log.wtf("GoogleApiClientConnecting", new StringBuilder((String.valueOf($r3).length() + 70) + String.valueOf($r5).length()).append("GoogleApiClient connecting is in step ").append($r3).append(" but received callback for step ").append($r5).toString(), new Exception());
        zzg(new ConnectionResult(8, null));
        return false;
    }

    private String zzhu(int $i0) throws  {
        switch ($i0) {
            case 0:
                return "STEP_SERVICE_BINDINGS_AND_SIGN_IN";
            case 1:
                return "STEP_GETTING_REMOTE_SERVICE";
            default:
                return "UNKNOWN";
        }
    }

    public void begin() throws  {
        this.DY.EZ.clear();
        this.Ek = false;
        this.Ec = null;
        this.Ee = 0;
        this.Ej = 2;
        this.El = false;
        this.En = false;
        HashMap $r1 = new HashMap();
        boolean $z0 = false;
        for (Api api : this.Eq.keySet()) {
            com.google.android.gms.common.api.Api.zze com_google_android_gms_common_api_Api_zze = (com.google.android.gms.common.api.Api.zze) this.DY.EK.get(api.zzarl());
            int $i0 = ((Integer) this.Eq.get(api)).intValue();
            $z0 = (api.zzari().getPriority() == 1) | $z0;
            if (com_google_android_gms_common_api_Api_zze.zzaec()) {
                this.Ek = true;
                if ($i0 < this.Ej) {
                    this.Ej = $i0;
                }
                if ($i0 != 0) {
                    this.Eh.add(api.zzarl());
                }
            }
            $r1.put(com_google_android_gms_common_api_Api_zze, new zza(this, api, $i0));
        }
        if ($z0) {
            this.Ek = false;
        }
        if (this.Ek) {
            zzg $r13 = this.Ep;
            zzqw $r14 = this.DY.DI;
            $r13.zzd(Integer.valueOf($r14.getSessionId()));
            zzqu com_google_android_gms_internal_zzqu = this;
            zze com_google_android_gms_internal_zzqu_zze = new zze();
            com.google.android.gms.common.api.Api.zza $r16 = this.CI;
            Context $r17 = this.mContext;
            $r14 = this.DY.DI;
            Looper $r18 = $r14.getLooper();
            $r13 = this.Ep;
            zzg $r19 = this.Ep;
            this.Ei = (zzach) $r16.zza($r17, $r18, $r13, $r19.zzawm(), com_google_android_gms_internal_zzqu_zze, com_google_android_gms_internal_zzqu_zze);
        }
        this.Ef = this.DY.EK.size();
        this.Er.add(zzqz.zzatt().submit(new zzb(this, $r1)));
    }

    public void connect() throws  {
    }

    public boolean disconnect() throws  {
        zzati();
        zzbl(true);
        this.DY.zzi(null);
        return true;
    }

    public void onConnected(Bundle $r1) throws  {
        if (zzht(1)) {
            if ($r1 != null) {
                this.Eg.putAll($r1);
            }
            if (zzatd()) {
                zzatg();
            }
        }
    }

    public void onConnectionSuspended(int i) throws  {
        zzg(new ConnectionResult(8, null));
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/common/ConnectionResult;", "Lcom/google/android/gms/common/api/Api", "<*>;I)V"}) ConnectionResult $r1, @Signature({"(", "Lcom/google/android/gms/common/ConnectionResult;", "Lcom/google/android/gms/common/api/Api", "<*>;I)V"}) Api<?> $r2, @Signature({"(", "Lcom/google/android/gms/common/ConnectionResult;", "Lcom/google/android/gms/common/api/Api", "<*>;I)V"}) int $i0) throws  {
        if (zzht(1)) {
            zzb($r1, $r2, $i0);
            if (zzatd()) {
                zzatg();
            }
        }
    }

    public <A extends com.google.android.gms.common.api.Api.zzb, R extends Result, T extends com.google.android.gms.internal.zzqk.zza<R, A>> T zzc(@Signature({"<A::", "Lcom/google/android/gms/common/api/Api$zzb;", "R::", "Lcom/google/android/gms/common/api/Result;", "T:", "Lcom/google/android/gms/internal/zzqk$zza", "<TR;TA;>;>(TT;)TT;"}) T $r1) throws  {
        this.DY.DI.EE.add($r1);
        return $r1;
    }

    public <A extends com.google.android.gms.common.api.Api.zzb, T extends com.google.android.gms.internal.zzqk.zza<? extends Result, A>> T zzd(@Signature({"<A::", "Lcom/google/android/gms/common/api/Api$zzb;", "T:", "Lcom/google/android/gms/internal/zzqk$zza", "<+", "Lcom/google/android/gms/common/api/Result;", "TA;>;>(TT;)TT;"}) T t) throws  {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }
}
