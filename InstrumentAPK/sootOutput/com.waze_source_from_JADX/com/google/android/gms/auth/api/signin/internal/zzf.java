package com.google.android.gms.auth.api.signin.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzf extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzf {
        public zza() throws  {
            attachInterface(this, "com.google.android.gms.auth.api.signin.internal.IRevocationService");
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.auth.api.signin.internal.IRevocationService");
                    zzaev();
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.auth.api.signin.internal.IRevocationService");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void zzaev() throws RemoteException;
}
