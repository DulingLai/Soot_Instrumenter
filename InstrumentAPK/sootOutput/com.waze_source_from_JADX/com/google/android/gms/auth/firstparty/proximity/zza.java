package com.google.android.gms.auth.firstparty.proximity;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.firstparty.proximity.data.AuthorizationRequest;

/* compiled from: dalvik_source_com.waze.apk */
public interface zza extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class zza extends Binder implements zza {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class zza implements zza {
            private IBinder zzahn;

            zza(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public Bundle zza(AuthorizationRequest $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.google.android.gms.auth.firstparty.proximity.IAuthorizationService");
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.zzahn.transact(1, $r2, $r3, 0);
                    $r3.readException();
                    Bundle $r7 = $r3.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel($r3) : null;
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }
        }

        public static zza zzdb(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.auth.firstparty.proximity.IAuthorizationService");
            return ($r1 == null || !($r1 instanceof zza)) ? new zza($r0) : (zza) $r1;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.google.android.gms.auth.firstparty.proximity.IAuthorizationService");
                    Bundle $r6 = zza($r1.readInt() != 0 ? (AuthorizationRequest) AuthorizationRequest.CREATOR.createFromParcel($r1) : null);
                    $r2.writeNoException();
                    if ($r6 != null) {
                        $r2.writeInt(1);
                        $r6.writeToParcel($r2, 1);
                    } else {
                        $r2.writeInt(0);
                    }
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.auth.firstparty.proximity.IAuthorizationService");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    Bundle zza(AuthorizationRequest authorizationRequest) throws RemoteException;
}
