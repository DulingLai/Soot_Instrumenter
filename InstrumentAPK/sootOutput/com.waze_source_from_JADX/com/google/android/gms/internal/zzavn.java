package com.google.android.gms.internal;

import dalvik.annotation.Signature;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzavn {
    static final Type[] bXY = new Type[0];

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zza implements Serializable, GenericArrayType {
        private final Type bXZ;

        public zza(Type $r1) throws  {
            this.bXZ = zzavn.zze($r1);
        }

        public boolean equals(Object $r1) throws  {
            return ($r1 instanceof GenericArrayType) && zzavn.zza((Type) this, (Type) (GenericArrayType) $r1);
        }

        public Type getGenericComponentType() throws  {
            return this.bXZ;
        }

        public int hashCode() throws  {
            return this.bXZ.hashCode();
        }

        public String toString() throws  {
            return String.valueOf(zzavn.zzg(this.bXZ)).concat("[]");
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzb implements Serializable, ParameterizedType {
        private final Type bYa;
        private final Type bYb;
        private final Type[] bYc;

        public zzb(Type $r1, Type $r2, Type... $r3) throws  {
            if ($r2 instanceof Class) {
                Class $r4 = (Class) $r2;
                boolean $z0 = Modifier.isStatic($r4.getModifiers()) || $r4.getEnclosingClass() == null;
                $z0 = $r1 != null || $z0;
                zzavm.zzbn($z0);
            }
            this.bYa = $r1 == null ? null : zzavn.zze($r1);
            this.bYb = zzavn.zze($r2);
            this.bYc = (Type[]) $r3.clone();
            for (int $i0 = 0; $i0 < this.bYc.length; $i0++) {
                zzavm.zzag(this.bYc[$i0]);
                zzavn.zzi(this.bYc[$i0]);
                this.bYc[$i0] = zzavn.zze(this.bYc[$i0]);
            }
        }

        public boolean equals(Object $r1) throws  {
            return ($r1 instanceof ParameterizedType) && zzavn.zza((Type) this, (Type) (ParameterizedType) $r1);
        }

        public Type[] getActualTypeArguments() throws  {
            return (Type[]) this.bYc.clone();
        }

        public Type getOwnerType() throws  {
            return this.bYa;
        }

        public Type getRawType() throws  {
            return this.bYb;
        }

        public int hashCode() throws  {
            return (Arrays.hashCode(this.bYc) ^ this.bYb.hashCode()) ^ zzavn.zzda(this.bYa);
        }

        public String toString() throws  {
            StringBuilder $r1 = new StringBuilder((this.bYc.length + 1) * 30);
            $r1.append(zzavn.zzg(this.bYb));
            if (this.bYc.length == 0) {
                return $r1.toString();
            }
            $r1.append("<").append(zzavn.zzg(this.bYc[0]));
            for (int $i0 = 1; $i0 < this.bYc.length; $i0++) {
                $r1.append(", ").append(zzavn.zzg(this.bYc[$i0]));
            }
            return $r1.append(">").toString();
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zzc implements Serializable, WildcardType {
        private final Type bYd;
        private final Type bYe;

        public zzc(Type[] $r1, Type[] $r2) throws  {
            boolean $z0 = true;
            zzavm.zzbn($r2.length <= 1);
            zzavm.zzbn($r1.length == 1);
            if ($r2.length == 1) {
                zzavm.zzag($r2[0]);
                zzavn.zzi($r2[0]);
                if ($r1[0] != Object.class) {
                    $z0 = false;
                }
                zzavm.zzbn($z0);
                this.bYe = zzavn.zze($r2[0]);
                this.bYd = Object.class;
                return;
            }
            zzavm.zzag($r1[0]);
            zzavn.zzi($r1[0]);
            this.bYe = null;
            this.bYd = zzavn.zze($r1[0]);
        }

        public boolean equals(Object $r1) throws  {
            return ($r1 instanceof WildcardType) && zzavn.zza((Type) this, (Type) (WildcardType) $r1);
        }

        public Type[] getLowerBounds() throws  {
            if (this.bYe == null) {
                return zzavn.bXY;
            }
            return new Type[]{this.bYe};
        }

        public Type[] getUpperBounds() throws  {
            return new Type[]{this.bYd};
        }

        public int hashCode() throws  {
            return (this.bYe != null ? this.bYe.hashCode() + 31 : 1) ^ (this.bYd.hashCode() + 31);
        }

        public String toString() throws  {
            String $r2;
            String $r3;
            if (this.bYe != null) {
                $r2 = "? super ";
                $r3 = String.valueOf(zzavn.zzg(this.bYe));
                return $r3.length() != 0 ? $r2.concat($r3) : new String("? super ");
            } else if (this.bYd == Object.class) {
                return "?";
            } else {
                $r2 = "? extends ";
                $r3 = String.valueOf(zzavn.zzg(this.bYd));
                return $r3.length() != 0 ? $r2.concat($r3) : new String("? extends ");
            }
        }
    }

    static boolean equal(Object $r0, Object $r1) throws  {
        return $r0 == $r1 || ($r0 != null && $r0.equals($r1));
    }

    private static int zza(Object[] $r0, Object $r1) throws  {
        for (int $i0 = 0; $i0 < $r0.length; $i0++) {
            if ($r1.equals($r0[$i0])) {
                return $i0;
            }
        }
        throw new NoSuchElementException();
    }

    private static Class<?> zza(@Signature({"(", "Ljava/lang/reflect/TypeVariable", "<*>;)", "Ljava/lang/Class", "<*>;"}) TypeVariable<?> $r0) throws  {
        GenericDeclaration $r1 = $r0.getGenericDeclaration();
        return $r1 instanceof Class ? (Class) $r1 : null;
    }

    public static ParameterizedType zza(Type $r0, Type $r1, Type... $r2) throws  {
        return new zzb($r0, $r1, $r2);
    }

    public static Type zza(@Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;)", "Ljava/lang/reflect/Type;"}) Type $r0, @Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;)", "Ljava/lang/reflect/Type;"}) Class<?> $r1) throws  {
        $r0 = zzb($r0, $r1, Collection.class);
        Type $r2 = $r0;
        if ($r0 instanceof WildcardType) {
            $r2 = ((WildcardType) $r0).getUpperBounds()[0];
        }
        return $r2 instanceof ParameterizedType ? ((ParameterizedType) $r2).getActualTypeArguments()[0] : Object.class;
    }

    static Type zza(@Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;)", "Ljava/lang/reflect/Type;"}) Type $r1, @Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;)", "Ljava/lang/reflect/Type;"}) Class<?> $r2, @Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;)", "Ljava/lang/reflect/Type;"}) Class<?> $r0) throws  {
        if ($r0 == $r2) {
            return $r1;
        }
        if ($r0.isInterface()) {
            Class[] $r3 = $r2.getInterfaces();
            int $i1 = $r3.length;
            for (int $i0 = 0; $i0 < $i1; $i0++) {
                if ($r3[$i0] == $r0) {
                    return $r2.getGenericInterfaces()[$i0];
                }
                if ($r0.isAssignableFrom($r3[$i0])) {
                    return zza($r2.getGenericInterfaces()[$i0], $r3[$i0], (Class) $r0);
                }
            }
        }
        if (!$r2.isInterface()) {
            Class $r22;
            while ($r22 != Object.class) {
                Class $r4 = $r22.getSuperclass();
                if ($r4 == $r0) {
                    return $r22.getGenericSuperclass();
                }
                if ($r0.isAssignableFrom($r4)) {
                    return zza($r22.getGenericSuperclass(), $r4, (Class) $r0);
                }
                $r22 = $r4;
            }
        }
        return $r0;
    }

    public static Type zza(@Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;", "Ljava/lang/reflect/Type;", ")", "Ljava/lang/reflect/Type;"}) Type $r0, @Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;", "Ljava/lang/reflect/Type;", ")", "Ljava/lang/reflect/Type;"}) Class<?> $r1, @Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;", "Ljava/lang/reflect/Type;", ")", "Ljava/lang/reflect/Type;"}) Type $r3) throws  {
        while ($r3 instanceof TypeVariable) {
            Type $r4 = (TypeVariable) $r3;
            $r3 = zza($r0, (Class) $r1, (TypeVariable) $r4);
            if ($r3 == $r4) {
                return $r3;
            }
        }
        if (($r3 instanceof Class) && ((Class) $r3).isArray()) {
            Class $r32 = (Class) $r3;
            Type $r5 = $r32.getComponentType();
            $r0 = zza($r0, (Class) $r1, $r5);
            return $r5 != $r0 ? zzb($r0) : $r32;
        } else if ($r3 instanceof GenericArrayType) {
            GenericArrayType $r33 = (GenericArrayType) $r3;
            $r7 = $r33.getGenericComponentType();
            $r0 = zza($r0, (Class) $r1, $r7);
            return $r7 != $r0 ? zzb($r0) : $r33;
        } else if ($r3 instanceof ParameterizedType) {
            ParameterizedType $r34 = (ParameterizedType) $r3;
            Type $r2 = $r34.getOwnerType();
            $r7 = zza($r0, (Class) $r1, $r2);
            boolean $z0 = $r7 != $r2;
            $r8 = $r34.getActualTypeArguments();
            int $i0 = $r8.length;
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                Type $r9 = zza($r0, (Class) $r1, $r8[$i1]);
                if ($r9 != $r8[$i1]) {
                    if (!$z0) {
                        $r8 = (Type[]) $r8.clone();
                        $z0 = true;
                    }
                    $r8[$i1] = $r9;
                }
            }
            return $z0 ? zza($r7, $r34.getRawType(), $r8) : $r34;
        } else if (!($r3 instanceof WildcardType)) {
            return $r3;
        } else {
            WildcardType $r35 = (WildcardType) $r3;
            $r8 = $r35.getLowerBounds();
            Type[] $r12 = $r35.getUpperBounds();
            if ($r8.length == 1) {
                $r0 = zza($r0, (Class) $r1, $r8[0]);
                return $r0 != $r8[0] ? zzd($r0) : $r35;
            } else if ($r12.length != 1) {
                return $r35;
            } else {
                $r0 = zza($r0, (Class) $r1, $r12[0]);
                return $r0 != $r12[0] ? zzc($r0) : $r35;
            }
        }
    }

    static Type zza(@Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;", "Ljava/lang/reflect/TypeVariable", "<*>;)", "Ljava/lang/reflect/Type;"}) Type $r0, @Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;", "Ljava/lang/reflect/TypeVariable", "<*>;)", "Ljava/lang/reflect/Type;"}) Class<?> $r1, @Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;", "Ljava/lang/reflect/TypeVariable", "<*>;)", "Ljava/lang/reflect/Type;"}) TypeVariable<?> $r2) throws  {
        Class $r3 = zza($r2);
        if ($r3 == null) {
            return $r2;
        }
        $r0 = zza($r0, (Class) $r1, $r3);
        if (!($r0 instanceof ParameterizedType)) {
            return $r2;
        }
        return ((ParameterizedType) $r0).getActualTypeArguments()[zza($r3.getTypeParameters(), (Object) $r2)];
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean zza(java.lang.reflect.Type r27, java.lang.reflect.Type r28) throws  {
        /*
        r2 = 1;
        r0 = r27;
        r1 = r28;
        if (r0 != r1) goto L_0x0009;
    L_0x0007:
        r3 = 1;
        return r3;
    L_0x0009:
        r0 = r27;
        r4 = r0 instanceof java.lang.Class;
        if (r4 == 0) goto L_0x0018;
    L_0x000f:
        r0 = r27;
        r1 = r28;
        r2 = r0.equals(r1);
        return r2;
    L_0x0018:
        r0 = r27;
        r4 = r0 instanceof java.lang.reflect.ParameterizedType;
        if (r4 == 0) goto L_0x0063;
    L_0x001e:
        r0 = r28;
        r4 = r0 instanceof java.lang.reflect.ParameterizedType;
        if (r4 == 0) goto L_0x0108;
    L_0x0024:
        r6 = r27;
        r6 = (java.lang.reflect.ParameterizedType) r6;
        r5 = r6;
        r8 = r28;
        r8 = (java.lang.reflect.ParameterizedType) r8;
        r7 = r8;
        r27 = r5.getOwnerType();
        r28 = r7.getOwnerType();
        r0 = r27;
        r1 = r28;
        r4 = equal(r0, r1);
        if (r4 == 0) goto L_0x0061;
    L_0x0040:
        r27 = r5.getRawType();
        r28 = r7.getRawType();
        r0 = r27;
        r1 = r28;
        r4 = r0.equals(r1);
        if (r4 == 0) goto L_0x0061;
    L_0x0052:
        r9 = r5.getActualTypeArguments();
        r10 = r7.getActualTypeArguments();
        r4 = java.util.Arrays.equals(r9, r10);
        if (r4 == 0) goto L_0x0061;
    L_0x0060:
        return r2;
    L_0x0061:
        r2 = 0;
        goto L_0x0060;
    L_0x0063:
        r0 = r27;
        r4 = r0 instanceof java.lang.reflect.GenericArrayType;
        if (r4 == 0) goto L_0x008a;
    L_0x0069:
        r0 = r28;
        r2 = r0 instanceof java.lang.reflect.GenericArrayType;
        if (r2 == 0) goto L_0x010a;
    L_0x006f:
        r12 = r27;
        r12 = (java.lang.reflect.GenericArrayType) r12;
        r11 = r12;
        r14 = r28;
        r14 = (java.lang.reflect.GenericArrayType) r14;
        r13 = r14;
        r27 = r11.getGenericComponentType();
        r28 = r13.getGenericComponentType();
        r0 = r27;
        r1 = r28;
        r2 = zza(r0, r1);
        return r2;
    L_0x008a:
        r0 = r27;
        r4 = r0 instanceof java.lang.reflect.WildcardType;
        if (r4 == 0) goto L_0x00c5;
    L_0x0090:
        r0 = r28;
        r4 = r0 instanceof java.lang.reflect.WildcardType;
        if (r4 == 0) goto L_0x010c;
    L_0x0096:
        r16 = r27;
        r16 = (java.lang.reflect.WildcardType) r16;
        r15 = r16;
        r18 = r28;
        r18 = (java.lang.reflect.WildcardType) r18;
        r17 = r18;
        r9 = r15.getUpperBounds();
        r0 = r17;
        r10 = r0.getUpperBounds();
        r4 = java.util.Arrays.equals(r9, r10);
        if (r4 == 0) goto L_0x00c3;
    L_0x00b2:
        r9 = r15.getLowerBounds();
        r0 = r17;
        r10 = r0.getLowerBounds();
        r4 = java.util.Arrays.equals(r9, r10);
        if (r4 == 0) goto L_0x00c3;
    L_0x00c2:
        return r2;
    L_0x00c3:
        r2 = 0;
        goto L_0x00c2;
    L_0x00c5:
        r0 = r27;
        r4 = r0 instanceof java.lang.reflect.TypeVariable;
        if (r4 == 0) goto L_0x010e;
    L_0x00cb:
        r0 = r28;
        r4 = r0 instanceof java.lang.reflect.TypeVariable;
        if (r4 == 0) goto L_0x0110;
    L_0x00d1:
        r20 = r27;
        r20 = (java.lang.reflect.TypeVariable) r20;
        r19 = r20;
        r22 = r28;
        r22 = (java.lang.reflect.TypeVariable) r22;
        r21 = r22;
        r0 = r19;
        r23 = r0.getGenericDeclaration();
        r0 = r21;
        r24 = r0.getGenericDeclaration();
        r0 = r23;
        r1 = r24;
        if (r0 != r1) goto L_0x0106;
    L_0x00ef:
        r0 = r19;
        r25 = r0.getName();
        r0 = r21;
        r26 = r0.getName();
        r0 = r25;
        r1 = r26;
        r4 = r0.equals(r1);
        if (r4 == 0) goto L_0x0106;
    L_0x0105:
        return r2;
    L_0x0106:
        r2 = 0;
        goto L_0x0105;
    L_0x0108:
        r3 = 0;
        return r3;
    L_0x010a:
        r3 = 0;
        return r3;
    L_0x010c:
        r3 = 0;
        return r3;
    L_0x010e:
        r3 = 0;
        return r3;
    L_0x0110:
        r3 = 0;
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzavn.zza(java.lang.reflect.Type, java.lang.reflect.Type):boolean");
    }

    public static GenericArrayType zzb(Type $r0) throws  {
        return new zza($r0);
    }

    static Type zzb(@Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;)", "Ljava/lang/reflect/Type;"}) Type $r0, @Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;)", "Ljava/lang/reflect/Type;"}) Class<?> $r1, @Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;)", "Ljava/lang/reflect/Type;"}) Class<?> $r2) throws  {
        zzavm.zzbn($r2.isAssignableFrom($r1));
        return zza($r0, (Class) $r1, zza($r0, (Class) $r1, (Class) $r2));
    }

    public static Type[] zzb(@Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;)[", "Ljava/lang/reflect/Type;"}) Type $r0, @Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;)[", "Ljava/lang/reflect/Type;"}) Class<?> $r1) throws  {
        if ($r0 == Properties.class) {
            return new Type[]{String.class, String.class};
        }
        $r0 = zzb($r0, $r1, Map.class);
        if ($r0 instanceof ParameterizedType) {
            return ((ParameterizedType) $r0).getActualTypeArguments();
        }
        return new Type[]{Object.class, Object.class};
    }

    public static WildcardType zzc(Type $r0) throws  {
        return new zzc(new Type[]{$r0}, bXY);
    }

    public static WildcardType zzd(Type $r0) throws  {
        return new zzc(new Type[]{Object.class}, new Type[]{$r0});
    }

    private static int zzda(Object $r0) throws  {
        return $r0 != null ? $r0.hashCode() : 0;
    }

    public static Type zze(Type $r0) throws  {
        if ($r0 instanceof Class) {
            Serializable $r2;
            Class $r1 = (Class) $r0;
            if ($r1.isArray()) {
                $r2 = r10;
                zza r10 = new zza(zze($r1.getComponentType()));
            } else {
                Object $r22 = $r1;
            }
            return (Type) $r2;
        } else if ($r0 instanceof ParameterizedType) {
            ParameterizedType $r3 = (ParameterizedType) $r0;
            return new zzb($r3.getOwnerType(), $r3.getRawType(), $r3.getActualTypeArguments());
        } else if ($r0 instanceof GenericArrayType) {
            return new zza(((GenericArrayType) $r0).getGenericComponentType());
        } else {
            if (!($r0 instanceof WildcardType)) {
                return $r0;
            }
            WildcardType $r8 = (WildcardType) $r0;
            return new zzc($r8.getUpperBounds(), $r8.getLowerBounds());
        }
    }

    public static Class<?> zzf(@Signature({"(", "Ljava/lang/reflect/Type;", ")", "Ljava/lang/Class", "<*>;"}) Type $r1) throws  {
        if ($r1 instanceof Class) {
            return (Class) $r1;
        }
        if ($r1 instanceof ParameterizedType) {
            $r1 = ((ParameterizedType) $r1).getRawType();
            zzavm.zzbn($r1 instanceof Class);
            return (Class) $r1;
        } else if ($r1 instanceof GenericArrayType) {
            return Array.newInstance(zzf(((GenericArrayType) $r1).getGenericComponentType()), 0).getClass();
        } else {
            if ($r1 instanceof TypeVariable) {
                return Object.class;
            }
            if ($r1 instanceof WildcardType) {
                return zzf(((WildcardType) $r1).getUpperBounds()[0]);
            }
            String $r8 = $r1 == null ? "null" : $r1.getClass().getName();
            String $r10 = String.valueOf("Expected a Class, ParameterizedType, or GenericArrayType, but <");
            String $r11 = String.valueOf($r1);
            throw new IllegalArgumentException(new StringBuilder(((String.valueOf($r10).length() + 13) + String.valueOf($r11).length()) + String.valueOf($r8).length()).append($r10).append($r11).append("> is of type ").append($r8).toString());
        }
    }

    public static String zzg(Type $r0) throws  {
        return $r0 instanceof Class ? ((Class) $r0).getName() : $r0.toString();
    }

    public static Type zzh(Type $r0) throws  {
        return $r0 instanceof GenericArrayType ? ((GenericArrayType) $r0).getGenericComponentType() : ((Class) $r0).getComponentType();
    }

    private static void zzi(Type $r0) throws  {
        boolean $z0 = (($r0 instanceof Class) && ((Class) $r0).isPrimitive()) ? false : true;
        zzavm.zzbn($z0);
    }
}
