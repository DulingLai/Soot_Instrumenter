package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.zzru;
import dalvik.annotation.Signature;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* compiled from: dalvik_source_com.waze.apk */
public class zzb extends AsyncTaskLoader<Void> implements zzru {
    private Semaphore fQ = new Semaphore(0);
    private Set<GoogleApiClient> fR;

    public zzb(@Signature({"(", "Landroid/content/Context;", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/GoogleApiClient;", ">;)V"}) Context $r1, @Signature({"(", "Landroid/content/Context;", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/GoogleApiClient;", ">;)V"}) Set<GoogleApiClient> $r2) throws  {
        super($r1);
        this.fR = $r2;
    }

    public /* synthetic */ Object loadInBackground() throws  {
        return zzaep();
    }

    protected void onStartLoading() throws  {
        this.fQ.drainPermits();
        forceLoad();
    }

    public Void zzaep() throws  {
        this = this;
        int $i0 = 0;
        for (GoogleApiClient zza : this.fR) {
            if (zza.zza((zzru) this)) {
                $i0++;
            }
        }
        try {
            this.fQ.tryAcquire($i0, 5, TimeUnit.SECONDS);
        } catch (InterruptedException $r7) {
            Log.i("GACSignInLoader", "Unexpected InterruptedException", $r7);
            Thread.currentThread().interrupt();
        }
        return null;
    }

    public void zzaeq() throws  {
        this.fQ.release();
    }
}
