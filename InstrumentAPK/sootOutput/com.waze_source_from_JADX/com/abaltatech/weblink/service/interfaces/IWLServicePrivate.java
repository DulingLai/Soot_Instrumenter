package com.abaltatech.weblink.service.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IWLServicePrivate extends IInterface {

    public static abstract class Stub extends Binder implements IWLServicePrivate {
        private static final String DESCRIPTOR = "com.abaltatech.weblink.service.interfaces.IWLServicePrivate";
        static final int TRANSACTION_activateApplication = 6;
        static final int TRANSACTION_getActiveApp = 7;
        static final int TRANSACTION_getApplicationName = 8;
        static final int TRANSACTION_getKeyboardManager = 10;
        static final int TRANSACTION_getRegisteredApps = 9;
        static final int TRANSACTION_isWLServerStarted = 3;
        static final int TRANSACTION_setListener = 5;
        static final int TRANSACTION_setProperty = 4;
        static final int TRANSACTION_setVoiceRecognitionVisible = 11;
        static final int TRANSACTION_startWLServer = 1;
        static final int TRANSACTION_stopWLServer = 2;

        private static class Proxy implements IWLServicePrivate {
            private IBinder mRemote;

            public String getInterfaceDescriptor() throws  {
                return "com.abaltatech.weblink.service.interfaces.IWLServicePrivate";
            }

            Proxy(IBinder $r1) throws  {
                this.mRemote = $r1;
            }

            public IBinder asBinder() throws  {
                return this.mRemote;
            }

            public boolean startWLServer() throws RemoteException {
                boolean $z0 = true;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLServicePrivate");
                    this.mRemote.transact(1, $r1, $r2, 0);
                    $r2.readException();
                    if ($r2.readInt() == 0) {
                        $z0 = false;
                    }
                    $r2.recycle();
                    $r1.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void stopWLServer() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLServicePrivate");
                    this.mRemote.transact(2, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public boolean isWLServerStarted() throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLServicePrivate");
                    this.mRemote.transact(3, $r1, $r2, 0);
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

            public void setProperty(String $r1, String $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLServicePrivate");
                    $r3.writeString($r1);
                    $r3.writeString($r2);
                    this.mRemote.transact(4, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void setListener(IWLServicePrivateNotification $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLServicePrivate");
                    $r2.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    this.mRemote.transact(5, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void activateApplication(int $i0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLServicePrivate");
                    $r1.writeInt($i0);
                    this.mRemote.transact(6, $r1, $r2, 0);
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
                    $r1.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLServicePrivate");
                    this.mRemote.transact(7, $r1, $r2, 0);
                    $r2.readException();
                    int $i0 = $r2.readInt();
                    return $i0;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public String getApplicationName(int $i0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLServicePrivate");
                    $r1.writeInt($i0);
                    this.mRemote.transact(8, $r1, $r2, 0);
                    $r2.readException();
                    String $r4 = $r2.readString();
                    return $r4;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public int[] getRegisteredApps() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLServicePrivate");
                    this.mRemote.transact(9, $r1, $r2, 0);
                    $r2.readException();
                    int[] $r4 = $r2.createIntArray();
                    return $r4;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public IWLKeyboardManager getKeyboardManager() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLServicePrivate");
                    this.mRemote.transact(10, $r1, $r2, 0);
                    $r2.readException();
                    IWLKeyboardManager $r4 = com.abaltatech.weblink.service.interfaces.IWLKeyboardManager.Stub.asInterface($r2.readStrongBinder());
                    return $r4;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void setVoiceRecognitionVisible(boolean $z0) throws RemoteException {
                byte $b0 = (byte) 1;
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLServicePrivate");
                    if (!$z0) {
                        $b0 = (byte) 0;
                    }
                    $r1.writeInt($b0);
                    this.mRemote.transact(11, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }
        }

        public Stub() throws  {
            attachInterface(this, "com.abaltatech.weblink.service.interfaces.IWLServicePrivate");
        }

        public static IWLServicePrivate asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.abaltatech.weblink.service.interfaces.IWLServicePrivate");
            if ($r1 == null || !($r1 instanceof IWLServicePrivate)) {
                return new Proxy($r0);
            }
            return (IWLServicePrivate) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            byte $b2 = (byte) 0;
            boolean $z0;
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLServicePrivate");
                    $z0 = startWLServer();
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 2:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLServicePrivate");
                    stopWLServer();
                    $r2.writeNoException();
                    return true;
                case 3:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLServicePrivate");
                    $z0 = isWLServerStarted();
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 4:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLServicePrivate");
                    setProperty($r1.readString(), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 5:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLServicePrivate");
                    setListener(com.abaltatech.weblink.service.interfaces.IWLServicePrivateNotification.Stub.asInterface($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 6:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLServicePrivate");
                    activateApplication($r1.readInt());
                    $r2.writeNoException();
                    return true;
                case 7:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLServicePrivate");
                    $i0 = getActiveApp();
                    $r2.writeNoException();
                    $r2.writeInt($i0);
                    return true;
                case 8:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLServicePrivate");
                    String $r3 = getApplicationName($r1.readInt());
                    $r2.writeNoException();
                    $r2.writeString($r3);
                    return true;
                case 9:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLServicePrivate");
                    int[] $r7 = getRegisteredApps();
                    $r2.writeNoException();
                    $r2.writeIntArray($r7);
                    return true;
                case 10:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLServicePrivate");
                    IWLKeyboardManager $r8 = getKeyboardManager();
                    $r2.writeNoException();
                    $r2.writeStrongBinder($r8 != null ? $r8.asBinder() : null);
                    return true;
                case 11:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLServicePrivate");
                    if ($r1.readInt() != 0) {
                        $z0 = true;
                    } else {
                        $z0 = false;
                    }
                    setVoiceRecognitionVisible($z0);
                    return true;
                case 1598968902:
                    $r2.writeString("com.abaltatech.weblink.service.interfaces.IWLServicePrivate");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void activateApplication(int i) throws RemoteException;

    int getActiveApp() throws RemoteException;

    String getApplicationName(int i) throws RemoteException;

    IWLKeyboardManager getKeyboardManager() throws RemoteException;

    int[] getRegisteredApps() throws RemoteException;

    boolean isWLServerStarted() throws RemoteException;

    void setListener(IWLServicePrivateNotification iWLServicePrivateNotification) throws RemoteException;

    void setProperty(String str, String str2) throws RemoteException;

    void setVoiceRecognitionVisible(boolean z) throws RemoteException;

    boolean startWLServer() throws RemoteException;

    void stopWLServer() throws RemoteException;
}
