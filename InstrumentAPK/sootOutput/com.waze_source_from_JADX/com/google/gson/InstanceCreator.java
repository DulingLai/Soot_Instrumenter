package com.google.gson;

import dalvik.annotation.Signature;
import java.lang.reflect.Type;

public interface InstanceCreator<T> {
    T createInstance(@Signature({"(", "Ljava/lang/reflect/Type;", ")TT;"}) Type type) throws ;
}
