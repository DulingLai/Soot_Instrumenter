package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.DeadObjectException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzah;
import com.google.android.gms.internal.zzqk.zza;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class zzqt implements zzqx {
    private final zzqy DY;
    private boolean DZ = false;

    public zzqt(zzqy $r1) throws  {
        this.DY = $r1;
    }

    private <A extends zzb> void zzf(@Signature({"<A::", "Lcom/google/android/gms/common/api/Api$zzb;", ">(", "Lcom/google/android/gms/internal/zzqk$zza", "<+", "Lcom/google/android/gms/common/api/Result;", "TA;>;)V"}) zza<? extends Result, A> $r1) throws DeadObjectException {
        this.DY.DI.EQ.zzg($r1);
        zze $r6 = this.DY.DI.zzb($r1.zzarl());
        zzb $r7 = $r6;
        if ($r6.isConnected() || !this.DY.EZ.containsKey($r1.zzarl())) {
            if ($r6 instanceof zzah) {
                $r7 = ((zzah) $r6).zzaxk();
            }
            $r1.zzb($r7);
            return;
        }
        $r1.zzag(new Status(17));
    }

    public void begin() throws  {
    }

    public void connect() throws  {
        if (this.DZ) {
            this.DZ = false;
            this.DY.zza(new zza(this, this) {
                final /* synthetic */ zzqt Ea;

                public void zzatc() throws  {
                    this.Ea.DY.Fd.zzr(null);
                }
            });
        }
    }

    public boolean disconnect() throws  {
        if (this.DZ) {
            return false;
        }
        if (this.DY.DI.zzato()) {
            this.DZ = true;
            for (zzsa zzauq : this.DY.DI.EP) {
                zzauq.zzauq();
            }
            return false;
        }
        this.DY.zzi(null);
        return true;
    }

    public void onConnected(Bundle bundle) throws  {
    }

    public void onConnectionSuspended(int $i0) throws  {
        this.DY.zzi(null);
        this.DY.Fd.zze($i0, this.DZ);
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/common/ConnectionResult;", "Lcom/google/android/gms/common/api/Api", "<*>;I)V"}) ConnectionResult connectionResult, @Signature({"(", "Lcom/google/android/gms/common/ConnectionResult;", "Lcom/google/android/gms/common/api/Api", "<*>;I)V"}) Api<?> api, @Signature({"(", "Lcom/google/android/gms/common/ConnectionResult;", "Lcom/google/android/gms/common/api/Api", "<*>;I)V"}) int i) throws  {
    }

    void zzatb() throws  {
        if (this.DZ) {
            this.DZ = false;
            this.DY.DI.EQ.release();
            disconnect();
        }
    }

    public <A extends zzb, R extends Result, T extends zza<R, A>> T zzc(@Signature({"<A::", "Lcom/google/android/gms/common/api/Api$zzb;", "R::", "Lcom/google/android/gms/common/api/Result;", "T:", "Lcom/google/android/gms/internal/zzqk$zza", "<TR;TA;>;>(TT;)TT;"}) T $r1) throws  {
        return zzd($r1);
    }

    public <A extends zzb, T extends zza<? extends Result, A>> T zzd(@Signature({"<A::", "Lcom/google/android/gms/common/api/Api$zzb;", "T:", "Lcom/google/android/gms/internal/zzqk$zza", "<+", "Lcom/google/android/gms/common/api/Result;", "TA;>;>(TT;)TT;"}) T $r1) throws  {
        try {
            zzf($r1);
            return $r1;
        } catch (DeadObjectException e) {
            this.DY.zza(new zza(this, this) {
                final /* synthetic */ zzqt Ea;

                public void zzatc() throws  {
                    this.Ea.onConnectionSuspended(1);
                }
            });
            return $r1;
        }
    }
}
