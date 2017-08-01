package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.util.ArrayList;

public final class ObjectTypeAdapter extends TypeAdapter<Object> {
    public static final TypeAdapterFactory FACTORY = new C10421();
    private final Gson gson;

    static class C10421 implements TypeAdapterFactory {
        C10421() throws  {
        }

        public <T> TypeAdapter<T> create(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) Gson $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) TypeToken<T> $r2) throws  {
            if ($r2.getRawType() == Object.class) {
                return new ObjectTypeAdapter($r1);
            }
            return null;
        }
    }

    ObjectTypeAdapter(Gson $r1) throws  {
        this.gson = $r1;
    }

    public Object read(JsonReader $r1) throws IOException {
        switch ($r1.peek()) {
            case BEGIN_ARRAY:
                ArrayList $r6 = new ArrayList();
                $r1.beginArray();
                while ($r1.hasNext()) {
                    $r6.add(read($r1));
                }
                $r1.endArray();
                return $r6;
            case BEGIN_OBJECT:
                LinkedTreeMap $r2 = new LinkedTreeMap();
                $r1.beginObject();
                while ($r1.hasNext()) {
                    $r2.put($r1.nextName(), read($r1));
                }
                $r1.endObject();
                return $r2;
            case STRING:
                return $r1.nextString();
            case NUMBER:
                return Double.valueOf($r1.nextDouble());
            case BOOLEAN:
                return Boolean.valueOf($r1.nextBoolean());
            case NULL:
                $r1.nextNull();
                return null;
            default:
                throw new IllegalStateException();
        }
    }

    public void write(JsonWriter $r1, Object $r2) throws IOException {
        if ($r2 == null) {
            $r1.nullValue();
            return;
        }
        TypeAdapter $r5 = this.gson.getAdapter($r2.getClass());
        if ($r5 instanceof ObjectTypeAdapter) {
            $r1.beginObject();
            $r1.endObject();
            return;
        }
        $r5.write($r1, $r2);
    }
}
