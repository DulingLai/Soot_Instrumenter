package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzh extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzh {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzh {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public void zza(int $i0, PendingIntent $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.location.internal.IGeofencerCallbacks");
                    $r2.writeInt($i0);
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(3, $r2, null, 1);
                } finally {
                    $r2.recycle();
                }
            }

            public void zza(int $i0, String[] $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.location.internal.IGeofencerCallbacks");
                    $r2.writeInt($i0);
                    $r2.writeStringArray($r1);
                    this.zzahn.transact(1, $r2, null, 1);
                } finally {
                    $r2.recycle();
                }
            }

            public void zzb(int $i0, String[] $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.location.internal.IGeofencerCallbacks");
                    $r2.writeInt($i0);
                    $r2.writeStringArray($r1);
                    this.zzahn.transact(2, $r2, null, 1);
                } finally {
                    $r2.recycle();
                }
            }
        }

        public zza() throws  {
            attachInterface(this, "com.google.android.gms.location.internal.IGeofencerCallbacks");
        }

        public static zzh zzky(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.location.internal.IGeofencerCallbacks");
            return ($r1 == null || !($r1 instanceof zzh)) ? new zza($r0) : (zzh) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGeofencerCallbacks");
                    zza($r1.readInt(), $r1.createStringArray());
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGeofencerCallbacks");
                    zzb($r1.readInt(), $r1.createStringArray());
                    return true;
                case 3:
                    $r1.enforceInterface("com.google.android.gms.location.internal.IGeofencerCallbacks");
                    zza($r1.readInt(), $r1.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel($r1) : null);
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.location.internal.IGeofencerCallbacks");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void zza(int i, PendingIntent pendingIntent) throws RemoteException;

    void zza(int i, String[] strArr) throws RemoteException;

    void zzb(int i, String[] strArr) throws RemoteException;
}
