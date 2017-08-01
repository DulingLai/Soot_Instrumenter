package com.google.android.gms.internal;

import dalvik.annotation.Signature;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzawh extends zzavg<Time> {
    public static final zzavh bYW = new C08021();
    private final DateFormat bZw = new SimpleDateFormat("hh:mm:ss a");

    /* compiled from: dalvik_source_com.waze.apk */
    static class C08021 implements zzavh {
        C08021() throws  {
        }

        public <T> zzavg<T> zza(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzauo com_google_android_gms_internal_zzauo, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzawk<T> $r2) throws  {
            return $r2.hN() == Time.class ? new zzawh() : null;
        }
    }

    public synchronized void zza(zzawn $r1, Time $r2) throws IOException {
        $r1.zzyp($r2 == null ? null : this.bZw.format($r2));
    }

    public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
        return zzn($r1);
    }

    public synchronized Time zzn(zzawl $r1) throws IOException {
        Time $r4;
        if ($r1.hC() == zzawm.NULL) {
            $r1.nextNull();
            $r4 = null;
        } else {
            try {
                $r4 = new Time(this.bZw.parse($r1.nextString()).getTime());
            } catch (Throwable $r8) {
                throw new zzavd($r8);
            }
        }
        return $r4;
    }
}
