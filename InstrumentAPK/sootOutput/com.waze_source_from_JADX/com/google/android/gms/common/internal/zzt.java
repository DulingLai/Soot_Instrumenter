package com.google.android.gms.common.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzt extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzt {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzt {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public void zza(int $i0, Bundle $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.common.internal.IGmsCallbacks");
                    $r2.writeInt($i0);
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(2, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void zza(int $i0, IBinder $r1, Bundle $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.common.internal.IGmsCallbacks");
                    $r3.writeInt($i0);
                    $r3.writeStrongBinder($r1);
                    if ($r2 != null) {
                        $r3.writeInt(1);
                        $r2.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    this.zzahn.transact(1, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }
        }

        public zza() throws  {
            attachInterface(this, "com.google.android.gms.common.internal.IGmsCallbacks");
        }

        public static zzt zzgu(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.common.internal.IGmsCallbacks");
            return ($r1 == null || !($r1 instanceof zzt)) ? new zza($r0) : (zzt) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            Bundle $r3 = null;
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.common.internal.IGmsCallbacks");
                    $i0 = $r1.readInt();
                    IBinder $r4 = $r1.readStrongBinder();
                    if ($r1.readInt() != 0) {
                        $r3 = (Bundle) Bundle.CREATOR.createFromParcel($r1);
                    }
                    zza($i0, $r4, $r3);
                    $r2.writeNoException();
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.gms.common.internal.IGmsCallbacks");
                    $i0 = $r1.readInt();
                    if ($r1.readInt() != 0) {
                        $r3 = (Bundle) Bundle.CREATOR.createFromParcel($r1);
                    }
                    zza($i0, $r3);
                    $r2.writeNoException();
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.common.internal.IGmsCallbacks");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void zza(int i, Bundle bundle) throws RemoteException;

    void zza(int i, IBinder iBinder, Bundle bundle) throws RemoteException;
}
