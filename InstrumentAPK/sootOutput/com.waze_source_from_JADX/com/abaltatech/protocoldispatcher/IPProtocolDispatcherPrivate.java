package com.abaltatech.protocoldispatcher;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPProtocolDispatcherPrivate extends IInterface {

    public static abstract class Stub extends Binder implements IPProtocolDispatcherPrivate {
        private static final String DESCRIPTOR = "com.abaltatech.protocoldispatcher.IPProtocolDispatcherPrivate";
        static final int TRANSACTION_disconnectFromClient = 6;
        static final int TRANSACTION_getActiveApp = 4;
        static final int TRANSACTION_getIsAOAEnabled = 12;
        static final int TRANSACTION_getIsTCPIPEnabled = 10;
        static final int TRANSACTION_pauseActiveApp = 2;
        static final int TRANSACTION_registerNotificationListener = 7;
        static final int TRANSACTION_resumeActiveApp = 3;
        static final int TRANSACTION_sendControlMessage = 5;
        static final int TRANSACTION_setActiveApp = 1;
        static final int TRANSACTION_setIsAOAEnabled = 11;
        static final int TRANSACTION_setIsTCPIPEnabled = 9;
        static final int TRANSACTION_setLoggingEnabled = 13;
        static final int TRANSACTION_unregisterNotificationListener = 8;

        private static class Proxy implements IPProtocolDispatcherPrivate {
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

            public void setActiveApp(int $i0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeInt($i0);
                    this.mRemote.transact(1, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void pauseActiveApp() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void resumeActiveApp() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public int getActiveApp() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, $r1, $r2, 0);
                    $r2.readException();
                    int $i0 = $r2.readInt();
                    return $i0;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public boolean sendControlMessage(byte[] $r1) throws RemoteException {
                boolean $z0 = false;
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeByteArray($r1);
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

            public void disconnectFromClient() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void registerNotificationListener(IPProtocolDispatcherPrivateNotification $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    this.mRemote.transact(7, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void unregisterNotificationListener(IPProtocolDispatcherPrivateNotification $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    this.mRemote.transact(8, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void setIsTCPIPEnabled(boolean $z0) throws RemoteException {
                byte $b0 = (byte) 0;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    if ($z0) {
                        $b0 = (byte) 1;
                    }
                    $r1.writeInt($b0);
                    this.mRemote.transact(9, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public boolean getIsTCPIPEnabled() throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, $r1, $r2, 0);
                    $r2.readException();
                    if ($r2.readInt() != 0) {
                        $z0 = true;
                    }
                    $r2.recycle();
                    $r1.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void setIsAOAEnabled(boolean $z0) throws RemoteException {
                byte $b0 = (byte) 0;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    if ($z0) {
                        $b0 = (byte) 1;
                    }
                    $r1.writeInt($b0);
                    this.mRemote.transact(11, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public boolean getIsAOAEnabled() throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, $r1, $r2, 0);
                    $r2.readException();
                    if ($r2.readInt() != 0) {
                        $z0 = true;
                    }
                    $r2.recycle();
                    $r1.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void setLoggingEnabled(boolean $z0) throws RemoteException {
                byte $b0 = (byte) 0;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    if ($z0) {
                        $b0 = (byte) 1;
                    }
                    $r1.writeInt($b0);
                    this.mRemote.transact(13, $r1, $r2, 0);
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

        public static IPProtocolDispatcherPrivate asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface(DESCRIPTOR);
            if ($r1 == null || !($r1 instanceof IPProtocolDispatcherPrivate)) {
                return new Proxy($r0);
            }
            return (IPProtocolDispatcherPrivate) $r1;
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
                    setActiveApp($r1.readInt());
                    $r2.writeNoException();
                    return true;
                case 2:
                    $r1.enforceInterface(DESCRIPTOR);
                    pauseActiveApp();
                    $r2.writeNoException();
                    return true;
                case 3:
                    $r1.enforceInterface(DESCRIPTOR);
                    resumeActiveApp();
                    $r2.writeNoException();
                    return true;
                case 4:
                    $r1.enforceInterface(DESCRIPTOR);
                    $i0 = getActiveApp();
                    $r2.writeNoException();
                    $r2.writeInt($i0);
                    return true;
                case 5:
                    $r1.enforceInterface(DESCRIPTOR);
                    $z0 = sendControlMessage($r1.createByteArray());
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 6:
                    $r1.enforceInterface(DESCRIPTOR);
                    disconnectFromClient();
                    $r2.writeNoException();
                    return true;
                case 7:
                    $r1.enforceInterface(DESCRIPTOR);
                    registerNotificationListener(com.abaltatech.protocoldispatcher.IPProtocolDispatcherPrivateNotification.Stub.asInterface($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 8:
                    $r1.enforceInterface(DESCRIPTOR);
                    unregisterNotificationListener(com.abaltatech.protocoldispatcher.IPProtocolDispatcherPrivateNotification.Stub.asInterface($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 9:
                    $r1.enforceInterface(DESCRIPTOR);
                    if ($r1.readInt() != 0) {
                        $z0 = true;
                    } else {
                        $z0 = false;
                    }
                    setIsTCPIPEnabled($z0);
                    $r2.writeNoException();
                    return true;
                case 10:
                    $r1.enforceInterface(DESCRIPTOR);
                    $z0 = getIsTCPIPEnabled();
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 11:
                    $r1.enforceInterface(DESCRIPTOR);
                    if ($r1.readInt() != 0) {
                        $z0 = true;
                    } else {
                        $z0 = false;
                    }
                    setIsAOAEnabled($z0);
                    $r2.writeNoException();
                    return true;
                case 12:
                    $r1.enforceInterface(DESCRIPTOR);
                    $z0 = getIsAOAEnabled();
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 13:
                    $r1.enforceInterface(DESCRIPTOR);
                    if ($r1.readInt() != 0) {
                        $z0 = true;
                    } else {
                        $z0 = false;
                    }
                    setLoggingEnabled($z0);
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

    void disconnectFromClient() throws RemoteException;

    int getActiveApp() throws RemoteException;

    boolean getIsAOAEnabled() throws RemoteException;

    boolean getIsTCPIPEnabled() throws RemoteException;

    void pauseActiveApp() throws RemoteException;

    void registerNotificationListener(IPProtocolDispatcherPrivateNotification iPProtocolDispatcherPrivateNotification) throws RemoteException;

    void resumeActiveApp() throws RemoteException;

    boolean sendControlMessage(byte[] bArr) throws RemoteException;

    void setActiveApp(int i) throws RemoteException;

    void setIsAOAEnabled(boolean z) throws RemoteException;

    void setIsTCPIPEnabled(boolean z) throws RemoteException;

    void setLoggingEnabled(boolean z) throws RemoteException;

    void unregisterNotificationListener(IPProtocolDispatcherPrivateNotification iPProtocolDispatcherPrivateNotification) throws RemoteException;
}
