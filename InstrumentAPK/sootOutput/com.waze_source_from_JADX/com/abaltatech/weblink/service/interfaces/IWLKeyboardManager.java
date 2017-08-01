package com.abaltatech.weblink.service.interfaces;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IWLKeyboardManager extends IInterface {

    public static abstract class Stub extends Binder implements IWLKeyboardManager {
        private static final String DESCRIPTOR = "com.abaltatech.weblink.service.interfaces.IWLKeyboardManager";
        static final int TRANSACTION_getCurrentKeyboard = 4;
        static final int TRANSACTION_getKeyboardName = 2;
        static final int TRANSACTION_getKeyboardSettingsIntent = 3;
        static final int TRANSACTION_getRegisteredKeyboardIDs = 1;
        static final int TRANSACTION_setCurrentKeyboard = 5;

        private static class Proxy implements IWLKeyboardManager {
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

            public String[] getRegisteredKeyboardIDs() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, $r1, $r2, 0);
                    $r2.readException();
                    String[] $r4 = $r2.createStringArray();
                    return $r4;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public String getKeyboardName(String $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeString($r1);
                    this.mRemote.transact(2, $r2, $r3, 0);
                    $r3.readException();
                    $r1 = $r3.readString();
                    return $r1;
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public Intent getKeyboardSettingsIntent(String $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    Intent $r7;
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeString($r1);
                    this.mRemote.transact(3, $r2, $r3, 0);
                    $r3.readException();
                    if ($r3.readInt() != 0) {
                        $r7 = (Intent) Intent.CREATOR.createFromParcel($r3);
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

            public String getCurrentKeyboard() throws RemoteException {
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

            public boolean setCurrentKeyboard(String $r1) throws RemoteException {
                boolean $z0 = false;
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeString($r1);
                    this.mRemote.transact(5, $r2, $r3, 0);
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
        }

        public Stub() throws  {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWLKeyboardManager asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface(DESCRIPTOR);
            if ($r1 == null || !($r1 instanceof IWLKeyboardManager)) {
                return new Proxy($r0);
            }
            return (IWLKeyboardManager) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            byte $b2 = (byte) 0;
            String $r4;
            switch ($i0) {
                case 1:
                    $r1.enforceInterface(DESCRIPTOR);
                    String[] $r3 = getRegisteredKeyboardIDs();
                    $r2.writeNoException();
                    $r2.writeStringArray($r3);
                    return true;
                case 2:
                    $r1.enforceInterface(DESCRIPTOR);
                    $r4 = getKeyboardName($r1.readString());
                    $r2.writeNoException();
                    $r2.writeString($r4);
                    return true;
                case 3:
                    $r1.enforceInterface(DESCRIPTOR);
                    Intent $r5 = getKeyboardSettingsIntent($r1.readString());
                    $r2.writeNoException();
                    if ($r5 != null) {
                        $r2.writeInt(1);
                        $r5.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 4:
                    $r1.enforceInterface(DESCRIPTOR);
                    $r4 = getCurrentKeyboard();
                    $r2.writeNoException();
                    $r2.writeString($r4);
                    return true;
                case 5:
                    $r1.enforceInterface(DESCRIPTOR);
                    boolean $z0 = setCurrentKeyboard($r1.readString());
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 1598968902:
                    $r2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    String getCurrentKeyboard() throws RemoteException;

    String getKeyboardName(String str) throws RemoteException;

    Intent getKeyboardSettingsIntent(String str) throws RemoteException;

    String[] getRegisteredKeyboardIDs() throws RemoteException;

    boolean setCurrentKeyboard(String str) throws RemoteException;
}
