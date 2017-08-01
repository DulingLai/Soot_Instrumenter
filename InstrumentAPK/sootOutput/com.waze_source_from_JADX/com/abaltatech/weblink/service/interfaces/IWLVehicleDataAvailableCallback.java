package com.abaltatech.weblink.service.interfaces;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IWLVehicleDataAvailableCallback extends IInterface {

    public static abstract class Stub extends Binder implements IWLVehicleDataAvailableCallback {
        private static final String DESCRIPTOR = "com.abaltatech.weblink.service.interfaces.IWLVehicleDataAvailableCallback";
        static final int TRANSACTION_onVehicleDataAvailable = 1;

        private static class Proxy implements IWLVehicleDataAvailableCallback {
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

            public void onVehicleDataAvailable(Bundle $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.mRemote.transact(1, $r2, null, 1);
                } finally {
                    $r2.recycle();
                }
            }
        }

        public Stub() throws  {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWLVehicleDataAvailableCallback asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface(DESCRIPTOR);
            if ($r1 == null || !($r1 instanceof IWLVehicleDataAvailableCallback)) {
                return new Proxy($r0);
            }
            return (IWLVehicleDataAvailableCallback) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    Bundle $r5;
                    $r1.enforceInterface(DESCRIPTOR);
                    if ($r1.readInt() != 0) {
                        $r5 = (Bundle) Bundle.CREATOR.createFromParcel($r1);
                    } else {
                        $r5 = null;
                    }
                    onVehicleDataAvailable($r5);
                    return true;
                case 1598968902:
                    $r2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void onVehicleDataAvailable(Bundle bundle) throws RemoteException;
}
