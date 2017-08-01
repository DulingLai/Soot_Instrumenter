package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzre extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzre {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzre {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public void zzv(Status $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.common.api.internal.IStatusCallback");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(1, $r2, null, 1);
                } finally {
                    $r2.recycle();
                }
            }
        }

        public zza() throws  {
            attachInterface(this, "com.google.android.gms.common.api.internal.IStatusCallback");
        }

        public static zzre zzgk(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.common.api.internal.IStatusCallback");
            return ($r1 == null || !($r1 instanceof zzre)) ? new zza($r0) : (zzre) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.common.api.internal.IStatusCallback");
                    zzv($r1.readInt() != 0 ? (Status) Status.CREATOR.createFromParcel($r1) : null);
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.common.api.internal.IStatusCallback");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void zzv(Status status) throws RemoteException;
}
