package com.google.android.gms.internal;

import dalvik.annotation.Signature;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzavw {

    /* compiled from: dalvik_source_com.waze.apk */
    static class C07934 extends zzavw {
        C07934() throws  {
        }

        public <T> T zze(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TT;>;)TT;"}) Class<T> $r1) throws  {
            String $r3 = String.valueOf($r1);
            throw new UnsupportedOperationException(new StringBuilder(String.valueOf($r3).length() + 16).append("Cannot allocate ").append($r3).toString());
        }
    }

    public static zzavw hA() throws  {
        final Method $r4;
        zzavw $r5;
        try {
            Class $r0 = Class.forName("sun.misc.Unsafe");
            Field $r1 = $r0.getDeclaredField("theUnsafe");
            $r1.setAccessible(true);
            final Object $r2 = $r1.get(null);
            $r4 = $r0.getMethod("allocateInstance", new Class[]{Class.class});
            $r5 = r12;
            zzavw r12 = new zzavw() {
                public <T> T zze(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TT;>;)TT;"}) Class<T> $r1) throws Exception {
                    return $r4.invoke($r2, new Object[]{$r1});
                }
            };
            return $r5;
        } catch (Exception e) {
            zzavw c07912;
            try {
                $r4 = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", new Class[]{Class.class});
                $r4.setAccessible(true);
                int $i0 = ((Integer) $r4.invoke(null, new Object[]{Object.class})).intValue();
                $r4 = ObjectStreamClass.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Integer.TYPE});
                $r4.setAccessible(true);
                $r5 = c07912;
                final int i = $i0;
                c07912 = new zzavw() {
                    public <T> T zze(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TT;>;)TT;"}) Class<T> $r1) throws Exception {
                        return $r4.invoke(null, new Object[]{$r1, Integer.valueOf(i)});
                    }
                };
                return $r5;
            } catch (Exception e2) {
                try {
                    $r4 = ObjectInputStream.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Class.class});
                    $r4.setAccessible(true);
                    $r5 = c07912;
                    c07912 = new zzavw() {
                        public <T> T zze(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TT;>;)TT;"}) Class<T> $r1) throws Exception {
                            return $r4.invoke(null, new Object[]{$r1, Object.class});
                        }
                    };
                    return $r5;
                } catch (Exception e3) {
                    return new C07934();
                }
            }
        }
    }

    public abstract <T> T zze(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TT;>;)TT;"}) Class<T> cls) throws Exception;
}
