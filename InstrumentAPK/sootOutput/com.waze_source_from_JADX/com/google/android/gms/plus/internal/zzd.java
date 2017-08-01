package com.google.android.gms.plus.internal;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzd extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzd {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzd {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public void cancelClick() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusOneDelegate");
                    this.zzahn.transact(2, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public PendingIntent getResolution() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusOneDelegate");
                    this.zzahn.transact(1, $r1, $r2, 0);
                    $r2.readException();
                    PendingIntent $r6 = $r2.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel($r2) : null;
                    $r2.recycle();
                    $r1.recycle();
                    return $r6;
                } catch (Throwable th) {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void reinitialize() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusOneDelegate");
                    this.zzahn.transact(3, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }
        }

        public static zzd zzox(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.plus.internal.IPlusOneDelegate");
            return ($r1 == null || !($r1 instanceof zzd)) ? new zza($r0) : (zzd) $r1;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusOneDelegate");
                    PendingIntent $r3 = getResolution();
                    $r2.writeNoException();
                    if ($r3 != null) {
                        $r2.writeInt(1);
                        $r3.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusOneDelegate");
                    cancelClick();
                    $r2.writeNoException();
                    return true;
                case 3:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusOneDelegate");
                    reinitialize();
                    $r2.writeNoException();
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.plus.internal.IPlusOneDelegate");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void cancelClick() throws RemoteException;

    PendingIntent getResolution() throws RemoteException;

    void reinitialize() throws RemoteException;
}
