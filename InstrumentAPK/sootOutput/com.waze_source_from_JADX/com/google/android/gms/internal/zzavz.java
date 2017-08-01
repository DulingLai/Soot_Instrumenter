package com.google.android.gms.internal;

import dalvik.annotation.Signature;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzavz extends zzavg<Date> {
    public static final zzavh bYW = new C07951();
    private final DateFormat bXg = DateFormat.getDateTimeInstance(2, 2, Locale.US);
    private final DateFormat bXh = DateFormat.getDateTimeInstance(2, 2);
    private final DateFormat bXi = hB();

    /* compiled from: dalvik_source_com.waze.apk */
    static class C07951 implements zzavh {
        C07951() throws  {
        }

        public <T> zzavg<T> zza(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzauo com_google_android_gms_internal_zzauo, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzawk<T> $r2) throws  {
            return $r2.hN() == Date.class ? new zzavz() : null;
        }
    }

    private static DateFormat hB() throws  {
        SimpleDateFormat $r0 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        $r0.setTimeZone(TimeZone.getTimeZone("UTC"));
        return $r0;
    }

    private synchronized Date zzyn(String $r1) throws  {
        Date $r4;
        try {
            $r4 = this.bXh.parse($r1);
        } catch (ParseException e) {
            try {
                $r4 = this.bXg.parse($r1);
            } catch (ParseException e2) {
                try {
                    $r4 = this.bXi.parse($r1);
                } catch (ParseException $r7) {
                    throw new zzavd($r1, $r7);
                }
            }
        }
        return $r4;
    }

    public synchronized void zza(zzawn $r1, Date $r2) throws IOException {
        if ($r2 == null) {
            $r1.hM();
        } else {
            $r1.zzyp(this.bXg.format($r2));
        }
    }

    public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
        return zzk($r1);
    }

    public Date zzk(zzawl $r1) throws IOException {
        if ($r1.hC() != zzawm.NULL) {
            return zzyn($r1.nextString());
        }
        $r1.nextNull();
        return null;
    }
}
