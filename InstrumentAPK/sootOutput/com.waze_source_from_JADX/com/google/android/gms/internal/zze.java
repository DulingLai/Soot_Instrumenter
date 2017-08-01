package com.google.android.gms.internal;

import android.os.Handler;
import dalvik.annotation.Signature;
import java.util.concurrent.Executor;

/* compiled from: dalvik_source_com.waze.apk */
public class zze implements zzn {
    private final Executor zzr;

    /* compiled from: dalvik_source_com.waze.apk */
    private class zza implements Runnable {
        final /* synthetic */ zze zzt;
        private final zzk zzu;
        private final zzm zzv;
        private final Runnable zzw;

        public zza(zze $r1, zzk $r2, zzm $r3, Runnable $r4) throws  {
            this.zzt = $r1;
            this.zzu = $r2;
            this.zzv = $r3;
            this.zzw = $r4;
        }

        public void run() throws  {
            if (this.zzu.isCanceled()) {
                this.zzu.zzd("canceled-at-delivery");
                return;
            }
            if (this.zzv.isSuccess()) {
                this.zzu.zza(this.zzv.result);
            } else {
                this.zzu.zzc(this.zzv.zzbf);
            }
            if (this.zzv.zzbg) {
                this.zzu.zzc("intermediate-response");
            } else {
                this.zzu.zzd("done");
            }
            if (this.zzw != null) {
                this.zzw.run();
            }
        }
    }

    public zze(final Handler $r1) throws  {
        this.zzr = new Executor(this) {
            final /* synthetic */ zze zzt;

            public void execute(Runnable $r1) throws  {
                $r1.post($r1);
            }
        };
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzk", "<*>;", "Lcom/google/android/gms/internal/zzm", "<*>;)V"}) zzk<?> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzk", "<*>;", "Lcom/google/android/gms/internal/zzm", "<*>;)V"}) zzm<?> $r2) throws  {
        zza($r1, $r2, null);
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzk", "<*>;", "Lcom/google/android/gms/internal/zzm", "<*>;", "Ljava/lang/Runnable;", ")V"}) zzk<?> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzk", "<*>;", "Lcom/google/android/gms/internal/zzm", "<*>;", "Ljava/lang/Runnable;", ")V"}) zzm<?> $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzk", "<*>;", "Lcom/google/android/gms/internal/zzm", "<*>;", "Ljava/lang/Runnable;", ")V"}) Runnable $r3) throws  {
        $r1.zzu();
        $r1.zzc("post-response");
        this.zzr.execute(new zza(this, $r1, $r2, $r3));
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzk", "<*>;", "Lcom/google/android/gms/internal/zzr;", ")V"}) zzk<?> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzk", "<*>;", "Lcom/google/android/gms/internal/zzr;", ")V"}) zzr $r2) throws  {
        $r1.zzc("post-error");
        this.zzr.execute(new zza(this, $r1, zzm.zzd($r2), null));
    }
}
