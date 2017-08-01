package com.google.gson;

import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class JsonElement {
    abstract JsonElement deepCopy() throws ;

    public boolean isJsonArray() throws  {
        return this instanceof JsonArray;
    }

    public boolean isJsonObject() throws  {
        return this instanceof JsonObject;
    }

    public boolean isJsonPrimitive() throws  {
        return this instanceof JsonPrimitive;
    }

    public boolean isJsonNull() throws  {
        return this instanceof JsonNull;
    }

    public JsonObject getAsJsonObject() throws  {
        if (isJsonObject()) {
            return (JsonObject) this;
        }
        throw new IllegalStateException("Not a JSON Object: " + this);
    }

    public JsonArray getAsJsonArray() throws  {
        if (isJsonArray()) {
            return (JsonArray) this;
        }
        throw new IllegalStateException("This is not a JSON Array.");
    }

    public JsonPrimitive getAsJsonPrimitive() throws  {
        if (isJsonPrimitive()) {
            return (JsonPrimitive) this;
        }
        throw new IllegalStateException("This is not a JSON Primitive.");
    }

    public JsonNull getAsJsonNull() throws  {
        if (isJsonNull()) {
            return (JsonNull) this;
        }
        throw new IllegalStateException("This is not a JSON Null.");
    }

    public boolean getAsBoolean() throws  {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    Boolean getAsBooleanWrapper() throws  {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public Number getAsNumber() throws  {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public String getAsString() throws  {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public double getAsDouble() throws  {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public float getAsFloat() throws  {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public long getAsLong() throws  {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public int getAsInt() throws  {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public byte getAsByte() throws  {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public char getAsCharacter() throws  {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public BigDecimal getAsBigDecimal() throws  {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public BigInteger getAsBigInteger() throws  {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public short getAsShort() throws  {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public String toString() throws  {
        try {
            StringWriter $r3 = new StringWriter();
            JsonWriter $r2 = new JsonWriter($r3);
            $r2.setLenient(true);
            Streams.write(this, $r2);
            return $r3.toString();
        } catch (IOException $r1) {
            throw new AssertionError($r1);
        }
    }
}
