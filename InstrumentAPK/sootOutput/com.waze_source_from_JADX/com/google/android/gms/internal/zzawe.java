package com.google.android.gms.internal;

import dalvik.annotation.Signature;
import java.io.IOException;
import java.util.ArrayList;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzawe extends zzavg<Object> {
    public static final zzavh bYW = new C07981();
    private final zzauo bXS;

    /* compiled from: dalvik_source_com.waze.apk */
    static class C07981 implements zzavh {
        C07981() throws  {
        }

        public <T> zzavg<T> zza(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzauo $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzawk<T> $r2) throws  {
            return $r2.hN() == Object.class ? new zzawe($r1) : null;
        }
    }

    private zzawe(zzauo $r1) throws  {
        this.bXS = $r1;
    }

    public void zza(zzawn $r1, Object $r2) throws IOException {
        if ($r2 == null) {
            $r1.hM();
            return;
        }
        zzavg $r5 = this.bXS.zzj($r2.getClass());
        if ($r5 instanceof zzawe) {
            $r1.hK();
            $r1.hL();
            return;
        }
        $r5.zza($r1, $r2);
    }

    public Object zzb(zzawl $r1) throws IOException {
        switch ($r1.hC()) {
            case BEGIN_ARRAY:
                ArrayList $r5 = r11;
                ArrayList r11 = new ArrayList();
                $r1.beginArray();
                while ($r1.hasNext()) {
                    $r5.add(zzb($r1));
                }
                $r1.endArray();
                return $r5;
            case BEGIN_OBJECT:
                zzavs $r52 = r12;
                zzavs r12 = new zzavs();
                $r1.beginObject();
                while ($r1.hasNext()) {
                    $r52.put($r1.nextName(), zzb($r1));
                }
                $r1.endObject();
                return $r52;
            case STRING:
                return $r1.nextString();
            case NUMBER:
                return Double.valueOf($r1.nextDouble());
            case BOOLEAN:
                return Boolean.valueOf($r1.nextBoolean());
            case NULL:
                $r1.nextNull();
                return null;
            default:
                throw new IllegalStateException();
        }
    }
}
