package com.facebook.internal;

import dalvik.annotation.Signature;

public class Mutable<T> {
    public T value;

    public Mutable(@Signature({"(TT;)V"}) T $r1) throws  {
        this.value = $r1;
    }
}
