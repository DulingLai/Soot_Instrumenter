package com.abaltatech.weblink.service.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IWLVirtualConnectionNotification extends IInterface {

    public static abstract class Stub extends Binder implements IWLVirtualConnectionNotification {
        private static final String DESCRIPTOR = "com.abaltatech.weblink.service.interfaces.IWLVirtualConnectionNotification";
        static final int TRANSACTION_onTransportAttached = 1;
        static final int TRANSACTION_onTransportDetached = 2;
        static final int TRANSACTION_onVirtualConnectionClosed = 4;
        static final int TRANSACTION_onVirtualConnectionData = 3;

        private static class Proxy implements IWLVirtualConnectionNotification {
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

            public void onTransportAttached() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void onTransportDetached() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public boolean onVirtualConnectionData(byte[] $r1, byte[] $r2, byte[] $r3) throws RemoteException {
                boolean $z0 = false;
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r4.writeByteArray($r1);
                    $r4.writeByteArray($r2);
                    $r4.writeByteArray($r3);
                    this.mRemote.transact(3, $r4, $r5, 0);
                    $r5.readException();
                    if ($r5.readInt() != 0) {
                        $z0 = true;
                    }
                    $r5.recycle();
                    $r4.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public void onVirtualConnectionClosed(byte[] $r1, byte[] $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r3.writeByteArray($r1);
                    $r3.writeByteArray($r2);
                    this.mRemote.transact(4, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }
        }

        public Stub() throws  {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWLVirtualConnectionNotification asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface(DESCRIPTOR);
            if ($r1 == null || !($r1 instanceof IWLVirtualConnectionNotification)) {
                return new Proxy($r0);
            }
            return (IWLVirtualConnectionNotification) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface(DESCRIPTOR);
                    onTransportAttached();
                    $r2.writeNoException();
                    return true;
                case 2:
                    $r1.enforceInterface(DESCRIPTOR);
                    onTransportDetached();
                    $r2.writeNoException();
                    return true;
                case 3:
                    byte $b2;
                    $r1.enforceInterface(DESCRIPTOR);
                    boolean $z0 = onVirtualConnectionData($r1.createByteArray(), $r1.createByteArray(), $r1.createByteArray());
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    } else {
                        $b2 = (byte) 0;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 4:
                    $r1.enforceInterface(DESCRIPTOR);
                    onVirtualConnectionClosed($r1.createByteArray(), $r1.createByteArray());
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

    void onTransportAttached() throws RemoteException;

    void onTransportDetached() throws RemoteException;

    void onVirtualConnectionClosed(byte[] bArr, byte[] bArr2) throws RemoteException;

    boolean onVirtualConnectionData(byte[] bArr, byte[] bArr2, byte[] bArr3) throws RemoteException;
}
