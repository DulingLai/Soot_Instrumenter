package com.google.android.gms.plus.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzc extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzc {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzc {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public zzd zza(zzd $r1, int $i0, int $i1, String $r2, int $i2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusOneButtonCreator");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r3.writeInt($i0);
                    $r3.writeInt($i1);
                    $r3.writeString($r2);
                    $r3.writeInt($i2);
                    this.zzahn.transact(1, $r3, $r4, 0);
                    $r4.readException();
                    $r1 = com.google.android.gms.dynamic.zzd.zza.zzin($r4.readStrongBinder());
                    return $r1;
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public zzd zza(zzd $r1, int $i0, int $i1, String $r2, String $r3) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusOneButtonCreator");
                    $r4.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r4.writeInt($i0);
                    $r4.writeInt($i1);
                    $r4.writeString($r2);
                    $r4.writeString($r3);
                    this.zzahn.transact(2, $r4, $r5, 0);
                    $r5.readException();
                    $r1 = com.google.android.gms.dynamic.zzd.zza.zzin($r5.readStrongBinder());
                    return $r1;
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }
        }

        public static zzc zzow(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.plus.internal.IPlusOneButtonCreator");
            return ($r1 == null || !($r1 instanceof zzc)) ? new zza($r0) : (zzc) $r1;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            IBinder $r3 = null;
            zzd $r4;
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusOneButtonCreator");
                    $r4 = zza(com.google.android.gms.dynamic.zzd.zza.zzin($r1.readStrongBinder()), $r1.readInt(), $r1.readInt(), $r1.readString(), $r1.readInt());
                    $r2.writeNoException();
                    $r2.writeStrongBinder($r4 != null ? $r4.asBinder() : null);
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.gms.plus.internal.IPlusOneButtonCreator");
                    $r4 = zza(com.google.android.gms.dynamic.zzd.zza.zzin($r1.readStrongBinder()), $r1.readInt(), $r1.readInt(), $r1.readString(), $r1.readString());
                    $r2.writeNoException();
                    if ($r4 != null) {
                        $r3 = $r4.asBinder();
                    }
                    $r2.writeStrongBinder($r3);
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.plus.internal.IPlusOneButtonCreator");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    zzd zza(zzd com_google_android_gms_dynamic_zzd, int i, int i2, String str, int i3) throws RemoteException;

    zzd zza(zzd com_google_android_gms_dynamic_zzd, int i, int i2, String str, String str2) throws RemoteException;
}
