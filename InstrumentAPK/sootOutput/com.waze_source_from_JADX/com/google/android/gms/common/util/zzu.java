package com.google.android.gms.common.util;

import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzab;
import dalvik.annotation.Signature;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzu {
    public static String[] zzb(Scope[] $r0) throws  {
        zzab.zzb((Object) $r0, (Object) "scopes can't be null.");
        String[] $r1 = new String[$r0.length];
        for (int $i0 = 0; $i0 < $r0.length; $i0++) {
            $r1[$i0] = $r0[$i0].zzasc();
        }
        return $r1;
    }

    public static String[] zzd(@Signature({"(", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/api/Scope;", ">;)[", "Ljava/lang/String;"}) Set<Scope> $r0) throws  {
        zzab.zzb((Object) $r0, (Object) "scopes can't be null.");
        return zzb((Scope[]) $r0.toArray(new Scope[$r0.size()]));
    }
}
