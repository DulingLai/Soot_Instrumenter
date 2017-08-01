package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.MediaMetadataCompat.Builder;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.VolumeProviderCompat;
import android.support.v4.media.session.IMediaSession.Stub;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import dalvik.annotation.Signature;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MediaSessionCompat {
    public static final String ACTION_ARGUMENT_EXTRAS = "android.support.v4.media.session.action.ARGUMENT_EXTRAS";
    public static final String ACTION_ARGUMENT_URI = "android.support.v4.media.session.action.ARGUMENT_URI";
    public static final String ACTION_PLAY_FROM_URI = "android.support.v4.media.session.action.PLAY_FROM_URI";
    public static final int FLAG_HANDLES_MEDIA_BUTTONS = 1;
    public static final int FLAG_HANDLES_TRANSPORT_CONTROLS = 2;
    private static final String TAG = "MediaSessionCompat";
    private final ArrayList<OnActiveChangeListener> mActiveListeners;
    private final MediaControllerCompat mController;
    private final MediaSessionImpl mImpl;

    public static abstract class Callback {
        final Object mCallbackObj;

        private class StubApi21 implements Callback {
            private StubApi21() throws  {
            }

            public void onCommand(String $r1, Bundle $r2, ResultReceiver $r3) throws  {
                Callback.this.onCommand($r1, $r2, $r3);
            }

            public boolean onMediaButtonEvent(Intent $r1) throws  {
                return Callback.this.onMediaButtonEvent($r1);
            }

            public void onPlay() throws  {
                Callback.this.onPlay();
            }

            public void onPlayFromMediaId(String $r1, Bundle $r2) throws  {
                Callback.this.onPlayFromMediaId($r1, $r2);
            }

            public void onPlayFromSearch(String $r1, Bundle $r2) throws  {
                Callback.this.onPlayFromSearch($r1, $r2);
            }

            public void onSkipToQueueItem(long $l0) throws  {
                Callback.this.onSkipToQueueItem($l0);
            }

            public void onPause() throws  {
                Callback.this.onPause();
            }

            public void onSkipToNext() throws  {
                Callback.this.onSkipToNext();
            }

            public void onSkipToPrevious() throws  {
                Callback.this.onSkipToPrevious();
            }

            public void onFastForward() throws  {
                Callback.this.onFastForward();
            }

            public void onRewind() throws  {
                Callback.this.onRewind();
            }

            public void onStop() throws  {
                Callback.this.onStop();
            }

            public void onSeekTo(long $l0) throws  {
                Callback.this.onSeekTo($l0);
            }

            public void onSetRating(Object $r1) throws  {
                Callback.this.onSetRating(RatingCompat.fromRating($r1));
            }

            public void onCustomAction(String $r1, Bundle $r2) throws  {
                if ($r1.equals(MediaSessionCompat.ACTION_PLAY_FROM_URI)) {
                    Callback.this.onPlayFromUri((Uri) $r2.getParcelable(MediaSessionCompat.ACTION_ARGUMENT_URI), (Bundle) $r2.getParcelable(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS));
                    return;
                }
                Callback.this.onCustomAction($r1, $r2);
            }
        }

        private class StubApi23 extends StubApi21 implements android.support.v4.media.session.MediaSessionCompatApi23.Callback {
            private StubApi23() throws  {
                super();
            }

            public void onPlayFromUri(Uri $r1, Bundle $r2) throws  {
                Callback.this.onPlayFromUri($r1, $r2);
            }
        }

        public boolean onMediaButtonEvent(Intent mediaButtonEvent) throws  {
            return false;
        }

        public Callback() throws  {
            if (VERSION.SDK_INT >= 23) {
                this.mCallbackObj = MediaSessionCompatApi23.createCallback(new StubApi23());
            } else if (VERSION.SDK_INT >= 21) {
                this.mCallbackObj = MediaSessionCompatApi21.createCallback(new StubApi21());
            } else {
                this.mCallbackObj = null;
            }
        }

        public void onCommand(String command, Bundle extras, ResultReceiver cb) throws  {
        }

        public void onPlay() throws  {
        }

        public void onPlayFromMediaId(String mediaId, Bundle extras) throws  {
        }

        public void onPlayFromSearch(String query, Bundle extras) throws  {
        }

        public void onPlayFromUri(Uri uri, Bundle extras) throws  {
        }

        public void onSkipToQueueItem(long id) throws  {
        }

        public void onPause() throws  {
        }

        public void onSkipToNext() throws  {
        }

        public void onSkipToPrevious() throws  {
        }

        public void onFastForward() throws  {
        }

        public void onRewind() throws  {
        }

        public void onStop() throws  {
        }

        public void onSeekTo(long pos) throws  {
        }

        public void onSetRating(RatingCompat rating) throws  {
        }

        public void onCustomAction(String action, Bundle extras) throws  {
        }
    }

    interface MediaSessionImpl {
        Object getMediaSession() throws ;

        Object getRemoteControlClient() throws ;

        Token getSessionToken() throws ;

        boolean isActive() throws ;

        void release() throws ;

        void sendSessionEvent(String str, Bundle bundle) throws ;

        void setActive(boolean z) throws ;

        void setCallback(Callback callback, Handler handler) throws ;

        void setExtras(Bundle bundle) throws ;

        void setFlags(int i) throws ;

        void setMediaButtonReceiver(PendingIntent pendingIntent) throws ;

        void setMetadata(MediaMetadataCompat mediaMetadataCompat) throws ;

        void setPlaybackState(PlaybackStateCompat playbackStateCompat) throws ;

        void setPlaybackToLocal(int i) throws ;

        void setPlaybackToRemote(VolumeProviderCompat volumeProviderCompat) throws ;

        void setQueue(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v4/media/session/MediaSessionCompat$QueueItem;", ">;)V"}) List<QueueItem> list) throws ;

        void setQueueTitle(CharSequence charSequence) throws ;

        void setRatingType(int i) throws ;

        void setSessionActivity(PendingIntent pendingIntent) throws ;
    }

    static class MediaSessionImplApi21 implements MediaSessionImpl {
        private PendingIntent mMediaButtonIntent;
        private final Object mSessionObj;
        private final Token mToken = new Token(MediaSessionCompatApi21.getSessionToken(this.mSessionObj));

        public Object getRemoteControlClient() throws  {
            return null;
        }

        public MediaSessionImplApi21(Context $r1, String $r2) throws  {
            this.mSessionObj = MediaSessionCompatApi21.createSession($r1, $r2);
        }

        public MediaSessionImplApi21(Object $r1) throws  {
            this.mSessionObj = MediaSessionCompatApi21.verifySession($r1);
        }

        public void setCallback(Callback $r1, Handler $r2) throws  {
            MediaSessionCompatApi21.setCallback(this.mSessionObj, $r1 == null ? null : $r1.mCallbackObj, $r2);
        }

        public void setFlags(int $i0) throws  {
            MediaSessionCompatApi21.setFlags(this.mSessionObj, $i0);
        }

        public void setPlaybackToLocal(int $i0) throws  {
            MediaSessionCompatApi21.setPlaybackToLocal(this.mSessionObj, $i0);
        }

        public void setPlaybackToRemote(VolumeProviderCompat $r1) throws  {
            MediaSessionCompatApi21.setPlaybackToRemote(this.mSessionObj, $r1.getVolumeProvider());
        }

        public void setActive(boolean $z0) throws  {
            MediaSessionCompatApi21.setActive(this.mSessionObj, $z0);
        }

        public boolean isActive() throws  {
            return MediaSessionCompatApi21.isActive(this.mSessionObj);
        }

        public void sendSessionEvent(String $r1, Bundle $r2) throws  {
            MediaSessionCompatApi21.sendSessionEvent(this.mSessionObj, $r1, $r2);
        }

        public void release() throws  {
            MediaSessionCompatApi21.release(this.mSessionObj);
        }

        public Token getSessionToken() throws  {
            return this.mToken;
        }

        public void setPlaybackState(PlaybackStateCompat $r1) throws  {
            MediaSessionCompatApi21.setPlaybackState(this.mSessionObj, $r1 == null ? null : $r1.getPlaybackState());
        }

        public void setMetadata(MediaMetadataCompat $r1) throws  {
            MediaSessionCompatApi21.setMetadata(this.mSessionObj, $r1 == null ? null : $r1.getMediaMetadata());
        }

        public void setSessionActivity(PendingIntent $r1) throws  {
            MediaSessionCompatApi21.setSessionActivity(this.mSessionObj, $r1);
        }

        public void setMediaButtonReceiver(PendingIntent $r1) throws  {
            this.mMediaButtonIntent = $r1;
            MediaSessionCompatApi21.setMediaButtonReceiver(this.mSessionObj, $r1);
        }

        public void setQueue(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v4/media/session/MediaSessionCompat$QueueItem;", ">;)V"}) List<QueueItem> $r1) throws  {
            ArrayList $r2 = null;
            if ($r1 != null) {
                $r2 = new ArrayList();
                for (QueueItem queueItem : $r1) {
                    $r2.add(queueItem.getQueueItem());
                }
            }
            MediaSessionCompatApi21.setQueue(this.mSessionObj, $r2);
        }

        public void setQueueTitle(CharSequence $r1) throws  {
            MediaSessionCompatApi21.setQueueTitle(this.mSessionObj, $r1);
        }

        public void setRatingType(int $i0) throws  {
            if (VERSION.SDK_INT >= 22) {
                MediaSessionCompatApi22.setRatingType(this.mSessionObj, $i0);
            }
        }

        public void setExtras(Bundle $r1) throws  {
            MediaSessionCompatApi21.setExtras(this.mSessionObj, $r1);
        }

        public Object getMediaSession() throws  {
            return this.mSessionObj;
        }
    }

    static class MediaSessionImplBase implements MediaSessionImpl {
        private final AudioManager mAudioManager;
        private volatile Callback mCallback;
        private final ComponentName mComponentName;
        private final Context mContext;
        private final RemoteCallbackList<IMediaControllerCallback> mControllerCallbacks = new RemoteCallbackList();
        private boolean mDestroyed = false;
        private Bundle mExtras;
        private int mFlags;
        private MessageHandler mHandler;
        private boolean mIsActive = false;
        private boolean mIsMbrRegistered = false;
        private boolean mIsRccRegistered = false;
        private int mLocalStream;
        private final Object mLock = new Object();
        private final PendingIntent mMediaButtonEventReceiver;
        private MediaMetadataCompat mMetadata;
        private final String mPackageName;
        private List<QueueItem> mQueue;
        private CharSequence mQueueTitle;
        private int mRatingType;
        private final Object mRccObj;
        private PendingIntent mSessionActivity;
        private PlaybackStateCompat mState;
        private final MediaSessionStub mStub;
        private final String mTag;
        private final Token mToken;
        private android.support.v4.media.VolumeProviderCompat.Callback mVolumeCallback = new C00871();
        private VolumeProviderCompat mVolumeProvider;
        private int mVolumeType;

        class C00871 extends android.support.v4.media.VolumeProviderCompat.Callback {
            C00871() throws  {
            }

            public void onVolumeChanged(VolumeProviderCompat $r1) throws  {
                if (MediaSessionImplBase.this.mVolumeProvider == $r1) {
                    MediaSessionImplBase.this.sendVolumeInfoChanged(new ParcelableVolumeInfo(MediaSessionImplBase.this.mVolumeType, MediaSessionImplBase.this.mLocalStream, $r1.getVolumeControl(), $r1.getMaxVolume(), $r1.getCurrentVolume()));
                }
            }
        }

        class C00882 implements Callback {
            C00882() throws  {
            }

            public void onSetRating(Object $r1) throws  {
                MediaSessionImplBase.this.postToHandler(12, RatingCompat.fromRating($r1));
            }

            public void onSeekTo(long $l0) throws  {
                MediaSessionImplBase.this.postToHandler(11, Long.valueOf($l0));
            }
        }

        private static final class Command {
            public final String command;
            public final Bundle extras;
            public final ResultReceiver stub;

            public Command(String $r1, Bundle $r2, ResultReceiver $r3) throws  {
                this.command = $r1;
                this.extras = $r2;
                this.stub = $r3;
            }
        }

        class MediaSessionStub extends Stub {
            MediaSessionStub() throws  {
            }

            public void sendCommand(String $r1, Bundle $r2, ResultReceiverWrapper $r3) throws  {
                MediaSessionImplBase.this.postToHandler(15, new Command($r1, $r2, $r3.mResultReceiver));
            }

            public boolean sendMediaButton(KeyEvent $r1) throws  {
                boolean $z0 = (MediaSessionImplBase.this.mFlags & 1) != 0;
                if (!$z0) {
                    return $z0;
                }
                MediaSessionImplBase.this.postToHandler(14, $r1);
                return $z0;
            }

            public void registerCallbackListener(IMediaControllerCallback $r1) throws  {
                if (MediaSessionImplBase.this.mDestroyed) {
                    try {
                        $r1.onSessionDestroyed();
                        return;
                    } catch (Exception e) {
                        return;
                    }
                }
                MediaSessionImplBase.this.mControllerCallbacks.register($r1);
            }

            public void unregisterCallbackListener(IMediaControllerCallback $r1) throws  {
                MediaSessionImplBase.this.mControllerCallbacks.unregister($r1);
            }

            public String getPackageName() throws  {
                return MediaSessionImplBase.this.mPackageName;
            }

            public String getTag() throws  {
                return MediaSessionImplBase.this.mTag;
            }

            public PendingIntent getLaunchPendingIntent() throws  {
                PendingIntent $r3;
                synchronized (MediaSessionImplBase.this.mLock) {
                    $r3 = MediaSessionImplBase.this.mSessionActivity;
                }
                return $r3;
            }

            public long getFlags() throws  {
                long $l0;
                synchronized (MediaSessionImplBase.this.mLock) {
                    $l0 = (long) MediaSessionImplBase.this.mFlags;
                }
                return $l0;
            }

            public ParcelableVolumeInfo getVolumeAttributes() throws  {
                int $i0;
                int $i1;
                int $i2;
                int $i3;
                int $i4;
                synchronized (MediaSessionImplBase.this.mLock) {
                    $i0 = MediaSessionImplBase.this.mVolumeType;
                    $i1 = MediaSessionImplBase.this.mLocalStream;
                    VolumeProviderCompat $r3 = MediaSessionImplBase.this.mVolumeProvider;
                    if ($i0 == 2) {
                        $i2 = $r3.getVolumeControl();
                        $i3 = $r3.getMaxVolume();
                        $i4 = $r3.getCurrentVolume();
                    } else {
                        $i2 = 2;
                        $i3 = MediaSessionImplBase.this.mAudioManager.getStreamMaxVolume($i1);
                        $i4 = MediaSessionImplBase.this.mAudioManager.getStreamVolume($i1);
                    }
                }
                return new ParcelableVolumeInfo($i0, $i1, $i2, $i3, $i4);
            }

            public void adjustVolume(int $i0, int $i1, String packageName) throws  {
                MediaSessionImplBase.this.adjustVolume($i0, $i1);
            }

            public void setVolumeTo(int $i0, int $i1, String packageName) throws  {
                MediaSessionImplBase.this.setVolumeTo($i0, $i1);
            }

            public void play() throws RemoteException {
                MediaSessionImplBase.this.postToHandler(1);
            }

            public void playFromMediaId(String $r1, Bundle $r2) throws RemoteException {
                MediaSessionImplBase.this.postToHandler(2, $r1, $r2);
            }

            public void playFromSearch(String $r1, Bundle $r2) throws RemoteException {
                MediaSessionImplBase.this.postToHandler(3, $r1, $r2);
            }

            public void playFromUri(Uri $r1, Bundle $r2) throws RemoteException {
                MediaSessionImplBase.this.postToHandler(18, $r1, $r2);
            }

            public void skipToQueueItem(long $l0) throws  {
                MediaSessionImplBase.this.postToHandler(4, Long.valueOf($l0));
            }

            public void pause() throws RemoteException {
                MediaSessionImplBase.this.postToHandler(5);
            }

            public void stop() throws RemoteException {
                MediaSessionImplBase.this.postToHandler(6);
            }

            public void next() throws RemoteException {
                MediaSessionImplBase.this.postToHandler(7);
            }

            public void previous() throws RemoteException {
                MediaSessionImplBase.this.postToHandler(8);
            }

            public void fastForward() throws RemoteException {
                MediaSessionImplBase.this.postToHandler(9);
            }

            public void rewind() throws RemoteException {
                MediaSessionImplBase.this.postToHandler(10);
            }

            public void seekTo(long $l0) throws RemoteException {
                MediaSessionImplBase.this.postToHandler(11, Long.valueOf($l0));
            }

            public void rate(RatingCompat $r1) throws RemoteException {
                MediaSessionImplBase.this.postToHandler(12, $r1);
            }

            public void sendCustomAction(String $r1, Bundle $r2) throws RemoteException {
                MediaSessionImplBase.this.postToHandler(13, $r1, $r2);
            }

            public MediaMetadataCompat getMetadata() throws  {
                return MediaSessionImplBase.this.mMetadata;
            }

            public PlaybackStateCompat getPlaybackState() throws  {
                return MediaSessionImplBase.this.getStateWithUpdatedPosition();
            }

            public List<QueueItem> getQueue() throws  {
                List $r3;
                synchronized (MediaSessionImplBase.this.mLock) {
                    $r3 = MediaSessionImplBase.this.mQueue;
                }
                return $r3;
            }

            public CharSequence getQueueTitle() throws  {
                return MediaSessionImplBase.this.mQueueTitle;
            }

            public Bundle getExtras() throws  {
                Bundle $r3;
                synchronized (MediaSessionImplBase.this.mLock) {
                    $r3 = MediaSessionImplBase.this.mExtras;
                }
                return $r3;
            }

            public int getRatingType() throws  {
                return MediaSessionImplBase.this.mRatingType;
            }

            public boolean isTransportControlEnabled() throws  {
                return (MediaSessionImplBase.this.mFlags & 2) != 0;
            }
        }

        private class MessageHandler extends Handler {
            private static final int KEYCODE_MEDIA_PAUSE = 127;
            private static final int KEYCODE_MEDIA_PLAY = 126;
            private static final int MSG_ADJUST_VOLUME = 16;
            private static final int MSG_COMMAND = 15;
            private static final int MSG_CUSTOM_ACTION = 13;
            private static final int MSG_FAST_FORWARD = 9;
            private static final int MSG_MEDIA_BUTTON = 14;
            private static final int MSG_NEXT = 7;
            private static final int MSG_PAUSE = 5;
            private static final int MSG_PLAY = 1;
            private static final int MSG_PLAY_MEDIA_ID = 2;
            private static final int MSG_PLAY_SEARCH = 3;
            private static final int MSG_PLAY_URI = 18;
            private static final int MSG_PREVIOUS = 8;
            private static final int MSG_RATE = 12;
            private static final int MSG_REWIND = 10;
            private static final int MSG_SEEK_TO = 11;
            private static final int MSG_SET_VOLUME = 17;
            private static final int MSG_SKIP_TO_ITEM = 4;
            private static final int MSG_STOP = 6;

            public MessageHandler(Looper $r2) throws  {
                super($r2);
            }

            public void post(int $i0, Object $r1, Bundle $r2) throws  {
                Message $r3 = obtainMessage($i0, $r1);
                $r3.setData($r2);
                $r3.sendToTarget();
            }

            public void post(int $i0, Object $r1) throws  {
                obtainMessage($i0, $r1).sendToTarget();
            }

            public void post(int $i0) throws  {
                post($i0, null);
            }

            public void post(int $i0, Object $r1, int $i1) throws  {
                obtainMessage($i0, $i1, 0, $r1).sendToTarget();
            }

            public void handleMessage(Message $r1) throws  {
                Callback $r4 = MediaSessionImplBase.this.mCallback;
                if ($r4 != null) {
                    switch ($r1.what) {
                        case 1:
                            $r4.onPlay();
                            return;
                        case 2:
                            $r4.onPlayFromMediaId((String) $r1.obj, $r1.getData());
                            return;
                        case 3:
                            $r4.onPlayFromSearch((String) $r1.obj, $r1.getData());
                            return;
                        case 4:
                            $r4.onSkipToQueueItem(((Long) $r1.obj).longValue());
                            return;
                        case 5:
                            $r4.onPause();
                            return;
                        case 6:
                            $r4.onStop();
                            return;
                        case 7:
                            $r4.onSkipToNext();
                            return;
                        case 8:
                            $r4.onSkipToPrevious();
                            return;
                        case 9:
                            $r4.onFastForward();
                            return;
                        case 10:
                            $r4.onRewind();
                            return;
                        case 11:
                            $r4.onSeekTo(((Long) $r1.obj).longValue());
                            return;
                        case 12:
                            $r4.onSetRating((RatingCompat) $r1.obj);
                            return;
                        case 13:
                            $r4.onCustomAction((String) $r1.obj, $r1.getData());
                            return;
                        case 14:
                            KeyEvent $r11 = (KeyEvent) $r1.obj;
                            Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                            intent.putExtra("android.intent.extra.KEY_EVENT", $r11);
                            if (!$r4.onMediaButtonEvent(intent)) {
                                onMediaButtonEvent($r11, $r4);
                                return;
                            }
                            return;
                        case 15:
                            Command $r12 = (Command) $r1.obj;
                            String $r6 = $r12.command;
                            Bundle $r7 = $r12.extras;
                            ResultReceiver $r13 = $r12.stub;
                            $r4.onCommand($r6, $r7, $r13);
                            return;
                        case 16:
                            MediaSessionImplBase.this.adjustVolume(((Integer) $r1.obj).intValue(), 0);
                            return;
                        case 17:
                            MediaSessionImplBase.this.setVolumeTo(((Integer) $r1.obj).intValue(), 0);
                            return;
                        case 18:
                            $r4.onPlayFromUri((Uri) $r1.obj, $r1.getData());
                            return;
                        default:
                            return;
                    }
                }
            }

            private void onMediaButtonEvent(KeyEvent $r1, Callback $r2) throws  {
                boolean $z0 = true;
                if ($r1 != null && $r1.getAction() == 0) {
                    long $l1 = MediaSessionImplBase.this.mState == null ? 0 : MediaSessionImplBase.this.mState.getActions();
                    switch ($r1.getKeyCode()) {
                        case 79:
                        case 85:
                            boolean $z1;
                            if (MediaSessionImplBase.this.mState == null || MediaSessionImplBase.this.mState.getState() != 3) {
                                $z1 = false;
                            } else {
                                $z1 = true;
                            }
                            boolean $z2 = (516 & $l1) != 0;
                            if ((514 & $l1) == 0) {
                                $z0 = false;
                            }
                            if ($z1 && $z0) {
                                $r2.onPause();
                                return;
                            } else if (!$z1 && $z2) {
                                $r2.onPlay();
                                return;
                            } else {
                                return;
                            }
                        case 86:
                            if ((1 & $l1) != 0) {
                                $r2.onStop();
                                return;
                            }
                            return;
                        case 87:
                            if ((32 & $l1) != 0) {
                                $r2.onSkipToNext();
                                return;
                            }
                            return;
                        case 88:
                            if ((16 & $l1) != 0) {
                                $r2.onSkipToPrevious();
                                return;
                            }
                            return;
                        case 89:
                            if ((8 & $l1) != 0) {
                                $r2.onRewind();
                                return;
                            }
                            return;
                        case 90:
                            if ((64 & $l1) != 0) {
                                $r2.onFastForward();
                                return;
                            }
                            return;
                        case 126:
                            if ((4 & $l1) != 0) {
                                $r2.onPlay();
                                return;
                            }
                            return;
                        case 127:
                            if ((2 & $l1) != 0) {
                                $r2.onPause();
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                }
            }
        }

        public Object getMediaSession() throws  {
            return null;
        }

        public MediaSessionImplBase(Context $r1, String $r2, ComponentName $r3, PendingIntent $r4) throws  {
            if ($r3 == null) {
                throw new IllegalArgumentException("MediaButtonReceiver component may not be null.");
            }
            this.mContext = $r1;
            this.mPackageName = $r1.getPackageName();
            this.mAudioManager = (AudioManager) $r1.getSystemService("audio");
            this.mTag = $r2;
            this.mComponentName = $r3;
            this.mMediaButtonEventReceiver = $r4;
            this.mStub = new MediaSessionStub();
            this.mToken = new Token(this.mStub);
            this.mRatingType = 0;
            this.mVolumeType = 1;
            this.mLocalStream = 3;
            if (VERSION.SDK_INT >= 14) {
                this.mRccObj = MediaSessionCompatApi14.createRemoteControlClient($r4);
            } else {
                this.mRccObj = null;
            }
        }

        public void setCallback(Callback $r1, Handler $r3) throws  {
            this.mCallback = $r1;
            if ($r1 == null) {
                if (VERSION.SDK_INT >= 18) {
                    MediaSessionCompatApi18.setOnPlaybackPositionUpdateListener(this.mRccObj, null);
                }
                if (VERSION.SDK_INT >= 19) {
                    MediaSessionCompatApi19.setOnMetadataUpdateListener(this.mRccObj, null);
                    return;
                }
                return;
            }
            if ($r3 == null) {
                $r3 = new Handler();
            }
            synchronized (this.mLock) {
                this.mHandler = new MessageHandler($r3.getLooper());
            }
            C00882 $r2 = new C00882();
            if (VERSION.SDK_INT >= 18) {
                MediaSessionCompatApi18.setOnPlaybackPositionUpdateListener(this.mRccObj, MediaSessionCompatApi18.createPlaybackPositionUpdateListener($r2));
            }
            if (VERSION.SDK_INT >= 19) {
                MediaSessionCompatApi19.setOnMetadataUpdateListener(this.mRccObj, MediaSessionCompatApi19.createMetadataUpdateListener($r2));
            }
        }

        private void postToHandler(int $i0) throws  {
            postToHandler($i0, null);
        }

        private void postToHandler(int $i0, Object $r1) throws  {
            postToHandler($i0, $r1, null);
        }

        private void postToHandler(int $i0, Object $r1, Bundle $r2) throws  {
            synchronized (this.mLock) {
                if (this.mHandler != null) {
                    this.mHandler.post($i0, $r1, $r2);
                }
            }
        }

        public void setFlags(int $i0) throws  {
            synchronized (this.mLock) {
                this.mFlags = $i0;
            }
            update();
        }

        public void setPlaybackToLocal(int stream) throws  {
            if (this.mVolumeProvider != null) {
                this.mVolumeProvider.setCallback(null);
            }
            this.mVolumeType = 1;
            sendVolumeInfoChanged(new ParcelableVolumeInfo(this.mVolumeType, this.mLocalStream, 2, this.mAudioManager.getStreamMaxVolume(this.mLocalStream), this.mAudioManager.getStreamVolume(this.mLocalStream)));
        }

        public void setPlaybackToRemote(VolumeProviderCompat $r1) throws  {
            if ($r1 == null) {
                throw new IllegalArgumentException("volumeProvider may not be null");
            }
            if (this.mVolumeProvider != null) {
                this.mVolumeProvider.setCallback(null);
            }
            this.mVolumeType = 2;
            this.mVolumeProvider = $r1;
            sendVolumeInfoChanged(new ParcelableVolumeInfo(this.mVolumeType, this.mLocalStream, this.mVolumeProvider.getVolumeControl(), this.mVolumeProvider.getMaxVolume(), this.mVolumeProvider.getCurrentVolume()));
            $r1.setCallback(this.mVolumeCallback);
        }

        public void setActive(boolean $z0) throws  {
            if ($z0 != this.mIsActive) {
                this.mIsActive = $z0;
                if (update()) {
                    setMetadata(this.mMetadata);
                    setPlaybackState(this.mState);
                }
            }
        }

        public boolean isActive() throws  {
            return this.mIsActive;
        }

        public void sendSessionEvent(String $r1, Bundle $r2) throws  {
            sendEvent($r1, $r2);
        }

        public void release() throws  {
            this.mIsActive = false;
            this.mDestroyed = true;
            update();
            sendSessionDestroyed();
        }

        public Token getSessionToken() throws  {
            return this.mToken;
        }

        public void setPlaybackState(PlaybackStateCompat $r1) throws  {
            synchronized (this.mLock) {
                this.mState = $r1;
            }
            sendState($r1);
            if (!this.mIsActive) {
                return;
            }
            if ($r1 != null) {
                if (VERSION.SDK_INT >= 18) {
                    MediaSessionCompatApi18.setState(this.mRccObj, $r1.getState(), $r1.getPosition(), $r1.getPlaybackSpeed(), $r1.getLastPositionUpdateTime());
                } else if (VERSION.SDK_INT >= 14) {
                    MediaSessionCompatApi14.setState(this.mRccObj, $r1.getState());
                }
                if (VERSION.SDK_INT >= 19) {
                    MediaSessionCompatApi19.setTransportControlFlags(this.mRccObj, $r1.getActions());
                } else if (VERSION.SDK_INT >= 18) {
                    MediaSessionCompatApi18.setTransportControlFlags(this.mRccObj, $r1.getActions());
                } else if (VERSION.SDK_INT >= 14) {
                    MediaSessionCompatApi14.setTransportControlFlags(this.mRccObj, $r1.getActions());
                }
            } else if (VERSION.SDK_INT >= 14) {
                MediaSessionCompatApi14.setState(this.mRccObj, 0);
                MediaSessionCompatApi14.setTransportControlFlags(this.mRccObj, 0);
            }
        }

        private MediaMetadataCompat cloneMetadataIfNeeded(MediaMetadataCompat $r2) throws  {
            if ($r2 == null) {
                return null;
            }
            if (!$r2.containsKey(MediaMetadataCompat.METADATA_KEY_ART) && !$r2.containsKey(MediaMetadataCompat.METADATA_KEY_ALBUM_ART)) {
                return $r2;
            }
            Builder $r1 = new Builder($r2);
            Bitmap $r3 = $r2.getBitmap(MediaMetadataCompat.METADATA_KEY_ART);
            if ($r3 != null) {
                $r1.putBitmap(MediaMetadataCompat.METADATA_KEY_ART, $r3.copy($r3.getConfig(), false));
            }
            $r3 = $r2.getBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART);
            if ($r3 != null) {
                $r1.putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, $r3.copy($r3.getConfig(), false));
            }
            return $r1.build();
        }

        public void setMetadata(MediaMetadataCompat $r1) throws  {
            Bundle $r2 = null;
            if (VERSION.SDK_INT >= 14 && $r1 != null) {
                $r1 = cloneMetadataIfNeeded($r1);
            }
            synchronized (this.mLock) {
                this.mMetadata = $r1;
            }
            sendMetadata($r1);
            if (!this.mIsActive) {
                return;
            }
            Object $r3;
            if (VERSION.SDK_INT >= 19) {
                $r3 = this.mRccObj;
                if ($r1 != null) {
                    $r2 = $r1.getBundle();
                }
                MediaSessionCompatApi19.setMetadata($r3, $r2, this.mState == null ? 0 : this.mState.getActions());
            } else if (VERSION.SDK_INT >= 14) {
                $r3 = this.mRccObj;
                if ($r1 != null) {
                    $r2 = $r1.getBundle();
                }
                MediaSessionCompatApi14.setMetadata($r3, $r2);
            }
        }

        public void setSessionActivity(PendingIntent $r1) throws  {
            synchronized (this.mLock) {
                this.mSessionActivity = $r1;
            }
        }

        public void setMediaButtonReceiver(PendingIntent mbr) throws  {
        }

        public void setQueue(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v4/media/session/MediaSessionCompat$QueueItem;", ">;)V"}) List<QueueItem> $r1) throws  {
            this.mQueue = $r1;
            sendQueue($r1);
        }

        public void setQueueTitle(CharSequence $r1) throws  {
            this.mQueueTitle = $r1;
            sendQueueTitle($r1);
        }

        public Object getRemoteControlClient() throws  {
            return this.mRccObj;
        }

        public void setRatingType(int $i0) throws  {
            this.mRatingType = $i0;
        }

        public void setExtras(Bundle $r1) throws  {
            this.mExtras = $r1;
        }

        private boolean update() throws  {
            if (this.mIsActive) {
                if (VERSION.SDK_INT >= 8) {
                    if (!this.mIsMbrRegistered && (this.mFlags & 1) != 0) {
                        if (VERSION.SDK_INT >= 18) {
                            MediaSessionCompatApi18.registerMediaButtonEventReceiver(this.mContext, this.mMediaButtonEventReceiver, this.mComponentName);
                        } else {
                            MediaSessionCompatApi8.registerMediaButtonEventReceiver(this.mContext, this.mComponentName);
                        }
                        this.mIsMbrRegistered = true;
                    } else if (this.mIsMbrRegistered && (this.mFlags & 1) == 0) {
                        if (VERSION.SDK_INT >= 18) {
                            MediaSessionCompatApi18.unregisterMediaButtonEventReceiver(this.mContext, this.mMediaButtonEventReceiver, this.mComponentName);
                        } else {
                            MediaSessionCompatApi8.unregisterMediaButtonEventReceiver(this.mContext, this.mComponentName);
                        }
                        this.mIsMbrRegistered = false;
                    }
                }
                if (VERSION.SDK_INT < 14) {
                    return false;
                }
                if (!this.mIsRccRegistered && (this.mFlags & 2) != 0) {
                    MediaSessionCompatApi14.registerRemoteControlClient(this.mContext, this.mRccObj);
                    this.mIsRccRegistered = true;
                    return true;
                } else if (!this.mIsRccRegistered) {
                    return false;
                } else {
                    if ((this.mFlags & 2) != 0) {
                        return false;
                    }
                    MediaSessionCompatApi14.setState(this.mRccObj, 0);
                    MediaSessionCompatApi14.unregisterRemoteControlClient(this.mContext, this.mRccObj);
                    this.mIsRccRegistered = false;
                    return false;
                }
            }
            if (this.mIsMbrRegistered) {
                if (VERSION.SDK_INT >= 18) {
                    MediaSessionCompatApi18.unregisterMediaButtonEventReceiver(this.mContext, this.mMediaButtonEventReceiver, this.mComponentName);
                } else {
                    MediaSessionCompatApi8.unregisterMediaButtonEventReceiver(this.mContext, this.mComponentName);
                }
                this.mIsMbrRegistered = false;
            }
            if (!this.mIsRccRegistered) {
                return false;
            }
            MediaSessionCompatApi14.setState(this.mRccObj, 0);
            MediaSessionCompatApi14.unregisterRemoteControlClient(this.mContext, this.mRccObj);
            this.mIsRccRegistered = false;
            return false;
        }

        private void adjustVolume(int $i0, int $i1) throws  {
            if (this.mVolumeType != 2) {
                this.mAudioManager.adjustStreamVolume(this.mLocalStream, $i0, $i1);
            } else if (this.mVolumeProvider != null) {
                this.mVolumeProvider.onAdjustVolume($i0);
            }
        }

        private void setVolumeTo(int $i0, int $i1) throws  {
            if (this.mVolumeType != 2) {
                this.mAudioManager.setStreamVolume(this.mLocalStream, $i0, $i1);
            } else if (this.mVolumeProvider != null) {
                this.mVolumeProvider.onSetVolumeTo($i0);
            }
        }

        private PlaybackStateCompat getStateWithUpdatedPosition() throws  {
            long $l0 = -1;
            synchronized (this.mLock) {
                PlaybackStateCompat $r3 = this.mState;
                if (this.mMetadata != null && this.mMetadata.containsKey(MediaMetadataCompat.METADATA_KEY_DURATION)) {
                    $l0 = this.mMetadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION);
                }
            }
            PlaybackStateCompat $r5 = null;
            if ($r3 != null && ($r3.getState() == 3 || $r3.getState() == 4 || $r3.getState() == 5)) {
                long $l2 = $r3.getLastPositionUpdateTime();
                long $l3 = SystemClock.elapsedRealtime();
                if ($l2 > 0) {
                    long $l22 = $r3.getPlaybackSpeed() * ((float) ($l3 - $l2));
                    Object obj = $l22;
                    $l2 = ((long) $l22) + $r3.getPosition();
                    if ($l0 >= 0 && $l2 > $l0) {
                        $l2 = $l0;
                    } else if ($l2 < 0) {
                        $l2 = 0;
                    }
                    $l22 = new PlaybackStateCompat.Builder($r3);
                    $l22.setState($r3.getState(), $l2, $r3.getPlaybackSpeed(), $l3);
                    $r5 = $l22.build();
                }
            }
            if ($r5 == null) {
                return $r3;
            }
            return $r5;
        }

        private void sendVolumeInfoChanged(ParcelableVolumeInfo $r1) throws  {
            for (int $i0 = this.mControllerCallbacks.beginBroadcast() - 1; $i0 >= 0; $i0--) {
                try {
                    ((IMediaControllerCallback) this.mControllerCallbacks.getBroadcastItem($i0)).onVolumeInfoChanged($r1);
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        private void sendSessionDestroyed() throws  {
            for (int $i0 = this.mControllerCallbacks.beginBroadcast() - 1; $i0 >= 0; $i0--) {
                try {
                    ((IMediaControllerCallback) this.mControllerCallbacks.getBroadcastItem($i0)).onSessionDestroyed();
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
            this.mControllerCallbacks.kill();
        }

        private void sendEvent(String $r1, Bundle $r2) throws  {
            for (int $i0 = this.mControllerCallbacks.beginBroadcast() - 1; $i0 >= 0; $i0--) {
                try {
                    ((IMediaControllerCallback) this.mControllerCallbacks.getBroadcastItem($i0)).onEvent($r1, $r2);
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        private void sendState(PlaybackStateCompat $r1) throws  {
            for (int $i0 = this.mControllerCallbacks.beginBroadcast() - 1; $i0 >= 0; $i0--) {
                try {
                    ((IMediaControllerCallback) this.mControllerCallbacks.getBroadcastItem($i0)).onPlaybackStateChanged($r1);
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        private void sendMetadata(MediaMetadataCompat $r1) throws  {
            for (int $i0 = this.mControllerCallbacks.beginBroadcast() - 1; $i0 >= 0; $i0--) {
                try {
                    ((IMediaControllerCallback) this.mControllerCallbacks.getBroadcastItem($i0)).onMetadataChanged($r1);
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        private void sendQueue(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v4/media/session/MediaSessionCompat$QueueItem;", ">;)V"}) List<QueueItem> $r1) throws  {
            for (int $i0 = this.mControllerCallbacks.beginBroadcast() - 1; $i0 >= 0; $i0--) {
                try {
                    ((IMediaControllerCallback) this.mControllerCallbacks.getBroadcastItem($i0)).onQueueChanged($r1);
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        private void sendQueueTitle(CharSequence $r1) throws  {
            for (int $i0 = this.mControllerCallbacks.beginBroadcast() - 1; $i0 >= 0; $i0--) {
                try {
                    ((IMediaControllerCallback) this.mControllerCallbacks.getBroadcastItem($i0)).onQueueTitleChanged($r1);
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }
    }

    public interface OnActiveChangeListener {
        void onActiveChanged() throws ;
    }

    public static final class QueueItem implements Parcelable {
        public static final Creator<QueueItem> CREATOR = new C00891();
        public static final int UNKNOWN_ID = -1;
        private final MediaDescriptionCompat mDescription;
        private final long mId;
        private Object mItem;

        static class C00891 implements Creator<QueueItem> {
            C00891() throws  {
            }

            public QueueItem createFromParcel(Parcel $r1) throws  {
                return new QueueItem($r1);
            }

            public QueueItem[] newArray(int $i0) throws  {
                return new QueueItem[$i0];
            }
        }

        public int describeContents() throws  {
            return 0;
        }

        public QueueItem(MediaDescriptionCompat $r1, long $l0) throws  {
            this(null, $r1, $l0);
        }

        private QueueItem(Object $r1, MediaDescriptionCompat $r2, long $l0) throws  {
            if ($r2 == null) {
                throw new IllegalArgumentException("Description cannot be null.");
            } else if ($l0 == -1) {
                throw new IllegalArgumentException("Id cannot be QueueItem.UNKNOWN_ID");
            } else {
                this.mDescription = $r2;
                this.mId = $l0;
                this.mItem = $r1;
            }
        }

        private QueueItem(Parcel $r1) throws  {
            this.mDescription = (MediaDescriptionCompat) MediaDescriptionCompat.CREATOR.createFromParcel($r1);
            this.mId = $r1.readLong();
        }

        public MediaDescriptionCompat getDescription() throws  {
            return this.mDescription;
        }

        public long getQueueId() throws  {
            return this.mId;
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            this.mDescription.writeToParcel($r1, $i0);
            $r1.writeLong(this.mId);
        }

        public Object getQueueItem() throws  {
            if (this.mItem != null || VERSION.SDK_INT < 21) {
                return this.mItem;
            }
            this.mItem = QueueItem.createItem(this.mDescription.getMediaDescription(), this.mId);
            return this.mItem;
        }

        public static QueueItem obtain(Object $r0) throws  {
            return new QueueItem($r0, MediaDescriptionCompat.fromMediaDescription(QueueItem.getDescription($r0)), QueueItem.getQueueId($r0));
        }

        public String toString() throws  {
            return "MediaSession.QueueItem {Description=" + this.mDescription + ", Id=" + this.mId + " }";
        }
    }

    static final class ResultReceiverWrapper implements Parcelable {
        public static final Creator<ResultReceiverWrapper> CREATOR = new C00901();
        private ResultReceiver mResultReceiver;

        static class C00901 implements Creator<ResultReceiverWrapper> {
            C00901() throws  {
            }

            public ResultReceiverWrapper createFromParcel(Parcel $r1) throws  {
                return new ResultReceiverWrapper($r1);
            }

            public ResultReceiverWrapper[] newArray(int $i0) throws  {
                return new ResultReceiverWrapper[$i0];
            }
        }

        public int describeContents() throws  {
            return 0;
        }

        public ResultReceiverWrapper(ResultReceiver $r1) throws  {
            this.mResultReceiver = $r1;
        }

        ResultReceiverWrapper(Parcel $r1) throws  {
            this.mResultReceiver = (ResultReceiver) ResultReceiver.CREATOR.createFromParcel($r1);
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            this.mResultReceiver.writeToParcel($r1, $i0);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SessionFlags {
    }

    public static final class Token implements Parcelable {
        public static final Creator<Token> CREATOR = new C00911();
        private final Object mInner;

        static class C00911 implements Creator<Token> {
            C00911() throws  {
            }

            public Token createFromParcel(Parcel $r1) throws  {
                Object $r3;
                if (VERSION.SDK_INT >= 21) {
                    $r3 = $r1.readParcelable(null);
                } else {
                    $r3 = $r1.readStrongBinder();
                }
                return new Token($r3);
            }

            public Token[] newArray(int $i0) throws  {
                return new Token[$i0];
            }
        }

        public int describeContents() throws  {
            return 0;
        }

        Token(Object $r1) throws  {
            this.mInner = $r1;
        }

        public static Token fromToken(Object $r0) throws  {
            return ($r0 == null || VERSION.SDK_INT < 21) ? null : new Token(MediaSessionCompatApi21.verifyToken($r0));
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            if (VERSION.SDK_INT >= 21) {
                $r1.writeParcelable((Parcelable) this.mInner, $i0);
            } else {
                $r1.writeStrongBinder((IBinder) this.mInner);
            }
        }

        public Object getToken() throws  {
            return this.mInner;
        }
    }

    public MediaSessionCompat(Context $r1, String $r2) throws  {
        this($r1, $r2, null, null);
    }

    public MediaSessionCompat(Context $r1, String $r2, ComponentName $r4, PendingIntent $r5) throws  {
        MediaSessionCompat mediaSessionCompat = this;
        this.mActiveListeners = new ArrayList();
        if ($r1 == null) {
            throw new IllegalArgumentException("context must not be null");
        } else if (TextUtils.isEmpty($r2)) {
            throw new IllegalArgumentException("tag must not be null or empty");
        } else {
            Intent $r3;
            if ($r4 == null) {
                $r3 = new Intent("android.intent.action.MEDIA_BUTTON");
                $r3.setPackage($r1.getPackageName());
                List $r10 = $r1.getPackageManager().queryBroadcastReceivers($r3, 0);
                if ($r10.size() == 1) {
                    ResolveInfo $r12 = (ResolveInfo) $r10.get(0);
                    ActivityInfo $r13 = $r12.activityInfo;
                    String $r8 = $r13.packageName;
                    String $r14 = $r12.activityInfo;
                    String $r132 = $r14;
                    ComponentName componentName = new ComponentName($r8, $r14.name);
                } else if ($r10.size() > 1) {
                    Log.w(TAG, "More than one BroadcastReceiver that handles android.intent.action.MEDIA_BUTTON was found, using null. Provide a specific ComponentName to use as this session's media button receiver");
                }
            }
            if ($r4 != null && $r5 == null) {
                $r3 = new Intent("android.intent.action.MEDIA_BUTTON");
                $r3.setComponent($r4);
                $r5 = PendingIntent.getBroadcast($r1, 0, $r3, 0);
            }
            if (VERSION.SDK_INT >= 21) {
                this.mImpl = new MediaSessionImplApi21($r1, $r2);
                MediaSessionImpl mediaSessionImpl = this.mImpl;
                MediaSessionImpl $r16 = mediaSessionImpl;
                mediaSessionImpl.setMediaButtonReceiver($r5);
            } else {
                this.mImpl = new MediaSessionImplBase($r1, $r2, $r4, $r5);
            }
            this.mController = new MediaControllerCompat($r1, this);
        }
    }

    private MediaSessionCompat(Context $r1, MediaSessionImpl $r2) throws  {
        this.mActiveListeners = new ArrayList();
        this.mImpl = $r2;
        this.mController = new MediaControllerCompat($r1, this);
    }

    public void setCallback(Callback $r1) throws  {
        setCallback($r1, null);
    }

    public void setCallback(Callback $r1, Handler $r3) throws  {
        MediaSessionImpl $r2 = this.mImpl;
        if ($r3 == null) {
            $r3 = new Handler();
        }
        $r2.setCallback($r1, $r3);
    }

    public void setSessionActivity(PendingIntent $r1) throws  {
        this.mImpl.setSessionActivity($r1);
    }

    public void setMediaButtonReceiver(PendingIntent $r1) throws  {
        this.mImpl.setMediaButtonReceiver($r1);
    }

    public void setFlags(int $i0) throws  {
        this.mImpl.setFlags($i0);
    }

    public void setPlaybackToLocal(int $i0) throws  {
        this.mImpl.setPlaybackToLocal($i0);
    }

    public void setPlaybackToRemote(VolumeProviderCompat $r1) throws  {
        if ($r1 == null) {
            throw new IllegalArgumentException("volumeProvider may not be null!");
        }
        this.mImpl.setPlaybackToRemote($r1);
    }

    public void setActive(boolean $z0) throws  {
        this.mImpl.setActive($z0);
        Iterator $r3 = this.mActiveListeners.iterator();
        while ($r3.hasNext()) {
            ((OnActiveChangeListener) $r3.next()).onActiveChanged();
        }
    }

    public boolean isActive() throws  {
        return this.mImpl.isActive();
    }

    public void sendSessionEvent(String $r1, Bundle $r2) throws  {
        if (TextUtils.isEmpty($r1)) {
            throw new IllegalArgumentException("event cannot be null or empty");
        }
        this.mImpl.sendSessionEvent($r1, $r2);
    }

    public void release() throws  {
        this.mImpl.release();
    }

    public Token getSessionToken() throws  {
        return this.mImpl.getSessionToken();
    }

    public MediaControllerCompat getController() throws  {
        return this.mController;
    }

    public void setPlaybackState(PlaybackStateCompat $r1) throws  {
        this.mImpl.setPlaybackState($r1);
    }

    public void setMetadata(MediaMetadataCompat $r1) throws  {
        this.mImpl.setMetadata($r1);
    }

    public void setQueue(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v4/media/session/MediaSessionCompat$QueueItem;", ">;)V"}) List<QueueItem> $r1) throws  {
        this.mImpl.setQueue($r1);
    }

    public void setQueueTitle(CharSequence $r1) throws  {
        this.mImpl.setQueueTitle($r1);
    }

    public void setRatingType(int $i0) throws  {
        this.mImpl.setRatingType($i0);
    }

    public void setExtras(Bundle $r1) throws  {
        this.mImpl.setExtras($r1);
    }

    public Object getMediaSession() throws  {
        return this.mImpl.getMediaSession();
    }

    public Object getRemoteControlClient() throws  {
        return this.mImpl.getRemoteControlClient();
    }

    public void addOnActiveChangeListener(OnActiveChangeListener $r1) throws  {
        if ($r1 == null) {
            throw new IllegalArgumentException("Listener may not be null");
        }
        this.mActiveListeners.add($r1);
    }

    public void removeOnActiveChangeListener(OnActiveChangeListener $r1) throws  {
        if ($r1 == null) {
            throw new IllegalArgumentException("Listener may not be null");
        }
        this.mActiveListeners.remove($r1);
    }

    public static MediaSessionCompat obtain(Context $r0, Object $r1) throws  {
        return new MediaSessionCompat($r0, new MediaSessionImplApi21($r1));
    }
}
