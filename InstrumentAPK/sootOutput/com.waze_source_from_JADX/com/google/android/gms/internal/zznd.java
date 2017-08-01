package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.api.proxy.ProxyResponse;

/* compiled from: dalvik_source_com.waze.apk */
public interface zznd extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zznd {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zznd {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public void zza(ProxyResponse $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.api.internal.IAuthCallbacks");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(1, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void zzeo(String $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.api.internal.IAuthCallbacks");
                    $r2.writeString($r1);
                    this.zzahn.transact(2, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }
        }

        public zza() throws  {
            attachInterface(this, "com.google.android.gms.auth.api.internal.IAuthCallbacks");
        }

        public static zznd zzct(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.auth.api.internal.IAuthCallbacks");
            return ($r1 == null || !($r1 instanceof zznd)) ? new zza($r0) : (zznd) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.auth.api.internal.IAuthCallbacks");
                    zza($r1.readInt() != 0 ? (ProxyResponse) ProxyResponse.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.gms.auth.api.internal.IAuthCallbacks");
                    zzeo($r1.readString());
                    $r2.writeNoException();
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.auth.api.internal.IAuthCallbacks");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void zza(ProxyResponse proxyResponse) throws RemoteException;

    void zzeo(String str) throws RemoteException;
}
