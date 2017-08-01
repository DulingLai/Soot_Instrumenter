package com.abaltatech.weblink.service.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import dalvik.annotation.Signature;
import java.util.List;

public interface IWLAppsServiceDiscoveryNotification extends IInterface {

    public static abstract class Stub extends Binder implements IWLAppsServiceDiscoveryNotification {
        private static final String DESCRIPTOR = "com.abaltatech.weblink.service.interfaces.IWLAppsServiceDiscoveryNotification";
        static final int TRANSACTION_onServiceDiscoveryCompleted = 2;
        static final int TRANSACTION_onServiceDiscoveryFailed = 3;
        static final int TRANSACTION_onServiceFound = 1;

        private static class Proxy implements IWLAppsServiceDiscoveryNotification {
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

            public void onServiceFound(@Signature({"(", "Lcom/abaltatech/weblink/service/interfaces/IWLAppsServiceHandler;", "I", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")V"}) IWLAppsServiceHandler $r1, @Signature({"(", "Lcom/abaltatech/weblink/service/interfaces/IWLAppsServiceHandler;", "I", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Lcom/abaltatech/weblink/service/interfaces/IWLAppsServiceHandler;", "I", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(", "Lcom/abaltatech/weblink/service/interfaces/IWLAppsServiceHandler;", "I", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")V"}) List<String> $r3, @Signature({"(", "Lcom/abaltatech/weblink/service/interfaces/IWLAppsServiceHandler;", "I", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")V"}) String $r4) throws RemoteException {
                IBinder $r5 = null;
                Parcel $r6 = Parcel.obtain();
                try {
                    $r6.writeInterfaceToken(Stub.DESCRIPTOR);
                    if ($r1 != null) {
                        $r5 = $r1.asBinder();
                    }
                    $r6.writeStrongBinder($r5);
                    $r6.writeInt($i0);
                    $r6.writeString($r2);
                    $r6.writeStringList($r3);
                    $r6.writeString($r4);
                    this.mRemote.transact(1, $r6, null, 1);
                } finally {
                    $r6.recycle();
                }
            }

            public void onServiceDiscoveryCompleted(int $i0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeInt($i0);
                    this.mRemote.transact(2, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }

            public void onServiceDiscoveryFailed(String $r1) throws RemoteException {
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

        public static IWLAppsServiceDiscoveryNotification asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface(DESCRIPTOR);
            if ($r1 == null || !($r1 instanceof IWLAppsServiceDiscoveryNotification)) {
                return new Proxy($r0);
            }
            return (IWLAppsServiceDiscoveryNotification) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface(DESCRIPTOR);
                    onServiceFound(com.abaltatech.weblink.service.interfaces.IWLAppsServiceHandler.Stub.asInterface($r1.readStrongBinder()), $r1.readInt(), $r1.readString(), $r1.createStringArrayList(), $r1.readString());
                    return true;
                case 2:
                    $r1.enforceInterface(DESCRIPTOR);
                    onServiceDiscoveryCompleted($r1.readInt());
                    return true;
                case 3:
                    $r1.enforceInterface(DESCRIPTOR);
                    onServiceDiscoveryFailed($r1.readString());
                    return true;
                case 1598968902:
                    $r2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void onServiceDiscoveryCompleted(int i) throws RemoteException;

    void onServiceDiscoveryFailed(String str) throws RemoteException;

    void onServiceFound(@Signature({"(", "Lcom/abaltatech/weblink/service/interfaces/IWLAppsServiceHandler;", "I", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")V"}) IWLAppsServiceHandler iWLAppsServiceHandler, @Signature({"(", "Lcom/abaltatech/weblink/service/interfaces/IWLAppsServiceHandler;", "I", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")V"}) int i, @Signature({"(", "Lcom/abaltatech/weblink/service/interfaces/IWLAppsServiceHandler;", "I", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")V"}) String str, @Signature({"(", "Lcom/abaltatech/weblink/service/interfaces/IWLAppsServiceHandler;", "I", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")V"}) List<String> list, @Signature({"(", "Lcom/abaltatech/weblink/service/interfaces/IWLAppsServiceHandler;", "I", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")V"}) String str2) throws RemoteException;
}
