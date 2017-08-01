package com.google.gson.internal;

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

public final class C$Gson$Types {
    static final Type[] EMPTY_TYPE_ARRAY = new Type[0];

    /* compiled from: $Gson$Types */
    private static final class GenericArrayTypeImpl implements Serializable, GenericArrayType {
        private static final long serialVersionUID = 0;
        private final Type componentType;

        public GenericArrayTypeImpl(Type $r1) throws  {
            this.componentType = C$Gson$Types.canonicalize($r1);
        }

        public Type getGenericComponentType() throws  {
            return this.componentType;
        }

        public boolean equals(Object $r1) throws  {
            return ($r1 instanceof GenericArrayType) && C$Gson$Types.equals(this, (GenericArrayType) $r1);
        }

        public int hashCode() throws  {
            return this.componentType.hashCode();
        }

        public String toString() throws  {
            return C$Gson$Types.typeToString(this.componentType) + "[]";
        }
    }

    /* compiled from: $Gson$Types */
    private static final class ParameterizedTypeImpl implements Serializable, ParameterizedType {
        private static final long serialVersionUID = 0;
        private final Type ownerType;
        private final Type rawType;
        private final Type[] typeArguments;

        public ParameterizedTypeImpl(Type $r1, Type $r2, Type... $r3) throws  {
            boolean $z0 = false;
            if ($r2 instanceof Class) {
                Class $r4 = (Class) $r2;
                boolean $z1 = Modifier.isStatic($r4.getModifiers()) || $r4.getEnclosingClass() == null;
                if ($r1 != null || $z1) {
                    $z0 = true;
                }
                C$Gson$Preconditions.checkArgument($z0);
            }
            if ($r1 == null) {
                $r1 = null;
            } else {
                $r1 = C$Gson$Types.canonicalize($r1);
            }
            this.ownerType = $r1;
            this.rawType = C$Gson$Types.canonicalize($r2);
            this.typeArguments = (Type[]) $r3.clone();
            for (int $i0 = 0; $i0 < this.typeArguments.length; $i0++) {
                C$Gson$Preconditions.checkNotNull(this.typeArguments[$i0]);
                C$Gson$Types.checkNotPrimitive(this.typeArguments[$i0]);
                this.typeArguments[$i0] = C$Gson$Types.canonicalize(this.typeArguments[$i0]);
            }
        }

        public Type[] getActualTypeArguments() throws  {
            return (Type[]) this.typeArguments.clone();
        }

        public Type getRawType() throws  {
            return this.rawType;
        }

        public Type getOwnerType() throws  {
            return this.ownerType;
        }

        public boolean equals(Object $r1) throws  {
            return ($r1 instanceof ParameterizedType) && C$Gson$Types.equals(this, (ParameterizedType) $r1);
        }

        public int hashCode() throws  {
            return (Arrays.hashCode(this.typeArguments) ^ this.rawType.hashCode()) ^ C$Gson$Types.hashCodeOrZero(this.ownerType);
        }

        public String toString() throws  {
            StringBuilder $r1 = new StringBuilder((this.typeArguments.length + 1) * 30);
            $r1.append(C$Gson$Types.typeToString(this.rawType));
            if (this.typeArguments.length == 0) {
                return $r1.toString();
            }
            $r1.append("<").append(C$Gson$Types.typeToString(this.typeArguments[0]));
            for (int $i0 = 1; $i0 < this.typeArguments.length; $i0++) {
                $r1.append(", ").append(C$Gson$Types.typeToString(this.typeArguments[$i0]));
            }
            return $r1.append(">").toString();
        }
    }

    /* compiled from: $Gson$Types */
    private static final class WildcardTypeImpl implements Serializable, WildcardType {
        private static final long serialVersionUID = 0;
        private final Type lowerBound;
        private final Type upperBound;

        public Type[] getUpperBounds() throws  {
            return new Type[]{this.upperBound};
        }

        public WildcardTypeImpl(Type[] $r1, Type[] $r2) throws  {
            boolean $z1;
            boolean $z0 = true;
            C$Gson$Preconditions.checkArgument($r2.length <= 1);
            if ($r1.length == 1) {
                $z1 = true;
            } else {
                $z1 = false;
            }
            C$Gson$Preconditions.checkArgument($z1);
            if ($r2.length == 1) {
                C$Gson$Preconditions.checkNotNull($r2[0]);
                C$Gson$Types.checkNotPrimitive($r2[0]);
                if ($r1[0] != Object.class) {
                    $z0 = false;
                }
                C$Gson$Preconditions.checkArgument($z0);
                this.lowerBound = C$Gson$Types.canonicalize($r2[0]);
                this.upperBound = Object.class;
                return;
            }
            C$Gson$Preconditions.checkNotNull($r1[0]);
            C$Gson$Types.checkNotPrimitive($r1[0]);
            this.lowerBound = null;
            this.upperBound = C$Gson$Types.canonicalize($r1[0]);
        }

