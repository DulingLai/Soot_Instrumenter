package android.support.v4.media;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;

class IMediaBrowserServiceAdapterApi21 {

    static abstract class Stub extends Binder implements IInterface {
        private static final String DESCRIPTOR = "android.service.media.IMediaBrowserService";
        private static final int TRANSACTION_addSubscription = 3;
        private static final int TRANSACTION_connect = 1;
        private static final int TRANSACTION_disconnect = 2;
        private static final int TRANSACTION_getMediaItem = 5;
        private static final int TRANSACTION_removeSubscription = 4;

        public abstract void addSubscription(String str, Object obj) throws ;

        public abstract void connect(String str, Bundle bundle, Object obj) throws ;

        public abstract void disconnect(Object obj) throws ;

        public abstract void getMediaItem(String str, ResultReceiver resultReceiver) throws ;

        public abstract void removeSubscription(String str, Object obj) throws ;

        public Stub() throws  {
            attachInterface(this, DESCRIPTOR);
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            String $r3;
            switch ($i0) {
                case 1:
                    Bundle $r6;
                    $r1.enforceInterface(DESCRIPTOR);
                    $r3 = $r1.readString();
                    if ($r1.readInt() != 0) {
                        $r6 = (Bundle) Bundle.CREATOR.createFromParcel($r1);
                    } else {
                        $r6 = null;
                    }
                    connect($r3, $r6, Stub.asInterface($r1.readStrongBinder()));
                    return true;
                case 2:
                    $r1.enforceInterface(DESCRIPTOR);
                    disconnect(Stub.asInterface($r1.readStrongBinder()));
                    return true;
                case 3:
                    $r1.enforceInterface(DESCRIPTOR);
                    addSubscription($r1.readString(), Stub.asInterface($r1.readStrongBinder()));
                    return true;
                case 4:
                    $r1.enforceInterface(DESCRIPTOR);
                    removeSubscription($r1.readString(), Stub.asInterface($r1.readStrongBinder()));
                    return true;
                case 5:
                    ResultReceiver $r8;
                    $r1.enforceInterface(DESCRIPTOR);
                    $r3 = $r1.readString();
                    if ($r1.readInt() != 0) {
                        $r8 = (ResultReceiver) ResultReceiver.CREATOR.createFromParcel($r1);
                    } else {
                        $r8 = null;
                    }
                    getMediaItem($r3, $r8);
                    return true;
                case 1598968902:
                    $r2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    IMediaBrowserServiceAdapterApi21() throws  {
    }
}
