package com.google.gson.reflect;

import com.abaltatech.mcp.mcs.utils.FilenameUtils;
import com.google.gson.internal.C$Gson$Preconditions;
import com.google.gson.internal.C$Gson$Types;
import dalvik.annotation.Signature;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

public class TypeToken<T> {
    final int hashCode;
    final Class<? super T> rawType;
    final Type type;

    protected TypeToken() throws  {
        this.type = getSuperclassTypeParameter(getClass());
        this.rawType = C$Gson$Types.getRawType(this.type);
        this.hashCode = this.type.hashCode();
    }

    TypeToken(Type $r1) throws  {
        this.type = C$Gson$Types.canonicalize((Type) C$Gson$Preconditions.checkNotNull($r1));
        this.rawType = C$Gson$Types.getRawType(this.type);
        this.hashCode = this.type.hashCode();
    }

    static Type getSuperclassTypeParameter(@Signature({"(", "Ljava/lang/Class", "<*>;)", "Ljava/lang/reflect/Type;"}) Class<?> $r0) throws  {
        Type $r1 = $r0.getGenericSuperclass();
        if (!($r1 instanceof Class)) {
            return C$Gson$Types.canonicalize(((ParameterizedType) $r1).getActualTypeArguments()[0]);
        }
        throw new RuntimeException("Missing type parameter.");
    }

    public final Class<? super T> getRawType() throws  {
        return this.rawType;
    }

    public final Type getType() throws  {
        return this.type;
    }

    @Deprecated
    public boolean isAssignableFrom(@Deprecated @Signature({"(", "Ljava/lang/Class", "<*>;)Z"}) Class<?> $r1) throws  {
        return isAssignableFrom((Type) $r1);
    }

    @Deprecated
    public boolean isAssignableFrom(@Deprecated Type $r1) throws  {
        if ($r1 == null) {
            return false;
        }
        if (this.type.equals($r1)) {
            return true;
        }
        if (this.type instanceof Class) {
            return this.rawType.isAssignableFrom(C$Gson$Types.getRawType($r1));
        }
        if (this.type instanceof ParameterizedType) {
            return isAssignableFrom($r1, (ParameterizedType) this.type, new HashMap());
        }
        if (this.type instanceof GenericArrayType) {
            boolean $z0 = this.rawType.isAssignableFrom(C$Gson$Types.getRawType($r1)) && isAssignableFrom($r1, (GenericArrayType) this.type);
            return $z0;
        }
        throw buildUnexpectedTypeError(this.type, Class.class, ParameterizedType.class, GenericArrayType.class);
    }

    @Deprecated
    public boolean isAssignableFrom(@Deprecated @Signature({"(", "Lcom/google/gson/reflect/TypeToken", "<*>;)Z"}) TypeToken<?> $r1) throws  {
        return isAssignableFrom($r1.getType());
    }

    private static boolean isAssignableFrom(Type $r1, GenericArrayType $r0) throws  {
        Type $r2 = $r0.getGenericComponentType();
        if (!($r2 instanceof ParameterizedType)) {
            return true;
        }
        Type $r3 = $r1;
        if ($r1 instanceof GenericArrayType) {
            $r3 = ((GenericArrayType) $r1).getGenericComponentType();
        } else if ($r1 instanceof Class) {
            Class $r6 = (Class) $r1;
            while ($r6.isArray()) {
                $r6 = $r6.getComponentType();
            }
            Object $r32 = $r6;
        }
        return isAssignableFrom($r3, (ParameterizedType) $r2, new HashMap());
    }