        public Type[] getLowerBounds() throws  {
            if (this.lowerBound == null) {
                return C$Gson$Types.EMPTY_TYPE_ARRAY;
            }
            return new Type[]{this.lowerBound};
        }

        public boolean equals(Object $r1) throws  {
            return ($r1 instanceof WildcardType) && C$Gson$Types.equals(this, (WildcardType) $r1);
        }

        public int hashCode() throws  {
            return (this.lowerBound != null ? this.lowerBound.hashCode() + 31 : 1) ^ (this.upperBound.hashCode() + 31);
        }

        public String toString() throws  {
            if (this.lowerBound != null) {
                return "? super " + C$Gson$Types.typeToString(this.lowerBound);
            }
            if (this.upperBound == Object.class) {
                return "?";
            }
            return "? extends " + C$Gson$Types.typeToString(this.upperBound);
        }
    }

    private C$Gson$Types() throws  {
        throw new UnsupportedOperationException();
    }

    public static ParameterizedType newParameterizedTypeWithOwner(Type $r0, Type $r1, Type... $r2) throws  {
        return new ParameterizedTypeImpl($r0, $r1, $r2);
    }

    public static GenericArrayType arrayOf(Type $r0) throws  {
        return new GenericArrayTypeImpl($r0);
    }

    public static WildcardType subtypeOf(Type $r0) throws  {
        return new WildcardTypeImpl(new Type[]{$r0}, EMPTY_TYPE_ARRAY);
    }

    public static WildcardType supertypeOf(Type $r0) throws  {
        return new WildcardTypeImpl(new Type[]{Object.class}, new Type[]{$r0});
    }

    public static Type canonicalize(Type $r0) throws  {
        if ($r0 instanceof Class) {
            Serializable $r2;
            Class $r1 = (Class) $r0;
            if ($r1.isArray()) {
                $r2 = r10;
                GenericArrayTypeImpl r10 = new GenericArrayTypeImpl(C$Gson$Types.canonicalize($r1.getComponentType()));
            } else {
                Object $r22 = $r1;
            }
            return (Type) $r2;
        } else if ($r0 instanceof ParameterizedType) {
            ParameterizedType $r3 = (ParameterizedType) $r0;
            return new ParameterizedTypeImpl($r3.getOwnerType(), $r3.getRawType(), $r3.getActualTypeArguments());
        } else if ($r0 instanceof GenericArrayType) {
            return new GenericArrayTypeImpl(((GenericArrayType) $r0).getGenericComponentType());
        } else {
            if (!($r0 instanceof WildcardType)) {
                return $r0;
            }
            WildcardType $r8 = (WildcardType) $r0;
            return new WildcardTypeImpl($r8.getUpperBounds(), $r8.getLowerBounds());
        }
    }

