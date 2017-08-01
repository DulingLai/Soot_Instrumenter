package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.api.proxy.ProxyGrpcRequest;
import com.google.android.gms.auth.api.proxy.ProxyRequest;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzne extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zzne {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zzne {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public void zza(zznd $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.api.internal.IAuthService");
                    $r2.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    this.zzahn.transact(3, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void zza(zznd $r1, ProxyGrpcRequest $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.auth.api.internal.IAuthService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    if ($r2 != null) {
                        $r3.writeInt(1);
                        $r2.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    this.zzahn.transact(2, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void zza(zznd $r1, ProxyRequest $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.google.android.gms.auth.api.internal.IAuthService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
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
        }

        public static zzne zzcu(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.auth.api.internal.IAuthService");
            return ($r1 == null || !($r1 instanceof zzne)) ? new zza($r0) : (zzne) $r1;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            AbstractSafeParcelable $r3 = null;
            zznd $r5;
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.auth.api.internal.IAuthService");
                    $r5 = com.google.android.gms.internal.zznd.zza.zzct($r1.readStrongBinder());
                    if ($r1.readInt() != 0) {
                        $r3 = (ProxyRequest) ProxyRequest.CREATOR.createFromParcel($r1);
                    }
                    zza($r5, (ProxyRequest) $r3);
                    $r2.writeNoException();
                    return true;
                case 2:
                    $r1.enforceInterface("com.google.android.gms.auth.api.internal.IAuthService");
                    $r5 = com.google.android.gms.internal.zznd.zza.zzct($r1.readStrongBinder());
                    if ($r1.readInt() != 0) {
                        $r3 = (ProxyGrpcRequest) ProxyGrpcRequest.CREATOR.createFromParcel($r1);
                    }
                    zza($r5, (ProxyGrpcRequest) $r3);
                    $r2.writeNoException();
                    return true;
                case 3:
                    $r1.enforceInterface("com.google.android.gms.auth.api.internal.IAuthService");
                    zza(com.google.android.gms.internal.zznd.zza.zzct($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.auth.api.internal.IAuthService");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void zza(zznd com_google_android_gms_internal_zznd) throws RemoteException;

    void zza(zznd com_google_android_gms_internal_zznd, ProxyGrpcRequest proxyGrpcRequest) throws RemoteException;

    void zza(zznd com_google_android_gms_internal_zznd, ProxyRequest proxyRequest) throws RemoteException;
}
