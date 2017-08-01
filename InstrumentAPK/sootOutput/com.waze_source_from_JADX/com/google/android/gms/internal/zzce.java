package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzce extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzce {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzce {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public String getId() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    this.zzahn.transact(1, $r1, $r2, 0);
                    $r2.readException();
                    String $r4 = $r2.readString();
                    return $r4;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void zzc(String $r1, boolean $z0) throws RemoteException {
                byte $b0 = (byte) 0;
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    $r2.writeString($r1);
                    if ($z0) {
                        $b0 = (byte) 1;
                    }
                    $r2.writeInt($b0);
                    this.zzahn.transact(4, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public boolean zzf(boolean $z0) throws RemoteException {
                boolean $z1 = true;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    $r1.writeInt($z0 ? (byte) 1 : (byte) 0);
                    this.zzahn.transact(2, $r1, $r2, 0);
                    $r2.readException();
                    if ($r2.readInt() == 0) {
                        $z1 = false;
                    }
                    $r2.recycle();
                    $r1.recycle();
                    return $z1;
                } catch (Throwable th) {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public String zzr(String $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    $r2.writeString($r1);
                    this.zzahn.transact(3, $r2, $r3, 0);
                    $r3.readException();
                    $r1 = $r3.readString();
                    return $r1;
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }
        }

        public static zzce zzg(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
            return ($r1 == null || !($r1 instanceof zzce)) ? new zza($r0) : (zzce) $r1;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            boolean $z0 = false;
            String $r3;
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    $r3 = getId();
                    $r2.writeNoException();
                    $r2.writeString($r3);
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    boolean $z1 = zzf($r1.readInt() != 0);
                    $r2.writeNoException();
                    if ($z1) {
                        $z0 = true;
                    }
                    $r2.writeInt($z0);
                    return true;
                case 3:
                    $r1.enforceInterface("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    $r3 = zzr($r1.readString());
                    $r2.writeNoException();
                    $r2.writeString($r3);
                    return true;
                case 4:
                    $r1.enforceInterface("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    $r3 = $r1.readString();
                    if ($r1.readInt() != 0) {
                        $z0 = true;
                    }
                    zzc($r3, $z0);
                    $r2.writeNoException();
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    String getId() throws RemoteException;

    void zzc(String str, boolean z) throws RemoteException;

    boolean zzf(boolean z) throws RemoteException;

    String zzr(String str) throws RemoteException;
}
