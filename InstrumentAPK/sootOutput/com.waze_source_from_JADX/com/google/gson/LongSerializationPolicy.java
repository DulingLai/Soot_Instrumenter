package com.google.gson;

public enum LongSerializationPolicy {
    DEFAULT {
        public JsonElement serialize(Long $r1) throws  {
            return new JsonPrimitive((Number) $r1);
        }
    },
    STRING {
        public JsonElement serialize(Long $r1) throws  {
            return new JsonPrimitive(String.valueOf($r1));
        }
    };

    public abstract JsonElement serialize(Long l) throws ;
}
