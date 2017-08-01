package com.abaltatech.weblink.service.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IWLServiceErrorCallback extends IInterface {

    public static abstract class Stub extends Binder implements IWLServiceErrorCallback {
        private static final String DESCRIPTOR = "com.abaltatech.weblink.service.interfaces.IWLServiceErrorCallback";
        static final int TRANSACTION_onError = 1;

        private static class Proxy implements IWLServiceErrorCallback {
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

            public void onError(int $i0, String $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeInt($i0);
                    $r2.writeString($r1);
                    this.mRemote.transact(1, $r2, null, 1);
                } finally {
                    $r2.recycle();
                }
            }
        }

        public Stub() throws  {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWLServiceErrorCallback asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface(DESCRIPTOR);
            if ($r1 == null || !($r1 instanceof IWLServiceErrorCallback)) {
                return new Proxy($r0);
            }
            return (IWLServiceErrorCallback) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface(DESCRIPTOR);
                    onError($r1.readInt(), $r1.readString());
                    return true;
                case 1598968902:
                    $r2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void onError(int i, String str) throws RemoteException;
}
