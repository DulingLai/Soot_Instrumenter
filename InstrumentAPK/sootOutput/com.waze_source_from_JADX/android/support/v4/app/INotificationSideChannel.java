package android.support.v4.app;

import android.app.Notification;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface INotificationSideChannel extends IInterface {

    public static abstract class Stub extends Binder implements INotificationSideChannel {
        private static final String DESCRIPTOR = "android.support.v4.app.INotificationSideChannel";
        static final int TRANSACTION_cancel = 2;
        static final int TRANSACTION_cancelAll = 3;
        static final int TRANSACTION_notify = 1;

        private static class Proxy implements INotificationSideChannel {
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

            public void notify(String $r1, int $i0, String $r2, Notification $r3) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r4.writeString($r1);
                    $r4.writeInt($i0);
                    $r4.writeString($r2);
                    if ($r3 != null) {
                        $r4.writeInt(1);
                        $r3.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    this.mRemote.transact(1, $r4, null, 1);
                } finally {
                    $r4.recycle();
                }
            }

            public void cancel(String $r1, int $i0, String $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r3.writeString($r1);
                    $r3.writeInt($i0);
                    $r3.writeString($r2);
                    this.mRemote.transact(2, $r3, null, 1);
                } finally {
                    $r3.recycle();
                }
            }

            public void cancelAll(String $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeString($r1);
                    this.mRemote.transact(3, $r2, null, 1);
                } finally {
                    $r2.recycle();
                }
            }
        }

        public Stub() throws  {
            attachInterface(this, DESCRIPTOR);
        }

        public static INotificationSideChannel asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface(DESCRIPTOR);
            if ($r1 == null || !($r1 instanceof INotificationSideChannel)) {
                return new Proxy($r0);
            }
            return (INotificationSideChannel) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    Notification $r7;
                    $r1.enforceInterface(DESCRIPTOR);
                    String $r3 = $r1.readString();
                    $i0 = $r1.readInt();
                    String $r4 = $r1.readString();
                    if ($r1.readInt() != 0) {
                        $r7 = (Notification) Notification.CREATOR.createFromParcel($r1);
                    } else {
                        $r7 = null;
                    }
                    notify($r3, $i0, $r4, $r7);
                    return true;
                case 2:
                    $r1.enforceInterface(DESCRIPTOR);
                    cancel($r1.readString(), $r1.readInt(), $r1.readString());
                    return true;
                case 3:
                    $r1.enforceInterface(DESCRIPTOR);
                    cancelAll($r1.readString());
                    return true;
                case 1598968902:
                    $r2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void cancel(String str, int i, String str2) throws RemoteException;

    void cancelAll(String str) throws RemoteException;

    void notify(String str, int i, String str2, Notification notification) throws RemoteException;
}
