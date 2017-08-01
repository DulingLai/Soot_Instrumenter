package com.google.gson;

public final class JsonNull extends JsonElement {
    public static final JsonNull INSTANCE = new JsonNull();

    public boolean equals(Object $r1) throws  {
        return this == $r1 || ($r1 instanceof JsonNull);
    }

    JsonNull deepCopy() throws  {
        return INSTANCE;
    }

    public int hashCode() throws  {
        return JsonNull.class.hashCode();
    }
}
