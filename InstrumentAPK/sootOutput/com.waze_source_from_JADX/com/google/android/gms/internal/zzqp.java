package com.google.android.gms.internal;

import android.app.Activity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.zzc;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.util.zza;
import dalvik.annotation.Signature;
import java.util.Iterator;

/* compiled from: dalvik_source_com.waze.apk */
public class zzqp extends zzql {
    private zzra Cr;
    private final zza<zzc<?>> DV = new zza();

    zzqp(zzri $r1, zzra $r2) throws  {
        super($r1);
        this.Cr = $r2;
        this.FM.zza("ConnectionlessLifecycleHelper", (zzrh) this);
    }

    public static zzqp zza(Activity $r0, zzra $r1) throws  {
        zzri $r2 = zzrh.zzt($r0);
        zzqp $r4 = (zzqp) $r2.zza("ConnectionlessLifecycleHelper", zzqp.class);
        if ($r4 == null) {
            return new zzqp($r2, $r1);
        }
        $r4.Cr = $r1;
        return $r4;
    }

    public void onStop() throws  {
        super.onStop();
        Iterator $r2 = this.DV.iterator();
        while ($r2.hasNext()) {
            ((zzc) $r2.next()).release();
        }
        this.DV.clear();
        this.Cr.zza(this);
    }

    protected void zza(ConnectionResult $r1, int $i0) throws  {
        this.Cr.zza($r1, $i0);
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/common/api/zzc", "<*>;)V"}) zzc<?> $r1) throws  {
        zzab.zzb((Object) $r1, (Object) "Api instance cannot be null");
        this.DV.add($r1);
    }

    protected void zzasf() throws  {
        this.Cr.zzasf();
    }
}
