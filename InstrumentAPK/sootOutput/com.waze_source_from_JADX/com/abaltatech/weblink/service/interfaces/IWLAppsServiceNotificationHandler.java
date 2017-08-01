package com.abaltatech.weblink.service.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IWLAppsServiceNotificationHandler extends IInterface {

    public static abstract class Stub extends Binder implements IWLAppsServiceNotificationHandler {
        private static final String DESCRIPTOR = "com.abaltatech.weblink.service.interfaces.IWLAppsServiceNotificationHandler";
        static final int TRANSACTION_onNotification = 1;

        private static class Proxy implements IWLAppsServiceNotificationHandler {
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

            public void onNotification(String $r1, byte[] $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r3.writeString($r1);
                    $r3.writeByteArray($r2);
                    this.mRemote.transact(1, $r3, null, 1);
                } finally {
                    $r3.recycle();
                }
            }
        }

        public Stub() throws  {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWLAppsServiceNotificationHandler asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface(DESCRIPTOR);
            if ($r1 == null || !($r1 instanceof IWLAppsServiceNotificationHandler)) {
                return new Proxy($r0);
            }
            return (IWLAppsServiceNotificationHandler) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface(DESCRIPTOR);
                    onNotification($r1.readString(), $r1.createByteArray());
                    return true;
                case 1598968902:
                    $r2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void onNotification(String str, byte[] bArr) throws RemoteException;
}
