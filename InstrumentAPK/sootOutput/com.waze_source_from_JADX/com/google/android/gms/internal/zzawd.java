package com.google.android.gms.internal;

import dalvik.annotation.Signature;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzawd implements zzavh {
    private final zzavo bXt;
    private final boolean bZi;

    /* compiled from: dalvik_source_com.waze.apk */
    private final class zza<K, V> extends zzavg<Map<K, V>> {
        private final zzavt<? extends Map<K, V>> bZa;
        private final zzavg<K> bZj;
        private final zzavg<V> bZk;
        final /* synthetic */ zzawd bZl;

        public zza(@Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzavg", "<TK;>;", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzavg", "<TV;>;", "Lcom/google/android/gms/internal/zzavt", "<+", "Ljava/util/Map", "<TK;TV;>;>;)V"}) zzawd $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzavg", "<TK;>;", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzavg", "<TV;>;", "Lcom/google/android/gms/internal/zzavt", "<+", "Ljava/util/Map", "<TK;TV;>;>;)V"}) zzauo $r2, @Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzavg", "<TK;>;", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzavg", "<TV;>;", "Lcom/google/android/gms/internal/zzavt", "<+", "Ljava/util/Map", "<TK;TV;>;>;)V"}) Type $r3, @Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzavg", "<TK;>;", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzavg", "<TV;>;", "Lcom/google/android/gms/internal/zzavt", "<+", "Ljava/util/Map", "<TK;TV;>;>;)V"}) zzavg<K> $r4, @Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzavg", "<TK;>;", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzavg", "<TV;>;", "Lcom/google/android/gms/internal/zzavt", "<+", "Ljava/util/Map", "<TK;TV;>;>;)V"}) Type $r5, @Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzavg", "<TK;>;", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzavg", "<TV;>;", "Lcom/google/android/gms/internal/zzavt", "<+", "Ljava/util/Map", "<TK;TV;>;>;)V"}) zzavg<V> $r6, @Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzavg", "<TK;>;", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzavg", "<TV;>;", "Lcom/google/android/gms/internal/zzavt", "<+", "Ljava/util/Map", "<TK;TV;>;>;)V"}) zzavt<? extends Map<K, V>> $r7) throws  {
            this.bZl = $r1;
            this.bZj = new zzawi($r2, $r4, $r3);
            this.bZk = new zzawi($r2, $r6, $r5);
            this.bZa = $r7;
        }

        private String zze(zzauu $r1) throws  {
            if ($r1.hj()) {
                zzava $r2 = $r1.hn();
                if ($r2.hq()) {
                    return String.valueOf($r2.hb());
                }
                if ($r2.hp()) {
                    return Boolean.toString($r2.hg());
                }
                if ($r2.hr()) {
                    return $r2.hc();
                }
                throw new AssertionError();
            } else if ($r1.hk()) {
                return "null";
            } else {
                throw new AssertionError();
            }
        }

        public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzawn;", "Ljava/util/Map", "<TK;TV;>;)V"}) zzawn $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzawn;", "Ljava/util/Map", "<TK;TV;>;)V"}) Map<K, V> $r2) throws IOException {
            int $i0 = 0;
            if ($r2 == null) {
                $r1.hM();
            } else if (this.bZl.bZi) {
                ArrayList $r3 = new ArrayList($r2.size());
                ArrayList $r4 = new ArrayList($r2.size());
                boolean $z0 = false;
                for (Entry $r9 : $r2.entrySet()) {
                    zzauu $r12 = this.bZj.zzcz($r9.getKey());
                    $r3.add($r12);
                    $r4.add($r9.getValue());
                    boolean $z1 = $r12.hh() || $r12.hi();
                    $z0 = $z1 | $z0;
                }
                if ($z0) {
                    $r1.hI();
                    while ($i0 < $r3.size()) {
                        $r1.hI();
                        zzavv.zzb((zzauu) $r3.get($i0), $r1);
                        this.bZk.zza($r1, $r4.get($i0));
                        $r1.hJ();
                        $i0++;
                    }
                    $r1.hJ();
                    return;
                }
                $r1.hK();
                while ($i0 < $r3.size()) {
                    $r1.zzyo(zze((zzauu) $r3.get($i0)));
                    this.bZk.zza($r1, $r4.get($i0));
                    $i0++;
                }
                $r1.hL();
            } else {
                $r1.hK();
                for (Entry $r92 : $r2.entrySet()) {
                    $r1.zzyo(String.valueOf($r92.getKey()));
                    this.bZk.zza($r1, $r92.getValue());
                }
                $r1.hL();
            }
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzl($r1);
        }

        public Map<K, V> zzl(@Signature({"(", "Lcom/google/android/gms/internal/zzawl;", ")", "Ljava/util/Map", "<TK;TV;>;"}) zzawl $r1) throws IOException {
            zzawm $r2 = $r1.hC();
            if ($r2 == zzawm.NULL) {
                $r1.nextNull();
                return null;
            }
            Map $r4 = (Map) this.bZa.hv();
            Object $r6;
            if ($r2 == zzawm.BEGIN_ARRAY) {
                $r1.beginArray();
                while ($r1.hasNext()) {
                    $r1.beginArray();
                    $r6 = this.bZj.zzb($r1);
                    if ($r4.put($r6, this.bZk.zzb($r1)) != null) {
                        String $r10 = String.valueOf($r6);
                        throw new zzavd(new StringBuilder(String.valueOf($r10).length() + 15).append("duplicate key: ").append($r10).toString());
                    }
                    $r1.endArray();
                }
                $r1.endArray();
                return $r4;
            }
            $r1.beginObject();
            while ($r1.hasNext()) {
                zzavq.bYx.zzi($r1);
                $r6 = this.bZj.zzb($r1);
                if ($r4.put($r6, this.bZk.zzb($r1)) != null) {
                    $r10 = String.valueOf($r6);
                    throw new zzavd(new StringBuilder(String.valueOf($r10).length() + 15).append("duplicate key: ").append($r10).toString());
                }
            }
            $r1.endObject();
            return $r4;
        }
    }

    public zzawd(zzavo $r1, boolean $z0) throws  {
        this.bXt = $r1;
        this.bZi = $z0;
    }

    private zzavg<?> zza(@Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Ljava/lang/reflect/Type;", ")", "Lcom/google/android/gms/internal/zzavg", "<*>;"}) zzauo $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzauo;", "Ljava/lang/reflect/Type;", ")", "Lcom/google/android/gms/internal/zzavg", "<*>;"}) Type $r2) throws  {
        return ($r2 == Boolean.TYPE || $r2 == Boolean.class) ? zzawj.bZE : $r1.zza(zzawk.zzl($r2));
    }

    public <T> zzavg<T> zza(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzauo $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzawk<T> $r2) throws  {
        Type $r3 = $r2.hO();
        if (!Map.class.isAssignableFrom($r2.hN())) {
            return null;
        }
        Type[] $r7 = zzavn.zzb($r3, zzavn.zzf($r3));
        zzavg $r8 = zza($r1, $r7[0]);
        zzavg $r10 = $r1.zza(zzawk.zzl($r7[1]));
        zzavo com_google_android_gms_internal_zzavo = this.bXt;
        zzavo $r11 = com_google_android_gms_internal_zzavo;
        zzavt $r12 = com_google_android_gms_internal_zzavo.zzb($r2);
        return new zza(this, $r1, $r7[0], $r8, $r7[1], $r10, $r12);
    }
}
