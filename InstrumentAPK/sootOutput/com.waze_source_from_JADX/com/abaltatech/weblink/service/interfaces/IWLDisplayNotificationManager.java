package com.abaltatech.weblink.service.interfaces;

import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

public interface IWLDisplayNotificationManager extends IInterface {

    public static abstract class Stub extends Binder implements IWLDisplayNotificationManager {
        private static final String DESCRIPTOR = "com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationManager";
        static final int TRANSACTION_addNotification = 1;
        static final int TRANSACTION_dismissNotification = 2;
        static final int TRANSACTION_getMaxNotificationsPerApplication = 3;
        static final int TRANSACTION_getNotifications = 4;
        static final int TRANSACTION_registerEventListener = 5;
        static final int TRANSACTION_setNotificationManagerDrawingEnabled = 7;
        static final int TRANSACTION_unregisterEventListener = 6;

        private static class Proxy implements IWLDisplayNotificationManager {
            private IBinder mRemote;

            public String getInterfaceDescriptor() throws  {
                return "com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationManager";
            }

            Proxy(IBinder $r1) throws  {
                this.mRemote = $r1;
            }

            public IBinder asBinder() throws  {
                return this.mRemote;
            }

            public IWLDisplayNotificationData addNotification(String $r1, int $i0, String $r2, Bitmap $r3, boolean $z0, boolean $z1, int $i1, boolean $z2, PendingIntent $r4, PendingIntent $r5) throws RemoteException {
                Parcel $r6 = Parcel.obtain();
                Parcel $r7 = Parcel.obtain();
                try {
                    byte $b2;
                    $r6.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationManager");
                    $r6.writeString($r1);
                    $r6.writeInt($i0);
                    $r6.writeString($r2);
                    if ($r3 != null) {
                        $r6.writeInt(1);
                        $r3.writeToParcel($r6, 0);
                    } else {
                        $r6.writeInt(0);
                    }
                    if ($z0) {
                        $b2 = (byte) 1;
                    } else {
                        $b2 = (byte) 0;
                    }
                    $r6.writeInt($b2);
                    if ($z1) {
                        $b2 = (byte) 1;
                    } else {
                        $b2 = (byte) 0;
                    }
                    $r6.writeInt($b2);
                    $r6.writeInt($i1);
                    if ($z2) {
                        $b2 = (byte) 1;
                    } else {
                        $b2 = (byte) 0;
                    }
                    $r6.writeInt($b2);
                    if ($r4 != null) {
                        $r6.writeInt(1);
                        $r4.writeToParcel($r6, 0);
                    } else {
                        $r6.writeInt(0);
                    }
                    if ($r5 != null) {
                        $r6.writeInt(1);
                        $r5.writeToParcel($r6, 0);
                    } else {
                        $r6.writeInt(0);
                    }
                    this.mRemote.transact(1, $r6, $r7, 0);
                    $r7.readException();
                    IWLDisplayNotificationData $r9 = com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationData.Stub.asInterface($r7.readStrongBinder());
                    return $r9;
                } finally {
                    $r7.recycle();
                    $r6.recycle();
                }
            }

