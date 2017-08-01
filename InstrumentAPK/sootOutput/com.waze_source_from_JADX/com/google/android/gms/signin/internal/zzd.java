package com.google.android.gms.signin.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;

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

            public void zza(ConnectionResult $r1, AuthAccountResult $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInCallbacks");
                    if ($r1 != null) {
                        $r3.writeInt(1);
                        $r1.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    if ($r2 != null) {
                        $r3.writeInt(1);
                        $r2.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    this.zzahn.transact(3, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zza(Status $r1, GoogleSignInAccount $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInCallbacks");
                    if ($r1 != null) {
                        $r3.writeInt(1);
                        $r1.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    if ($r2 != null) {
                        $r3.writeInt(1);
                        $r2.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    this.zzahn.transact(7, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zzb(SignInResponse $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInCallbacks");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(8, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void zzin(Status $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInCallbacks");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(4, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void zzio(Status $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInCallbacks");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(6, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }
        }

        public zza() throws  {
            attachInterface(this, "com.google.android.gms.signin.internal.ISignInCallbacks");
        }

        public static zzd zzqf(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.signin.internal.ISignInCallbacks");
            return ($r1 == null || !($r1 instanceof zzd)) ? new zza($r0) : (zzd) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 3:
                    $r1.enforceInterface("com.google.android.gms.signin.internal.ISignInCallbacks");
                    zza($r1.readInt() != 0 ? (ConnectionResult) ConnectionResult.CREATOR.createFromParcel($r1) : null, $r1.readInt() != 0 ? (AuthAccountResult) AuthAccountResult.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 4:
                    $r1.enforceInterface("com.google.android.gms.signin.internal.ISignInCallbacks");
                    zzin($r1.readInt() != 0 ? (Status) Status.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 6:
                    $r1.enforceInterface("com.google.android.gms.signin.internal.ISignInCallbacks");
                    zzio($r1.readInt() != 0 ? (Status) Status.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 7:
                    $r1.enforceInterface("com.google.android.gms.signin.internal.ISignInCallbacks");
                    zza($r1.readInt() != 0 ? (Status) Status.CREATOR.createFromParcel($r1) : null, $r1.readInt() != 0 ? (GoogleSignInAccount) GoogleSignInAccount.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 8:
                    $r1.enforceInterface("com.google.android.gms.signin.internal.ISignInCallbacks");
                    zzb($r1.readInt() != 0 ? (SignInResponse) SignInResponse.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.signin.internal.ISignInCallbacks");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void zza(ConnectionResult connectionResult, AuthAccountResult authAccountResult) throws RemoteException;

    void zza(Status status, GoogleSignInAccount googleSignInAccount) throws RemoteException;

    void zzb(SignInResponse signInResponse) throws RemoteException;

    void zzin(Status status) throws RemoteException;

    void zzio(Status status) throws RemoteException;
}
