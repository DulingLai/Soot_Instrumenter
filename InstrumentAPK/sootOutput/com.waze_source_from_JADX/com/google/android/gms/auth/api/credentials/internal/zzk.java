package com.google.android.gms.auth.api.credentials.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.common.api.Status;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzk extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzk {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzk {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public void zza(Status $r1, Credential $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.auth.api.credentials.internal.ICredentialsCallbacks");
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
                    this.zzahn.transact(1, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zza(Status $r1, String $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.auth.api.credentials.internal.ICredentialsCallbacks");
                    if ($r1 != null) {
                        $r3.writeInt(1);
                        $r1.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    $r3.writeString($r2);
                    this.zzahn.transact(3, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zzj(Status $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.api.credentials.internal.ICredentialsCallbacks");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(2, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }
        }

        public zza() throws  {
            attachInterface(this, "com.google.android.gms.auth.api.credentials.internal.ICredentialsCallbacks");
        }

        public static zzk zzcq(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.auth.api.credentials.internal.ICredentialsCallbacks");
            return ($r1 == null || !($r1 instanceof zzk)) ? new zza($r0) : (zzk) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.auth.api.credentials.internal.ICredentialsCallbacks");
                    zza($r1.readInt() != 0 ? (Status) Status.CREATOR.createFromParcel($r1) : null, $r1.readInt() != 0 ? (Credential) Credential.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.gms.auth.api.credentials.internal.ICredentialsCallbacks");
                    zzj($r1.readInt() != 0 ? (Status) Status.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 3:
                    $r1.enforceInterface("com.google.android.gms.auth.api.credentials.internal.ICredentialsCallbacks");
                    zza($r1.readInt() != 0 ? (Status) Status.CREATOR.createFromParcel($r1) : null, $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.auth.api.credentials.internal.ICredentialsCallbacks");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void zza(Status status, Credential credential) throws RemoteException;

    void zza(Status status, String str) throws RemoteException;

    void zzj(Status status) throws RemoteException;
}
