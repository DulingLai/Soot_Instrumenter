package com.google.android.now;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface INowAuthService extends IInterface {

    public static abstract class Stub extends Binder implements INowAuthService {
        private static final String DESCRIPTOR = "com.google.android.now.INowAuthService";
        static final int TRANSACTION_getAuthCode = 2;

        private static class Proxy implements INowAuthService {
            private IBinder mRemote;

            public String getInterfaceDescriptor() throws  {
                return Stub.DESCRIPTOR;
            }

            Proxy(IBinder $r1) throws  {
                this.mRemote = $r1;
            }

            public IBinder asBinder() throws  {
                return this.mRemote;
            }

            public Bundle getAuthCode(String $r1, String $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    Bundle $r8;
                    $r3.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r3.writeString($r1);
                    $r3.writeString($r2);
                    this.mRemote.transact(2, $r3, $r4, 0);
                    $r4.readException();
                    if ($r4.readInt() != 0) {
                        $r8 = (Bundle) Bundle.CREATOR.createFromParcel($r4);
                    } else {
                        $r8 = null;
                    }
                    $r4.recycle();
                    $r3.recycle();
                    return $r8;
                } catch (Throwable th) {
                    $r4.recycle();
                    $r3.recycle();
                }
            }
        }

        public Stub() throws  {
            attachInterface(this, DESCRIPTOR);
        }

        public static INowAuthService asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface(DESCRIPTOR);
            if ($r1 == null || !($r1 instanceof INowAuthService)) {
                return new Proxy($r0);
            }
            return (INowAuthService) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 2:
                    $r1.enforceInterface(DESCRIPTOR);
                    Bundle $r5 = getAuthCode($r1.readString(), $r1.readString());
                    $r2.writeNoException();
                    if ($r5 != null) {
                        $r2.writeInt(1);
                        $r5.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 1598968902:
                    $r2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    Bundle getAuthCode(String str, String str2) throws RemoteException;
}
