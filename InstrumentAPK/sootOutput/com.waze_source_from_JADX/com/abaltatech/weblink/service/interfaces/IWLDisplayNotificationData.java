package com.abaltatech.weblink.service.interfaces;

import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IWLDisplayNotificationData extends IInterface {

    public static abstract class Stub extends Binder implements IWLDisplayNotificationData {
        private static final String DESCRIPTOR = "com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationData";
        static final int TRANSACTION_getBitmap = 10;
        static final int TRANSACTION_getBitmapURL = 8;
        static final int TRANSACTION_getHasCancelButton = 12;
        static final int TRANSACTION_getIsSystemNotification = 15;
        static final int TRANSACTION_getNotificationID = 1;
        static final int TRANSACTION_getPriority = 17;
        static final int TRANSACTION_getRemainingTimeout = 6;
        static final int TRANSACTION_getShowProgress = 19;
        static final int TRANSACTION_getTimeout = 5;
        static final int TRANSACTION_getTitle = 3;
        static final int TRANSACTION_registerEventListener = 13;
        static final int TRANSACTION_setBitmap = 9;
        static final int TRANSACTION_setBitmapURL = 7;
        static final int TRANSACTION_setHasCancelButton = 11;
        static final int TRANSACTION_setIsSystemNotification = 16;
        static final int TRANSACTION_setPriority = 18;
        static final int TRANSACTION_setShowProgress = 20;
        static final int TRANSACTION_setTimeout = 4;
        static final int TRANSACTION_setTitle = 2;
        static final int TRANSACTION_unregisterEventListener = 14;

        private static class Proxy implements IWLDisplayNotificationData {
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

            public long getNotificationID() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, $r1, $r2, 0);
                    $r2.readException();
                    long $l0 = $r2.readLong();
                    return $l0;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void setTitle(String $r1) throws RemoteException {
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

            public String getTitle() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, $r1, $r2, 0);
                    $r2.readException();
                    String $r4 = $r2.readString();
                    return $r4;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void setTimeout(int $i0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeInt($i0);
                    this.mRemote.transact(4, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public int getTimeout() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, $r1, $r2, 0);
                    $r2.readException();
                    int $i0 = $r2.readInt();
                    return $i0;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public int getRemainingTimeout() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, $r1, $r2, 0);
                    $r2.readException();
                    int $i0 = $r2.readInt();
                    return $i0;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void setBitmapURL(String $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeString($r1);
                    this.mRemote.transact(7, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public String getBitmapURL() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, $r1, $r2, 0);
                    $r2.readException();
                    String $r4 = $r2.readString();
                    return $r4;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void setBitmap(Bitmap $r1) throws RemoteException {
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

            public Bitmap getBitmap() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    Bitmap $r6;
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, $r1, $r2, 0);
                    $r2.readException();
                    if ($r2.readInt() != 0) {
                        $r6 = (Bitmap) Bitmap.CREATOR.createFromParcel($r2);
                    } else {
                        $r6 = null;
                    }
                    $r2.recycle();
                    $r1.recycle();
                    return $r6;
                } catch (Throwable th) {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void setHasCancelButton(boolean $z0) throws RemoteException {
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

            public boolean getHasCancelButton() throws RemoteException {
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

            public void registerEventListener(IWLDisplayNotificationEventListener $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    this.mRemote.transact(13, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void unregisterEventListener(IWLDisplayNotificationEventListener $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    this.mRemote.transact(14, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public boolean getIsSystemNotification() throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, $r1, $r2, 0);
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

            public void setIsSystemNotification(boolean $z0) throws RemoteException {
                byte $b0 = (byte) 0;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    if ($z0) {
                        $b0 = (byte) 1;
                    }
                    $r1.writeInt($b0);
                    this.mRemote.transact(16, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public int getPriority() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, $r1, $r2, 0);
                    $r2.readException();
                    int $i0 = $r2.readInt();
                    return $i0;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void setPriority(int $i0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeInt($i0);
                    this.mRemote.transact(18, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public boolean getShowProgress() throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(19, $r1, $r2, 0);
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

            public void setShowProgress(boolean $z0) throws RemoteException {
                byte $b0 = (byte) 0;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    if ($z0) {
                        $b0 = (byte) 1;
                    }
                    $r1.writeInt($b0);
                    this.mRemote.transact(20, $r1, $r2, 0);
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

        public static IWLDisplayNotificationData asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface(DESCRIPTOR);
            if ($r1 == null || !($r1 instanceof IWLDisplayNotificationData)) {
                return new Proxy($r0);
            }
            return (IWLDisplayNotificationData) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            boolean $z0 = false;
            String $r3;
            Bitmap $r6;
            boolean $z1;
            switch ($i0) {
                case 1:
                    $r1.enforceInterface(DESCRIPTOR);
                    long $l2 = getNotificationID();
                    $r2.writeNoException();
                    $r2.writeLong($l2);
                    return true;
                case 2:
                    $r1.enforceInterface(DESCRIPTOR);
                    setTitle($r1.readString());
                    $r2.writeNoException();
                    return true;
                case 3:
                    $r1.enforceInterface(DESCRIPTOR);
                    $r3 = getTitle();
                    $r2.writeNoException();
                    $r2.writeString($r3);
                    return true;
                case 4:
                    $r1.enforceInterface(DESCRIPTOR);
                    setTimeout($r1.readInt());
                    $r2.writeNoException();
                    return true;
                case 5:
                    $r1.enforceInterface(DESCRIPTOR);
                    $i0 = getTimeout();
                    $r2.writeNoException();
                    $r2.writeInt($i0);
                    return true;
                case 6:
                    $r1.enforceInterface(DESCRIPTOR);
                    $i0 = getRemainingTimeout();
                    $r2.writeNoException();
                    $r2.writeInt($i0);
                    return true;
                case 7:
                    $r1.enforceInterface(DESCRIPTOR);
                    setBitmapURL($r1.readString());
                    $r2.writeNoException();
                    return true;
                case 8:
                    $r1.enforceInterface(DESCRIPTOR);
                    $r3 = getBitmapURL();
                    $r2.writeNoException();
                    $r2.writeString($r3);
                    return true;
                case 9:
                    $r1.enforceInterface(DESCRIPTOR);
                    if ($r1.readInt() != 0) {
                        $r6 = (Bitmap) Bitmap.CREATOR.createFromParcel($r1);
                    } else {
                        $r6 = null;
                    }
                    setBitmap($r6);
                    $r2.writeNoException();
                    return true;
                case 10:
                    $r1.enforceInterface(DESCRIPTOR);
                    $r6 = getBitmap();
                    $r2.writeNoException();
                    if ($r6 != null) {
                        $r2.writeInt(1);
                        $r6.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 11:
                    $r1.enforceInterface(DESCRIPTOR);
                    if ($r1.readInt() != 0) {
                        $z0 = true;
                    }
                    setHasCancelButton($z0);
                    $r2.writeNoException();
                    return true;
                case 12:
                    $r1.enforceInterface(DESCRIPTOR);
                    $z1 = getHasCancelButton();
                    $r2.writeNoException();
                    if ($z1) {
                        $z0 = true;
                    }
                    $r2.writeInt($z0);
                    return true;
                case 13:
                    $r1.enforceInterface(DESCRIPTOR);
                    registerEventListener(com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationEventListener.Stub.asInterface($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 14:
                    $r1.enforceInterface(DESCRIPTOR);
                    unregisterEventListener(com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationEventListener.Stub.asInterface($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 15:
                    $r1.enforceInterface(DESCRIPTOR);
                    $z1 = getIsSystemNotification();
                    $r2.writeNoException();
                    if ($z1) {
                        $z0 = true;
                    }
                    $r2.writeInt($z0);
                    return true;
                case 16:
                    $r1.enforceInterface(DESCRIPTOR);
                    if ($r1.readInt() != 0) {
                        $z0 = true;
                    }
                    setIsSystemNotification($z0);
                    $r2.writeNoException();
                    return true;
                case 17:
                    $r1.enforceInterface(DESCRIPTOR);
                    $i0 = getPriority();
                    $r2.writeNoException();
                    $r2.writeInt($i0);
                    return true;
                case 18:
                    $r1.enforceInterface(DESCRIPTOR);
                    setPriority($r1.readInt());
                    $r2.writeNoException();
                    return true;
                case 19:
                    $r1.enforceInterface(DESCRIPTOR);
                    $z1 = getShowProgress();
                    $r2.writeNoException();
                    if ($z1) {
                        $z0 = true;
                    }
                    $r2.writeInt($z0);
                    return true;
                case 20:
                    $r1.enforceInterface(DESCRIPTOR);
                    if ($r1.readInt() != 0) {
                        $z0 = true;
                    }
                    setShowProgress($z0);
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

    Bitmap getBitmap() throws RemoteException;

    String getBitmapURL() throws RemoteException;

    boolean getHasCancelButton() throws RemoteException;

    boolean getIsSystemNotification() throws RemoteException;

    long getNotificationID() throws RemoteException;

    int getPriority() throws RemoteException;

    int getRemainingTimeout() throws RemoteException;

    boolean getShowProgress() throws RemoteException;

    int getTimeout() throws RemoteException;

    String getTitle() throws RemoteException;

    void registerEventListener(IWLDisplayNotificationEventListener iWLDisplayNotificationEventListener) throws RemoteException;

    void setBitmap(Bitmap bitmap) throws RemoteException;

    void setBitmapURL(String str) throws RemoteException;

    void setHasCancelButton(boolean z) throws RemoteException;

    void setIsSystemNotification(boolean z) throws RemoteException;

    void setPriority(int i) throws RemoteException;

    void setShowProgress(boolean z) throws RemoteException;

    void setTimeout(int i) throws RemoteException;

    void setTitle(String str) throws RemoteException;

    void unregisterEventListener(IWLDisplayNotificationEventListener iWLDisplayNotificationEventListener) throws RemoteException;
}
