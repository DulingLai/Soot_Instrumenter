package com.google.android.gms.common.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzx extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzx {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzx {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public zzd zza(zzd $r1, int $i0, int $i1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.common.internal.ISignInButtonCreator");
                    $r2.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r2.writeInt($i0);
                    $r2.writeInt($i1);
                    this.zzahn.transact(1, $r2, $r3, 0);
                    $r3.readException();
                    $r1 = com.google.android.gms.dynamic.zzd.zza.zzin($r3.readStrongBinder());
                    return $r1;
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public zzd zza(zzd $r1, SignInButtonConfig $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.common.internal.ISignInButtonCreator");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    if ($r2 != null) {
                        $r3.writeInt(1);
                        $r2.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    this.zzahn.transact(2, $r3, $r4, 0);
                    $r4.readException();
                    $r1 = com.google.android.gms.dynamic.zzd.zza.zzin($r4.readStrongBinder());
                    return $r1;
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }
        }

        public static zzx zzgy(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.common.internal.ISignInButtonCreator");
            return ($r1 == null || !($r1 instanceof zzx)) ? new zza($r0) : (zzx) $r1;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            IBinder $r3 = null;
            zzd $r4;
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.common.internal.ISignInButtonCreator");
                    $r4 = zza(com.google.android.gms.dynamic.zzd.zza.zzin($r1.readStrongBinder()), $r1.readInt(), $r1.readInt());
                    $r2.writeNoException();
                    $r2.writeStrongBinder($r4 != null ? $r4.asBinder() : null);
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.gms.common.internal.ISignInButtonCreator");
                    $r4 = zza(com.google.android.gms.dynamic.zzd.zza.zzin($r1.readStrongBinder()), $r1.readInt() != 0 ? (SignInButtonConfig) SignInButtonConfig.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    if ($r4 != null) {
                        $r3 = $r4.asBinder();
                    }
                    $r2.writeStrongBinder($r3);
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.common.internal.ISignInButtonCreator");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    zzd zza(zzd com_google_android_gms_dynamic_zzd, int i, int i2) throws RemoteException;

    zzd zza(zzd com_google_android_gms_dynamic_zzd, SignInButtonConfig signInButtonConfig) throws RemoteException;
}
