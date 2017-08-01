package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.Locale;

public final class DateTypeAdapter extends TypeAdapter<Date> {
    public static final TypeAdapterFactory FACTORY = new C10391();
    private final DateFormat enUsFormat = DateFormat.getDateTimeInstance(2, 2, Locale.US);
    private final DateFormat localFormat = DateFormat.getDateTimeInstance(2, 2);

    static class C10391 implements TypeAdapterFactory {
        C10391() throws  {
        }

        public <T> TypeAdapter<T> create(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) Gson gson, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) TypeToken<T> $r2) throws  {
            return $r2.getRawType() == Date.class ? new DateTypeAdapter() : null;
        }
    }

    public Date read(JsonReader $r1) throws IOException {
        if ($r1.peek() != JsonToken.NULL) {
            return deserializeToDate($r1.nextString());
        }
        $r1.nextNull();
        return null;
    }

    private synchronized Date deserializeToDate(String $r1) throws  {
        Date $r4;
        try {
            $r4 = this.localFormat.parse($r1);
        } catch (ParseException e) {
            try {
                $r4 = this.enUsFormat.parse($r1);
            } catch (ParseException e2) {
                try {
                    $r4 = ISO8601Utils.parse($r1, new ParsePosition(0));
                } catch (ParseException $r2) {
                    throw new JsonSyntaxException($r1, $r2);
                }
            }
        }
        return $r4;
    }

    public synchronized void write(JsonWriter $r1, Date $r2) throws IOException {
        if ($r2 == null) {
            $r1.nullValue();
        } else {
            $r1.value(this.enUsFormat.format($r2));
        }
    }
}
