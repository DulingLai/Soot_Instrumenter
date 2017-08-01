package com.waze;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IServiceAIDL extends IInterface {

    public static abstract class Stub extends Binder implements IServiceAIDL {
        private static final String DESCRIPTOR = "com.waze.IServiceAIDL";
        static final int TRANSACTION_basicTypes = 3;
        static final int TRANSACTION_getPid = 1;
        static final int TRANSACTION_sendKey = 2;

        private static class Proxy implements IServiceAIDL {
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

            public int getPid() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, $r1, $r2, 0);
                    $r2.readException();
                    int $i0 = $r2.readInt();
                    return $i0;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void sendKey(String $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeString($r1);
                    this.mRemote.transact(2, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void basicTypes(int $i0, long $l1, boolean $z0, float $f0, double $d0, String $r1) throws RemoteException {
                byte $b2 = (byte) 0;
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeInt($i0);
                    $r2.writeLong($l1);
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    $r2.writeFloat($f0);
                    $r2.writeDouble($d0);
                    $r2.writeString($r1);
                    this.mRemote.transact(3, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }
        }

        public Stub() throws  {
            attachInterface(this, DESCRIPTOR);
        }

        public static IServiceAIDL asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface(DESCRIPTOR);
            if ($r1 == null || !($r1 instanceof IServiceAIDL)) {
                return new Proxy($r0);
            }
            return (IServiceAIDL) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface(DESCRIPTOR);
                    $i0 = getPid();
                    $r2.writeNoException();
                    $r2.writeInt($i0);
                    return true;
                case 2:
                    $r1.enforceInterface(DESCRIPTOR);
                    sendKey($r1.readString());
                    $r2.writeNoException();
                    return true;
                case 3:
                    $r1.enforceInterface(DESCRIPTOR);
                    basicTypes($r1.readInt(), $r1.readLong(), $r1.readInt() != 0, $r1.readFloat(), $r1.readDouble(), $r1.readString());
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

    void basicTypes(int i, long j, boolean z, float f, double d, String str) throws RemoteException;

    int getPid() throws RemoteException;

    void sendKey(String str) throws RemoteException;
}
