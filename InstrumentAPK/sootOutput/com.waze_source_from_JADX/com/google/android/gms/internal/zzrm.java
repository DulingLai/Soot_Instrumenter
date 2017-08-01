package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.internal.zzab;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzrm<L> {
    private final zza FS;
    private volatile L mListener;

    /* compiled from: dalvik_source_com.waze.apk */
    public interface zzb<L> {
        void zzata() throws ;

        void zzy(@Signature({"(T", "L;", ")V"}) L l) throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private final class zza extends Handler {
        final /* synthetic */ zzrm FT;

        public zza(zzrm $r1, Looper $r2) throws  {
            this.FT = $r1;
            super($r2);
        }

        public void handleMessage(Message $r1) throws  {
            boolean $z0 = true;
            if ($r1.what != 1) {
                $z0 = false;
            }
            zzab.zzbn($z0);
            this.FT.zzb((zzb) $r1.obj);
        }
    }

    zzrm(@Signature({"(", "Landroid/os/Looper;", "T", "L;", ")V"}) Looper $r1, @Signature({"(", "Landroid/os/Looper;", "T", "L;", ")V"}) L $r2) throws  {
        this.FS = new zza(this, $r1);
        this.mListener = zzab.zzb((Object) $r2, (Object) "Listener must not be null");
    }

    public void clear() throws  {
        this.mListener = null;
    }

    public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzrm$zzb", "<-T", "L;", ">;)V"}) zzb<? super L> $r1) throws  {
        zzab.zzb((Object) $r1, (Object) "Notifier must not be null");
        this.FS.sendMessage(this.FS.obtainMessage(1, $r1));
    }

    void zzb(@Signature({"(", "Lcom/google/android/gms/internal/zzrm$zzb", "<-T", "L;", ">;)V"}) zzb<? super L> $r1) throws  {
        Object $r2 = this.mListener;
        if ($r2 == null) {
            $r1.zzata();
            return;
        }
        try {
            $r1.zzy($r2);
        } catch (RuntimeException $r3) {
            $r1.zzata();
            throw $r3;
        }
    }
}
