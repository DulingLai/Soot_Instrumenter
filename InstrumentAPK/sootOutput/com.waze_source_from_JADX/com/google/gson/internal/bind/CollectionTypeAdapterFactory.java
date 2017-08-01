package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.C$Gson$Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

public final class CollectionTypeAdapterFactory implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;

    private static final class Adapter<E> extends TypeAdapter<Collection<E>> {
        private final ObjectConstructor<? extends Collection<E>> constructor;
        private final TypeAdapter<E> elementTypeAdapter;

        public Adapter(@Signature({"(", "Lcom/google/gson/Gson;", "Ljava/lang/reflect/Type;", "Lcom/google/gson/TypeAdapter", "<TE;>;", "Lcom/google/gson/internal/ObjectConstructor", "<+", "Ljava/util/Collection", "<TE;>;>;)V"}) Gson $r1, @Signature({"(", "Lcom/google/gson/Gson;", "Ljava/lang/reflect/Type;", "Lcom/google/gson/TypeAdapter", "<TE;>;", "Lcom/google/gson/internal/ObjectConstructor", "<+", "Ljava/util/Collection", "<TE;>;>;)V"}) Type $r2, @Signature({"(", "Lcom/google/gson/Gson;", "Ljava/lang/reflect/Type;", "Lcom/google/gson/TypeAdapter", "<TE;>;", "Lcom/google/gson/internal/ObjectConstructor", "<+", "Ljava/util/Collection", "<TE;>;>;)V"}) TypeAdapter<E> $r3, @Signature({"(", "Lcom/google/gson/Gson;", "Ljava/lang/reflect/Type;", "Lcom/google/gson/TypeAdapter", "<TE;>;", "Lcom/google/gson/internal/ObjectConstructor", "<+", "Ljava/util/Collection", "<TE;>;>;)V"}) ObjectConstructor<? extends Collection<E>> $r4) throws  {
            this.elementTypeAdapter = new TypeAdapterRuntimeTypeWrapper($r1, $r3, $r2);
            this.constructor = $r4;
        }

        public Collection<E> read(@Signature({"(", "Lcom/google/gson/stream/JsonReader;", ")", "Ljava/util/Collection", "<TE;>;"}) JsonReader $r1) throws IOException {
            if ($r1.peek() == JsonToken.NULL) {
                $r1.nextNull();
                return null;
            }
            Collection $r4 = (Collection) this.constructor.construct();
            $r1.beginArray();
            while ($r1.hasNext()) {
                $r4.add(this.elementTypeAdapter.read($r1));
            }
            $r1.endArray();
            return $r4;
        }

        public void write(@Signature({"(", "Lcom/google/gson/stream/JsonWriter;", "Ljava/util/Collection", "<TE;>;)V"}) JsonWriter $r1, @Signature({"(", "Lcom/google/gson/stream/JsonWriter;", "Ljava/util/Collection", "<TE;>;)V"}) Collection<E> $r2) throws IOException {
            if ($r2 == null) {
                $r1.nullValue();
                return;
            }
            $r1.beginArray();
            for (E $r4 : $r2) {
                this.elementTypeAdapter.write($r1, $r4);
            }
            $r1.endArray();
        }
    }

    public CollectionTypeAdapterFactory(ConstructorConstructor $r1) throws  {
        this.constructorConstructor = $r1;
    }

    public <T> TypeAdapter<T> create(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) Gson $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) TypeToken<T> $r2) throws  {
        Type $r3 = $r2.getType();
        Class $r4 = $r2.getRawType();
        if (!Collection.class.isAssignableFrom($r4)) {
            return null;
        }
        $r3 = C$Gson$Types.getCollectionElementType($r3, $r4);
        return new Adapter($r1, $r3, $r1.getAdapter(TypeToken.get($r3)), this.constructorConstructor.get($r2));
    }
}
