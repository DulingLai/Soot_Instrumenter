package com.abaltatech.weblink.service.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.inputmethod.EditorInfo;

public interface IWLApp extends IInterface {

    public static abstract class Stub extends Binder implements IWLApp {
        private static final String DESCRIPTOR = "com.abaltatech.weblink.service.interfaces.IWLApp";
        static final int TRANSACTION_activateApplicationByAppID = 3;
        static final int TRANSACTION_hideKeyboard = 6;
        static final int TRANSACTION_registerForCommand = 1;
        static final int TRANSACTION_requestActivation = 2;
        static final int TRANSACTION_setKeyboardMode = 4;
        static final int TRANSACTION_setUpdatesEnabled = 7;
        static final int TRANSACTION_showKeyboard = 5;

        private static class Proxy implements IWLApp {
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

            public void registerForCommand(int $i0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeInt($i0);
                    this.mRemote.transact(1, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }

            public void requestActivation() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }

            public boolean activateApplicationByAppID(String $r1) throws RemoteException {
                boolean $z0 = false;
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeString($r1);
                    this.mRemote.transact(3, $r2, $r3, 0);
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

            public void setKeyboardMode(int $i0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeInt($i0);
                    this.mRemote.transact(4, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }

            public void showKeyboard(IWLInputConnection $r1, EditorInfo $r2) throws RemoteException {
                IBinder $r3 = null;
                Parcel $r4 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken(Stub.DESCRIPTOR);
                    if ($r1 != null) {
                        $r3 = $r1.asBinder();
                    }
                    $r4.writeStrongBinder($r3);
                    if ($r2 != null) {
                        $r4.writeInt(1);
                        $r2.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    this.mRemote.transact(5, $r4, null, 1);
                } finally {
                    $r4.recycle();
                }
            }

            public void hideKeyboard() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }

            public void setUpdatesEnabled(boolean $z0) throws RemoteException {
                byte $b0 = (byte) 0;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    if ($z0) {
                        $b0 = (byte) 1;
                    }
                    $r1.writeInt($b0);
                    this.mRemote.transact(7, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }
        }

        public Stub() throws  {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWLApp asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface(DESCRIPTOR);
            if ($r1 == null || !($r1 instanceof IWLApp)) {
                return new Proxy($r0);
            }
            return (IWLApp) $r1;
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
                    registerForCommand($r1.readInt());
                    return true;
                case 2:
                    $r1.enforceInterface(DESCRIPTOR);
                    requestActivation();
                    return true;
                case 3:
                    $r1.enforceInterface(DESCRIPTOR);
                    $z0 = activateApplicationByAppID($r1.readString());
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 4:
                    $r1.enforceInterface(DESCRIPTOR);
                    setKeyboardMode($r1.readInt());
                    return true;
                case 5:
                    EditorInfo $r8;
                    $r1.enforceInterface(DESCRIPTOR);
                    IWLInputConnection $r5 = com.abaltatech.weblink.service.interfaces.IWLInputConnection.Stub.asInterface($r1.readStrongBinder());
                    if ($r1.readInt() != 0) {
                        $r8 = (EditorInfo) EditorInfo.CREATOR.createFromParcel($r1);
                    } else {
                        $r8 = null;
                    }
                    showKeyboard($r5, $r8);
                    return true;
                case 6:
                    $r1.enforceInterface(DESCRIPTOR);
                    hideKeyboard();
                    return true;
                case 7:
                    $r1.enforceInterface(DESCRIPTOR);
                    if ($r1.readInt() != 0) {
                        $z0 = true;
                    } else {
                        $z0 = false;
                    }
                    setUpdatesEnabled($z0);
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

    boolean activateApplicationByAppID(String str) throws RemoteException;

    void hideKeyboard() throws RemoteException;

    void registerForCommand(int i) throws RemoteException;

    void requestActivation() throws RemoteException;

    void setKeyboardMode(int i) throws RemoteException;

    void setUpdatesEnabled(boolean z) throws RemoteException;

    void showKeyboard(IWLInputConnection iWLInputConnection, EditorInfo editorInfo) throws RemoteException;
}
