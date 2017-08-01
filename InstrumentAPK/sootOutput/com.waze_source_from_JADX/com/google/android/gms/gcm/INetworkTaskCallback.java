package com.google.android.gms.gcm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: dalvik_source_com.waze.apk */
public interface INetworkTaskCallback extends IInterface {

    /* compiled from: dalvik_source_com.waze.apk */
    public static abstract class Stub extends Binder implements INetworkTaskCallback {

        /* compiled from: dalvik_source_com.waze.apk */
        private static class Proxy implements INetworkTaskCallback {
            private IBinder zzahn;

            Proxy(IBinder $r1) throws  {
                this.zzahn = $r1;
            }

            public IBinder asBinder() throws  {
                return this.zzahn;
            }

            public String getInterfaceDescriptor() throws  {
                return "com.google.android.gms.gcm.INetworkTaskCallback";
            }

            public void taskFinished(int $i0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken("com.google.android.gms.gcm.INetworkTaskCallback");
                    $r1.writeInt($i0);
                    this.zzahn.transact(2, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }
        }

        public Stub() throws  {
            attachInterface(this, "com.google.android.gms.gcm.INetworkTaskCallback");
        }

        public static INetworkTaskCallback asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface("com.google.android.gms.gcm.INetworkTaskCallback");
            return ($r1 == null || !($r1 instanceof INetworkTaskCallback)) ? new Proxy($r0) : (INetworkTaskCallback) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            switch ($i0) {
                case 2:
                    $r1.enforceInterface("com.google.android.gms.gcm.INetworkTaskCallback");
                    taskFinished($r1.readInt());
                    $r2.writeNoException();
                    return true;
                case 1598968902:
                    $r2.writeString("com.google.android.gms.gcm.INetworkTaskCallback");
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void taskFinished(int i) throws RemoteException;
}
