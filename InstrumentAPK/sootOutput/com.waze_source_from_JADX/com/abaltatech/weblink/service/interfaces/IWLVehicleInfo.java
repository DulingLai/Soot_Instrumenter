package com.abaltatech.weblink.service.interfaces;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IWLVehicleInfo extends IInterface {

    public static abstract class Stub extends Binder implements IWLVehicleInfo {
        private static final String DESCRIPTOR = "com.abaltatech.weblink.service.interfaces.IWLVehicleInfo";
        static final int TRANSACTION_getVehicleData = 1;
        static final int TRANSACTION_isVehicleDataAvailable = 2;
        static final int TRANSACTION_subscribe = 3;
        static final int TRANSACTION_unsubscribe = 4;

        private static class Proxy implements IWLVehicleInfo {
            private IBinder mRemote;

            public String getInterfaceDescriptor() throws  {
                return "com.abaltatech.weblink.service.interfaces.IWLVehicleInfo";
            }

            Proxy(IBinder $r1) throws  {
                this.mRemote = $r1;
            }

            public IBinder asBinder() throws  {
                return this.mRemote;
            }

            public Bundle getVehicleData(String $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    Bundle $r7;
                    $r2.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLVehicleInfo");
                    $r2.writeString($r1);
                    this.mRemote.transact(1, $r2, $r3, 0);
                    $r3.readException();
                    if ($r3.readInt() != 0) {
                        $r7 = (Bundle) Bundle.CREATOR.createFromParcel($r3);
                    } else {
                        $r7 = null;
                    }
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public int isVehicleDataAvailable(String $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLVehicleInfo");
                    $r2.writeString($r1);
                    this.mRemote.transact(2, $r2, $r3, 0);
                    $r3.readException();
                    int $i0 = $r3.readInt();
                    return $i0;
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void subscribe(IWLVehicleDataAvailableCallback $r1) throws RemoteException {
                IBinder $r2 = null;
                Parcel $r3 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLVehicleInfo");
                    if ($r1 != null) {
                        $r2 = $r1.asBinder();
                    }
                    $r3.writeStrongBinder($r2);
                    this.mRemote.transact(3, $r3, null, 1);
                } finally {
                    $r3.recycle();
                }
            }

            public void unsubscribe(IWLVehicleDataAvailableCallback $r1) throws RemoteException {
                IBinder $r2 = null;
                Parcel $r3 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLVehicleInfo");
                    if ($r1 != null) {
                        $r2 = $r1.asBinder();
                    }
                    $r3.writeStrongBinder($r2);
                    this.mRemote.transact(4, $r3, null, 1);
                } finally {
                    $r3.recycle();
                }
            }
        }

        public Stub() throws  {
            attachInterface(this, "com.abaltatech.weblink.service.interfaces.IWLVehicleInfo");
        }

        public static IWLVehicleInfo asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.abaltatech.weblink.service.interfaces.IWLVehicleInfo");
            if ($r1 == null || !($r1 instanceof IWLVehicleInfo)) {
                return new Proxy($r0);
            }
            return (IWLVehicleInfo) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLVehicleInfo");
                    Bundle $r4 = getVehicleData($r1.readString());
                    $r2.writeNoException();
                    if ($r4 != null) {
                        $r2.writeInt(1);
                        $r4.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 2:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLVehicleInfo");
                    $i0 = isVehicleDataAvailable($r1.readString());
                    $r2.writeNoException();
                    $r2.writeInt($i0);
                    return true;
                case 3:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLVehicleInfo");
                    subscribe(com.abaltatech.weblink.service.interfaces.IWLVehicleDataAvailableCallback.Stub.asInterface($r1.readStrongBinder()));
                    return true;
                case 4:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLVehicleInfo");
                    unsubscribe(com.abaltatech.weblink.service.interfaces.IWLVehicleDataAvailableCallback.Stub.asInterface($r1.readStrongBinder()));
                    return true;
                case 1598968902:
                    $r2.writeString("com.abaltatech.weblink.service.interfaces.IWLVehicleInfo");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    Bundle getVehicleData(String str) throws RemoteException;

    int isVehicleDataAvailable(String str) throws RemoteException;

    void subscribe(IWLVehicleDataAvailableCallback iWLVehicleDataAvailableCallback) throws RemoteException;

    void unsubscribe(IWLVehicleDataAvailableCallback iWLVehicleDataAvailableCallback) throws RemoteException;
}
