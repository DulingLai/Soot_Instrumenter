package com.google.android.gms.internal;

import dalvik.annotation.Signature;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzavp implements zzavh, Cloneable {
    public static final zzavp bYm = new zzavp();
    private double bYn = -1.0d;
    private int bYo = 136;
    private boolean bYp = true;
    private List<zzauk> bYq = Collections.emptyList();
    private List<zzauk> bYr = Collections.emptyList();

    private boolean zza(zzavk $r1) throws  {
        return $r1 == null || $r1.hu() <= this.bYn;
    }

    private boolean zza(zzavk $r1, zzavl $r2) throws  {
        return zza($r1) && zza($r2);
    }

    private boolean zza(zzavl $r1) throws  {
        return $r1 == null || $r1.hu() > this.bYn;
    }

    private boolean zzl(@Signature({"(", "Ljava/lang/Class", "<*>;)Z"}) Class<?> $r1) throws  {
        return !Enum.class.isAssignableFrom($r1) && ($r1.isAnonymousClass() || $r1.isLocalClass());
    }

    private boolean zzm(@Signature({"(", "Ljava/lang/Class", "<*>;)Z"}) Class<?> $r1) throws  {
        return $r1.isMemberClass() && !zzn($r1);
    }

    private boolean zzn(@Signature({"(", "Ljava/lang/Class", "<*>;)Z"}) Class<?> $r1) throws  {
        return ($r1.getModifiers() & 8) != 0;
    }

    protected /* synthetic */ Object clone() throws CloneNotSupportedException {
        return hw();
    }

    protected zzavp hw() throws  {
        try {
            return (zzavp) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public <T> zzavg<T> zza(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzauo $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzawk<T> $r2) throws  {
        Class $r3 = $r2.hN();
        boolean $z0 = zza($r3, true);
        boolean $z1 = zza($r3, false);
        if (!$z0 && !$z1) {
            return null;
        }
        final boolean z = $z1;
        final boolean z2 = $z0;
        final zzauo com_google_android_gms_internal_zzauo = $r1;
        final zzawk<T> com_google_android_gms_internal_zzawk_T = $r2;
        return new zzavg<T>(this) {
            private zzavg<T> bXB;
            final /* synthetic */ zzavp bYw;

            private zzavg<T> hs() throws  {
                zzavg $r3 = this.bXB;
                if ($r3 != null) {
                    return $r3;
                }
                $r3 = com_google_android_gms_internal_zzauo.zza(this.bYw, com_google_android_gms_internal_zzawk_T);
                this.bXB = $r3;
                return $r3;
            }

            public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzawn;", "TT;)V"}) zzawn $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzawn;", "TT;)V"}) T $r2) throws IOException {
                if (z2) {
                    $r1.hM();
                } else {
                    hs().zza($r1, $r2);
                }
            }

            public T zzb(@Signature({"(", "Lcom/google/android/gms/internal/zzawl;", ")TT;"}) zzawl $r1) throws IOException {
                if (!z) {
                    return hs().zzb($r1);
                }
                $r1.skipValue();
                return null;
            }
        };
    }

    public zzavp zza(zzauk $r1, boolean $z0, boolean $z1) throws  {
        zzavp $r2 = hw();
        if ($z0) {
            $r2.bYq = new ArrayList(this.bYq);
            $r2.bYq.add($r1);
        }
        if (!$z1) {
            return $r2;
        }
        $r2.bYr = new ArrayList(this.bYr);
        $r2.bYr.add($r1);
        return $r2;
    }

    public boolean zza(@Signature({"(", "Ljava/lang/Class", "<*>;Z)Z"}) Class<?> $r1, @Signature({"(", "Ljava/lang/Class", "<*>;Z)Z"}) boolean $z0) throws  {
        if (this.bYn != -1.0d) {
            if (!zza((zzavk) $r1.getAnnotation(zzavk.class), (zzavl) $r1.getAnnotation(zzavl.class))) {
                return true;
            }
        }
        if (!this.bYp && zzm($r1)) {
            return true;
        }
        if (zzl($r1)) {
            return true;
        }
        for (zzauk zzg : $z0 ? this.bYq : this.bYr) {
            if (zzg.zzg($r1)) {
                return true;
            }
        }
        return false;
    }

    public boolean zza(Field $r1, boolean $z0) throws  {
        if ((this.bYo & $r1.getModifiers()) != 0) {
            return true;
        }
        if (this.bYn != -1.0d) {
            if (!zza((zzavk) $r1.getAnnotation(zzavk.class), (zzavl) $r1.getAnnotation(zzavl.class))) {
                return true;
            }
        }
        if ($r1.isSynthetic()) {
            return true;
        }
        if (!this.bYp) {
            if (zzm($r1.getType())) {
                return true;
            }
        }
        if (zzl($r1.getType())) {
            return true;
        }
        List<zzauk> $r6 = $z0 ? this.bYq : this.bYr;
        if (!$r6.isEmpty()) {
            zzaul com_google_android_gms_internal_zzaul = new zzaul($r1);
            for (zzauk zza : $r6) {
                if (zza.zza(com_google_android_gms_internal_zzaul)) {
                    return true;
                }
            }
        }
        return false;
    }

    public zzavp zzj(int... $r1) throws  {
        this = hw();
        this.bYo = 0;
        for (int $i3 : $r1) {
            this.bYo = $i3 | this.bYo;
        }
        return this;
    }
}