            public void dismissNotification(IWLDisplayNotificationData $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationManager");
                    $r2.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    this.mRemote.transact(2, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public int getMaxNotificationsPerApplication() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationManager");
                    this.mRemote.transact(3, $r1, $r2, 0);
                    $r2.readException();
                    int $i0 = $r2.readInt();
                    return $i0;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public List<WLDisplayNotificationDataParcelable> getNotifications(@Signature({"(I)", "Ljava/util/List", "<", "Lcom/abaltatech/weblink/service/interfaces/WLDisplayNotificationDataParcelable;", ">;"}) int $i0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationManager");
                    $r1.writeInt($i0);
                    this.mRemote.transact(4, $r1, $r2, 0);
                    $r2.readException();
                    ArrayList $r5 = $r2.createTypedArrayList(WLDisplayNotificationDataParcelable.CREATOR);
                    return $r5;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void registerEventListener(IWLDisplayNotificationListEventListener $r1) throws RemoteException {
                IBinder $r2 = null;
                Parcel $r3 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationManager");
                    if ($r1 != null) {
                        $r2 = $r1.asBinder();
                    }
                    $r3.writeStrongBinder($r2);
                    this.mRemote.transact(5, $r3, null, 1);
                } finally {
                    $r3.recycle();
                }
            }

            public void unregisterEventListener(IWLDisplayNotificationListEventListener $r1) throws RemoteException {
                IBinder $r2 = null;
                Parcel $r3 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationManager");
                    if ($r1 != null) {
                        $r2 = $r1.asBinder();
                    }
                    $r3.writeStrongBinder($r2);
                    this.mRemote.transact(6, $r3, null, 1);
                } finally {
                    $r3.recycle();
                }
            }

            public void setNotificationManagerDrawingEnabled(boolean $z0) throws RemoteException {
                byte $b0 = (byte) 1;
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationManager");
                    if (!$z0) {
                        $b0 = (byte) 0;
                    }
                    $r1.writeInt($b0);
                    this.mRemote.transact(7, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }
        }

        public Stub() throws  {
            attachInterface(this, "com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationManager");
        }

        public static IWLDisplayNotificationManager asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationManager");
            if ($r1 == null || !($r1 instanceof IWLDisplayNotificationManager)) {
                return new Proxy($r0);
            }
            return (IWLDisplayNotificationManager) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    Bitmap bitmap;
                    PendingIntent $r8;
                    PendingIntent $r9;
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationManager");
                    String $r3 = $r1.readString();
                    $i0 = $r1.readInt();
                    String $r4 = $r1.readString();
                    if ($r1.readInt() != 0) {
                        bitmap = (Bitmap) Bitmap.CREATOR.createFromParcel($r1);
                    } else {
                        bitmap = null;
                    }
                    boolean $z0 = $r1.readInt() != 0;
                    boolean z = $r1.readInt() != 0;
                    $i1 = $r1.readInt();
                    boolean z2 = $r1.readInt() != 0;
                    if ($r1.readInt() != 0) {
                        $r8 = (PendingIntent) PendingIntent.CREATOR.createFromParcel($r1);
                    } else {
                        $r8 = null;
                    }
                    if ($r1.readInt() != 0) {
                        $r9 = (PendingIntent) PendingIntent.CREATOR.createFromParcel($r1);
                    } else {
                        $r9 = null;
                    }
                    IWLDisplayNotificationData $r10 = addNotification($r3, $i0, $r4, bitmap, $z0, z, $i1, z2, $r8, $r9);
                    $r2.writeNoException();
                    $r2.writeStrongBinder($r10 != null ? $r10.asBinder() : null);
                    return true;
                case 2:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationManager");
                    dismissNotification(com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationData.Stub.asInterface($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 3:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationManager");
                    $i0 = getMaxNotificationsPerApplication();
                    $r2.writeNoException();
                    $r2.writeInt($i0);
                    return true;
                case 4:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationManager");
                    List $r12 = getNotifications($r1.readInt());
                    $r2.writeNoException();
                    $r2.writeTypedList($r12);
                    return true;
                case 5:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationManager");
                    registerEventListener(com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationListEventListener.Stub.asInterface($r1.readStrongBinder()));
                    return true;
                case 6:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationManager");
                    unregisterEventListener(com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationListEventListener.Stub.asInterface($r1.readStrongBinder()));
                    return true;
                case 7:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationManager");
                    setNotificationManagerDrawingEnabled($r1.readInt() != 0);
                    return true;
                case 1598968902:
                    $r2.writeString("com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationManager");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    IWLDisplayNotificationData addNotification(String str, int i, String str2, Bitmap bitmap, boolean z, boolean z2, int i2, boolean z3, PendingIntent pendingIntent, PendingIntent pendingIntent2) throws RemoteException;

    void dismissNotification(IWLDisplayNotificationData iWLDisplayNotificationData) throws RemoteException;

    int getMaxNotificationsPerApplication() throws RemoteException;

    List<WLDisplayNotificationDataParcelable> getNotifications(@Signature({"(I)", "Ljava/util/List", "<", "Lcom/abaltatech/weblink/service/interfaces/WLDisplayNotificationDataParcelable;", ">;"}) int i) throws RemoteException;

    void registerEventListener(IWLDisplayNotificationListEventListener iWLDisplayNotificationListEventListener) throws RemoteException;

    void setNotificationManagerDrawingEnabled(boolean z) throws RemoteException;

    void unregisterEventListener(IWLDisplayNotificationListEventListener iWLDisplayNotificationListEventListener) throws RemoteException;
}
