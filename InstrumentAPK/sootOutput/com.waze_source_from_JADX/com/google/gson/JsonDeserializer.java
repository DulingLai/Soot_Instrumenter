package com.google.gson;

import dalvik.annotation.Signature;
import java.lang.reflect.Type;

public interface JsonDeserializer<T> {
    T deserialize(@Signature({"(", "Lcom/google/gson/JsonElement;", "Ljava/lang/reflect/Type;", "Lcom/google/gson/JsonDeserializationContext;", ")TT;"}) JsonElement jsonElement, @Signature({"(", "Lcom/google/gson/JsonElement;", "Ljava/lang/reflect/Type;", "Lcom/google/gson/JsonDeserializationContext;", ")TT;"}) Type type, @Signature({"(", "Lcom/google/gson/JsonElement;", "Ljava/lang/reflect/Type;", "Lcom/google/gson/JsonDeserializationContext;", ")TT;"}) JsonDeserializationContext jsonDeserializationContext) throws JsonParseException;
}
