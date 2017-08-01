package com.google.android.gms.common.internal;

import dalvik.annotation.Signature;
import java.util.Iterator;

/* compiled from: dalvik_source_com.waze.apk */
public class zzy {
    private final String separator;

    private zzy(String $r1) throws  {
        this.separator = $r1;
    }

    public static zzy zzgx(String $r0) throws  {
        return new zzy($r0);
    }

    public final String zza(@Signature({"(", "Ljava/lang/Iterable", "<*>;)", "Ljava/lang/String;"}) Iterable<?> $r1) throws  {
        return zza(new StringBuilder(), $r1).toString();
    }

    public final StringBuilder zza(@Signature({"(", "Ljava/lang/StringBuilder;", "Ljava/lang/Iterable", "<*>;)", "Ljava/lang/StringBuilder;"}) StringBuilder $r1, @Signature({"(", "Ljava/lang/StringBuilder;", "Ljava/lang/Iterable", "<*>;)", "Ljava/lang/StringBuilder;"}) Iterable<?> $r2) throws  {
        Iterator $r3 = $r2.iterator();
        if (!$r3.hasNext()) {
            return $r1;
        }
        $r1.append(zzae($r3.next()));
        while ($r3.hasNext()) {
            $r1.append(this.separator);
            $r1.append(zzae($r3.next()));
        }
        return $r1;
    }

    CharSequence zzae(Object $r2) throws  {
        return $r2 instanceof CharSequence ? (CharSequence) $r2 : $r2.toString();
    }
}
