package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class TimeTypeAdapter extends TypeAdapter<Time> {
    public static final TypeAdapterFactory FACTORY = new C10461();
    private final DateFormat format = new SimpleDateFormat("hh:mm:ss a");

    static class C10461 implements TypeAdapterFactory {
        C10461() throws  {
        }

        public <T> TypeAdapter<T> create(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) Gson gson, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) TypeToken<T> $r2) throws  {
            return $r2.getRawType() == Time.class ? new TimeTypeAdapter() : null;
        }
    }

    public synchronized Time read(JsonReader $r1) throws IOException {
        Time $r5;
        if ($r1.peek() == JsonToken.NULL) {
            $r1.nextNull();
            $r5 = null;
        } else {
            try {
                $r5 = new Time(this.format.parse($r1.nextString()).getTime());
            } catch (Throwable $r2) {
                throw new JsonSyntaxException($r2);
            }
        }
        return $r5;
    }

    public synchronized void write(JsonWriter $r1, Time $r2) throws IOException {
        $r1.value($r2 == null ? null : this.format.format($r2));
    }
}
