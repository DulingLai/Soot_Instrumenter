package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzui extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzui {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzui {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public int zza(zzd $r1, String $r2, boolean $z0) throws RemoteException {
                byte $b0 = (byte) 0;
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.dynamite.IDynamiteLoader");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r3.writeString($r2);
                    if ($z0) {
                        $b0 = (byte) 1;
                    }
                    $r3.writeInt($b0);
                    this.zzahn.transact(3, $r3, $r4, 0);
                    $r4.readException();
                    int $i1 = $r4.readInt();
                    return $i1;
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public zzd zza(zzd $r1, String $r2, int $i0) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.dynamite.IDynamiteLoader");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r3.writeString($r2);
                    $r3.writeInt($i0);
                    this.zzahn.transact(2, $r3, $r4, 0);
                    $r4.readException();
                    $r1 = com.google.android.gms.dynamic.zzd.zza.zzin($r4.readStrongBinder());
                    return $r1;
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public int zzd(zzd $r1, String $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.dynamite.IDynamiteLoader");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r3.writeString($r2);
                    this.zzahn.transact(1, $r3, $r4, 0);
                    $r4.readException();
                    int $i0 = $r4.readInt();
                    return $i0;
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }
        }

        public static zzui zzio(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoader");
            return ($r1 == null || !($r1 instanceof zzui)) ? new zza($r0) : (zzui) $r1;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.dynamite.IDynamiteLoader");
                    $i0 = zzd(com.google.android.gms.dynamic.zzd.zza.zzin($r1.readStrongBinder()), $r1.readString());
                    $r2.writeNoException();
                    $r2.writeInt($i0);
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.gms.dynamite.IDynamiteLoader");
                    zzd $r4 = zza(com.google.android.gms.dynamic.zzd.zza.zzin($r1.readStrongBinder()), $r1.readString(), $r1.readInt());
                    $r2.writeNoException();
                    $r2.writeStrongBinder($r4 != null ? $r4.asBinder() : null);
                    return true;
                case 3:
                    $r1.enforceInterface("com.google.android.gms.dynamite.IDynamiteLoader");
                    $i0 = zza(com.google.android.gms.dynamic.zzd.zza.zzin($r1.readStrongBinder()), $r1.readString(), $r1.readInt() != 0);
                    $r2.writeNoException();
                    $r2.writeInt($i0);
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.dynamite.IDynamiteLoader");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    int zza(zzd com_google_android_gms_dynamic_zzd, String str, boolean z) throws RemoteException;

    zzd zza(zzd com_google_android_gms_dynamic_zzd, String str, int i) throws RemoteException;

    int zzd(zzd com_google_android_gms_dynamic_zzd, String str) throws RemoteException;
}
