package com.google.android.gms.common.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzr extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzr {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzr {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public void cancel() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.common.internal.ICancelToken");
                    this.zzahn.transact(2, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }
        }

        public static zzr zzgs(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.common.internal.ICancelToken");
            return ($r1 == null || !($r1 instanceof zzr)) ? new zza($r0) : (zzr) $r1;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 2:
                    $r1.enforceInterface("com.google.android.gms.common.internal.ICancelToken");
                    cancel();
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.common.internal.ICancelToken");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void cancel() throws RemoteException;
}
