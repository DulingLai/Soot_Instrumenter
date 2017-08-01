package com.abaltatech.weblink.service.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IWLDisplayNotificationListEventListener extends IInterface {

    public static abstract class Stub extends Binder implements IWLDisplayNotificationListEventListener {
        private static final String DESCRIPTOR = "com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationListEventListener";
        static final int TRANSACTION_onNotificationListChanged = 1;

        private static class Proxy implements IWLDisplayNotificationListEventListener {
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

            public void onNotificationListChanged() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }
        }

        public Stub() throws  {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWLDisplayNotificationListEventListener asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface(DESCRIPTOR);
            if ($r1 == null || !($r1 instanceof IWLDisplayNotificationListEventListener)) {
                return new Proxy($r0);
            }
            return (IWLDisplayNotificationListEventListener) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface(DESCRIPTOR);
                    onNotificationListChanged();
                    return true;
                case 1598968902:
                    $r2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void onNotificationListChanged() throws RemoteException;
}
