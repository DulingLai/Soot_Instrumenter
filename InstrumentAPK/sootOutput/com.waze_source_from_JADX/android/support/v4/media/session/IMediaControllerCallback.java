package android.support.v4.media.session;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat.QueueItem;
import android.text.TextUtils;
import dalvik.annotation.Signature;
import java.util.List;

public interface IMediaControllerCallback extends IInterface {

    public static abstract class Stub extends Binder implements IMediaControllerCallback {
        private static final String DESCRIPTOR = "android.support.v4.media.session.IMediaControllerCallback";
        static final int TRANSACTION_onEvent = 1;
        static final int TRANSACTION_onExtrasChanged = 7;
        static final int TRANSACTION_onMetadataChanged = 4;
        static final int TRANSACTION_onPlaybackStateChanged = 3;
        static final int TRANSACTION_onQueueChanged = 5;
        static final int TRANSACTION_onQueueTitleChanged = 6;
        static final int TRANSACTION_onSessionDestroyed = 2;
        static final int TRANSACTION_onVolumeInfoChanged = 8;

        private static class Proxy implements IMediaControllerCallback {
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

            public void onEvent(String $r1, Bundle $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r3.writeString($r1);
                    if ($r2 != null) {
                        $r3.writeInt(1);
                        $r2.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    this.mRemote.transact(1, $r3, null, 1);
                } finally {
                    $r3.recycle();
                }
            }

            public void onSessionDestroyed() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, $r1, null, 1);
                } finally {
                    $r1.recycle();
                }
            }

            public void onPlaybackStateChanged(PlaybackStateCompat $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.mRemote.transact(3, $r2, null, 1);
                } finally {
                    $r2.recycle();
                }
            }

            public void onMetadataChanged(MediaMetadataCompat $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.mRemote.transact(4, $r2, null, 1);
                } finally {
                    $r2.recycle();
                }
            }

            public void onQueueChanged(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v4/media/session/MediaSessionCompat$QueueItem;", ">;)V"}) List<QueueItem> $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeTypedList($r1);
                    this.mRemote.transact(5, $r2, null, 1);
                } finally {
                    $r2.recycle();
                }
            }

            public void onQueueTitleChanged(CharSequence $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        TextUtils.writeToParcel($r1, $r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.mRemote.transact(6, $r2, null, 1);
                } finally {
                    $r2.recycle();
                }
            }

            public void onExtrasChanged(Bundle $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.mRemote.transact(7, $r2, null, 1);
                } finally {
                    $r2.recycle();
                }
            }

            public void onVolumeInfoChanged(ParcelableVolumeInfo $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.mRemote.transact(8, $r2, null, 1);
                } finally {
                    $r2.recycle();
                }
            }
        }

        public Stub() throws  {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMediaControllerCallback asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface(DESCRIPTOR);
            if ($r1 == null || !($r1 instanceof IMediaControllerCallback)) {
                return new Proxy($r0);
            }
            return (IMediaControllerCallback) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            Bundle $r6;
            switch ($i0) {
                case 1:
                    $r1.enforceInterface(DESCRIPTOR);
                    String $r3 = $r1.readString();
                    if ($r1.readInt() != 0) {
                        $r6 = (Bundle) Bundle.CREATOR.createFromParcel($r1);
                    } else {
                        $r6 = null;
                    }
                    onEvent($r3, $r6);
                    return true;
                case 2:
                    $r1.enforceInterface(DESCRIPTOR);
                    onSessionDestroyed();
                    return true;
                case 3:
                    PlaybackStateCompat $r7;
                    $r1.enforceInterface(DESCRIPTOR);
                    if ($r1.readInt() != 0) {
                        $r7 = (PlaybackStateCompat) PlaybackStateCompat.CREATOR.createFromParcel($r1);
                    } else {
                        $r7 = null;
                    }
                    onPlaybackStateChanged($r7);
                    return true;
                case 4:
                    MediaMetadataCompat $r8;
                    $r1.enforceInterface(DESCRIPTOR);
                    if ($r1.readInt() != 0) {
                        $r8 = (MediaMetadataCompat) MediaMetadataCompat.CREATOR.createFromParcel($r1);
                    } else {
                        $r8 = null;
                    }
                    onMetadataChanged($r8);
                    return true;
                case 5:
                    $r1.enforceInterface(DESCRIPTOR);
                    onQueueChanged($r1.createTypedArrayList(QueueItem.CREATOR));
                    return true;
                case 6:
                    CharSequence $r10;
                    $r1.enforceInterface(DESCRIPTOR);
                    if ($r1.readInt() != 0) {
                        $r10 = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel($r1);
                    } else {
                        $r10 = null;
                    }
                    onQueueTitleChanged($r10);
                    return true;
                case 7:
                    $r1.enforceInterface(DESCRIPTOR);
                    if ($r1.readInt() != 0) {
                        $r6 = (Bundle) Bundle.CREATOR.createFromParcel($r1);
                    } else {
                        $r6 = null;
                    }
                    onExtrasChanged($r6);
                    return true;
                case 8:
                    ParcelableVolumeInfo $r11;
                    $r1.enforceInterface(DESCRIPTOR);
                    if ($r1.readInt() != 0) {
                        $r11 = (ParcelableVolumeInfo) ParcelableVolumeInfo.CREATOR.createFromParcel($r1);
                    } else {
                        $r11 = null;
                    }
                    onVolumeInfoChanged($r11);
                    return true;
                case 1598968902:
                    $r2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void onEvent(String str, Bundle bundle) throws RemoteException;

    void onExtrasChanged(Bundle bundle) throws RemoteException;

    void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) throws RemoteException;

    void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) throws RemoteException;

    void onQueueChanged(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v4/media/session/MediaSessionCompat$QueueItem;", ">;)V"}) List<QueueItem> list) throws RemoteException;

    void onQueueTitleChanged(CharSequence charSequence) throws RemoteException;

    void onSessionDestroyed() throws RemoteException;

    void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) throws RemoteException;
}
