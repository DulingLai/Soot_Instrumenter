package com.google.android.gms.internal;

import dalvik.annotation.Signature;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/* compiled from: dalvik_source_com.waze.apk */
public class zzawk<T> {
    final Type bZy;
    final Class<? super T> caC;
    final int caD;

    protected zzawk() throws  {
        this.bZy = zzp(getClass());
        this.caC = zzavn.zzf(this.bZy);
        this.caD = this.bZy.hashCode();
    }

    zzawk(Type $r1) throws  {
        this.bZy = zzavn.zze((Type) zzavm.zzag($r1));
        this.caC = zzavn.zzf(this.bZy);
        this.caD = this.bZy.hashCode();
    }

    public static zzawk<?> zzl(@Signature({"(", "Ljava/lang/reflect/Type;", ")", "Lcom/google/android/gms/internal/zzawk", "<*>;"}) Type $r0) throws  {
        return new zzawk($r0);
    }

    static Type zzp(@Signature({"(", "Ljava/lang/Class", "<*>;)", "Ljava/lang/reflect/Type;"}) Class<?> $r0) throws  {
        Type $r1 = $r0.getGenericSuperclass();
        if (!($r1 instanceof Class)) {
            return zzavn.zze(((ParameterizedType) $r1).getActualTypeArguments()[0]);
        }
        throw new RuntimeException("Missing type parameter.");
    }

    public static <T> zzawk<T> zzq(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TT;>;)", "Lcom/google/android/gms/internal/zzawk", "<TT;>;"}) Class<T> $r0) throws  {
        return new zzawk($r0);
    }

    public final boolean equals(Object $r2) throws  {
        return ($r2 instanceof zzawk) && zzavn.zza(this.bZy, ((zzawk) $r2).bZy);
    }

    public final Class<? super T> hN() throws  {
        return this.caC;
    }

    public final Type hO() throws  {
        return this.bZy;
    }

    public final int hashCode() throws  {
        return this.caD;
    }

    public final String toString() throws  {
        return zzavn.zzg(this.bZy);
    }
}
