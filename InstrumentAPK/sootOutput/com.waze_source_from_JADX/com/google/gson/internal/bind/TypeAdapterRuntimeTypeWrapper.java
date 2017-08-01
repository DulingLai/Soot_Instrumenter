package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

final class TypeAdapterRuntimeTypeWrapper<T> extends TypeAdapter<T> {
    private final Gson context;
    private final TypeAdapter<T> delegate;
    private final Type type;

    TypeAdapterRuntimeTypeWrapper(@Signature({"(", "Lcom/google/gson/Gson;", "Lcom/google/gson/TypeAdapter", "<TT;>;", "Ljava/lang/reflect/Type;", ")V"}) Gson $r1, @Signature({"(", "Lcom/google/gson/Gson;", "Lcom/google/gson/TypeAdapter", "<TT;>;", "Ljava/lang/reflect/Type;", ")V"}) TypeAdapter<T> $r2, @Signature({"(", "Lcom/google/gson/Gson;", "Lcom/google/gson/TypeAdapter", "<TT;>;", "Ljava/lang/reflect/Type;", ")V"}) Type $r3) throws  {
        this.context = $r1;
        this.delegate = $r2;
        this.type = $r3;
    }

    public T read(@Signature({"(", "Lcom/google/gson/stream/JsonReader;", ")TT;"}) JsonReader $r1) throws IOException {
        return this.delegate.read($r1);
    }

    public void write(@Signature({"(", "Lcom/google/gson/stream/JsonWriter;", "TT;)V"}) JsonWriter $r1, @Signature({"(", "Lcom/google/gson/stream/JsonWriter;", "TT;)V"}) T $r2) throws IOException {
        TypeAdapter $r3 = this.delegate;
        Type $r4 = getRuntimeTypeIfMoreSpecific(this.type, $r2);
        if ($r4 != this.type) {
            $r3 = this.context.getAdapter(TypeToken.get($r4));
            if ($r3 instanceof Adapter) {
                if (!(this.delegate instanceof Adapter)) {
                    $r3 = this.delegate;
                }
            }
        }
        $r3.write($r1, $r2);
    }

    private Type getRuntimeTypeIfMoreSpecific(Type $r3, Object $r1) throws  {
        if ($r1 == null) {
            return $r3;
        }
        if ($r3 == Object.class || ($r3 instanceof TypeVariable) || ($r3 instanceof Class)) {
            return $r1.getClass();
        }
        return $r3;
    }
}
