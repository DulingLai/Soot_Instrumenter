package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzsw extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzsw {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzsw {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public zzd zza(zzd $r1, zzd $r2, zzd $r3, String $r4) throws RemoteException {
                IBinder $r5 = null;
                Parcel $r6 = Parcel.obtain();
                Parcel $r7 = Parcel.obtain();
                try {
                    $r6.writeInterfaceToken("com.google.android.gms.common.net.ISocketFactoryCreator");
                    $r6.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r6.writeStrongBinder($r2 != null ? $r2.asBinder() : null);
                    if ($r3 != null) {
                        $r5 = $r3.asBinder();
                    }
                    $r6.writeStrongBinder($r5);
                    $r6.writeString($r4);
                    this.zzahn.transact(2, $r6, $r7, 0);
                    $r7.readException();
                    $r1 = com.google.android.gms.dynamic.zzd.zza.zzin($r7.readStrongBinder());
                    return $r1;
                } finally {
                    $r7.recycle();
                    $r6.recycle();
                }
            }

            public zzd zza(zzd $r1, zzd $r2, zzd $r3, boolean $z0) throws RemoteException {
                byte $b0 = (byte) 1;
                IBinder $r4 = null;
                Parcel $r5 = Parcel.obtain();
                Parcel $r6 = Parcel.obtain();
                try {
                    $r5.writeInterfaceToken("com.google.android.gms.common.net.ISocketFactoryCreator");
                    $r5.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r5.writeStrongBinder($r2 != null ? $r2.asBinder() : null);
                    if ($r3 != null) {
                        $r4 = $r3.asBinder();
                    }
                    $r5.writeStrongBinder($r4);
                    if (!$z0) {
                        $b0 = (byte) 0;
                    }
                    $r5.writeInt($b0);
                    this.zzahn.transact(1, $r5, $r6, 0);
                    $r6.readException();
                    $r1 = com.google.android.gms.dynamic.zzd.zza.zzin($r6.readStrongBinder());
                    return $r1;
                } finally {
                    $r6.recycle();
                    $r5.recycle();
                }
            }
        }

        public static zzsw zzhd(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.common.net.ISocketFactoryCreator");
            return ($r1 == null || !($r1 instanceof zzsw)) ? new zza($r0) : (zzsw) $r1;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            IBinder $r3 = null;
            zzd $r4;
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.common.net.ISocketFactoryCreator");
                    $r4 = zza(com.google.android.gms.dynamic.zzd.zza.zzin($r1.readStrongBinder()), com.google.android.gms.dynamic.zzd.zza.zzin($r1.readStrongBinder()), com.google.android.gms.dynamic.zzd.zza.zzin($r1.readStrongBinder()), $r1.readInt() != 0);
                    $r2.writeNoException();
                    $r2.writeStrongBinder($r4 != null ? $r4.asBinder() : null);
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.gms.common.net.ISocketFactoryCreator");
                    $r4 = zza(com.google.android.gms.dynamic.zzd.zza.zzin($r1.readStrongBinder()), com.google.android.gms.dynamic.zzd.zza.zzin($r1.readStrongBinder()), com.google.android.gms.dynamic.zzd.zza.zzin($r1.readStrongBinder()), $r1.readString());
                    $r2.writeNoException();
                    if ($r4 != null) {
                        $r3 = $r4.asBinder();
                    }
                    $r2.writeStrongBinder($r3);
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.common.net.ISocketFactoryCreator");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    zzd zza(zzd com_google_android_gms_dynamic_zzd, zzd com_google_android_gms_dynamic_zzd2, zzd com_google_android_gms_dynamic_zzd3, String str) throws RemoteException;

    zzd zza(zzd com_google_android_gms_dynamic_zzd, zzd com_google_android_gms_dynamic_zzd2, zzd com_google_android_gms_dynamic_zzd3, boolean z) throws RemoteException;
}
