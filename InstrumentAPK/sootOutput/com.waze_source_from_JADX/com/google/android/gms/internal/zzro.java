package com.google.android.gms.internal;

import android.app.Activity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.CancellationException;

/* compiled from: dalvik_source_com.waze.apk */
public class zzro extends zzql {
    private TaskCompletionSource<Void> Db = new TaskCompletionSource();

    private zzro(zzri $r1) throws  {
        super($r1);
        this.FM.zza("GmsAvailabilityHelper", (zzrh) this);
    }

    public static zzro zzv(Activity $r0) throws  {
        zzri $r1 = zzrh.zzt($r0);
        zzro $r3 = (zzro) $r1.zza("GmsAvailabilityHelper", zzro.class);
        if ($r3 == null) {
            return new zzro($r1);
        }
        if (!$r3.Db.getTask().isComplete()) {
            return $r3;
        }
        $r3.Db = new TaskCompletionSource();
        return $r3;
    }

    public Task<Void> getTask() throws  {
        return this.Db.getTask();
    }

    public void onStop() throws  {
        super.onStop();
        this.Db.setException(new CancellationException());
    }

    protected void zza(ConnectionResult connectionResult, int i) throws  {
        this.Db.setException(new Exception());
    }

    protected void zzasf() throws  {
        int $i0 = this.CH.isGooglePlayServicesAvailable(this.FM.zzauk());
        if ($i0 == 0) {
            this.Db.setResult(null);
        } else {
            zzk(new ConnectionResult($i0, null));
        }
    }

    public void zzk(ConnectionResult $r1) throws  {
        zzb($r1, 0);
    }
}
