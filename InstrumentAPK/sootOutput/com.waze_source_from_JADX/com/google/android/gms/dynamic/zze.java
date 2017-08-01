package com.google.android.gms.dynamic;

import android.os.IBinder;
import com.google.android.gms.dynamic.zzd.zza;
import dalvik.annotation.Signature;
import java.lang.reflect.Field;

/* compiled from: dalvik_source_com.waze.apk */
public final class zze<T> extends zza {
    private final T mWrappedObject;

    private zze(@Signature({"(TT;)V"}) T $r1) throws  {
        this.mWrappedObject = $r1;
    }

    public static <T> T zzae(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/dynamic/zzd;", ")TT;"}) zzd $r0) throws  {
        if ($r0 instanceof zze) {
            return ((zze) $r0).mWrappedObject;
        }
        IBinder $r2 = $r0.asBinder();
        Field[] $r4 = $r2.getClass().getDeclaredFields();
        if ($r4.length == 1) {
            Field $r5 = $r4[0];
            if ($r5.isAccessible()) {
                throw new IllegalArgumentException("The concrete class implementing IObjectWrapper must have exactly one declared *private* field for the wrapped object. Preferably, this is an instance of the ObjectWrapper<T> class.");
            }
            $r5.setAccessible(true);
            try {
                return $r5.get($r2);
            } catch (NullPointerException $r7) {
                throw new IllegalArgumentException("Binder object is null.", $r7);
            } catch (IllegalArgumentException $r8) {
                throw new IllegalArgumentException("remoteBinder is the wrong class.", $r8);
            } catch (IllegalAccessException $r10) {
                throw new IllegalArgumentException("Could not access the field in remoteBinder.", $r10);
            }
        }
        throw new IllegalArgumentException("The concrete class implementing IObjectWrapper must have exactly *one* declared private field for the wrapped object.  Preferably, this is an instance of the ObjectWrapper<T> class.");
    }

    public static <T> zzd zzan(@Signature({"<T:", "Ljava/lang/Object;", ">(TT;)", "Lcom/google/android/gms/dynamic/zzd;"}) T $r0) throws  {
        return new zze($r0);
    }
}
