package com.abaltatech.weblinkserver;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IWLAppManager extends IInterface {

    public static abstract class Stub extends Binder implements IWLAppManager {
        private static final String DESCRIPTOR = "com.abaltatech.weblinkserver.IWLAppManager";
        static final int TRANSACTION_activateApp = 2;
        static final int TRANSACTION_canActivateApp = 1;
        static final int TRANSACTION_getCurrentAppID = 4;
        static final int TRANSACTION_onAppReadyForClient = 3;

        private static class Proxy implements IWLAppManager {
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

            public boolean canActivateApp(String $r1) throws RemoteException {
                boolean $z0 = true;
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeString($r1);
                    this.mRemote.transact(1, $r2, $r3, 0);
                    $r3.readException();
                    if ($r3.readInt() == 0) {
                        $z0 = false;
                    }
                    $r3.recycle();
                    $r2.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public boolean activateApp(String $r1) throws RemoteException {
                boolean $z0 = false;
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeString($r1);
                    this.mRemote.transact(2, $r2, $r3, 0);
                    $r3.readException();
                    if ($r3.readInt() != 0) {
                        $z0 = true;
                    }
                    $r3.recycle();
                    $r2.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void onAppReadyForClient(String $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeString($r1);
                    this.mRemote.transact(3, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public String getCurrentAppID() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, $r1, $r2, 0);
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
            attachInterface(this, DESCRIPTOR);
        }

        public static IWLAppManager asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface(DESCRIPTOR);
            if ($r1 == null || !($r1 instanceof IWLAppManager)) {
                return new Proxy($r0);
            }
            return (IWLAppManager) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            byte $b2 = (byte) 0;
            boolean $z0;
            switch ($i0) {
                case 1:
                    $r1.enforceInterface(DESCRIPTOR);
                    $z0 = canActivateApp($r1.readString());
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 2:
                    $r1.enforceInterface(DESCRIPTOR);
                    $z0 = activateApp($r1.readString());
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 3:
                    $r1.enforceInterface(DESCRIPTOR);
                    onAppReadyForClient($r1.readString());
                    $r2.writeNoException();
                    return true;
                case 4:
                    $r1.enforceInterface(DESCRIPTOR);
                    String $r3 = getCurrentAppID();
                    $r2.writeNoException();
                    $r2.writeString($r3);
                    return true;
                case 1598968902:
                    $r2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    boolean activateApp(String str) throws RemoteException;

    boolean canActivateApp(String str) throws RemoteException;

    String getCurrentAppID() throws RemoteException;

    void onAppReadyForClient(String str) throws RemoteException;
}
