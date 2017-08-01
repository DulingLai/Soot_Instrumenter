package com.google.android.gms.common.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzaa {

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zza {
        private final List<String> Kf;
        private final Object zzcmn;

        private zza(Object $r1) throws  {
            this.zzcmn = zzab.zzag($r1);
            this.Kf = new ArrayList();
        }

        public String toString() throws  {
            StringBuilder $r1 = new StringBuilder(100).append(this.zzcmn.getClass().getSimpleName()).append('{');
            int $i0 = this.Kf.size();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                $r1.append((String) this.Kf.get($i1));
                if ($i1 < $i0 - 1) {
                    $r1.append(", ");
                }
            }
            return $r1.append('}').toString();
        }

        public zza zzh(String $r1, Object $r2) throws  {
            List $r3 = this.Kf;
            $r1 = (String) zzab.zzag($r1);
            String $r5 = String.valueOf(String.valueOf($r2));
            $r3.add(new StringBuilder((String.valueOf($r1).length() + 1) + String.valueOf($r5).length()).append($r1).append("=").append($r5).toString());
            return this;
        }
    }

    public static boolean equal(Object $r0, Object $r1) throws  {
        return $r0 == $r1 || ($r0 != null && $r0.equals($r1));
    }

    public static int hashCode(Object... $r0) throws  {
        return Arrays.hashCode($r0);
    }

    public static zza zzaf(Object $r0) throws  {
        return new zza($r0);
    }
}
