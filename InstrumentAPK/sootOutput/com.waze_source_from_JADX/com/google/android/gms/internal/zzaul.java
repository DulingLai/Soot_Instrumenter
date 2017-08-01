package com.google.android.gms.internal;

import dalvik.annotation.Signature;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzaul {
    private final Field bXj;

    public zzaul(Field $r1) throws  {
        zzavm.zzag($r1);
        this.bXj = $r1;
    }

    public <T extends Annotation> T getAnnotation(@Signature({"<T::", "Ljava/lang/annotation/Annotation;", ">(", "Ljava/lang/Class", "<TT;>;)TT;"}) Class<T> $r1) throws  {
        return this.bXj.getAnnotation($r1);
    }
}
