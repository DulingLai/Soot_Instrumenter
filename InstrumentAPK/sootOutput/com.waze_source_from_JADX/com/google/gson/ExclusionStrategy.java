package com.google.gson;

import dalvik.annotation.Signature;

public interface ExclusionStrategy {
    boolean shouldSkipClass(@Signature({"(", "Ljava/lang/Class", "<*>;)Z"}) Class<?> cls) throws ;

    boolean shouldSkipField(FieldAttributes fieldAttributes) throws ;
}
