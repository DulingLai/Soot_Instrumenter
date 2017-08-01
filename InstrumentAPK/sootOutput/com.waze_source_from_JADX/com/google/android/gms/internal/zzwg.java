package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamite.descriptors.com.google.android.gms.flags.ModuleDescriptor;
import com.google.android.gms.internal.zzwh.zza;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class zzwg {
    private zzwh afS = null;
    private boolean zzanl = false;

    public void initialize(Context $r1) throws  {
        Exception $r8;
        synchronized (this) {
            if (this.zzanl) {
                return;
            }
            try {
                this.afS = zza.asInterface(zzuh.zza($r1, zzuh.aca, ModuleDescriptor.MODULE_ID).zzii("com.google.android.gms.flags.impl.FlagProviderImpl"));
                this.afS.init(zze.zzan($r1));
                this.zzanl = true;
            } catch (zzuh.zza e) {
                $r8 = e;
                Log.w("FlagValueProvider", "Failed to initialize flags module.", $r8);
            } catch (RemoteException e2) {
                $r8 = e2;
                Log.w("FlagValueProvider", "Failed to initialize flags module.", $r8);
            }
        }
    }

    public <T> T zzb(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzwe", "<TT;>;)TT;"}) zzwe<T> $r1) throws  {
        synchronized (this) {
            if (this.zzanl) {
                return $r1.zza(this.afS);
            }
            Object $r2 = $r1.getDefault();
            return $r2;
        }
    }
}