    public static Class<?> getRawType(@Signature({"(", "Ljava/lang/reflect/Type;", ")", "Ljava/lang/Class", "<*>;"}) Type $r0) throws  {
        if ($r0 instanceof Class) {
            return (Class) $r0;
        }
        if ($r0 instanceof ParameterizedType) {
            $r0 = ((ParameterizedType) $r0).getRawType();
            C$Gson$Preconditions.checkArgument($r0 instanceof Class);
            return (Class) $r0;
        } else if ($r0 instanceof GenericArrayType) {
            return Array.newInstance(C$Gson$Types.getRawType(((GenericArrayType) $r0).getGenericComponentType()), 0).getClass();
        } else {
            if ($r0 instanceof TypeVariable) {
                return Object.class;
            }
            if ($r0 instanceof WildcardType) {
                return C$Gson$Types.getRawType(((WildcardType) $r0).getUpperBounds()[0]);
            }
            String $r7;
            if ($r0 == null) {
                $r7 = "null";
            } else {
                $r7 = $r0.getClass().getName();
            }
            throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + $r0 + "> is of type " + $r7);
        }
    }

    static boolean equal(Object $r0, Object $r1) throws  {
        return $r0 == $r1 || ($r0 != null && $r0.equals($r1));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean equals(java.lang.reflect.Type r27, java.lang.reflect.Type r28) throws  {
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
        r4 = com.google.gson.internal.C$Gson$Types.equal(r0, r1);
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
        r2 = com.google.gson.internal.C$Gson$Types.equals(r0, r1);
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.$Gson$Types.equals(java.lang.reflect.Type, java.lang.reflect.Type):boolean");
    }

    static int hashCodeOrZero(Object $r0) throws  {
        return $r0 != null ? $r0.hashCode() : 0;
    }

    public static String typeToString(Type $r0) throws  {
        return $r0 instanceof Class ? ((Class) $r0).getName() : $r0.toString();
    }

    static Type getGenericSupertype(@Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;)", "Ljava/lang/reflect/Type;"}) Type $r2, @Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;)", "Ljava/lang/reflect/Type;"}) Class<?> $r1, @Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;)", "Ljava/lang/reflect/Type;"}) Class<?> $r0) throws  {
        if ($r0 == $r1) {
            return $r2;
        }
        if ($r0.isInterface()) {
            Class[] $r3 = $r1.getInterfaces();
            int $i0 = $r3.length;
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                if ($r3[$i1] == $r0) {
                    return $r1.getGenericInterfaces()[$i1];
                }
                if ($r0.isAssignableFrom($r3[$i1])) {
                    return C$Gson$Types.getGenericSupertype($r1.getGenericInterfaces()[$i1], $r3[$i1], $r0);
                }
            }
        }
        if (!$r1.isInterface()) {
            while ($r1 != Object.class) {
                Class<?> $r4 = $r1.getSuperclass();
                if ($r4 == $r0) {
                    return $r1.getGenericSuperclass();
                }
                if ($r0.isAssignableFrom($r4)) {
                    return C$Gson$Types.getGenericSupertype($r1.getGenericSuperclass(), $r4, $r0);
                }
                $r1 = $r4;
            }
        }
        return $r0;
    }

    static Type getSupertype(@Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;)", "Ljava/lang/reflect/Type;"}) Type $r0, @Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;)", "Ljava/lang/reflect/Type;"}) Class<?> $r1, @Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;", "Ljava/lang/Class", "<*>;)", "Ljava/lang/reflect/Type;"}) Class<?> $r2) throws  {
        C$Gson$Preconditions.checkArgument($r2.isAssignableFrom($r1));
        return C$Gson$Types.resolve($r0, $r1, C$Gson$Types.getGenericSupertype($r0, $r1, $r2));
    }

    public static Type getArrayComponentType(Type $r0) throws  {
        return $r0 instanceof GenericArrayType ? ((GenericArrayType) $r0).getGenericComponentType() : ((Class) $r0).getComponentType();
    }

    public static Type getCollectionElementType(@Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;)", "Ljava/lang/reflect/Type;"}) Type $r0, @Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;)", "Ljava/lang/reflect/Type;"}) Class<?> $r1) throws  {
        $r0 = C$Gson$Types.getSupertype($r0, $r1, Collection.class);
        Type $r2 = $r0;
        if ($r0 instanceof WildcardType) {
            $r2 = ((WildcardType) $r0).getUpperBounds()[0];
        }
        if ($r2 instanceof ParameterizedType) {
            return ((ParameterizedType) $r2).getActualTypeArguments()[0];
        }
        return Object.class;
    }

    public static Type[] getMapKeyAndValueTypes(@Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;)[", "Ljava/lang/reflect/Type;"}) Type $r0, @Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;)[", "Ljava/lang/reflect/Type;"}) Class<?> $r1) throws  {
        if ($r0 == Properties.class) {
            return new Type[]{String.class, String.class};
        }
        $r0 = C$Gson$Types.getSupertype($r0, $r1, Map.class);
        if ($r0 instanceof ParameterizedType) {
            return ((ParameterizedType) $r0).getActualTypeArguments();
        }
        return new Type[]{Object.class, Object.class};
    }

    public static Type resolve(@Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;", "Ljava/lang/reflect/Type;", ")", "Ljava/lang/reflect/Type;"}) Type $r0, @Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;", "Ljava/lang/reflect/Type;", ")", "Ljava/lang/reflect/Type;"}) Class<?> $r1, @Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;", "Ljava/lang/reflect/Type;", ")", "Ljava/lang/reflect/Type;"}) Type $r2) throws  {
        while ($r2 instanceof TypeVariable) {
            Type $r3 = (TypeVariable) $r2;
            Type $r4 = C$Gson$Types.resolveTypeVariable($r0, $r1, $r3);
            $r2 = $r4;
            if ($r4 == $r3) {
                return $r4;
            }
        }
        if (($r2 instanceof Class) && ((Class) $r2).isArray()) {
            $r2 = (Class) $r2;
            Class $r5 = ((Class) $r2).getComponentType();
            $r0 = C$Gson$Types.resolve($r0, $r1, $r5);
            if ($r5 != $r0) {
                $r2 = C$Gson$Types.arrayOf($r0);
            }
            return $r2;
        } else if ($r2 instanceof GenericArrayType) {
            GenericArrayType $r22 = (GenericArrayType) $r2;
            $r4 = $r22.getGenericComponentType();
            $r0 = C$Gson$Types.resolve($r0, $r1, $r4);
            if ($r4 != $r0) {
                return C$Gson$Types.arrayOf($r0);
            }
            return $r22;
        } else if ($r2 instanceof ParameterizedType) {
            boolean $z0;
            ParameterizedType $r23 = (ParameterizedType) $r2;
            Type $r7 = $r23.getOwnerType();
            $r4 = C$Gson$Types.resolve($r0, $r1, $r7);
            if ($r4 != $r7) {
                $z0 = true;
            } else {
                $z0 = false;
            }
            $r8 = $r23.getActualTypeArguments();
            $r9 = $r8;
            int $i0 = $r8.length;
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                $r7 = C$Gson$Types.resolve($r0, $r1, $r9[$i1]);
                if ($r7 != $r9[$i1]) {
                    if (!$z0) {
                        $r9 = (Type[]) $r9.clone();
                        $z0 = true;
                    }
                    $r9[$i1] = $r7;
                }
            }
            if ($z0) {
                return C$Gson$Types.newParameterizedTypeWithOwner($r4, $r23.getRawType(), $r9);
            }
            return $r23;
        } else if (!($r2 instanceof WildcardType)) {
            return $r2;
        } else {
            WildcardType $r24 = (WildcardType) $r2;
            $r8 = $r24.getLowerBounds();
            $r9 = $r24.getUpperBounds();
            if ($r8.length == 1) {
                $r0 = C$Gson$Types.resolve($r0, $r1, $r8[0]);
                if ($r0 != $r8[0]) {
                    return C$Gson$Types.supertypeOf($r0);
                }
                return $r24;
            } else if ($r9.length != 1) {
                return $r24;
            } else {
                $r0 = C$Gson$Types.resolve($r0, $r1, $r9[0]);
                return $r0 != $r9[0] ? C$Gson$Types.subtypeOf($r0) : $r24;
            }
        }
    }

    static Type resolveTypeVariable(@Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;", "Ljava/lang/reflect/TypeVariable", "<*>;)", "Ljava/lang/reflect/Type;"}) Type $r0, @Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;", "Ljava/lang/reflect/TypeVariable", "<*>;)", "Ljava/lang/reflect/Type;"}) Class<?> $r1, @Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<*>;", "Ljava/lang/reflect/TypeVariable", "<*>;)", "Ljava/lang/reflect/Type;"}) TypeVariable<?> $r2) throws  {
        Class $r3 = C$Gson$Types.declaringClassOf($r2);
        if ($r3 == null) {
            return $r2;
        }
        $r0 = C$Gson$Types.getGenericSupertype($r0, $r1, $r3);
        if (!($r0 instanceof ParameterizedType)) {
            return $r2;
        }
        return ((ParameterizedType) $r0).getActualTypeArguments()[C$Gson$Types.indexOf($r3.getTypeParameters(), $r2)];
    }

    private static int indexOf(Object[] $r0, Object $r1) throws  {
        for (int $i0 = 0; $i0 < $r0.length; $i0++) {
            if ($r1.equals($r0[$i0])) {
                return $i0;
            }
        }
        throw new NoSuchElementException();
    }

    private static Class<?> declaringClassOf(@Signature({"(", "Ljava/lang/reflect/TypeVariable", "<*>;)", "Ljava/lang/Class", "<*>;"}) TypeVariable<?> $r0) throws  {
        GenericDeclaration $r1 = $r0.getGenericDeclaration();
        return $r1 instanceof Class ? (Class) $r1 : null;
    }

    static void checkNotPrimitive(Type $r0) throws  {
        boolean $z0 = (($r0 instanceof Class) && ((Class) $r0).isPrimitive()) ? false : true;
        C$Gson$Preconditions.checkArgument($z0);
    }
}
