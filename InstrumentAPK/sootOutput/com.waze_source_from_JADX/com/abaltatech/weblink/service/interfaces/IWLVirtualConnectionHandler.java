package com.abaltatech.weblink.service.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IWLVirtualConnectionHandler extends IInterface {

    public static abstract class Stub extends Binder implements IWLVirtualConnectionHandler {
        private static final String DESCRIPTOR = "com.abaltatech.weblink.service.interfaces.IWLVirtualConnectionHandler";
        static final int TRANSACTION_closeVirtualConnection = 4;
        static final int TRANSACTION_registerNotification = 1;
        static final int TRANSACTION_sendVirtualConnectionData = 3;
        static final int TRANSACTION_unregisterNotification = 2;

        private static class Proxy implements IWLVirtualConnectionHandler {
            private IBinder mRemote;

            public String getInterfaceDescriptor() throws  {
                return "com.abaltatech.weblink.service.interfaces.IWLVirtualConnectionHandler";
            }

            Proxy(IBinder $r1) throws  {
                this.mRemote = $r1;
            }

            public IBinder asBinder() throws  {
                return this.mRemote;
            }

            public void registerNotification(IWLVirtualConnectionNotification $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLVirtualConnectionHandler");
                    $r2.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    this.mRemote.transact(1, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void unregisterNotification(IWLVirtualConnectionNotification $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLVirtualConnectionHandler");
                    $r2.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    this.mRemote.transact(2, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public boolean sendVirtualConnectionData(byte[] $r1, byte[] $r2, byte[] $r3) throws RemoteException {
                boolean $z0 = false;
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLVirtualConnectionHandler");
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

            public void closeVirtualConnection(byte[] $r1, byte[] $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLVirtualConnectionHandler");
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
            attachInterface(this, "com.abaltatech.weblink.service.interfaces.IWLVirtualConnectionHandler");
        }

        public static IWLVirtualConnectionHandler asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.abaltatech.weblink.service.interfaces.IWLVirtualConnectionHandler");
            if ($r1 == null || !($r1 instanceof IWLVirtualConnectionHandler)) {
                return new Proxy($r0);
            }
            return (IWLVirtualConnectionHandler) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLVirtualConnectionHandler");
                    registerNotification(com.abaltatech.weblink.service.interfaces.IWLVirtualConnectionNotification.Stub.asInterface($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 2:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLVirtualConnectionHandler");
                    unregisterNotification(com.abaltatech.weblink.service.interfaces.IWLVirtualConnectionNotification.Stub.asInterface($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 3:
                    byte $b2;
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLVirtualConnectionHandler");
                    boolean $z0 = sendVirtualConnectionData($r1.createByteArray(), $r1.createByteArray(), $r1.createByteArray());
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    } else {
                        $b2 = (byte) 0;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 4:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLVirtualConnectionHandler");
                    closeVirtualConnection($r1.createByteArray(), $r1.createByteArray());
                    $r2.writeNoException();
                    return true;
                case 1598968902:
                    $r2.writeString("com.abaltatech.weblink.service.interfaces.IWLVirtualConnectionHandler");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void closeVirtualConnection(byte[] bArr, byte[] bArr2) throws RemoteException;

    void registerNotification(IWLVirtualConnectionNotification iWLVirtualConnectionNotification) throws RemoteException;

    boolean sendVirtualConnectionData(byte[] bArr, byte[] bArr2, byte[] bArr3) throws RemoteException;

    void unregisterNotification(IWLVirtualConnectionNotification iWLVirtualConnectionNotification) throws RemoteException;
}
