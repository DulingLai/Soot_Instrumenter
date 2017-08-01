package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzwh extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzwh {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzwh {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public boolean getBooleanFlagValue(String $r1, boolean $z0, int $i0) throws RemoteException {
                boolean $z1 = true;
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.flags.IFlagProvider");
                    $r2.writeString($r1);
                    $r2.writeInt($z0 ? (byte) 1 : (byte) 0);
                    $r2.writeInt($i0);
                    this.zzahn.transact(2, $r2, $r3, 0);
                    $r3.readException();
                    if ($r3.readInt() == 0) {
                        $z1 = false;
                    }
                    $r3.recycle();
                    $r2.recycle();
                    return $z1;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public int getIntFlagValue(String $r1, int $i0, int $i1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.flags.IFlagProvider");
                    $r2.writeString($r1);
                    $r2.writeInt($i0);
                    $r2.writeInt($i1);
                    this.zzahn.transact(3, $r2, $r3, 0);
                    $r3.readException();
                    $i0 = $r3.readInt();
                    return $i0;
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public long getLongFlagValue(String $r1, long $l0, int $i1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.flags.IFlagProvider");
                    $r2.writeString($r1);
                    $r2.writeLong($l0);
                    $r2.writeInt($i1);
                    this.zzahn.transact(4, $r2, $r3, 0);
                    $r3.readException();
                    $l0 = $r3.readLong();
                    return $l0;
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public String getStringFlagValue(String $r1, String $r2, int $i0) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.flags.IFlagProvider");
                    $r3.writeString($r1);
                    $r3.writeString($r2);
                    $r3.writeInt($i0);
                    this.zzahn.transact(5, $r3, $r4, 0);
                    $r4.readException();
                    $r1 = $r4.readString();
                    return $r1;
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void init(zzd $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.flags.IFlagProvider");
                    $r2.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    this.zzahn.transact(1, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }
        }

        public zza() throws  {
            attachInterface(this, "com.google.android.gms.flags.IFlagProvider");
        }

        public static zzwh asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.flags.IFlagProvider");
            return ($r1 == null || !($r1 instanceof zzwh)) ? new zza($r0) : (zzwh) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            byte $b2 = (byte) 0;
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.flags.IFlagProvider");
                    init(com.google.android.gms.dynamic.zzd.zza.zzin($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.gms.flags.IFlagProvider");
                    boolean $z0 = getBooleanFlagValue($r1.readString(), $r1.readInt() != 0, $r1.readInt());
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 3:
                    $r1.enforceInterface("com.google.android.gms.flags.IFlagProvider");
                    $i0 = getIntFlagValue($r1.readString(), $r1.readInt(), $r1.readInt());
                    $r2.writeNoException();
                    $r2.writeInt($i0);
                    return true;
                case 4:
                    $r1.enforceInterface("com.google.android.gms.flags.IFlagProvider");
                    long $l3 = getLongFlagValue($r1.readString(), $r1.readLong(), $r1.readInt());
                    $r2.writeNoException();
                    $r2.writeLong($l3);
                    return true;
                case 5:
                    $r1.enforceInterface("com.google.android.gms.flags.IFlagProvider");
                    String $r5 = getStringFlagValue($r1.readString(), $r1.readString(), $r1.readInt());
                    $r2.writeNoException();
                    $r2.writeString($r5);
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.flags.IFlagProvider");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    boolean getBooleanFlagValue(String str, boolean z, int i) throws RemoteException;

    int getIntFlagValue(String str, int i, int i2) throws RemoteException;

    long getLongFlagValue(String str, long j, int i) throws RemoteException;

    String getStringFlagValue(String str, String str2, int i) throws RemoteException;

    void init(zzd com_google_android_gms_dynamic_zzd) throws RemoteException;
}
