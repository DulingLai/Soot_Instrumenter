package com.google.android.gms.internal;

import dalvik.annotation.Signature;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzavu {
    private static final Map<Class<?>, Class<?>> bYN;
    private static final Map<Class<?>, Class<?>> bYO;

    static {
        HashMap $r1 = new HashMap(16);
        HashMap $r0 = new HashMap(16);
        zza($r1, $r0, Boolean.TYPE, Boolean.class);
        zza($r1, $r0, Byte.TYPE, Byte.class);
        zza($r1, $r0, Character.TYPE, Character.class);
        zza($r1, $r0, Double.TYPE, Double.class);
        zza($r1, $r0, Float.TYPE, Float.class);
        zza($r1, $r0, Integer.TYPE, Integer.class);
        zza($r1, $r0, Long.TYPE, Long.class);
        zza($r1, $r0, Short.TYPE, Short.class);
        zza($r1, $r0, Void.TYPE, Void.class);
        bYN = Collections.unmodifiableMap($r1);
        bYO = Collections.unmodifiableMap($r0);
    }

    private static void zza(@Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;>;", "Ljava/util/Map", "<", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;>;", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;)V"}) Map<Class<?>, Class<?>> $r0, @Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;>;", "Ljava/util/Map", "<", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;>;", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;)V"}) Map<Class<?>, Class<?>> $r1, @Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;>;", "Ljava/util/Map", "<", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;>;", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;)V"}) Class<?> $r2, @Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;>;", "Ljava/util/Map", "<", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;>;", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;)V"}) Class<?> $r3) throws  {
        $r0.put($r2, $r3);
        $r1.put($r3, $r2);
    }

    public static boolean zzk(Type $r0) throws  {
        return bYN.containsKey($r0);
    }

    public static <T> Class<T> zzo(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TT;>;)", "Ljava/lang/Class", "<TT;>;"}) Class<T> $r0) throws  {
        Class $r3 = (Class) bYN.get(zzavm.zzag($r0));
        return $r3 == null ? $r0 : $r3;
    }
}
