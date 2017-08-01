package com.abaltatech.protocoldispatcher;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPProtocolDispatcherPrivateNotification extends IInterface {

    public static abstract class Stub extends Binder implements IPProtocolDispatcherPrivateNotification {
        private static final String DESCRIPTOR = "com.abaltatech.protocoldispatcher.IPProtocolDispatcherPrivateNotification";
        static final int TRANSACTION_onControlChannelClosed = 2;
        static final int TRANSACTION_onControlChannelReady = 1;
        static final int TRANSACTION_onControlMessageReceived = 3;
        static final int TRANSACTION_onVideoChannelError = 4;

        private static class Proxy implements IPProtocolDispatcherPrivateNotification {
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

            public void onControlChannelReady() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }

            public void onControlChannelClosed() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }

            public void onControlMessageReceived(byte[] $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeByteArray($r1);
                    this.mRemote.transact(3, $r2, null, 1);
                } finally {
                    $r2.recycle();
                }
            }

            public void onVideoChannelError(EVideoChannelError $r1, int $i0, String $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken(Stub.DESCRIPTOR);
                    if ($r1 != null) {
                        $r3.writeInt(1);
                        $r1.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    $r3.writeInt($i0);
                    $r3.writeString($r2);
                    this.mRemote.transact(4, $r3, null, 1);
                } finally {
                    $r3.recycle();
                }
            }
        }

        public Stub() throws  {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPProtocolDispatcherPrivateNotification asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface(DESCRIPTOR);
            if ($r1 == null || !($r1 instanceof IPProtocolDispatcherPrivateNotification)) {
                return new Proxy($r0);
            }
            return (IPProtocolDispatcherPrivateNotification) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface(DESCRIPTOR);
                    onControlChannelReady();
                    return true;
                case 2:
                    $r1.enforceInterface(DESCRIPTOR);
                    onControlChannelClosed();
                    return true;
                case 3:
                    $r1.enforceInterface(DESCRIPTOR);
                    onControlMessageReceived($r1.createByteArray());
                    return true;
                case 4:
                    EVideoChannelError $r6;
                    $r1.enforceInterface(DESCRIPTOR);
                    if ($r1.readInt() != 0) {
                        $r6 = (EVideoChannelError) EVideoChannelError.CREATOR.createFromParcel($r1);
                    } else {
                        $r6 = null;
                    }
                    onVideoChannelError($r6, $r1.readInt(), $r1.readString());
                    return true;
                case 1598968902:
                    $r2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void onControlChannelClosed() throws RemoteException;

    void onControlChannelReady() throws RemoteException;

    void onControlMessageReceived(byte[] bArr) throws RemoteException;

    void onVideoChannelError(EVideoChannelError eVideoChannelError, int i, String str) throws RemoteException;
}
