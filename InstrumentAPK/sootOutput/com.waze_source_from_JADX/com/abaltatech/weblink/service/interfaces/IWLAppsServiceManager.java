package com.abaltatech.weblink.service.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import dalvik.annotation.Signature;
import java.util.List;

public interface IWLAppsServiceManager extends IInterface {

    public static abstract class Stub extends Binder implements IWLAppsServiceManager {
        private static final String DESCRIPTOR = "com.abaltatech.weblink.service.interfaces.IWLAppsServiceManager";
        static final int TRANSACTION_findServiceByName = 3;
        static final int TRANSACTION_ping = 1;
        static final int TRANSACTION_registerService = 2;

        private static class Proxy implements IWLAppsServiceManager {
            private IBinder mRemote;

            public String getInterfaceDescriptor() throws  {
                return "com.abaltatech.weblink.service.interfaces.IWLAppsServiceManager";
            }

            Proxy(IBinder $r1) throws  {
                this.mRemote = $r1;
            }

            public IBinder asBinder() throws  {
                return this.mRemote;
            }

            public void ping() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLAppsServiceManager");
                    this.mRemote.transact(1, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public boolean registerService(@Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/abaltatech/weblink/service/interfaces/IWLAppsServiceHandler;", ")Z"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/abaltatech/weblink/service/interfaces/IWLAppsServiceHandler;", ")Z"}) List<String> $r2, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/abaltatech/weblink/service/interfaces/IWLAppsServiceHandler;", ")Z"}) String $r3, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/abaltatech/weblink/service/interfaces/IWLAppsServiceHandler;", ")Z"}) IWLAppsServiceHandler $r4) throws RemoteException {
                boolean $z0 = false;
                Parcel $r5 = Parcel.obtain();
                Parcel $r6 = Parcel.obtain();
                try {
                    $r5.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLAppsServiceManager");
                    $r5.writeString($r1);
                    $r5.writeStringList($r2);
                    $r5.writeString($r3);
                    $r5.writeStrongBinder($r4 != null ? $r4.asBinder() : null);
                    this.mRemote.transact(2, $r5, $r6, 0);
                    $r6.readException();
                    if ($r6.readInt() != 0) {
                        $z0 = true;
                    }
                    $r6.recycle();
                    $r5.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r6.recycle();
                    $r5.recycle();
                }
            }

            public void findServiceByName(String $r1, String $r2, IWLAppsServiceDiscoveryNotification $r3) throws RemoteException {
                IBinder $r4 = null;
                Parcel $r5 = Parcel.obtain();
                try {
                    $r5.writeInterfaceToken("com.abaltatech.weblink.service.interfaces.IWLAppsServiceManager");
                    $r5.writeString($r1);
                    $r5.writeString($r2);
                    if ($r3 != null) {
                        $r4 = $r3.asBinder();
                    }
                    $r5.writeStrongBinder($r4);
                    this.mRemote.transact(3, $r5, null, 1);
                } finally {
                    $r5.recycle();
                }
            }
        }

        public Stub() throws  {
            attachInterface(this, "com.abaltatech.weblink.service.interfaces.IWLAppsServiceManager");
        }

        public static IWLAppsServiceManager asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.abaltatech.weblink.service.interfaces.IWLAppsServiceManager");
            if ($r1 == null || !($r1 instanceof IWLAppsServiceManager)) {
                return new Proxy($r0);
            }
            return (IWLAppsServiceManager) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 1:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLAppsServiceManager");
                    ping();
                    $r2.writeNoException();
                    return true;
                case 2:
                    byte $b2;
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLAppsServiceManager");
                    boolean $z0 = registerService($r1.readString(), $r1.createStringArrayList(), $r1.readString(), com.abaltatech.weblink.service.interfaces.IWLAppsServiceHandler.Stub.asInterface($r1.readStrongBinder()));
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    } else {
                        $b2 = (byte) 0;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 3:
                    $r1.enforceInterface("com.abaltatech.weblink.service.interfaces.IWLAppsServiceManager");
                    findServiceByName($r1.readString(), $r1.readString(), com.abaltatech.weblink.service.interfaces.IWLAppsServiceDiscoveryNotification.Stub.asInterface($r1.readStrongBinder()));
                    return true;
                case 1598968902:
                    $r2.writeString("com.abaltatech.weblink.service.interfaces.IWLAppsServiceManager");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void findServiceByName(String str, String str2, IWLAppsServiceDiscoveryNotification iWLAppsServiceDiscoveryNotification) throws RemoteException;

    void ping() throws RemoteException;

    boolean registerService(@Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/abaltatech/weblink/service/interfaces/IWLAppsServiceHandler;", ")Z"}) String str, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/abaltatech/weblink/service/interfaces/IWLAppsServiceHandler;", ")Z"}) List<String> list, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/abaltatech/weblink/service/interfaces/IWLAppsServiceHandler;", ")Z"}) String str2, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Lcom/abaltatech/weblink/service/interfaces/IWLAppsServiceHandler;", ")Z"}) IWLAppsServiceHandler iWLAppsServiceHandler) throws RemoteException;
}
