package com.abaltatech.weblink.service.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IWLAppsServiceHandler extends IInterface {

    public static abstract class Stub extends Binder implements IWLAppsServiceHandler {
        private static final String DESCRIPTOR = "com.abaltatech.weblink.service.interfaces.IWLAppsServiceHandler";
        static final int TRANSACTION_cancelRequest = 2;
        static final int TRANSACTION_registerForNotification = 3;
        static final int TRANSACTION_sendRequest = 1;
        static final int TRANSACTION_unregisterFromNotification = 4;

        private static class Proxy implements IWLAppsServiceHandler {
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

            public int sendRequest(String $r1, String $r2, boolean $z0, byte[] $r3, IWLRequestSuccessCallback $r4, IWLServiceErrorCallback $r5) throws RemoteException {
                IBinder $r6 = null;
                byte $b0 = (byte) 1;
                Parcel $r7 = Parcel.obtain();
                Parcel $r8 = Parcel.obtain();
                try {
                    IBinder $r9;
                    $r7.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r7.writeString($r1);
                    $r7.writeString($r2);
                    if (!$z0) {
                        $b0 = (byte) 0;
                    }
                    $r7.writeInt($b0);
                    $r7.writeByteArray($r3);
                    if ($r4 != null) {
                        $r9 = $r4.asBinder();
                    } else {
                        $r9 = null;
                    }
                    $r7.writeStrongBinder($r9);
                    if ($r5 != null) {
                        $r6 = $r5.asBinder();
                    }
                    $r7.writeStrongBinder($r6);
                    this.mRemote.transact(1, $r7, $r8, 0);
                    $r8.readException();
                    int $i1 = $r8.readInt();
                    return $i1;
                } finally {
                    $r8.recycle();
                    $r7.recycle();
                }
            }

            public boolean cancelRequest(int $i0) throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeInt($i0);
                    this.mRemote.transact(2, $r1, $r2, 0);
                    $r2.readException();
                    if ($r2.readInt() != 0) {
                        $z0 = true;
                    }
                    $r2.recycle();
                    $r1.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void registerForNotification(String $r1, IWLAppsServiceNotificationHandler $r2) throws RemoteException {
                IBinder $r3 = null;
                Parcel $r4 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r4.writeString($r1);
                    if ($r2 != null) {
                        $r3 = $r2.asBinder();
                    }
                    $r4.writeStrongBinder($r3);
                    this.mRemote.transact(3, $r4, null, 1);
                } finally {
                    $r4.recycle();
                }
            }

            public void unregisterFromNotification(String $r1, IWLAppsServiceNotificationHandler $r2) throws RemoteException {
                IBinder $r3 = null;
                Parcel $r4 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r4.writeString($r1);
                    if ($r2 != null) {
                        $r3 = $r2.asBinder();
                    }
                    $r4.writeStrongBinder($r3);
                    this.mRemote.transact(4, $r4, null, 1);
                } finally {
                    $r4.recycle();
                }
            }
        }

        public Stub() throws  {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWLAppsServiceHandler asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface(DESCRIPTOR);
            if ($r1 == null || !($r1 instanceof IWLAppsServiceHandler)) {
                return new Proxy($r0);
            }
            return (IWLAppsServiceHandler) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            boolean $z0 = false;
            switch ($i0) {
                case 1:
                    $r1.enforceInterface(DESCRIPTOR);
                    String $r3 = $r1.readString();
                    String $r4 = $r1.readString();
                    if ($r1.readInt() != 0) {
                        $z0 = true;
                    }
                    $i0 = sendRequest($r3, $r4, $z0, $r1.createByteArray(), com.abaltatech.weblink.service.interfaces.IWLRequestSuccessCallback.Stub.asInterface($r1.readStrongBinder()), com.abaltatech.weblink.service.interfaces.IWLServiceErrorCallback.Stub.asInterface($r1.readStrongBinder()));
                    $r2.writeNoException();
                    $r2.writeInt($i0);
                    return true;
                case 2:
                    $r1.enforceInterface(DESCRIPTOR);
                    boolean $z1 = cancelRequest($r1.readInt());
                    $r2.writeNoException();
                    if ($z1) {
                        $z0 = true;
                    }
                    $r2.writeInt($z0);
                    return true;
                case 3:
                    $r1.enforceInterface(DESCRIPTOR);
                    registerForNotification($r1.readString(), com.abaltatech.weblink.service.interfaces.IWLAppsServiceNotificationHandler.Stub.asInterface($r1.readStrongBinder()));
                    return true;
                case 4:
                    $r1.enforceInterface(DESCRIPTOR);
                    unregisterFromNotification($r1.readString(), com.abaltatech.weblink.service.interfaces.IWLAppsServiceNotificationHandler.Stub.asInterface($r1.readStrongBinder()));
                    return true;
                case 1598968902:
                    $r2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    boolean cancelRequest(int i) throws RemoteException;

    void registerForNotification(String str, IWLAppsServiceNotificationHandler iWLAppsServiceNotificationHandler) throws RemoteException;

    int sendRequest(String str, String str2, boolean z, byte[] bArr, IWLRequestSuccessCallback iWLRequestSuccessCallback, IWLServiceErrorCallback iWLServiceErrorCallback) throws RemoteException;

    void unregisterFromNotification(String str, IWLAppsServiceNotificationHandler iWLAppsServiceNotificationHandler) throws RemoteException;
}
