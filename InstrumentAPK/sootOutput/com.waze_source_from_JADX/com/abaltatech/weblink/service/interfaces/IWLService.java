package com.abaltatech.weblink.service.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IWLService extends IInterface {

    public static abstract class Stub extends Binder implements IWLService {
        private static final String DESCRIPTOR = "com.abaltatech.weblink.service.interfaces.IWLService";
        static final int TRANSACTION_getClientFeatures = 4;
        static final int TRANSACTION_getClientFeaturesString = 5;
        static final int TRANSACTION_registerApplication = 1;
        static final int TRANSACTION_showWaitIndicator = 3;
        static final int TRANSACTION_unregisterApplication = 2;

        private static class Proxy implements IWLService {
            private IBinder mRemote;

            public String getInterfaceDescriptor() throws  {
                return "com.abaltatech.weblink.service.interfaces.IWLService";
            }

            Proxy(IBinder $r1) throws  {
                this.mRemote = $r1;
            }

            public IBinder asBinder() throws  {
                return this.mRemote;
            }

            public IWLApp registerApplication(IWLAppNotification $r1, String $r2, int $i0, String $r3) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLService");
                    $r4.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r4.writeString($r2);
                    $r4.writeInt($i0);
                    $r4.writeString($r3);
                    this.mRemote.transact(1, $r4, $r5, 0);
                    $r5.readException();
                    IWLApp $r7 = com.abaltatech.weblink.service.interfaces.IWLApp.Stub.asInterface($r5.readStrongBinder());
                    return $r7;
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public void unregisterApplication(IWLAppNotification $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLService");
                    $r2.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    this.mRemote.transact(2, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void showWaitIndicator(boolean $z0) throws RemoteException {
                byte $b0 = (byte) 1;
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLService");
                    if (!$z0) {
                        $b0 = (byte) 0;
                    }
                    $r1.writeInt($b0);
                    this.mRemote.transact(3, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }

            public int getClientFeatures() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLService");
                    this.mRemote.transact(4, $r1, $r2, 0);
                    $r2.readException();
                    int $i0 = $r2.readInt();
                    return $i0;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public String getClientFeaturesString() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLService");
                    this.mRemote.transact(5, $r1, $r2, 0);
                    $r2.readException();
                    String $r4 = $r2.readString();
                    return $r4;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }
        }

        public Stub() throws  {
            attachInterface(this, "com.abaltatech.weblink.service.interfaces.IWLService");
        }

        public static IWLService asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.abaltatech.weblink.service.interfaces.IWLService");
            if ($r1 == null || !($r1 instanceof IWLService)) {
                return new Proxy($r0);
            }
            return (IWLService) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLService");
                    IWLApp $r7 = registerApplication(com.abaltatech.weblink.service.interfaces.IWLAppNotification.Stub.asInterface($r1.readStrongBinder()), $r1.readString(), $r1.readInt(), $r1.readString());
                    $r2.writeNoException();
                    $r2.writeStrongBinder($r7 != null ? $r7.asBinder() : null);
                    return true;
                case 2:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLService");
                    unregisterApplication(com.abaltatech.weblink.service.interfaces.IWLAppNotification.Stub.asInterface($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 3:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLService");
                    showWaitIndicator($r1.readInt() != 0);
                    return true;
                case 4:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLService");
                    $i0 = getClientFeatures();
                    $r2.writeNoException();
                    $r2.writeInt($i0);
                    return true;
                case 5:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLService");
                    String $r5 = getClientFeaturesString();
                    $r2.writeNoException();
                    $r2.writeString($r5);
                    return true;
                case 1598968902:
                    $r2.writeString("com.abaltatech.weblink.service.interfaces.IWLService");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    int getClientFeatures() throws RemoteException;

    String getClientFeaturesString() throws RemoteException;

    IWLApp registerApplication(IWLAppNotification iWLAppNotification, String str, int i, String str2) throws RemoteException;

    void showWaitIndicator(boolean z) throws RemoteException;

    void unregisterApplication(IWLAppNotification iWLAppNotification) throws RemoteException;
}
