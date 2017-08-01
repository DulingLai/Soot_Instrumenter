package com.google.android.gms.internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
/* compiled from: dalvik_source_com.waze.apk */
public @interface zzavj {
    String[] ht() throws  default {};

    String value() throws ;
}
