package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.C$Gson$Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.JsonReaderInternalAccess;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public final class MapTypeAdapterFactory implements TypeAdapterFactory {
    final boolean complexMapKeySerialization;
    private final ConstructorConstructor constructorConstructor;

    private final class Adapter<K, V> extends TypeAdapter<Map<K, V>> {
        private final ObjectConstructor<? extends Map<K, V>> constructor;
        private final TypeAdapter<K> keyTypeAdapter;
        private final TypeAdapter<V> valueTypeAdapter;

        public Adapter(@Signature({"(", "Lcom/google/gson/Gson;", "Ljava/lang/reflect/Type;", "Lcom/google/gson/TypeAdapter", "<TK;>;", "Ljava/lang/reflect/Type;", "Lcom/google/gson/TypeAdapter", "<TV;>;", "Lcom/google/gson/internal/ObjectConstructor", "<+", "Ljava/util/Map", "<TK;TV;>;>;)V"}) Gson $r2, @Signature({"(", "Lcom/google/gson/Gson;", "Ljava/lang/reflect/Type;", "Lcom/google/gson/TypeAdapter", "<TK;>;", "Ljava/lang/reflect/Type;", "Lcom/google/gson/TypeAdapter", "<TV;>;", "Lcom/google/gson/internal/ObjectConstructor", "<+", "Ljava/util/Map", "<TK;TV;>;>;)V"}) Type $r3, @Signature({"(", "Lcom/google/gson/Gson;", "Ljava/lang/reflect/Type;", "Lcom/google/gson/TypeAdapter", "<TK;>;", "Ljava/lang/reflect/Type;", "Lcom/google/gson/TypeAdapter", "<TV;>;", "Lcom/google/gson/internal/ObjectConstructor", "<+", "Ljava/util/Map", "<TK;TV;>;>;)V"}) TypeAdapter<K> $r4, @Signature({"(", "Lcom/google/gson/Gson;", "Ljava/lang/reflect/Type;", "Lcom/google/gson/TypeAdapter", "<TK;>;", "Ljava/lang/reflect/Type;", "Lcom/google/gson/TypeAdapter", "<TV;>;", "Lcom/google/gson/internal/ObjectConstructor", "<+", "Ljava/util/Map", "<TK;TV;>;>;)V"}) Type $r5, @Signature({"(", "Lcom/google/gson/Gson;", "Ljava/lang/reflect/Type;", "Lcom/google/gson/TypeAdapter", "<TK;>;", "Ljava/lang/reflect/Type;", "Lcom/google/gson/TypeAdapter", "<TV;>;", "Lcom/google/gson/internal/ObjectConstructor", "<+", "Ljava/util/Map", "<TK;TV;>;>;)V"}) TypeAdapter<V> $r6, @Signature({"(", "Lcom/google/gson/Gson;", "Ljava/lang/reflect/Type;", "Lcom/google/gson/TypeAdapter", "<TK;>;", "Ljava/lang/reflect/Type;", "Lcom/google/gson/TypeAdapter", "<TV;>;", "Lcom/google/gson/internal/ObjectConstructor", "<+", "Ljava/util/Map", "<TK;TV;>;>;)V"}) ObjectConstructor<? extends Map<K, V>> $r7) throws  {
            this.keyTypeAdapter = new TypeAdapterRuntimeTypeWrapper($r2, $r4, $r3);
            this.valueTypeAdapter = new TypeAdapterRuntimeTypeWrapper($r2, $r6, $r5);
            this.constructor = $r7;
        }

        public Map<K, V> read(@Signature({"(", "Lcom/google/gson/stream/JsonReader;", ")", "Ljava/util/Map", "<TK;TV;>;"}) JsonReader $r1) throws IOException {
            JsonToken $r2 = $r1.peek();
            if ($r2 == JsonToken.NULL) {
                $r1.nextNull();
                return null;
            }
            Map $r4 = (Map) this.constructor.construct();
            Object $r6;
            if ($r2 == JsonToken.BEGIN_ARRAY) {
                $r1.beginArray();
                while ($r1.hasNext()) {
                    $r1.beginArray();
                    $r6 = this.keyTypeAdapter.read($r1);
                    if ($r4.put($r6, this.valueTypeAdapter.read($r1)) != null) {
                        throw new JsonSyntaxException("duplicate key: " + $r6);
                    }
                    $r1.endArray();
                }
                $r1.endArray();
                return $r4;
            }
            $r1.beginObject();
            while ($r1.hasNext()) {
                JsonReaderInternalAccess.INSTANCE.promoteNameToValue($r1);
                $r6 = this.keyTypeAdapter.read($r1);
                if ($r4.put($r6, this.valueTypeAdapter.read($r1)) != null) {
                    throw new JsonSyntaxException("duplicate key: " + $r6);
                }
            }
            $r1.endObject();
            return $r4;
        }

        public void write(@Signature({"(", "Lcom/google/gson/stream/JsonWriter;", "Ljava/util/Map", "<TK;TV;>;)V"}) JsonWriter $r1, @Signature({"(", "Lcom/google/gson/stream/JsonWriter;", "Ljava/util/Map", "<TK;TV;>;)V"}) Map<K, V> $r2) throws IOException {
            if ($r2 == null) {
                $r1.nullValue();
                return;
            }
            this = this;
            if (MapTypeAdapterFactory.this.complexMapKeySerialization) {
                boolean $z0 = false;
                ArrayList $r3 = new ArrayList($r2.size());
                ArrayList $r4 = new ArrayList($r2.size());
                for (Entry $r9 : $r2.entrySet()) {
                    JsonElement $r12 = this.keyTypeAdapter.toJsonTree($r9.getKey());
                    $r3.add($r12);
                    $r4.add($r9.getValue());
                    boolean $z1 = $r12.isJsonArray() || $r12.isJsonObject();
                    $z0 |= $z1;
                }
                int $i0;
                if ($z0) {
                    $r1.beginArray();
                    for ($i0 = 0; $i0 < $r3.size(); $i0++) {
                        $r1.beginArray();
                        Streams.write((JsonElement) $r3.get($i0), $r1);
                        this.valueTypeAdapter.write($r1, $r4.get($i0));
                        $r1.endArray();
                    }
                    $r1.endArray();
                    return;
                }
                $r1.beginObject();
                for ($i0 = 0; $i0 < $r3.size(); $i0++) {
                    $r1.name(keyToString((JsonElement) $r3.get($i0)));
                    this.valueTypeAdapter.write($r1, $r4.get($i0));
                }
                $r1.endObject();
                return;
            }
            $r1.beginObject();
            for (Entry $r92 : $r2.entrySet()) {
                $r1.name(String.valueOf($r92.getKey()));
                this.valueTypeAdapter.write($r1, $r92.getValue());
            }
            $r1.endObject();
        }

        private String keyToString(JsonElement $r1) throws  {
            if ($r1.isJsonPrimitive()) {
                JsonPrimitive $r2 = $r1.getAsJsonPrimitive();
                if ($r2.isNumber()) {
                    return String.valueOf($r2.getAsNumber());
                }
                if ($r2.isBoolean()) {
                    return Boolean.toString($r2.getAsBoolean());
                }
                if ($r2.isString()) {
                    return $r2.getAsString();
                }
                throw new AssertionError();
            } else if ($r1.isJsonNull()) {
                return "null";
            } else {
                throw new AssertionError();
            }
        }
    }

    public MapTypeAdapterFactory(ConstructorConstructor $r1, boolean $z0) throws  {
        this.constructorConstructor = $r1;
        this.complexMapKeySerialization = $z0;
    }

    public <T> TypeAdapter<T> create(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) Gson $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) TypeToken<T> $r2) throws  {
        Type $r3 = $r2.getType();
        if (!Map.class.isAssignableFrom($r2.getRawType())) {
            return null;
        }
        Type[] $r7 = C$Gson$Types.getMapKeyAndValueTypes($r3, C$Gson$Types.getRawType($r3));
        TypeAdapter $r8 = getKeyAdapter($r1, $r7[0]);
        TypeAdapter $r10 = $r1.getAdapter(TypeToken.get($r7[1]));
        ConstructorConstructor constructorConstructor = this.constructorConstructor;
        ConstructorConstructor $r11 = constructorConstructor;
        ObjectConstructor $r12 = constructorConstructor.get($r2);
        return new Adapter($r1, $r7[0], $r8, $r7[1], $r10, $r12);
    }

    private TypeAdapter<?> getKeyAdapter(@Signature({"(", "Lcom/google/gson/Gson;", "Ljava/lang/reflect/Type;", ")", "Lcom/google/gson/TypeAdapter", "<*>;"}) Gson $r1, @Signature({"(", "Lcom/google/gson/Gson;", "Ljava/lang/reflect/Type;", ")", "Lcom/google/gson/TypeAdapter", "<*>;"}) Type $r2) throws  {
        return ($r2 == Boolean.TYPE || $r2 == Boolean.class) ? TypeAdapters.BOOLEAN_AS_STRING : $r1.getAdapter(TypeToken.get($r2));
    }
}
