package com.google.android.gms.common.api;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Pair;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.internal.zzqh;
import com.google.android.gms.internal.zzqk.zza;
import com.google.android.gms.internal.zzra;
import com.google.android.gms.internal.zzrb;
import com.google.android.gms.internal.zzrn;
import com.google.android.gms.internal.zzrz;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import dalvik.annotation.Signature;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzc<O extends ApiOptions> {
    private final zzrn Co;
    private final O Cp;
    private final zzqh<O> Cq;
    private final zzra Cr;
    private final GoogleApiClient Cs;
    private final AtomicBoolean Ct;
    private final AtomicInteger Cu;
    private final Context mContext;
    private final int mId;
    private final Api<O> zn;
    private final Looper zzaih;

    public zzc(@NonNull @Signature({"(", "Landroid/app/Activity;", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;)V"}) Activity $r1, @Signature({"(", "Landroid/app/Activity;", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;)V"}) Api<O> $r2, @Signature({"(", "Landroid/app/Activity;", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;)V"}) O $r3) throws  {
        this($r1, (Api) $r2, (ApiOptions) $r3, $r1.getMainLooper());
    }

    public zzc(@NonNull @Signature({"(", "Landroid/app/Activity;", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;", "Landroid/os/Looper;", ")V"}) Activity $r1, @Signature({"(", "Landroid/app/Activity;", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;", "Landroid/os/Looper;", ")V"}) Api<O> $r2, @Signature({"(", "Landroid/app/Activity;", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;", "Landroid/os/Looper;", ")V"}) O $r3, @Signature({"(", "Landroid/app/Activity;", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;", "Landroid/os/Looper;", ")V"}) Looper $r4) throws  {
        zzc com_google_android_gms_common_api_zzc = this;
        this.Ct = new AtomicBoolean(false);
        this.Cu = new AtomicInteger(0);
        zzab.zzb((Object) $r1, (Object) "Null activity is not permitted.");
        zzab.zzb((Object) $r2, (Object) "Api must not be null.");
        zzab.zzb((Object) $r4, (Object) "Looper must not be null.");
        this.mContext = $r1.getApplicationContext();
        this.zn = $r2;
        this.Cp = $r3;
        this.zzaih = $r4;
        this.Co = new zzrn();
        this.Cq = new zzqh(this.zn, this.Cp);
        this.Cs = new zzrb(this);
        Pair $r11 = zzra.zza($r1, this);
        this.Cr = (zzra) $r11.first;
        this.mId = ((Integer) $r11.second).intValue();
    }

    public zzc(@NonNull @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;)V"}) Context $r1, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;)V"}) Api<O> $r2, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;)V"}) O $r3) throws  {
        this($r1, (Api) $r2, (ApiOptions) $r3, Looper.myLooper() != null ? Looper.myLooper() : Looper.getMainLooper());
    }

    public zzc(@NonNull @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;", "Landroid/os/Looper;", ")V"}) Context $r1, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;", "Landroid/os/Looper;", ")V"}) Api<O> $r2, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;", "Landroid/os/Looper;", ")V"}) O $r3, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;", "Landroid/os/Looper;", ")V"}) Looper $r4) throws  {
        zzc com_google_android_gms_common_api_zzc = this;
        this.Ct = new AtomicBoolean(false);
        this.Cu = new AtomicInteger(0);
        zzab.zzb((Object) $r1, (Object) "Null context is not permitted.");
        zzab.zzb((Object) $r2, (Object) "Api must not be null.");
        zzab.zzb((Object) $r4, (Object) "Looper must not be null.");
        this.mContext = $r1.getApplicationContext();
        this.zn = $r2;
        this.Cp = $r3;
        this.zzaih = $r4;
        this.Co = new zzrn();
        this.Cq = new zzqh(this.zn, this.Cp);
        this.Cs = new zzrb(this);
        Context context = this.mContext;
        $r1 = context;
        Pair $r10 = zzra.zza(context, this);
        this.Cr = (zzra) $r10.first;
        this.mId = ((Integer) $r10.second).intValue();
    }

    private <A extends zzb, T extends zza<? extends Result, A>> T zza(@Signature({"<A::", "Lcom/google/android/gms/common/api/Api$zzb;", "T:", "Lcom/google/android/gms/internal/zzqk$zza", "<+", "Lcom/google/android/gms/common/api/Result;", "TA;>;>(ITT;)TT;"}) int $i0, @NonNull @Signature({"<A::", "Lcom/google/android/gms/common/api/Api$zzb;", "T:", "Lcom/google/android/gms/internal/zzqk$zza", "<+", "Lcom/google/android/gms/common/api/Result;", "TA;>;>(ITT;)TT;"}) T $r1) throws  {
        $r1.zzasn();
        this.Cr.zza(this, $i0, $r1);
        return $r1;
    }

    private <TResult, A extends zzb> Task<TResult> zza(@Signature({"<TResult:", "Ljava/lang/Object;", "A::", "Lcom/google/android/gms/common/api/Api$zzb;", ">(I", "Lcom/google/android/gms/internal/zzrz", "<TA;TTResult;>;)", "Lcom/google/android/gms/tasks/Task", "<TTResult;>;"}) int $i0, @NonNull @Signature({"<TResult:", "Ljava/lang/Object;", "A::", "Lcom/google/android/gms/common/api/Api$zzb;", ">(I", "Lcom/google/android/gms/internal/zzrz", "<TA;TTResult;>;)", "Lcom/google/android/gms/tasks/Task", "<TTResult;>;"}) zzrz<A, TResult> $r1) throws  {
        TaskCompletionSource $r4 = new TaskCompletionSource();
        this.Cr.zza(this, $i0, $r1, $r4);
        return $r4.getTask();
    }

    public GoogleApiClient asGoogleApiClient() throws  {
        return this.Cs;
    }

    public Context getApplicationContext() throws  {
        return this.mContext;
    }

    public int getInstanceId() throws  {
        return this.mId;
    }

    public Looper getLooper() throws  {
        return this.zzaih;
    }

    public void release() throws  {
        boolean $z0 = true;
        if (!this.Ct.getAndSet(true)) {
            this.Co.release();
            zzra $r3 = this.Cr;
            int $i0 = this.mId;
            if (this.Cu.get() <= 0) {
                $z0 = false;
            }
            $r3.zzf($i0, $z0);
        }
    }

    public <A extends zzb, T extends zza<? extends Result, A>> T zza(@NonNull @Signature({"<A::", "Lcom/google/android/gms/common/api/Api$zzb;", "T:", "Lcom/google/android/gms/internal/zzqk$zza", "<+", "Lcom/google/android/gms/common/api/Result;", "TA;>;>(TT;)TT;"}) T $r1) throws  {
        return zza(0, (zza) $r1);
    }

    public <TResult, A extends zzb> Task<TResult> zza(@Signature({"<TResult:", "Ljava/lang/Object;", "A::", "Lcom/google/android/gms/common/api/Api$zzb;", ">(", "Lcom/google/android/gms/internal/zzrz", "<TA;TTResult;>;)", "Lcom/google/android/gms/tasks/Task", "<TTResult;>;"}) zzrz<A, TResult> $r1) throws  {
        return zza(0, (zzrz) $r1);
    }

    public void zzarq() throws  {
        this.Cu.incrementAndGet();
    }

    public void zzarr() throws  {
        if (this.Cu.decrementAndGet() == 0 && this.Ct.get()) {
            this.Cr.zzf(this.mId, false);
        }
    }

    public Api<O> zzars() throws  {
        return this.zn;
    }

    public O zzart() throws  {
        return this.Cp;
    }

    public zzqh<O> zzaru() throws  {
        return this.Cq;
    }

    public <A extends zzb, T extends zza<? extends Result, A>> T zzb(@NonNull @Signature({"<A::", "Lcom/google/android/gms/common/api/Api$zzb;", "T:", "Lcom/google/android/gms/internal/zzqk$zza", "<+", "Lcom/google/android/gms/common/api/Result;", "TA;>;>(TT;)TT;"}) T $r1) throws  {
        return zza(1, (zza) $r1);
    }

    public <TResult, A extends zzb> Task<TResult> zzb(@Signature({"<TResult:", "Ljava/lang/Object;", "A::", "Lcom/google/android/gms/common/api/Api$zzb;", ">(", "Lcom/google/android/gms/internal/zzrz", "<TA;TTResult;>;)", "Lcom/google/android/gms/tasks/Task", "<TTResult;>;"}) zzrz<A, TResult> $r1) throws  {
        return zza(1, (zzrz) $r1);
    }
}
