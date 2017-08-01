package com.abaltatech.weblink.service.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IWLAppDispatcherService extends IInterface {

    public static abstract class Stub extends Binder implements IWLAppDispatcherService {
        private static final String DESCRIPTOR = "com.abaltatech.weblink.service.interfaces.IWLAppDispatcherService";
        static final int TRANSACTION_registerCommandReceiver = 1;
        static final int TRANSACTION_sendCommand = 2;

        private static class Proxy implements IWLAppDispatcherService {
            private IBinder mRemote;

            public String getInterfaceDescriptor() throws  {
                return "com.abaltatech.weblink.service.interfaces.IWLAppDispatcherService";
            }

            Proxy(IBinder $r1) throws  {
                this.mRemote = $r1;
            }

            public IBinder asBinder() throws  {
                return this.mRemote;
            }

            public boolean registerCommandReceiver(IWLAppServiceCommandReceiver $r1, String $r2) throws RemoteException {
                boolean $z0 = true;
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLAppDispatcherService");
                    $r3.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    $r3.writeString($r2);
                    this.mRemote.transact(1, $r3, $r4, 0);
                    $r4.readException();
                    if ($r4.readInt() == 0) {
                        $z0 = false;
                    }
                    $r4.recycle();
                    $r3.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void sendCommand(String $r1, byte[] $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLAppDispatcherService");
                    $r3.writeString($r1);
                    $r3.writeByteArray($r2);
                    this.mRemote.transact(2, $r3, null, 1);
                } finally {
                    $r3.recycle();
                }
            }
        }

        public Stub() throws  {
            attachInterface(this, "com.abaltatech.weblink.service.interfaces.IWLAppDispatcherService");
        }

        public static IWLAppDispatcherService asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.abaltatech.weblink.service.interfaces.IWLAppDispatcherService");
            if ($r1 == null || !($r1 instanceof IWLAppDispatcherService)) {
                return new Proxy($r0);
            }
            return (IWLAppDispatcherService) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    byte $b2;
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLAppDispatcherService");
                    boolean $z0 = registerCommandReceiver(com.abaltatech.weblink.service.interfaces.IWLAppServiceCommandReceiver.Stub.asInterface($r1.readStrongBinder()), $r1.readString());
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    } else {
                        $b2 = (byte) 0;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 2:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLAppDispatcherService");
                    sendCommand($r1.readString(), $r1.createByteArray());
                    return true;
                case 1598968902:
                    $r2.writeString("com.abaltatech.weblink.service.interfaces.IWLAppDispatcherService");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    boolean registerCommandReceiver(IWLAppServiceCommandReceiver iWLAppServiceCommandReceiver, String str) throws RemoteException;

    void sendCommand(String str, byte[] bArr) throws RemoteException;
}
