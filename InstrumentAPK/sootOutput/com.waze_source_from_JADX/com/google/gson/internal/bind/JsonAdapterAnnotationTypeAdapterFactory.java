package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.reflect.TypeToken;
import dalvik.annotation.Signature;

public final class JsonAdapterAnnotationTypeAdapterFactory implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;

    public JsonAdapterAnnotationTypeAdapterFactory(ConstructorConstructor $r1) throws  {
        this.constructorConstructor = $r1;
    }

    public <T> TypeAdapter<T> create(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) Gson $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) TypeToken<T> $r2) throws  {
        JsonAdapter $r5 = (JsonAdapter) $r2.getRawType().getAnnotation(JsonAdapter.class);
        return $r5 == null ? null : getTypeAdapter(this.constructorConstructor, $r1, $r2, $r5);
    }

    static TypeAdapter<?> getTypeAdapter(@Signature({"(", "Lcom/google/gson/internal/ConstructorConstructor;", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<*>;", "Lcom/google/gson/annotations/JsonAdapter;", ")", "Lcom/google/gson/TypeAdapter", "<*>;"}) ConstructorConstructor $r0, @Signature({"(", "Lcom/google/gson/internal/ConstructorConstructor;", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<*>;", "Lcom/google/gson/annotations/JsonAdapter;", ")", "Lcom/google/gson/TypeAdapter", "<*>;"}) Gson $r1, @Signature({"(", "Lcom/google/gson/internal/ConstructorConstructor;", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<*>;", "Lcom/google/gson/annotations/JsonAdapter;", ")", "Lcom/google/gson/TypeAdapter", "<*>;"}) TypeToken<?> $r2, @Signature({"(", "Lcom/google/gson/internal/ConstructorConstructor;", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<*>;", "Lcom/google/gson/annotations/JsonAdapter;", ")", "Lcom/google/gson/TypeAdapter", "<*>;"}) JsonAdapter $r3) throws  {
        TypeAdapter $r8;
        Class $r4 = $r3.value();
        if (TypeAdapter.class.isAssignableFrom($r4)) {
            $r8 = (TypeAdapter) $r0.get(TypeToken.get($r4)).construct();
        } else if (TypeAdapterFactory.class.isAssignableFrom($r4)) {
            $r8 = ((TypeAdapterFactory) $r0.get(TypeToken.get($r4)).construct()).create($r1, $r2);
        } else {
            throw new IllegalArgumentException("@JsonAdapter value must be TypeAdapter or TypeAdapterFactory reference.");
        }
        return $r8.nullSafe();
    }
}
