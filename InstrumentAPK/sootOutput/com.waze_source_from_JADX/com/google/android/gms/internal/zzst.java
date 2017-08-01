package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzst extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzst {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzst {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public void zziw(int $i0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.common.internal.service.ICommonCallbacks");
                    $r1.writeInt($i0);
                    this.zzahn.transact(1, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }
        }

        public zza() throws  {
            attachInterface(this, "com.google.android.gms.common.internal.service.ICommonCallbacks");
        }

        public static zzst zzhb(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.common.internal.service.ICommonCallbacks");
            return ($r1 == null || !($r1 instanceof zzst)) ? new zza($r0) : (zzst) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.common.internal.service.ICommonCallbacks");
                    zziw($r1.readInt());
                    $r2.writeNoException();
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.common.internal.service.ICommonCallbacks");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void zziw(int i) throws RemoteException;
}
