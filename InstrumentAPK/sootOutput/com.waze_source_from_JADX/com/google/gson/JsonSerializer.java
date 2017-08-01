package com.google.gson;

import dalvik.annotation.Signature;
import java.lang.reflect.Type;

public interface JsonSerializer<T> {
    JsonElement serialize(@Signature({"(TT;", "Ljava/lang/reflect/Type;", "Lcom/google/gson/JsonSerializationContext;", ")", "Lcom/google/gson/JsonElement;"}) T t, @Signature({"(TT;", "Ljava/lang/reflect/Type;", "Lcom/google/gson/JsonSerializationContext;", ")", "Lcom/google/gson/JsonElement;"}) Type type, @Signature({"(TT;", "Ljava/lang/reflect/Type;", "Lcom/google/gson/JsonSerializationContext;", ")", "Lcom/google/gson/JsonElement;"}) JsonSerializationContext jsonSerializationContext) throws ;
}
