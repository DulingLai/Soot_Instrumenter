package com.google.gson.internal.bind;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public final class JsonTreeWriter extends JsonWriter {
    private static final JsonPrimitive SENTINEL_CLOSED = new JsonPrimitive("closed");
    private static final Writer UNWRITABLE_WRITER = new C10411();
    private String pendingName;
    private JsonElement product = JsonNull.INSTANCE;
    private final List<JsonElement> stack = new ArrayList();

    static class C10411 extends Writer {
        C10411() throws  {
        }

        public void write(char[] buffer, int offset, int counter) throws  {
            throw new AssertionError();
        }

        public void flush() throws IOException {
            throw new AssertionError();
        }

        public void close() throws IOException {
            throw new AssertionError();
        }
    }

    public JsonTreeWriter() throws  {
        super(UNWRITABLE_WRITER);
    }

    public JsonElement get() throws  {
        if (this.stack.isEmpty()) {
            return this.product;
        }
        throw new IllegalStateException("Expected one JSON element but was " + this.stack);
    }

    private JsonElement peek() throws  {
        return (JsonElement) this.stack.get(this.stack.size() - 1);
    }

    private void put(JsonElement $r1) throws  {
        if (this.pendingName != null) {
            if (!$r1.isJsonNull() || getSerializeNulls()) {
                ((JsonObject) peek()).add(this.pendingName, $r1);
            }
            this.pendingName = null;
        } else if (this.stack.isEmpty()) {
            this.product = $r1;
        } else {
            JsonElement $r3 = peek();
            if ($r3 instanceof JsonArray) {
                ((JsonArray) $r3).add($r1);
                return;
            }
            throw new IllegalStateException();
        }
    }

    public JsonWriter beginArray() throws IOException {
        JsonArray $r1 = new JsonArray();
        put($r1);
        this.stack.add($r1);
        return this;
    }

    public JsonWriter endArray() throws IOException {
        if (this.stack.isEmpty() || this.pendingName != null) {
            throw new IllegalStateException();
        } else if (peek() instanceof JsonArray) {
            this.stack.remove(this.stack.size() - 1);
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public JsonWriter beginObject() throws IOException {
        JsonObject $r1 = new JsonObject();
        put($r1);
        this.stack.add($r1);
        return this;
    }

    public JsonWriter endObject() throws IOException {
        if (this.stack.isEmpty() || this.pendingName != null) {
            throw new IllegalStateException();
        } else if (peek() instanceof JsonObject) {
            this.stack.remove(this.stack.size() - 1);
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public JsonWriter name(String $r1) throws IOException {
        if (this.stack.isEmpty() || this.pendingName != null) {
            throw new IllegalStateException();
        } else if (peek() instanceof JsonObject) {
            this.pendingName = $r1;
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public JsonWriter value(String $r0) throws IOException {
        if ($r0 == null) {
            return nullValue();
        }
        put(new JsonPrimitive($r0));
        return this;
    }

    public JsonWriter nullValue() throws IOException {
        put(JsonNull.INSTANCE);
        return this;
    }

    public JsonWriter value(boolean $z0) throws IOException {
        put(new JsonPrimitive(Boolean.valueOf($z0)));
        return this;
    }

    public JsonWriter value(double $d0) throws IOException {
        if (isLenient() || !(Double.isNaN($d0) || Double.isInfinite($d0))) {
            put(new JsonPrimitive(Double.valueOf($d0)));
            return this;
        }
        throw new IllegalArgumentException("JSON forbids NaN and infinities: " + $d0);
    }

    public JsonWriter value(long $l0) throws IOException {
        put(new JsonPrimitive(Long.valueOf($l0)));
        return this;
    }

    public JsonWriter value(Number $r0) throws IOException {
        if ($r0 == null) {
            return nullValue();
        }
        if (!isLenient()) {
            double $d0 = $r0.doubleValue();
            if (Double.isNaN($d0) || Double.isInfinite($d0)) {
                throw new IllegalArgumentException("JSON forbids NaN and infinities: " + $r0);
            }
        }
        put(new JsonPrimitive($r0));
        return this;
    }

    public void flush() throws IOException {
    }

    public void close() throws IOException {
        if (this.stack.isEmpty()) {
            this.stack.add(SENTINEL_CLOSED);
            return;
        }
        throw new IOException("Incomplete document");
    }
}
