package com.google.gson;

import com.google.gson.internal.C$Gson$Preconditions;
import dalvik.annotation.Signature;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

public final class FieldAttributes {
    private final Field field;

    public FieldAttributes(Field $r1) throws  {
        C$Gson$Preconditions.checkNotNull($r1);
        this.field = $r1;
    }

    public Class<?> getDeclaringClass() throws  {
        return this.field.getDeclaringClass();
    }

    public String getName() throws  {
        return this.field.getName();
    }

    public Type getDeclaredType() throws  {
        return this.field.getGenericType();
    }

    public Class<?> getDeclaredClass() throws  {
        return this.field.getType();
    }

    public <T extends Annotation> T getAnnotation(@Signature({"<T::", "Ljava/lang/annotation/Annotation;", ">(", "Ljava/lang/Class", "<TT;>;)TT;"}) Class<T> $r1) throws  {
        return this.field.getAnnotation($r1);
    }

    public Collection<Annotation> getAnnotations() throws  {
        return Arrays.asList(this.field.getAnnotations());
    }

    public boolean hasModifier(int $i0) throws  {
        return (this.field.getModifiers() & $i0) != 0;
    }

    Object get(Object $r1) throws IllegalAccessException {
        return this.field.get($r1);
    }

    boolean isSynthetic() throws  {
        return this.field.isSynthetic();
    }
}
