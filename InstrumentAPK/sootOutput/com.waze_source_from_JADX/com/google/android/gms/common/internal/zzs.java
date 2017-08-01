package com.google.android.gms.common.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzs extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzs {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzs {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public zzd zzare() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.common.internal.ICertData");
                    this.zzahn.transact(1, $r1, $r2, 0);
                    $r2.readException();
                    zzd $r4 = com.google.android.gms.dynamic.zzd.zza.zzin($r2.readStrongBinder());
                    return $r4;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public int zzarf() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.common.internal.ICertData");
                    this.zzahn.transact(2, $r1, $r2, 0);
                    $r2.readException();
                    int $i0 = $r2.readInt();
                    return $i0;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }
        }

        public zza() throws  {
            attachInterface(this, "com.google.android.gms.common.internal.ICertData");
        }

        public static zzs zzgt(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.common.internal.ICertData");
            return ($r1 == null || !($r1 instanceof zzs)) ? new zza($r0) : (zzs) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.common.internal.ICertData");
                    zzd $r3 = zzare();
                    $r2.writeNoException();
                    $r2.writeStrongBinder($r3 != null ? $r3.asBinder() : null);
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.gms.common.internal.ICertData");
                    $i0 = zzarf();
                    $r2.writeNoException();
                    $r2.writeInt($i0);
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.common.internal.ICertData");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    zzd zzare() throws RemoteException;

    int zzarf() throws RemoteException;
}
