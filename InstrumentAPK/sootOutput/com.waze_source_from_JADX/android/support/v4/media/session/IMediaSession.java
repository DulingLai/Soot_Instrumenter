package android.support.v4.media.session;

import android.app.PendingIntent;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.MediaSessionCompat.QueueItem;
import android.text.TextUtils;
import android.view.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public interface IMediaSession extends IInterface {

    public static abstract class Stub extends Binder implements IMediaSession {
        private static final String DESCRIPTOR = "android.support.v4.media.session.IMediaSession";
        static final int TRANSACTION_adjustVolume = 11;
        static final int TRANSACTION_fastForward = 22;
        static final int TRANSACTION_getExtras = 31;
        static final int TRANSACTION_getFlags = 9;
        static final int TRANSACTION_getLaunchPendingIntent = 8;
        static final int TRANSACTION_getMetadata = 27;
        static final int TRANSACTION_getPackageName = 6;
        static final int TRANSACTION_getPlaybackState = 28;
        static final int TRANSACTION_getQueue = 29;
        static final int TRANSACTION_getQueueTitle = 30;
        static final int TRANSACTION_getRatingType = 32;
        static final int TRANSACTION_getTag = 7;
        static final int TRANSACTION_getVolumeAttributes = 10;
        static final int TRANSACTION_isTransportControlEnabled = 5;
        static final int TRANSACTION_next = 20;
        static final int TRANSACTION_pause = 18;
        static final int TRANSACTION_play = 13;
        static final int TRANSACTION_playFromMediaId = 14;
        static final int TRANSACTION_playFromSearch = 15;
        static final int TRANSACTION_playFromUri = 16;
        static final int TRANSACTION_previous = 21;
        static final int TRANSACTION_rate = 25;
        static final int TRANSACTION_registerCallbackListener = 3;
        static final int TRANSACTION_rewind = 23;
        static final int TRANSACTION_seekTo = 24;
        static final int TRANSACTION_sendCommand = 1;
        static final int TRANSACTION_sendCustomAction = 26;
        static final int TRANSACTION_sendMediaButton = 2;
        static final int TRANSACTION_setVolumeTo = 12;
        static final int TRANSACTION_skipToQueueItem = 17;
        static final int TRANSACTION_stop = 19;
        static final int TRANSACTION_unregisterCallbackListener = 4;

        private static class Proxy implements IMediaSession {
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

            public void sendCommand(String $r1, Bundle $r2, ResultReceiverWrapper $r3) throws RemoteException {
                Parcel $r4 = Parcel.obtain();
                Parcel $r5 = Parcel.obtain();
                try {
                    $r4.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r4.writeString($r1);
                    if ($r2 != null) {
                        $r4.writeInt(1);
                        $r2.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    if ($r3 != null) {
                        $r4.writeInt(1);
                        $r3.writeToParcel($r4, 0);
                    } else {
                        $r4.writeInt(0);
                    }
                    this.mRemote.transact(1, $r4, $r5, 0);
                    $r5.readException();
                } finally {
                    $r5.recycle();
                    $r4.recycle();
                }
            }

            public boolean sendMediaButton(KeyEvent $r1) throws RemoteException {
                boolean $z0 = true;
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
                    this.mRemote.transact(2, $r2, $r3, 0);
                    $r3.readException();
                    if ($r3.readInt() == 0) {
                        $z0 = false;
                    }
                    $r3.recycle();
                    $r2.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void registerCallbackListener(IMediaControllerCallback $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    this.mRemote.transact(3, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void unregisterCallbackListener(IMediaControllerCallback $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeStrongBinder($r1 != null ? $r1.asBinder() : null);
                    this.mRemote.transact(4, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public boolean isTransportControlEnabled() throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, $r1, $r2, 0);
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

            public String getPackageName() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, $r1, $r2, 0);
                    $r2.readException();
                    String $r4 = $r2.readString();
                    return $r4;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public String getTag() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, $r1, $r2, 0);
                    $r2.readException();
                    String $r4 = $r2.readString();
                    return $r4;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public PendingIntent getLaunchPendingIntent() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    PendingIntent $r6;
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, $r1, $r2, 0);
                    $r2.readException();
                    if ($r2.readInt() != 0) {
                        $r6 = (PendingIntent) PendingIntent.CREATOR.createFromParcel($r2);
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

            public long getFlags() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, $r1, $r2, 0);
                    $r2.readException();
                    long $l0 = $r2.readLong();
                    return $l0;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public ParcelableVolumeInfo getVolumeAttributes() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    ParcelableVolumeInfo $r6;
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, $r1, $r2, 0);
                    $r2.readException();
                    if ($r2.readInt() != 0) {
                        $r6 = (ParcelableVolumeInfo) ParcelableVolumeInfo.CREATOR.createFromParcel($r2);
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

            public void adjustVolume(int $i0, int $i1, String $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeInt($i0);
                    $r2.writeInt($i1);
                    $r2.writeString($r1);
                    this.mRemote.transact(11, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void setVolumeTo(int $i0, int $i1, String $r1) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeInt($i0);
                    $r2.writeInt($i1);
                    $r2.writeString($r1);
                    this.mRemote.transact(12, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void play() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void playFromMediaId(String $r1, Bundle $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r3.writeString($r1);
                    if ($r2 != null) {
                        $r3.writeInt(1);
                        $r2.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    this.mRemote.transact(14, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void playFromSearch(String $r1, Bundle $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r3.writeString($r1);
                    if ($r2 != null) {
                        $r3.writeInt(1);
                        $r2.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    this.mRemote.transact(15, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void playFromUri(Uri $r1, Bundle $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken(Stub.DESCRIPTOR);
                    if ($r1 != null) {
                        $r3.writeInt(1);
                        $r1.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    if ($r2 != null) {
                        $r3.writeInt(1);
                        $r2.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    this.mRemote.transact(16, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public void skipToQueueItem(long $l0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeLong($l0);
                    this.mRemote.transact(17, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void pause() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(18, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void stop() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(19, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void next() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void previous() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(21, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void fastForward() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(22, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void rewind() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(23, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void seekTo(long $l0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeLong($l0);
                    this.mRemote.transact(24, $r1, $r2, 0);
                    $r2.readException();
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public void rate(RatingCompat $r1) throws RemoteException {
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
                    this.mRemote.transact(25, $r2, $r3, 0);
                    $r3.readException();
                } finally {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public void sendCustomAction(String $r1, Bundle $r2) throws RemoteException {
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r3.writeString($r1);
                    if ($r2 != null) {
                        $r3.writeInt(1);
                        $r2.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    this.mRemote.transact(26, $r3, $r4, 0);
                    $r4.readException();
                } finally {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public MediaMetadataCompat getMetadata() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    MediaMetadataCompat $r6;
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, $r1, $r2, 0);
                    $r2.readException();
                    if ($r2.readInt() != 0) {
                        $r6 = (MediaMetadataCompat) MediaMetadataCompat.CREATOR.createFromParcel($r2);
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

            public PlaybackStateCompat getPlaybackState() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    PlaybackStateCompat $r6;
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, $r1, $r2, 0);
                    $r2.readException();
                    if ($r2.readInt() != 0) {
                        $r6 = (PlaybackStateCompat) PlaybackStateCompat.CREATOR.createFromParcel($r2);
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

            public List<QueueItem> getQueue() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(29, $r1, $r2, 0);
                    $r2.readException();
                    ArrayList $r5 = $r2.createTypedArrayList(QueueItem.CREATOR);
                    return $r5;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public CharSequence getQueueTitle() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    CharSequence $r6;
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(30, $r1, $r2, 0);
                    $r2.readException();
                    if ($r2.readInt() != 0) {
                        $r6 = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel($r2);
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

            public Bundle getExtras() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    Bundle $r6;
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(31, $r1, $r2, 0);
                    $r2.readException();
                    if ($r2.readInt() != 0) {
                        $r6 = (Bundle) Bundle.CREATOR.createFromParcel($r2);
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

            public int getRatingType() throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, $r1, $r2, 0);
                    $r2.readException();
                    int $i0 = $r2.readInt();
                    return $i0;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }
        }

        public Stub() throws  {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMediaSession asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface(DESCRIPTOR);
            if ($r1 == null || !($r1 instanceof IMediaSession)) {
                return new Proxy($r0);
            }
            return (IMediaSession) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            byte $b2 = (byte) 0;
            String $r3;
            Bundle $r6;
            boolean $z0;
            switch ($i0) {
                case 1:
                    ResultReceiverWrapper $r7;
                    $r1.enforceInterface(DESCRIPTOR);
                    $r3 = $r1.readString();
                    if ($r1.readInt() != 0) {
                        $r6 = (Bundle) Bundle.CREATOR.createFromParcel($r1);
                    } else {
                        $r6 = null;
                    }
                    if ($r1.readInt() != 0) {
                        $r7 = (ResultReceiverWrapper) ResultReceiverWrapper.CREATOR.createFromParcel($r1);
                    } else {
                        $r7 = null;
                    }
                    sendCommand($r3, $r6, $r7);
                    $r2.writeNoException();
                    return true;
                case 2:
                    KeyEvent $r8;
                    $r1.enforceInterface(DESCRIPTOR);
                    if ($r1.readInt() != 0) {
                        $r8 = (KeyEvent) KeyEvent.CREATOR.createFromParcel($r1);
                    } else {
                        $r8 = null;
                    }
                    $z0 = sendMediaButton($r8);
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 3:
                    $r1.enforceInterface(DESCRIPTOR);
                    registerCallbackListener(android.support.v4.media.session.IMediaControllerCallback.Stub.asInterface($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 4:
                    $r1.enforceInterface(DESCRIPTOR);
                    unregisterCallbackListener(android.support.v4.media.session.IMediaControllerCallback.Stub.asInterface($r1.readStrongBinder()));
                    $r2.writeNoException();
                    return true;
                case 5:
                    $r1.enforceInterface(DESCRIPTOR);
                    $z0 = isTransportControlEnabled();
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 6:
                    $r1.enforceInterface(DESCRIPTOR);
                    $r3 = getPackageName();
                    $r2.writeNoException();
                    $r2.writeString($r3);
                    return true;
                case 7:
                    $r1.enforceInterface(DESCRIPTOR);
                    $r3 = getTag();
                    $r2.writeNoException();
                    $r2.writeString($r3);
                    return true;
                case 8:
                    $r1.enforceInterface(DESCRIPTOR);
                    PendingIntent $r11 = getLaunchPendingIntent();
                    $r2.writeNoException();
                    if ($r11 != null) {
                        $r2.writeInt(1);
                        $r11.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 9:
                    $r1.enforceInterface(DESCRIPTOR);
                    long $l3 = getFlags();
                    $r2.writeNoException();
                    $r2.writeLong($l3);
                    return true;
                case 10:
                    $r1.enforceInterface(DESCRIPTOR);
                    ParcelableVolumeInfo $r12 = getVolumeAttributes();
                    $r2.writeNoException();
                    if ($r12 != null) {
                        $r2.writeInt(1);
                        $r12.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 11:
                    $r1.enforceInterface(DESCRIPTOR);
                    adjustVolume($r1.readInt(), $r1.readInt(), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 12:
                    $r1.enforceInterface(DESCRIPTOR);
                    setVolumeTo($r1.readInt(), $r1.readInt(), $r1.readString());
                    $r2.writeNoException();
                    return true;
                case 13:
                    $r1.enforceInterface(DESCRIPTOR);
                    play();
                    $r2.writeNoException();
                    return true;
                case 14:
                    $r1.enforceInterface(DESCRIPTOR);
                    $r3 = $r1.readString();
                    if ($r1.readInt() != 0) {
                        $r6 = (Bundle) Bundle.CREATOR.createFromParcel($r1);
                    } else {
                        $r6 = null;
                    }
                    playFromMediaId($r3, $r6);
                    $r2.writeNoException();
                    return true;
                case 15:
                    $r1.enforceInterface(DESCRIPTOR);
                    $r3 = $r1.readString();
                    if ($r1.readInt() != 0) {
                        $r6 = (Bundle) Bundle.CREATOR.createFromParcel($r1);
                    } else {
                        $r6 = null;
                    }
                    playFromSearch($r3, $r6);
                    $r2.writeNoException();
                    return true;
                case 16:
                    Uri $r13;
                    $r1.enforceInterface(DESCRIPTOR);
                    if ($r1.readInt() != 0) {
                        $r13 = (Uri) Uri.CREATOR.createFromParcel($r1);
                    } else {
                        $r13 = null;
                    }
                    if ($r1.readInt() != 0) {
                        $r6 = (Bundle) Bundle.CREATOR.createFromParcel($r1);
                    } else {
                        $r6 = null;
                    }
                    playFromUri($r13, $r6);
                    $r2.writeNoException();
                    return true;
                case 17:
                    $r1.enforceInterface(DESCRIPTOR);
                    skipToQueueItem($r1.readLong());
                    $r2.writeNoException();
                    return true;
                case 18:
                    $r1.enforceInterface(DESCRIPTOR);
                    pause();
                    $r2.writeNoException();
                    return true;
                case 19:
                    $r1.enforceInterface(DESCRIPTOR);
                    stop();
                    $r2.writeNoException();
                    return true;
                case 20:
                    $r1.enforceInterface(DESCRIPTOR);
                    next();
                    $r2.writeNoException();
                    return true;
                case 21:
                    $r1.enforceInterface(DESCRIPTOR);
                    previous();
                    $r2.writeNoException();
                    return true;
                case 22:
                    $r1.enforceInterface(DESCRIPTOR);
                    fastForward();
                    $r2.writeNoException();
                    return true;
                case 23:
                    $r1.enforceInterface(DESCRIPTOR);
                    rewind();
                    $r2.writeNoException();
                    return true;
                case 24:
                    $r1.enforceInterface(DESCRIPTOR);
                    seekTo($r1.readLong());
                    $r2.writeNoException();
                    return true;
                case 25:
                    RatingCompat $r14;
                    $r1.enforceInterface(DESCRIPTOR);
                    if ($r1.readInt() != 0) {
                        $r14 = (RatingCompat) RatingCompat.CREATOR.createFromParcel($r1);
                    } else {
                        $r14 = null;
                    }
                    rate($r14);
                    $r2.writeNoException();
                    return true;
                case 26:
                    $r1.enforceInterface(DESCRIPTOR);
                    $r3 = $r1.readString();
                    if ($r1.readInt() != 0) {
                        $r6 = (Bundle) Bundle.CREATOR.createFromParcel($r1);
                    } else {
                        $r6 = null;
                    }
                    sendCustomAction($r3, $r6);
                    $r2.writeNoException();
                    return true;
                case 27:
                    $r1.enforceInterface(DESCRIPTOR);
                    MediaMetadataCompat $r15 = getMetadata();
                    $r2.writeNoException();
                    if ($r15 != null) {
                        $r2.writeInt(1);
                        $r15.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 28:
                    $r1.enforceInterface(DESCRIPTOR);
                    PlaybackStateCompat $r16 = getPlaybackState();
                    $r2.writeNoException();
                    if ($r16 != null) {
                        $r2.writeInt(1);
                        $r16.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 29:
                    $r1.enforceInterface(DESCRIPTOR);
                    List $r17 = getQueue();
                    $r2.writeNoException();
                    $r2.writeTypedList($r17);
                    return true;
                case 30:
                    $r1.enforceInterface(DESCRIPTOR);
                    CharSequence $r18 = getQueueTitle();
                    $r2.writeNoException();
                    if ($r18 != null) {
                        $r2.writeInt(1);
                        TextUtils.writeToParcel($r18, $r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 31:
                    $r1.enforceInterface(DESCRIPTOR);
                    $r6 = getExtras();
                    $r2.writeNoException();
                    if ($r6 != null) {
                        $r2.writeInt(1);
                        $r6.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 32:
                    $r1.enforceInterface(DESCRIPTOR);
                    $i0 = getRatingType();
                    $r2.writeNoException();
                    $r2.writeInt($i0);
                    return true;
                case 1598968902:
                    $r2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    void adjustVolume(int i, int i2, String str) throws RemoteException;

    void fastForward() throws RemoteException;

    Bundle getExtras() throws RemoteException;

    long getFlags() throws RemoteException;

    PendingIntent getLaunchPendingIntent() throws RemoteException;

    MediaMetadataCompat getMetadata() throws RemoteException;

    String getPackageName() throws RemoteException;

    PlaybackStateCompat getPlaybackState() throws RemoteException;

    List<QueueItem> getQueue() throws RemoteException;

    CharSequence getQueueTitle() throws RemoteException;

    int getRatingType() throws RemoteException;

    String getTag() throws RemoteException;

    ParcelableVolumeInfo getVolumeAttributes() throws RemoteException;

    boolean isTransportControlEnabled() throws RemoteException;

    void next() throws RemoteException;

    void pause() throws RemoteException;

    void play() throws RemoteException;

    void playFromMediaId(String str, Bundle bundle) throws RemoteException;

    void playFromSearch(String str, Bundle bundle) throws RemoteException;

    void playFromUri(Uri uri, Bundle bundle) throws RemoteException;

    void previous() throws RemoteException;

    void rate(RatingCompat ratingCompat) throws RemoteException;

    void registerCallbackListener(IMediaControllerCallback iMediaControllerCallback) throws RemoteException;

    void rewind() throws RemoteException;

    void seekTo(long j) throws RemoteException;

    void sendCommand(String str, Bundle bundle, ResultReceiverWrapper resultReceiverWrapper) throws RemoteException;

    void sendCustomAction(String str, Bundle bundle) throws RemoteException;

    boolean sendMediaButton(KeyEvent keyEvent) throws RemoteException;

    void setVolumeTo(int i, int i2, String str) throws RemoteException;

    void skipToQueueItem(long j) throws RemoteException;

    void stop() throws RemoteException;

    void unregisterCallbackListener(IMediaControllerCallback iMediaControllerCallback) throws RemoteException;
}
