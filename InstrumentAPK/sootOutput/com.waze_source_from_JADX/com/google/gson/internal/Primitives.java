package com.google.gson.internal;

import dalvik.annotation.Signature;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Primitives {
    private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER_TYPE;
    private static final Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE_TYPE;

    private Primitives() throws  {
        throw new UnsupportedOperationException();
    }

    static {
        HashMap $r0 = new HashMap(16);
        HashMap $r1 = new HashMap(16);
        add($r0, $r1, Boolean.TYPE, Boolean.class);
        add($r0, $r1, Byte.TYPE, Byte.class);
        add($r0, $r1, Character.TYPE, Character.class);
        add($r0, $r1, Double.TYPE, Double.class);
        add($r0, $r1, Float.TYPE, Float.class);
        add($r0, $r1, Integer.TYPE, Integer.class);
        add($r0, $r1, Long.TYPE, Long.class);
        add($r0, $r1, Short.TYPE, Short.class);
        add($r0, $r1, Void.TYPE, Void.class);
        PRIMITIVE_TO_WRAPPER_TYPE = Collections.unmodifiableMap($r0);
        WRAPPER_TO_PRIMITIVE_TYPE = Collections.unmodifiableMap($r1);
    }

    private static void add(@Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;>;", "Ljava/util/Map", "<", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;>;", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;)V"}) Map<Class<?>, Class<?>> $r0, @Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;>;", "Ljava/util/Map", "<", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;>;", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;)V"}) Map<Class<?>, Class<?>> $r1, @Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;>;", "Ljava/util/Map", "<", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;>;", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;)V"}) Class<?> $r2, @Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;>;", "Ljava/util/Map", "<", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;>;", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;)V"}) Class<?> $r3) throws  {
        $r0.put($r2, $r3);
        $r1.put($r3, $r2);
    }

    public static boolean isPrimitive(Type $r0) throws  {
        return PRIMITIVE_TO_WRAPPER_TYPE.containsKey($r0);
    }

    public static boolean isWrapperType(Type $r0) throws  {
        return WRAPPER_TO_PRIMITIVE_TYPE.containsKey(C$Gson$Preconditions.checkNotNull($r0));
    }

    public static <T> Class<T> wrap(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TT;>;)", "Ljava/lang/Class", "<TT;>;"}) Class<T> $r1) throws  {
        Class $r3 = (Class) PRIMITIVE_TO_WRAPPER_TYPE.get(C$Gson$Preconditions.checkNotNull($r1));
        return $r3 == null ? $r1 : $r3;
    }

    public static <T> Class<T> unwrap(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TT;>;)", "Ljava/lang/Class", "<TT;>;"}) Class<T> $r1) throws  {
        Class $r3 = (Class) WRAPPER_TO_PRIMITIVE_TYPE.get(C$Gson$Preconditions.checkNotNull($r1));
        return $r3 == null ? $r1 : $r3;
    }
}
