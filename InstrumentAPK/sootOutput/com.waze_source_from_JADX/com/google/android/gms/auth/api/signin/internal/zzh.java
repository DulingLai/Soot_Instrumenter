package com.google.android.gms.auth.api.signin.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

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

            public void zza(zzg $r1, GoogleSignInOptions $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.auth.api.signin.internal.ISignInService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    if ($r2 != null) {
                        $r3.writeInt(1);
                        $r2.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    this.zzahn.transact(101, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zzb(zzg $r1, GoogleSignInOptions $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.auth.api.signin.internal.ISignInService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    if ($r2 != null) {
                        $r3.writeInt(1);
                        $r2.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    this.zzahn.transact(102, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zzc(zzg $r1, GoogleSignInOptions $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.auth.api.signin.internal.ISignInService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    if ($r2 != null) {
                        $r3.writeInt(1);
                        $r2.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    this.zzahn.transact(103, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }
        }

        public static zzh zzcx(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.auth.api.signin.internal.ISignInService");
            return ($r1 == null || !($r1 instanceof zzh)) ? new zza($r0) : (zzh) $r1;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            GoogleSignInOptions $r3 = null;
            zzg $r5;
            switch ($i0) {
                case 101:
                    $r1.enforceInterface("com.google.android.gms.auth.api.signin.internal.ISignInService");
                    $r5 = com.google.android.gms.auth.api.signin.internal.zzg.zza.zzcw($r1.readStrongBinder());
                    if ($r1.readInt() != 0) {
                        $r3 = (GoogleSignInOptions) GoogleSignInOptions.CREATOR.createFromParcel($r1);
                    }
                    zza($r5, $r3);
                    $r2.writeNoException();
                    return true;
                case 102:
                    $r1.enforceInterface("com.google.android.gms.auth.api.signin.internal.ISignInService");
                    $r5 = com.google.android.gms.auth.api.signin.internal.zzg.zza.zzcw($r1.readStrongBinder());
                    if ($r1.readInt() != 0) {
                        $r3 = (GoogleSignInOptions) GoogleSignInOptions.CREATOR.createFromParcel($r1);
                    }
                    zzb($r5, $r3);
                    $r2.writeNoException();
                    return true;
                case 103:
                    $r1.enforceInterface("com.google.android.gms.auth.api.signin.internal.ISignInService");
                    $r5 = com.google.android.gms.auth.api.signin.internal.zzg.zza.zzcw($r1.readStrongBinder());
                    if ($r1.readInt() != 0) {
                        $r3 = (GoogleSignInOptions) GoogleSignInOptions.CREATOR.createFromParcel($r1);
                    }
                    zzc($r5, $r3);
                    $r2.writeNoException();
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.auth.api.signin.internal.ISignInService");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void zza(zzg com_google_android_gms_auth_api_signin_internal_zzg, GoogleSignInOptions googleSignInOptions) throws RemoteException;

    void zzb(zzg com_google_android_gms_auth_api_signin_internal_zzg, GoogleSignInOptions googleSignInOptions) throws RemoteException;

    void zzc(zzg com_google_android_gms_auth_api_signin_internal_zzg, GoogleSignInOptions googleSignInOptions) throws RemoteException;
}
