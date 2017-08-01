package com.abaltatech.weblink.service.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IWLAppServiceCommandReceiver extends IInterface {

    public static abstract class Stub extends Binder implements IWLAppServiceCommandReceiver {
        private static final String DESCRIPTOR = "com.abaltatech.weblink.service.interfaces.IWLAppServiceCommandReceiver";
        static final int TRANSACTION_onCommandReceived = 1;

        private static class Proxy implements IWLAppServiceCommandReceiver {
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

            public void onCommandReceived(byte[] $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeByteArray($r1);
                    this.mRemote.transact(1, $r2, null, 1);
                } finally {
                    $r2.recycle();
                }
            }
        }

        public Stub() throws  {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWLAppServiceCommandReceiver asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface(DESCRIPTOR);
            if ($r1 == null || !($r1 instanceof IWLAppServiceCommandReceiver)) {
                return new Proxy($r0);
            }
            return (IWLAppServiceCommandReceiver) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface(DESCRIPTOR);
                    onCommandReceived($r1.createByteArray());
                    return true;
                case 1598968902:
                    $r2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void onCommandReceived(byte[] bArr) throws RemoteException;
}
