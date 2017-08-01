package com.google.android.gms.location;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public interface zze extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zze {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zze {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public void onLocationAvailability(LocationAvailability $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.location.ILocationCallback");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(2, $r2, null, 1);
                } finally {
                    $r2.recycle();
                }
            }

            public void onLocationResult(LocationResult $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.location.ILocationCallback");
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
            attachInterface(this, "com.google.android.gms.location.ILocationCallback");
        }

        public static zze zzku(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.location.ILocationCallback");
            return ($r1 == null || !($r1 instanceof zze)) ? new zza($r0) : (zze) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            AbstractSafeParcelable $r3 = null;
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.location.ILocationCallback");
                    if ($r1.readInt() != 0) {
                        $r3 = (LocationResult) LocationResult.CREATOR.createFromParcel($r1);
                    }
                    onLocationResult((LocationResult) $r3);
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.gms.location.ILocationCallback");
                    if ($r1.readInt() != 0) {
                        $r3 = (LocationAvailability) LocationAvailability.CREATOR.createFromParcel($r1);
                    }
                    onLocationAvailability((LocationAvailability) $r3);
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.location.ILocationCallback");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void onLocationAvailability(LocationAvailability locationAvailability) throws RemoteException;

    void onLocationResult(LocationResult locationResult) throws RemoteException;
}
