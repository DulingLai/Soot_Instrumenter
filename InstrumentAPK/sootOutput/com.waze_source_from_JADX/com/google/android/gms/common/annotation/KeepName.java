package com.google.android.gms.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
/* compiled from: dalvik_source_com.waze.apk */
public @interface KeepName {
}
