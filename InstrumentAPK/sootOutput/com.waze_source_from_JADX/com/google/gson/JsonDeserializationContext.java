package com.google.gson;

import dalvik.annotation.Signature;
import java.lang.reflect.Type;

public interface JsonDeserializationContext {
    <T> T deserialize(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/JsonElement;", "Ljava/lang/reflect/Type;", ")TT;"}) JsonElement jsonElement, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/JsonElement;", "Ljava/lang/reflect/Type;", ")TT;"}) Type type) throws JsonParseException;
}
