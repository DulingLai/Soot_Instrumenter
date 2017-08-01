package com.abaltatech.weblink.service.interfaces;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IWLVehicleDataReceiver extends IInterface {

    public static abstract class Stub extends Binder implements IWLVehicleDataReceiver {
        private static final String DESCRIPTOR = "com.abaltatech.weblink.service.interfaces.IWLVehicleDataReceiver";
        static final int TRANSACTION_onDataAvailable = 1;

        private static class Proxy implements IWLVehicleDataReceiver {
            private IBinder mRemote;

            public String getInterfaceDescriptor() throws  {
                return "com.abaltatech.weblink.service.interfaces.IWLVehicleDataReceiver";
            }

            Proxy(IBinder $r1) throws  {
                this.mRemote = $r1;
            }

            public IBinder asBinder() throws  {
                return this.mRemote;
            }

            public void onDataAvailable(Bundle $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLVehicleDataReceiver");
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
            attachInterface(this, "com.abaltatech.weblink.service.interfaces.IWLVehicleDataReceiver");
        }

        public static IWLVehicleDataReceiver asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.abaltatech.weblink.service.interfaces.IWLVehicleDataReceiver");
            if ($r1 == null || !($r1 instanceof IWLVehicleDataReceiver)) {
                return new Proxy($r0);
            }
            return (IWLVehicleDataReceiver) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    Bundle $r5;
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLVehicleDataReceiver");
                    if ($r1.readInt() != 0) {
                        $r5 = (Bundle) Bundle.CREATOR.createFromParcel($r1);
                    } else {
                        $r5 = null;
                    }
                    onDataAvailable($r5);
                    return true;
                case 1598968902:
                    $r2.writeString("com.abaltatech.weblink.service.interfaces.IWLVehicleDataReceiver");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void onDataAvailable(Bundle bundle) throws RemoteException;
}
