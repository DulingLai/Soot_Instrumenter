package com.abaltatech.weblink.service.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IWLServicePrivateNotification extends IInterface {

    public static abstract class Stub extends Binder implements IWLServicePrivateNotification {
        private static final String DESCRIPTOR = "com.abaltatech.weblink.service.interfaces.IWLServicePrivateNotification";
        static final int TRANSACTION_onActivationRequested = 1;
        static final int TRANSACTION_onApplicationActivated = 6;
        static final int TRANSACTION_onApplicationDeactivated = 7;
        static final int TRANSACTION_onApplicationDied = 8;
        static final int TRANSACTION_onApplicationRegistered = 4;
        static final int TRANSACTION_onApplicationUnregistered = 5;
        static final int TRANSACTION_onConnectionClosed = 3;
        static final int TRANSACTION_onConnectionEstablished = 2;

        private static class Proxy implements IWLServicePrivateNotification {
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

            public boolean onActivationRequested(int $i0) throws RemoteException {
                boolean $z0 = true;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeInt($i0);
                    this.mRemote.transact(1, $r1, $r2, 0);
                    $r2.readException();
                    if ($r2.readInt() == 0) {
                        $z0 = false;
                    }
                    $r2.recycle();
                    $r1.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void onConnectionEstablished() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }

            public void onConnectionClosed() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }

            public void onApplicationRegistered(int $i0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeInt($i0);
                    this.mRemote.transact(4, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }

            public void onApplicationUnregistered(int $i0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeInt($i0);
                    this.mRemote.transact(5, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }

            public void onApplicationActivated(int $i0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeInt($i0);
                    this.mRemote.transact(6, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }

            public void onApplicationDeactivated(int $i0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeInt($i0);
                    this.mRemote.transact(7, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }

            public void onApplicationDied(int $i0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeInt($i0);
                    this.mRemote.transact(8, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }
        }

        public Stub() throws  {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWLServicePrivateNotification asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface(DESCRIPTOR);
            if ($r1 == null || !($r1 instanceof IWLServicePrivateNotification)) {
                return new Proxy($r0);
            }
            return (IWLServicePrivateNotification) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    byte $b2;
                    $r1.enforceInterface(DESCRIPTOR);
                    boolean $z0 = onActivationRequested($r1.readInt());
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    } else {
                        $b2 = (byte) 0;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 2:
                    $r1.enforceInterface(DESCRIPTOR);
                    onConnectionEstablished();
                    return true;
                case 3:
                    $r1.enforceInterface(DESCRIPTOR);
                    onConnectionClosed();
                    return true;
                case 4:
                    $r1.enforceInterface(DESCRIPTOR);
                    onApplicationRegistered($r1.readInt());
                    return true;
                case 5:
                    $r1.enforceInterface(DESCRIPTOR);
                    onApplicationUnregistered($r1.readInt());
                    return true;
                case 6:
                    $r1.enforceInterface(DESCRIPTOR);
                    onApplicationActivated($r1.readInt());
                    return true;
                case 7:
                    $r1.enforceInterface(DESCRIPTOR);
                    onApplicationDeactivated($r1.readInt());
                    return true;
                case 8:
                    $r1.enforceInterface(DESCRIPTOR);
                    onApplicationDied($r1.readInt());
                    return true;
                case 1598968902:
                    $r2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    boolean onActivationRequested(int i) throws RemoteException;

    void onApplicationActivated(int i) throws RemoteException;

    void onApplicationDeactivated(int i) throws RemoteException;

    void onApplicationDied(int i) throws RemoteException;

    void onApplicationRegistered(int i) throws RemoteException;

    void onApplicationUnregistered(int i) throws RemoteException;

    void onConnectionClosed() throws RemoteException;

    void onConnectionEstablished() throws RemoteException;
}
