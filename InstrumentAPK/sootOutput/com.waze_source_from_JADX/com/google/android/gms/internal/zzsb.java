package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.RemoteException;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultStore;
import com.google.android.gms.common.api.Status;
import dalvik.annotation.Signature;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/* compiled from: dalvik_source_com.waze.apk */
public class zzsb {
    private static final com.google.android.gms.internal.zzqk.zza<?, ?>[] Gl = new com.google.android.gms.internal.zzqk.zza[0];
    private final Map<zzc<?>, zze> EK;
    final Set<com.google.android.gms.internal.zzqk.zza<?, ?>> Gm;
    private final zzb Gn;
    private ResultStore Go;

    /* compiled from: dalvik_source_com.waze.apk */
    interface zzb {
        void zzh(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zza", "<**>;)V"}) com.google.android.gms.internal.zzqk.zza<?, ?> com_google_android_gms_internal_zzqk_zza___) throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C08511 implements zzb {
        final /* synthetic */ zzsb Gp;

        C08511(zzsb $r1) throws  {
            this.Gp = $r1;
        }

        public void zzh(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zza", "<**>;)V"}) com.google.android.gms.internal.zzqk.zza<?, ?> $r1) throws  {
            this.Gp.Gm.remove($r1);
            if ($r1.zzasb() != null && this.Gp.Go != null) {
                this.Gp.Go.remove($r1.zzasb().intValue());
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zza implements DeathRecipient, zzb {
        private final WeakReference<com.google.android.gms.internal.zzqk.zza<?, ?>> Gq;
        private final WeakReference<ResultStore> Gr;
        private final WeakReference<IBinder> Gs;

        private zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zza", "<**>;", "Lcom/google/android/gms/common/api/ResultStore;", "Landroid/os/IBinder;", ")V"}) com.google.android.gms.internal.zzqk.zza<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zza", "<**>;", "Lcom/google/android/gms/common/api/ResultStore;", "Landroid/os/IBinder;", ")V"}) ResultStore $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zza", "<**>;", "Lcom/google/android/gms/common/api/ResultStore;", "Landroid/os/IBinder;", ")V"}) IBinder $r3) throws  {
            this.Gr = new WeakReference($r2);
            this.Gq = new WeakReference($r1);
            this.Gs = new WeakReference($r3);
        }

        private void zzatx() throws  {
            com.google.android.gms.internal.zzqk.zza $r3 = (com.google.android.gms.internal.zzqk.zza) this.Gq.get();
            ResultStore $r4 = (ResultStore) this.Gr.get();
            if (!($r4 == null || $r3 == null)) {
                $r4.remove($r3.zzasb().intValue());
            }
            IBinder $r6 = (IBinder) this.Gs.get();
            if (this.Gs != null) {
                $r6.unlinkToDeath(this, 0);
            }
        }

        public void binderDied() throws  {
            zzatx();
        }

        public void zzh(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zza", "<**>;)V"}) com.google.android.gms.internal.zzqk.zza<?, ?> com_google_android_gms_internal_zzqk_zza___) throws  {
            zzatx();
        }
    }

    public zzsb(@Signature({"(", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ")V"}) zzc<?> $r1, @Signature({"(", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ")V"}) zze $r2) throws  {
        this.Gm = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
        this.Gn = new C08511(this);
        this.EK = new ArrayMap();
        this.EK.put($r1, $r2);
    }

    public zzsb(@Signature({"(", "Ljava/util/Map", "<", "Lcom/google/android/gms/common/api/Api$zzc", "<*>;", "Lcom/google/android/gms/common/api/Api$zze;", ">;)V"}) Map<zzc<?>, zze> $r1) throws  {
        this.Gm = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
        this.Gn = new C08511(this);
        this.EK = $r1;
    }

    private static void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzqk$zza", "<**>;", "Lcom/google/android/gms/common/api/ResultStore;", "Landroid/os/IBinder;", ")V"}) com.google.android.gms.internal.zzqk.zza<?, ?> $r0, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zza", "<**>;", "Lcom/google/android/gms/common/api/ResultStore;", "Landroid/os/IBinder;", ")V"}) ResultStore $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzqk$zza", "<**>;", "Lcom/google/android/gms/common/api/ResultStore;", "Landroid/os/IBinder;", ")V"}) IBinder $r2) throws  {
        if ($r0.isReady()) {
            $r0.zza(new zza($r0, $r1, $r2));
        } else if ($r2 == null || !$r2.isBinderAlive()) {
            $r0.zza(null);
            $r0.cancel();
            $r1.remove($r0.zzasb().intValue());
        } else {
            zzb $r3 = new zza($r0, $r1, $r2);
            $r0.zza($r3);
            try {
                $r2.linkToDeath($r3, 0);
            } catch (RemoteException e) {
                $r0.cancel();
                $r1.remove($r0.zzasb().intValue());
            }
        }
    }

    public void dump(PrintWriter $r1) throws  {
        $r1.append(" mUnconsumedApiCalls.size()=").println(this.Gm.size());
    }

    public void release() throws  {
        for (com.google.android.gms.internal.zzqk.zza $r1 : (com.google.android.gms.internal.zzqk.zza[]) this.Gm.toArray(Gl)) {
            $r1.zza(null);
            if ($r1.zzasb() != null) {
                $r1.zzasi();
                IBinder $r10 = ((zze) this.EK.get($r1.zzarl())).zzaro();
                ResultStore resultStore = this.Go;
                ResultStore $r11 = resultStore;
                zza($r1, resultStore, $r10);
                this.Gm.remove($r1);
            } else if ($r1.zzasm()) {
                this.Gm.remove($r1);
            }
        }
    }

    public void zza(ResultStore $r1) throws  {
        this.Go = $r1;
    }

    public void zzaus() throws  {
        for (com.google.android.gms.internal.zzqk.zza $r1 : (com.google.android.gms.internal.zzqk.zza[]) this.Gm.toArray(Gl)) {
            $r1.zzah(new Status(8, "The connection to Google Play services was lost"));
        }
    }

    public boolean zzaut() throws  {
        for (com.google.android.gms.internal.zzqk.zza $r4 : (com.google.android.gms.internal.zzqk.zza[]) this.Gm.toArray(Gl)) {
            if (!$r4.isReady()) {
                return true;
            }
        }
        return false;
    }

    <A extends com.google.android.gms.common.api.Api.zzb> void zzg(@Signature({"<A::", "Lcom/google/android/gms/common/api/Api$zzb;", ">(", "Lcom/google/android/gms/internal/zzqk$zza", "<+", "Lcom/google/android/gms/common/api/Result;", "TA;>;)V"}) com.google.android.gms.internal.zzqk.zza<? extends Result, A> $r1) throws  {
        this.Gm.add($r1);
        $r1.zza(this.Gn);
    }
}
