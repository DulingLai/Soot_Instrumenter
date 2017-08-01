package com.abaltatech.protocoldispatcher;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPProtocolDispatcher extends IInterface {

    public static abstract class Stub extends Binder implements IPProtocolDispatcher {
        private static final String DESCRIPTOR = "com.abaltatech.protocoldispatcher.IPProtocolDispatcher";
        static final int TRANSACTION_registerNotificationListener = 1;
        static final int TRANSACTION_setPortNumber = 3;
        static final int TRANSACTION_unregisterNotificationListener = 2;

        private static class Proxy implements IPProtocolDispatcher {
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

            public void registerNotificationListener(IPProtocolDispatcherNotification $r1, int $i0) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r2.writeInt($i0);
                    this.mRemote.transact(1, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void unregisterNotificationListener(IPProtocolDispatcherNotification $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    this.mRemote.transact(2, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void setPortNumber(int $i0, int $i1, int $i2) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeInt($i0);
                    $r1.writeInt($i1);
                    $r1.writeInt($i2);
                    this.mRemote.transact(3, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }
        }

        public Stub() throws  {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPProtocolDispatcher asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface(DESCRIPTOR);
            if ($r1 == null || !($r1 instanceof IPProtocolDispatcher)) {
                return new Proxy($r0);
            }
            return (IPProtocolDispatcher) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface(DESCRIPTOR);
                    registerNotificationListener(com.abaltatech.protocoldispatcher.IPProtocolDispatcherNotification.Stub.asInterface($r1.readStrongBinder()), $r1.readInt());
                    $r2.writeNoException();
                    return true;
                case 2:
                    $r1.enforceInterface(DESCRIPTOR);
                    unregisterNotificationListener(com.abaltatech.protocoldispatcher.IPProtocolDispatcherNotification.Stub.asInterface($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 3:
                    $r1.enforceInterface(DESCRIPTOR);
                    setPortNumber($r1.readInt(), $r1.readInt(), $r1.readInt());
                    $r2.writeNoException();
                    return true;
                case 1598968902:
                    $r2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void registerNotificationListener(IPProtocolDispatcherNotification iPProtocolDispatcherNotification, int i) throws RemoteException;

    void setPortNumber(int i, int i2, int i3) throws RemoteException;

    void unregisterNotificationListener(IPProtocolDispatcherNotification iPProtocolDispatcherNotification) throws RemoteException;
}
