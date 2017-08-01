package com.google.android.gms.internal;

import android.util.SparseArray;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.ResultStore;
import com.google.android.gms.common.internal.zzab;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class zzrr extends ResultStore {
    private final SparseArray<PendingResult<?>> FY = new SparseArray();
    private final SparseArray<ResultCallbacks<?>> FZ = new SparseArray();
    private final Object zzaix = new Object();

    public boolean hasPendingResult(int $i0) throws  {
        boolean $z0;
        synchronized (this.zzaix) {
            $z0 = this.FY.get($i0) != null;
        }
        return $z0;
    }

    public void remove(int $i0) throws  {
        synchronized (this.zzaix) {
            PendingResult $r4 = (PendingResult) this.FY.get($i0);
            if ($r4 != null) {
                this.FY.remove($i0);
                if (((ResultCallback) this.FZ.get($i0)) != null) {
                    $r4.setResultCallback(null);
                }
            }
        }
    }

    public void setResultCallbacks(int $i0, ResultCallbacks $r1) throws  {
        zzab.zzb((Object) $r1, (Object) "ResultCallbacks cannot be null.");
        synchronized (this.zzaix) {
            this.FZ.put($i0, $r1);
            PendingResult $r5 = (PendingResult) this.FY.get($i0);
            if ($r5 != null) {
                $r5.setResultCallback($r1);
            }
        }
    }

    public <R extends Result> void zza(@Signature({"<R::", "Lcom/google/android/gms/common/api/Result;", ">(I", "Lcom/google/android/gms/common/api/PendingResult", "<TR;>;)V"}) int $i0, @Signature({"<R::", "Lcom/google/android/gms/common/api/Result;", ">(I", "Lcom/google/android/gms/common/api/PendingResult", "<TR;>;)V"}) PendingResult<R> $r1) throws  {
        boolean $z0 = true;
        synchronized (this.zzaix) {
            zzab.zzb(this.FY.get($i0) == null, "ResultStore ResultId must be unique within the current activity. Violating ResultId: " + $i0);
            if ($r1.zzasb() != null) {
                $z0 = false;
            }
            zzab.zzb($z0, (Object) "PendingResult has already been saved.");
            $r1.zzhp($i0);
            this.FY.put($i0, $r1);
            ResultCallbacks $r8 = (ResultCallbacks) this.FZ.get($i0);
            if ($r8 != null) {
                $r1.setResultCallback($r8);
            }
        }
    }

    public void zzaun() throws  {
        synchronized (this.zzaix) {
            this.FZ.clear();
            for (int $i0 = 0; $i0 < this.FY.size(); $i0++) {
                ((PendingResult) this.FY.valueAt($i0)).setResultCallback(null);
            }
        }
    }

    public void zzz(Object $r1) throws  {
        synchronized (this.zzaix) {
            for (int $i0 = 0; $i0 < this.FY.size(); $i0++) {
                ((PendingResult) this.FY.valueAt($i0)).cancel();
            }
        }
        ResultStore.zzx($r1);
    }
}
