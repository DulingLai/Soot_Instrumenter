package com.abaltatech.weblink.service.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IWLDisplayNotificationEventListener extends IInterface {

    public static abstract class Stub extends Binder implements IWLDisplayNotificationEventListener {
        private static final String DESCRIPTOR = "com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationEventListener";
        static final int TRANSACTION_onNotificationClicked = 3;
        static final int TRANSACTION_onNotificationDismissed = 2;
        static final int TRANSACTION_onNotificationDisplayed = 1;

        private static class Proxy implements IWLDisplayNotificationEventListener {
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

            public void onNotificationDisplayed(long $l0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeLong($l0);
                    this.mRemote.transact(1, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }

            public void onNotificationDismissed(long $l0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeLong($l0);
                    this.mRemote.transact(2, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }

            public void onNotificationClicked(long $l0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeLong($l0);
                    this.mRemote.transact(3, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }
        }

        public Stub() throws  {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWLDisplayNotificationEventListener asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface(DESCRIPTOR);
            if ($r1 == null || !($r1 instanceof IWLDisplayNotificationEventListener)) {
                return new Proxy($r0);
            }
            return (IWLDisplayNotificationEventListener) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface(DESCRIPTOR);
                    onNotificationDisplayed($r1.readLong());
                    return true;
                case 2:
                    $r1.enforceInterface(DESCRIPTOR);
                    onNotificationDismissed($r1.readLong());
                    return true;
                case 3:
                    $r1.enforceInterface(DESCRIPTOR);
                    onNotificationClicked($r1.readLong());
                    return true;
                case 1598968902:
                    $r2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void onNotificationClicked(long j) throws RemoteException;

    void onNotificationDismissed(long j) throws RemoteException;

    void onNotificationDisplayed(long j) throws RemoteException;
}