    private static boolean isAssignableFrom(@Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/reflect/ParameterizedType;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/reflect/Type;", ">;)Z"}) Type $r0, @Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/reflect/ParameterizedType;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/reflect/Type;", ">;)Z"}) ParameterizedType $r1, @Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/reflect/ParameterizedType;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/reflect/Type;", ">;)Z"}) Map<String, Type> $r2) throws  {
        if ($r0 == null) {
            return false;
        }
        if ($r1.equals($r0)) {
            return true;
        }
        int $i0;
        Class $r4 = C$Gson$Types.getRawType($r0);
        ParameterizedType $r5 = null;
        if ($r0 instanceof ParameterizedType) {
            $r5 = (ParameterizedType) $r0;
        }
        if ($r5 != null) {
            Type[] $r6 = $r5.getActualTypeArguments();
            TypeVariable[] $r7 = $r4.getTypeParameters();
            for ($i0 = 0; $i0 < $r6.length; $i0++) {
                $r0 = $r6[$i0];
                TypeVariable $r3 = $r7[$i0];
                while ($r0 instanceof TypeVariable) {
                    $r0 = (Type) $r2.get(((TypeVariable) $r0).getName());
                }
                $r2.put($r3.getName(), $r0);
            }
            if (typeEquals($r5, $r1, $r2)) {
                return true;
            }
        }
        for (Type $r02 : $r4.getGenericInterfaces()) {
            if (isAssignableFrom($r02, $r1, new HashMap($r2))) {
                return true;
            }
        }
        return isAssignableFrom($r4.getGenericSuperclass(), $r1, new HashMap($r2));
    }

    private static boolean typeEquals(@Signature({"(", "Ljava/lang/reflect/ParameterizedType;", "Ljava/lang/reflect/ParameterizedType;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/reflect/Type;", ">;)Z"}) ParameterizedType $r0, @Signature({"(", "Ljava/lang/reflect/ParameterizedType;", "Ljava/lang/reflect/ParameterizedType;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/reflect/Type;", ">;)Z"}) ParameterizedType $r1, @Signature({"(", "Ljava/lang/reflect/ParameterizedType;", "Ljava/lang/reflect/ParameterizedType;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/reflect/Type;", ">;)Z"}) Map<String, Type> $r2) throws  {
        if (!$r0.getRawType().equals($r1.getRawType())) {
            return false;
        }
        Type[] $r5 = $r0.getActualTypeArguments();
        Type[] $r6 = $r1.getActualTypeArguments();
        for (int $i0 = 0; $i0 < $r5.length; $i0++) {
            if (!matches($r5[$i0], $r6[$i0], $r2)) {
                return false;
            }
        }
        return true;
    }

    private static AssertionError buildUnexpectedTypeError(@Signature({"(", "Ljava/lang/reflect/Type;", "[", "Ljava/lang/Class", "<*>;)", "Ljava/lang/AssertionError;"}) Type $r0, @Signature({"(", "Ljava/lang/reflect/Type;", "[", "Ljava/lang/Class", "<*>;)", "Ljava/lang/AssertionError;"}) Class<?>... $r1) throws  {
        StringBuilder $r3 = new StringBuilder("Unexpected type. Expected one of: ");
        for (Class $r2 : $r1) {
            $r3.append($r2.getName()).append(", ");
        }
        $r3.append("but got: ").append($r0.getClass().getName()).append(", for type token: ").append($r0.toString()).append(FilenameUtils.EXTENSION_SEPARATOR);
        return new AssertionError($r3.toString());
    }

    private static boolean matches(@Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/reflect/Type;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/reflect/Type;", ">;)Z"}) Type $r2, @Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/reflect/Type;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/reflect/Type;", ">;)Z"}) Type $r0, @Signature({"(", "Ljava/lang/reflect/Type;", "Ljava/lang/reflect/Type;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/reflect/Type;", ">;)Z"}) Map<String, Type> $r1) throws  {
        return $r0.equals($r2) || (($r2 instanceof TypeVariable) && $r0.equals($r1.get(((TypeVariable) $r2).getName())));
    }

    public final int hashCode() throws  {
        return this.hashCode;
    }

    public final boolean equals(Object $r2) throws  {
        return ($r2 instanceof TypeToken) && C$Gson$Types.equals(this.type, ((TypeToken) $r2).type);
    }

    public final String toString() throws  {
        return C$Gson$Types.typeToString(this.type);
    }

    public static TypeToken<?> get(@Signature({"(", "Ljava/lang/reflect/Type;", ")", "Lcom/google/gson/reflect/TypeToken", "<*>;"}) Type $r0) throws  {
        return new TypeToken($r0);
    }

    public static <T> TypeToken<T> get(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TT;>;)", "Lcom/google/gson/reflect/TypeToken", "<TT;>;"}) Class<T> $r0) throws  {
        return new TypeToken($r0);
    }
}
