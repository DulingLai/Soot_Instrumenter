package com.google.gson;

import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.internal.bind.JsonTreeWriter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public abstract class TypeAdapter<T> {

    class C10171 extends TypeAdapter<T> {
        C10171() throws  {
        }

        public void write(@Signature({"(", "Lcom/google/gson/stream/JsonWriter;", "TT;)V"}) JsonWriter $r1, @Signature({"(", "Lcom/google/gson/stream/JsonWriter;", "TT;)V"}) T $r2) throws IOException {
            if ($r2 == null) {
                $r1.nullValue();
            } else {
                TypeAdapter.this.write($r1, $r2);
            }
        }

        public T read(@Signature({"(", "Lcom/google/gson/stream/JsonReader;", ")TT;"}) JsonReader $r1) throws IOException {
            if ($r1.peek() != JsonToken.NULL) {
                return TypeAdapter.this.read($r1);
            }
            $r1.nextNull();
            return null;
        }
    }

    public abstract T read(@Signature({"(", "Lcom/google/gson/stream/JsonReader;", ")TT;"}) JsonReader jsonReader) throws IOException;

    public abstract void write(@Signature({"(", "Lcom/google/gson/stream/JsonWriter;", "TT;)V"}) JsonWriter jsonWriter, @Signature({"(", "Lcom/google/gson/stream/JsonWriter;", "TT;)V"}) T t) throws IOException;

    public final void toJson(@Signature({"(", "Ljava/io/Writer;", "TT;)V"}) Writer $r1, @Signature({"(", "Ljava/io/Writer;", "TT;)V"}) T $r2) throws IOException {
        write(new JsonWriter($r1), $r2);
    }

    public final TypeAdapter<T> nullSafe() throws  {
        return new C10171();
    }

    public final String toJson(@Signature({"(TT;)", "Ljava/lang/String;"}) T $r1) throws  {
        StringWriter $r3 = new StringWriter();
        try {
            toJson($r3, $r1);
            return $r3.toString();
        } catch (IOException $r2) {
            throw new AssertionError($r2);
        }
    }

    public final JsonElement toJsonTree(@Signature({"(TT;)", "Lcom/google/gson/JsonElement;"}) T $r1) throws  {
        try {
            JsonTreeWriter $r3 = new JsonTreeWriter();
            write($r3, $r1);
            return $r3.get();
        } catch (Throwable $r2) {
            throw new JsonIOException($r2);
        }
    }

    public final T fromJson(@Signature({"(", "Ljava/io/Reader;", ")TT;"}) Reader $r1) throws IOException {
        return read(new JsonReader($r1));
    }

    public final T fromJson(@Signature({"(", "Ljava/lang/String;", ")TT;"}) String $r1) throws IOException {
        return fromJson(new StringReader($r1));
    }

    public final T fromJsonTree(@Signature({"(", "Lcom/google/gson/JsonElement;", ")TT;"}) JsonElement $r1) throws  {
        try {
            return read(new JsonTreeReader($r1));
        } catch (Throwable $r2) {
            throw new JsonIOException($r2);
        }
    }
}
