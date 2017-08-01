package com.google.android.gms.dynamic;

import android.content.Context;
import android.os.IBinder;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.internal.zzab;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzg<T> {
    private final String abV;
    private T abW;

    /* compiled from: dalvik_source_com.waze.apk */
    public static class zza extends Exception {
        public zza(String $r1) throws  {
            super($r1);
        }

        public zza(String $r1, Throwable $r2) throws  {
            super($r1, $r2);
        }
    }

    protected zzg(String $r1) throws  {
        this.abV = $r1;
    }

    protected final T zzcm(@Signature({"(", "Landroid/content/Context;", ")TT;"}) Context $r1) throws zza {
        if (this.abW == null) {
            zzab.zzag($r1);
            $r1 = GooglePlayServicesUtilLight.getRemoteContext($r1);
            if ($r1 == null) {
                throw new zza("Could not get remote context.");
            }
            try {
                this.abW = zzd((IBinder) $r1.getClassLoader().loadClass(this.abV).newInstance());
            } catch (ClassNotFoundException $r8) {
                throw new zza("Could not load creator class.", $r8);
            } catch (InstantiationException $r9) {
                throw new zza("Could not instantiate creator.", $r9);
            } catch (IllegalAccessException $r10) {
                throw new zza("Could not access creator.", $r10);
            }
        }
        return this.abW;
    }

    protected abstract T zzd(@Signature({"(", "Landroid/os/IBinder;", ")TT;"}) IBinder iBinder) throws ;
}
