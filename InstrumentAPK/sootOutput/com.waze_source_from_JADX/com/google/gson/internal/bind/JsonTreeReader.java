package com.google.gson.internal.bind;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public final class JsonTreeReader extends JsonReader {
    private static final Object SENTINEL_CLOSED = new Object();
    private static final Reader UNREADABLE_READER = new C10401();
    private final List<Object> stack = new ArrayList();

    static class C10401 extends Reader {
        C10401() throws  {
        }

        public int read(char[] buffer, int offset, int count) throws IOException {
            throw new AssertionError();
        }

        public void close() throws IOException {
            throw new AssertionError();
        }
    }

    public JsonTreeReader(JsonElement $r1) throws  {
        super(UNREADABLE_READER);
        this.stack.add($r1);
    }

    public void beginArray() throws IOException {
        expect(JsonToken.BEGIN_ARRAY);
        this.stack.add(((JsonArray) peekStack()).iterator());
    }

    public void endArray() throws IOException {
        expect(JsonToken.END_ARRAY);
        popStack();
        popStack();
    }

    public void beginObject() throws IOException {
        expect(JsonToken.BEGIN_OBJECT);
        this.stack.add(((JsonObject) peekStack()).entrySet().iterator());
    }

    public void endObject() throws IOException {
        expect(JsonToken.END_OBJECT);
        popStack();
        popStack();
    }

    public boolean hasNext() throws IOException {
        JsonToken $r1 = peek();
        return ($r1 == JsonToken.END_OBJECT || $r1 == JsonToken.END_ARRAY) ? false : true;
    }

    public JsonToken peek() throws IOException {
        if (this.stack.isEmpty()) {
            return JsonToken.END_DOCUMENT;
        }
        Object $r2 = peekStack();
        if ($r2 instanceof Iterator) {
            boolean $z0 = this.stack.get(this.stack.size() - 2) instanceof JsonObject;
            Iterator $r5 = (Iterator) $r2;
            if (!$r5.hasNext()) {
                return $z0 ? JsonToken.END_OBJECT : JsonToken.END_ARRAY;
            } else {
                if ($z0) {
                    return JsonToken.NAME;
                }
                this.stack.add($r5.next());
                return peek();
            }
        } else if ($r2 instanceof JsonObject) {
            return JsonToken.BEGIN_OBJECT;
        } else {
            if ($r2 instanceof JsonArray) {
                return JsonToken.BEGIN_ARRAY;
            }
            if ($r2 instanceof JsonPrimitive) {
                JsonPrimitive $r7 = (JsonPrimitive) $r2;
                if ($r7.isString()) {
                    return JsonToken.STRING;
                }
                if ($r7.isBoolean()) {
                    return JsonToken.BOOLEAN;
                }
                if ($r7.isNumber()) {
                    return JsonToken.NUMBER;
                }
                throw new AssertionError();
            } else if ($r2 instanceof JsonNull) {
                return JsonToken.NULL;
            } else {
                if ($r2 == SENTINEL_CLOSED) {
                    throw new IllegalStateException("JsonReader is closed");
                }
                throw new AssertionError();
            }
        }
    }

    private Object peekStack() throws  {
        return this.stack.get(this.stack.size() - 1);
    }

    private Object popStack() throws  {
        return this.stack.remove(this.stack.size() - 1);
    }

    private void expect(JsonToken $r1) throws IOException {
        if (peek() != $r1) {
            throw new IllegalStateException("Expected " + $r1 + " but was " + peek());
        }
    }

    public String nextName() throws IOException {
        expect(JsonToken.NAME);
        Entry $r4 = (Entry) ((Iterator) peekStack()).next();
        this.stack.add($r4.getValue());
        return (String) $r4.getKey();
    }

    public String nextString() throws IOException {
        JsonToken $r1 = peek();
        if ($r1 == JsonToken.STRING || $r1 == JsonToken.NUMBER) {
            return ((JsonPrimitive) popStack()).getAsString();
        }
        throw new IllegalStateException("Expected " + JsonToken.STRING + " but was " + $r1);
    }

    public boolean nextBoolean() throws IOException {
        expect(JsonToken.BOOLEAN);
        return ((JsonPrimitive) popStack()).getAsBoolean();
    }

    public void nextNull() throws IOException {
        expect(JsonToken.NULL);
        popStack();
    }

    public double nextDouble() throws IOException {
        JsonToken $r1 = peek();
        if ($r1 == JsonToken.NUMBER || $r1 == JsonToken.STRING) {
            double $d0 = ((JsonPrimitive) peekStack()).getAsDouble();
            if (isLenient() || !(Double.isNaN($d0) || Double.isInfinite($d0))) {
                popStack();
                return $d0;
            }
            throw new NumberFormatException("JSON forbids NaN and infinities: " + $d0);
        }
        throw new IllegalStateException("Expected " + JsonToken.NUMBER + " but was " + $r1);
    }

    public long nextLong() throws IOException {
        JsonToken $r1 = peek();
        if ($r1 == JsonToken.NUMBER || $r1 == JsonToken.STRING) {
            long $l0 = ((JsonPrimitive) peekStack()).getAsLong();
            popStack();
            return $l0;
        }
        throw new IllegalStateException("Expected " + JsonToken.NUMBER + " but was " + $r1);
    }

    public int nextInt() throws IOException {
        JsonToken $r1 = peek();
        if ($r1 == JsonToken.NUMBER || $r1 == JsonToken.STRING) {
            int $i0 = ((JsonPrimitive) peekStack()).getAsInt();
            popStack();
            return $i0;
        }
        throw new IllegalStateException("Expected " + JsonToken.NUMBER + " but was " + $r1);
    }

    public void close() throws IOException {
        this.stack.clear();
        this.stack.add(SENTINEL_CLOSED);
    }

    public void skipValue() throws IOException {
        if (peek() == JsonToken.NAME) {
            nextName();
        } else {
            popStack();
        }
    }

    public String toString() throws  {
        return getClass().getSimpleName();
    }

    public void promoteNameToValue() throws IOException {
        expect(JsonToken.NAME);
        Entry $r5 = (Entry) ((Iterator) peekStack()).next();
        this.stack.add($r5.getValue());
        this.stack.add(new JsonPrimitive((String) $r5.getKey()));
    }
}
