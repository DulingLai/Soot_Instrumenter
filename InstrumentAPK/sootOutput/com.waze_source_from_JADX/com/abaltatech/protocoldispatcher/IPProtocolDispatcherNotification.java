package com.abaltatech.protocoldispatcher;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPProtocolDispatcherNotification extends IInterface {

    public static abstract class Stub extends Binder implements IPProtocolDispatcherNotification {
        private static final String DESCRIPTOR = "com.abaltatech.protocoldispatcher.IPProtocolDispatcherNotification";
        static final int TRANSACTION_onApplicationActivated = 1;
        static final int TRANSACTION_onApplicationDeactivated = 2;
        static final int TRANSACTION_onApplicationPaused = 3;
        static final int TRANSACTION_onApplicationResumed = 4;
        static final int TRANSACTION_onLoggingChanged = 7;
        static final int TRANSACTION_onVideoChannelClosed = 6;
        static final int TRANSACTION_onVideoChannelReady = 5;

        private static class Proxy implements IPProtocolDispatcherNotification {
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

            public void onApplicationActivated(int $i0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeInt($i0);
                    this.mRemote.transact(1, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }

            public void onApplicationDeactivated() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }

            public void onApplicationPaused() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }

            public void onApplicationResumed() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }

            public void onVideoChannelReady() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }

            public void onVideoChannelClosed() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }

            public void onLoggingChanged(boolean $z0) throws RemoteException {
                byte $b0 = (byte) 1;
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!$z0) {
                        $b0 = (byte) 0;
                    }
                    $r1.writeInt($b0);
                    this.mRemote.transact(7, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }
        }

        public Stub() throws  {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPProtocolDispatcherNotification asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface(DESCRIPTOR);
            if ($r1 == null || !($r1 instanceof IPProtocolDispatcherNotification)) {
                return new Proxy($r0);
            }
            return (IPProtocolDispatcherNotification) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface(DESCRIPTOR);
                    onApplicationActivated($r1.readInt());
                    return true;
                case 2:
                    $r1.enforceInterface(DESCRIPTOR);
                    onApplicationDeactivated();
                    return true;
                case 3:
                    $r1.enforceInterface(DESCRIPTOR);
                    onApplicationPaused();
                    return true;
                case 4:
                    $r1.enforceInterface(DESCRIPTOR);
                    onApplicationResumed();
                    return true;
                case 5:
                    $r1.enforceInterface(DESCRIPTOR);
                    onVideoChannelReady();
                    return true;
                case 6:
                    $r1.enforceInterface(DESCRIPTOR);
                    onVideoChannelClosed();
                    return true;
                case 7:
                    $r1.enforceInterface(DESCRIPTOR);
                    onLoggingChanged($r1.readInt() != 0);
                    return true;
                case 1598968902:
                    $r2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void onApplicationActivated(int i) throws RemoteException;

    void onApplicationDeactivated() throws RemoteException;

    void onApplicationPaused() throws RemoteException;

    void onApplicationResumed() throws RemoteException;

    void onLoggingChanged(boolean z) throws RemoteException;

    void onVideoChannelClosed() throws RemoteException;

    void onVideoChannelReady() throws RemoteException;
}
