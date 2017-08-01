package com.google.gson;

import com.google.gson.internal.C$Gson$Preconditions;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dalvik.annotation.Signature;
import java.io.IOException;

final class TreeTypeAdapter<T> extends TypeAdapter<T> {
    private TypeAdapter<T> delegate;
    private final JsonDeserializer<T> deserializer;
    private final Gson gson;
    private final JsonSerializer<T> serializer;
    private final TypeAdapterFactory skipPast;
    private final TypeToken<T> typeToken;

    private static class SingleTypeFactory implements TypeAdapterFactory {
        private final JsonDeserializer<?> deserializer;
        private final TypeToken<?> exactType;
        private final Class<?> hierarchyType;
        private final boolean matchRawType;
        private final JsonSerializer<?> serializer;

        SingleTypeFactory(@Signature({"(", "Ljava/lang/Object;", "Lcom/google/gson/reflect/TypeToken", "<*>;Z", "Ljava/lang/Class", "<*>;)V"}) Object $r3, @Signature({"(", "Ljava/lang/Object;", "Lcom/google/gson/reflect/TypeToken", "<*>;Z", "Ljava/lang/Class", "<*>;)V"}) TypeToken<?> $r1, @Signature({"(", "Ljava/lang/Object;", "Lcom/google/gson/reflect/TypeToken", "<*>;Z", "Ljava/lang/Class", "<*>;)V"}) boolean $z0, @Signature({"(", "Ljava/lang/Object;", "Lcom/google/gson/reflect/TypeToken", "<*>;Z", "Ljava/lang/Class", "<*>;)V"}) Class<?> $r2) throws  {
            JsonDeserializer $r5;
            this.serializer = $r3 instanceof JsonSerializer ? (JsonSerializer) $r3 : null;
            if ($r3 instanceof JsonDeserializer) {
                $r5 = (JsonDeserializer) $r3;
            } else {
                $r5 = null;
            }
            this.deserializer = $r5;
            boolean $z1 = (this.serializer == null && this.deserializer == null) ? false : true;
            C$Gson$Preconditions.checkArgument($z1);
            this.exactType = $r1;
            this.matchRawType = $z0;
            this.hierarchyType = $r2;
        }

        public <T> TypeAdapter<T> create(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) Gson $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) TypeToken<T> $r2) throws  {
            boolean $z0 = this.exactType != null ? this.exactType.equals($r2) || (this.matchRawType && this.exactType.getType() == $r2.getRawType()) : this.hierarchyType.isAssignableFrom($r2.getRawType());
            if (!$z0) {
                return null;
            }
            return new TreeTypeAdapter(this.serializer, this.deserializer, $r1, $r2, this);
        }
    }

    TreeTypeAdapter(@Signature({"(", "Lcom/google/gson/JsonSerializer", "<TT;>;", "Lcom/google/gson/JsonDeserializer", "<TT;>;", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;", "Lcom/google/gson/TypeAdapterFactory;", ")V"}) JsonSerializer<T> $r1, @Signature({"(", "Lcom/google/gson/JsonSerializer", "<TT;>;", "Lcom/google/gson/JsonDeserializer", "<TT;>;", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;", "Lcom/google/gson/TypeAdapterFactory;", ")V"}) JsonDeserializer<T> $r2, @Signature({"(", "Lcom/google/gson/JsonSerializer", "<TT;>;", "Lcom/google/gson/JsonDeserializer", "<TT;>;", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;", "Lcom/google/gson/TypeAdapterFactory;", ")V"}) Gson $r3, @Signature({"(", "Lcom/google/gson/JsonSerializer", "<TT;>;", "Lcom/google/gson/JsonDeserializer", "<TT;>;", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;", "Lcom/google/gson/TypeAdapterFactory;", ")V"}) TypeToken<T> $r4, @Signature({"(", "Lcom/google/gson/JsonSerializer", "<TT;>;", "Lcom/google/gson/JsonDeserializer", "<TT;>;", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;", "Lcom/google/gson/TypeAdapterFactory;", ")V"}) TypeAdapterFactory $r5) throws  {
        this.serializer = $r1;
        this.deserializer = $r2;
        this.gson = $r3;
        this.typeToken = $r4;
        this.skipPast = $r5;
    }

    public T read(@Signature({"(", "Lcom/google/gson/stream/JsonReader;", ")TT;"}) JsonReader $r1) throws IOException {
        if (this.deserializer == null) {
            return delegate().read($r1);
        }
        JsonElement $r5 = Streams.parse($r1);
        if ($r5.isJsonNull()) {
            return null;
        }
        return this.deserializer.deserialize($r5, this.typeToken.getType(), this.gson.deserializationContext);
    }

    public void write(@Signature({"(", "Lcom/google/gson/stream/JsonWriter;", "TT;)V"}) JsonWriter $r1, @Signature({"(", "Lcom/google/gson/stream/JsonWriter;", "TT;)V"}) T $r2) throws IOException {
        if (this.serializer == null) {
            delegate().write($r1, $r2);
        } else if ($r2 == null) {
            $r1.nullValue();
        } else {
            Streams.write(this.serializer.serialize($r2, this.typeToken.getType(), this.gson.serializationContext), $r1);
        }
    }

    private TypeAdapter<T> delegate() throws  {
        TypeAdapter $r4 = this.delegate;
        if ($r4 != null) {
            return $r4;
        }
        $r4 = this.gson.getDelegateAdapter(this.skipPast, this.typeToken);
        this.delegate = $r4;
        return $r4;
    }

    public static TypeAdapterFactory newFactory(@Signature({"(", "Lcom/google/gson/reflect/TypeToken", "<*>;", "Ljava/lang/Object;", ")", "Lcom/google/gson/TypeAdapterFactory;"}) TypeToken<?> $r0, @Signature({"(", "Lcom/google/gson/reflect/TypeToken", "<*>;", "Ljava/lang/Object;", ")", "Lcom/google/gson/TypeAdapterFactory;"}) Object $r1) throws  {
        return new SingleTypeFactory($r1, $r0, false, null);
    }

    public static TypeAdapterFactory newFactoryWithMatchRawType(@Signature({"(", "Lcom/google/gson/reflect/TypeToken", "<*>;", "Ljava/lang/Object;", ")", "Lcom/google/gson/TypeAdapterFactory;"}) TypeToken<?> $r0, @Signature({"(", "Lcom/google/gson/reflect/TypeToken", "<*>;", "Ljava/lang/Object;", ")", "Lcom/google/gson/TypeAdapterFactory;"}) Object $r1) throws  {
        return new SingleTypeFactory($r1, $r0, $r0.getType() == $r0.getRawType(), null);
    }

    public static TypeAdapterFactory newTypeHierarchyFactory(@Signature({"(", "Ljava/lang/Class", "<*>;", "Ljava/lang/Object;", ")", "Lcom/google/gson/TypeAdapterFactory;"}) Class<?> $r0, @Signature({"(", "Ljava/lang/Class", "<*>;", "Ljava/lang/Object;", ")", "Lcom/google/gson/TypeAdapterFactory;"}) Object $r1) throws  {
        return new SingleTypeFactory($r1, null, false, $r0);
    }
}
