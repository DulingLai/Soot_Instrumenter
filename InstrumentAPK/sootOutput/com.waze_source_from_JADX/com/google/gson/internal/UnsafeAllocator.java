package com.google.gson.internal;

import dalvik.annotation.Signature;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class UnsafeAllocator {

    static class C10374 extends UnsafeAllocator {
        C10374() throws  {
        }

        public <T> T newInstance(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TT;>;)TT;"}) Class<T> $r1) throws  {
            throw new UnsupportedOperationException("Cannot allocate " + $r1);
        }
    }

    public abstract <T> T newInstance(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TT;>;)TT;"}) Class<T> cls) throws Exception;

    public static UnsafeAllocator create() throws  {
        final Method $r4;
        UnsafeAllocator $r5;
        try {
            Class $r0 = Class.forName("sun.misc.Unsafe");
            Field $r1 = $r0.getDeclaredField("theUnsafe");
            $r1.setAccessible(true);
            final Object $r2 = $r1.get(null);
            $r4 = $r0.getMethod("allocateInstance", new Class[]{Class.class});
            $r5 = r12;
            UnsafeAllocator r12 = new UnsafeAllocator() {
                public <T> T newInstance(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TT;>;)TT;"}) Class<T> $r1) throws Exception {
                    return $r4.invoke($r2, new Object[]{$r1});
                }
            };
            return $r5;
        } catch (Exception e) {
            UnsafeAllocator c10352;
            try {
                $r4 = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", new Class[]{Class.class});
                $r4.setAccessible(true);
                int $i0 = ((Integer) $r4.invoke(null, new Object[]{Object.class})).intValue();
                $r4 = ObjectStreamClass.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Integer.TYPE});
                $r4.setAccessible(true);
                $r5 = c10352;
                final int i = $i0;
                c10352 = new UnsafeAllocator() {
                    public <T> T newInstance(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TT;>;)TT;"}) Class<T> $r1) throws Exception {
                        return $r4.invoke(null, new Object[]{$r1, Integer.valueOf(i)});
                    }
                };
                return $r5;
            } catch (Exception e2) {
                try {
                    $r4 = ObjectInputStream.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Class.class});
                    $r4.setAccessible(true);
                    $r5 = c10352;
                    c10352 = new UnsafeAllocator() {
                        public <T> T newInstance(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TT;>;)TT;"}) Class<T> $r1) throws Exception {
                            return $r4.invoke(null, new Object[]{$r1, Object.class});
                        }
                    };
                    return $r5;
                } catch (Exception e3) {
                    return new C10374();
                }
            }
        }
    }
}
