package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.C$Gson$Types;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public final class ArrayTypeAdapter<E> extends TypeAdapter<Object> {
    public static final TypeAdapterFactory FACTORY = new C10381();
    private final Class<E> componentType;
    private final TypeAdapter<E> componentTypeAdapter;

    static class C10381 implements TypeAdapterFactory {
        C10381() throws  {
        }

        public <T> TypeAdapter<T> create(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) Gson $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) TypeToken<T> $r2) throws  {
            Type $r3 = $r2.getType();
            if (!($r3 instanceof GenericArrayType) && (!($r3 instanceof Class) || !((Class) $r3).isArray())) {
                return null;
            }
            $r3 = C$Gson$Types.getArrayComponentType($r3);
            return new ArrayTypeAdapter($r1, $r1.getAdapter(TypeToken.get($r3)), C$Gson$Types.getRawType($r3));
        }
    }

    public ArrayTypeAdapter(@Signature({"(", "Lcom/google/gson/Gson;", "Lcom/google/gson/TypeAdapter", "<TE;>;", "Ljava/lang/Class", "<TE;>;)V"}) Gson $r1, @Signature({"(", "Lcom/google/gson/Gson;", "Lcom/google/gson/TypeAdapter", "<TE;>;", "Ljava/lang/Class", "<TE;>;)V"}) TypeAdapter<E> $r2, @Signature({"(", "Lcom/google/gson/Gson;", "Lcom/google/gson/TypeAdapter", "<TE;>;", "Ljava/lang/Class", "<TE;>;)V"}) Class<E> $r3) throws  {
        this.componentTypeAdapter = new TypeAdapterRuntimeTypeWrapper($r1, $r2, $r3);
        this.componentType = $r3;
    }

    public Object read(JsonReader $r1) throws IOException {
        if ($r1.peek() == JsonToken.NULL) {
            $r1.nextNull();
            return null;
        }
        ArrayList $r2 = new ArrayList();
        $r1.beginArray();
        while ($r1.hasNext()) {
            $r2.add(this.componentTypeAdapter.read($r1));
        }
        $r1.endArray();
        Object $r6 = Array.newInstance(this.componentType, $r2.size());
        for (int $i0 = 0; $i0 < $r2.size(); $i0++) {
            Array.set($r6, $i0, $r2.get($i0));
        }
        return $r6;
    }

    public void write(JsonWriter $r1, Object $r2) throws IOException {
        if ($r2 == null) {
            $r1.nullValue();
            return;
        }
        $r1.beginArray();
        int $i1 = Array.getLength($r2);
        for (int $i0 = 0; $i0 < $i1; $i0++) {
            this.componentTypeAdapter.write($r1, Array.get($r2, $i0));
        }
        $r1.endArray();
    }
}
