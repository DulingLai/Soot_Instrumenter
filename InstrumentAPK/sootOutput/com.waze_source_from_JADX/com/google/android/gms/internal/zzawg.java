package com.google.android.gms.internal;

import dalvik.annotation.Signature;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzawg extends zzavg<Date> {
    public static final zzavh bYW = new C08011();
    private final DateFormat bZw = new SimpleDateFormat("MMM d, yyyy");

    /* compiled from: dalvik_source_com.waze.apk */
    static class C08011 implements zzavh {
        C08011() throws  {
        }

        public <T> zzavg<T> zza(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzauo com_google_android_gms_internal_zzauo, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzawk<T> $r2) throws  {
            return $r2.hN() == Date.class ? new zzawg() : null;
        }
    }

    public synchronized void zza(zzawn $r1, Date $r2) throws IOException {
        $r1.zzyp($r2 == null ? null : this.bZw.format($r2));
    }

    public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
        return zzm($r1);
    }

    public synchronized Date zzm(zzawl $r1) throws IOException {
        Date $r4;
        if ($r1.hC() == zzawm.NULL) {
            $r1.nextNull();
            $r4 = null;
        } else {
            try {
                $r4 = new Date(this.bZw.parse($r1.nextString()).getTime());
            } catch (Throwable $r8) {
                throw new zzavd($r8);
            }
        }
        return $r4;
    }
}
