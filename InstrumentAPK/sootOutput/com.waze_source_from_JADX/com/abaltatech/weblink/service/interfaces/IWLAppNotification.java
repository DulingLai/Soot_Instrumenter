package com.abaltatech.weblink.service.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.InputEvent;
import android.view.Surface;

public interface IWLAppNotification extends IInterface {

    public static abstract class Stub extends Binder implements IWLAppNotification {
        private static final String DESCRIPTOR = "com.abaltatech.weblink.service.interfaces.IWLAppNotification";
        static final int TRANSACTION_getInterfaceRevision = 8;
        static final int TRANSACTION_onActivated = 1;
        static final int TRANSACTION_onDeactivated = 2;
        static final int TRANSACTION_onEvent = 6;
        static final int TRANSACTION_onKeybordStateChanged = 7;
        static final int TRANSACTION_onRenderFrame = 5;
        static final int TRANSACTION_onRenderStart = 3;
        static final int TRANSACTION_onRenderStop = 4;
        static final int TRANSACTION_onWLInputEvent = 9;

        private static class Proxy implements IWLAppNotification {
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

            public void onActivated(int $i0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeInt($i0);
                    this.mRemote.transact(1, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }

            public void onDeactivated() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }

            public void onRenderStart(Surface $r1, int $i0, int $i1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    $r2.writeInt($i0);
                    $r2.writeInt($i1);
                    this.mRemote.transact(3, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void onRenderStop() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void onRenderFrame() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void onEvent(InputEvent $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.mRemote.transact(6, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void onKeybordStateChanged(boolean $z0) throws RemoteException {
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

            public int getInterfaceRevision() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, $r1, $r2, 0);
                    $r2.readException();
                    int $i0 = $r2.readInt();
                    return $i0;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void onWLInputEvent(WLInputEventParcelable $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.mRemote.transact(9, $r2, $r3, 0);
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

        public static IWLAppNotification asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface(DESCRIPTOR);
            if ($r1 == null || !($r1 instanceof IWLAppNotification)) {
                return new Proxy($r0);
            }
            return (IWLAppNotification) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface(DESCRIPTOR);
                    onActivated($r1.readInt());
                    return true;
                case 2:
                    $r1.enforceInterface(DESCRIPTOR);
                    onDeactivated();
                    return true;
                case 3:
                    Surface $r5;
                    $r1.enforceInterface(DESCRIPTOR);
                    if ($r1.readInt() != 0) {
                        $r5 = (Surface) Surface.CREATOR.createFromParcel($r1);
                    } else {
                        $r5 = null;
                    }
                    onRenderStart($r5, $r1.readInt(), $r1.readInt());
                    $r2.writeNoException();
                    return true;
                case 4:
                    $r1.enforceInterface(DESCRIPTOR);
                    onRenderStop();
                    $r2.writeNoException();
                    return true;
                case 5:
                    $r1.enforceInterface(DESCRIPTOR);
                    onRenderFrame();
                    $r2.writeNoException();
                    return true;
                case 6:
                    InputEvent $r6;
                    $r1.enforceInterface(DESCRIPTOR);
                    if ($r1.readInt() != 0) {
                        $r6 = (InputEvent) InputEvent.CREATOR.createFromParcel($r1);
                    } else {
                        $r6 = null;
                    }
                    onEvent($r6);
                    $r2.writeNoException();
                    return true;
                case 7:
                    $r1.enforceInterface(DESCRIPTOR);
                    onKeybordStateChanged($r1.readInt() != 0);
                    $r2.writeNoException();
                    return true;
                case 8:
                    $r1.enforceInterface(DESCRIPTOR);
                    $i0 = getInterfaceRevision();
                    $r2.writeNoException();
                    $r2.writeInt($i0);
                    return true;
                case 9:
                    WLInputEventParcelable $r7;
                    $r1.enforceInterface(DESCRIPTOR);
                    if ($r1.readInt() != 0) {
                        $r7 = (WLInputEventParcelable) WLInputEventParcelable.CREATOR.createFromParcel($r1);
                    } else {
                        $r7 = null;
                    }
                    onWLInputEvent($r7);
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

    int getInterfaceRevision() throws RemoteException;

    void onActivated(int i) throws RemoteException;

    void onDeactivated() throws RemoteException;

    void onEvent(InputEvent inputEvent) throws RemoteException;

    void onKeybordStateChanged(boolean z) throws RemoteException;

    void onRenderFrame() throws RemoteException;

    void onRenderStart(Surface surface, int i, int i2) throws RemoteException;

    void onRenderStop() throws RemoteException;

    void onWLInputEvent(WLInputEventParcelable wLInputEventParcelable) throws RemoteException;
}
