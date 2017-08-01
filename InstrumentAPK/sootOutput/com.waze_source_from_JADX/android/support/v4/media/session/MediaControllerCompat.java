package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.IMediaControllerCallback.Stub;
import android.support.v4.media.session.MediaSessionCompat.QueueItem;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.support.v4.media.session.PlaybackStateCompat.CustomAction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

public final class MediaControllerCompat {
    private static final String TAG = "MediaControllerCompat";
    private final MediaControllerImpl mImpl;
    private final Token mToken;

    public static abstract class Callback implements DeathRecipient {
        private final Object mCallbackObj;
        private MessageHandler mHandler;
        private boolean mRegistered = false;

        private class MessageHandler extends Handler {
            private static final int MSG_DESTROYED = 8;
            private static final int MSG_EVENT = 1;
            private static final int MSG_UPDATE_EXTRAS = 7;
            private static final int MSG_UPDATE_METADATA = 3;
            private static final int MSG_UPDATE_PLAYBACK_STATE = 2;
            private static final int MSG_UPDATE_QUEUE = 5;
            private static final int MSG_UPDATE_QUEUE_TITLE = 6;
            private static final int MSG_UPDATE_VOLUME = 4;

            public MessageHandler(Looper $r2) throws  {
                super($r2);
            }

            public void handleMessage(Message $r1) throws  {
                this = this;
                if (Callback.this.mRegistered) {
                    switch ($r1.what) {
                        case 1:
                            Callback.this.onSessionEvent((String) $r1.obj, $r1.getData());
                            return;
                        case 2:
                            Callback.this.onPlaybackStateChanged((PlaybackStateCompat) $r1.obj);
                            return;
                        case 3:
                            Callback.this.onMetadataChanged((MediaMetadataCompat) $r1.obj);
                            return;
                        case 4:
                            Callback.this.onAudioInfoChanged((PlaybackInfo) $r1.obj);
                            return;
                        case 5:
                            Callback.this.onQueueChanged((List) $r1.obj);
                            return;
                        case 6:
                            Callback.this.onQueueTitleChanged((CharSequence) $r1.obj);
                            return;
                        case 7:
                            Callback.this.onExtrasChanged((Bundle) $r1.obj);
                            return;
                        case 8:
                            Callback.this.onSessionDestroyed();
                            return;
                        default:
                            return;
                    }
                }
            }

            public void post(int $i0, Object $r1, Bundle $r2) throws  {
                Message $r3 = obtainMessage($i0, $r1);
                $r3.setData($r2);
                $r3.sendToTarget();
            }
        }

        private class StubApi21 implements android.support.v4.media.session.MediaControllerCompatApi21.Callback {
            private StubApi21() throws  {
            }

            public void onSessionDestroyed() throws  {
                Callback.this.onSessionDestroyed();
            }

            public void onSessionEvent(String $r1, Bundle $r2) throws  {
                Callback.this.onSessionEvent($r1, $r2);
            }

            public void onPlaybackStateChanged(Object $r1) throws  {
                Callback.this.onPlaybackStateChanged(PlaybackStateCompat.fromPlaybackState($r1));
            }

            public void onMetadataChanged(Object $r1) throws  {
                Callback.this.onMetadataChanged(MediaMetadataCompat.fromMediaMetadata($r1));
            }
        }

        private class StubCompat extends Stub {
            private StubCompat() throws  {
            }

            public void onEvent(String $r1, Bundle $r2) throws RemoteException {
                Callback.this.mHandler.post(1, $r1, $r2);
            }

            public void onSessionDestroyed() throws RemoteException {
                Callback.this.mHandler.post(8, null, null);
            }

            public void onPlaybackStateChanged(PlaybackStateCompat $r1) throws RemoteException {
                Callback.this.mHandler.post(2, $r1, null);
            }

            public void onMetadataChanged(MediaMetadataCompat $r1) throws RemoteException {
                Callback.this.mHandler.post(3, $r1, null);
            }

            public void onQueueChanged(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v4/media/session/MediaSessionCompat$QueueItem;", ">;)V"}) List<QueueItem> $r1) throws RemoteException {
                Callback.this.mHandler.post(5, $r1, null);
            }

            public void onQueueTitleChanged(CharSequence $r1) throws RemoteException {
                Callback.this.mHandler.post(6, $r1, null);
            }

            public void onExtrasChanged(Bundle $r1) throws RemoteException {
                Callback.this.mHandler.post(7, $r1, null);
            }

            public void onVolumeInfoChanged(ParcelableVolumeInfo $r1) throws RemoteException {
                PlaybackInfo $r3 = null;
                if ($r1 != null) {
                    $r3 = new PlaybackInfo($r1.volumeType, $r1.audioStream, $r1.controlType, $r1.maxVolume, $r1.currentVolume);
                }
                Callback.this.mHandler.post(4, $r3, null);
            }
        }

        public Callback() throws  {
            if (VERSION.SDK_INT >= 21) {
                this.mCallbackObj = MediaControllerCompatApi21.createCallback(new StubApi21());
            } else {
                this.mCallbackObj = new StubCompat();
            }
        }

        public void onSessionDestroyed() throws  {
        }

        public void onSessionEvent(String event, Bundle extras) throws  {
        }

        public void onPlaybackStateChanged(PlaybackStateCompat state) throws  {
        }

        public void onMetadataChanged(MediaMetadataCompat metadata) throws  {
        }

        public void onQueueChanged(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v4/media/session/MediaSessionCompat$QueueItem;", ">;)V"}) List<QueueItem> list) throws  {
        }

        public void onQueueTitleChanged(CharSequence title) throws  {
        }

        public void onExtrasChanged(Bundle extras) throws  {
        }

        public void onAudioInfoChanged(PlaybackInfo info) throws  {
        }

        public void binderDied() throws  {
            onSessionDestroyed();
        }

        private void setHandler(Handler $r1) throws  {
            this.mHandler = new MessageHandler($r1.getLooper());
        }
    }

    interface MediaControllerImpl {
        void adjustVolume(int i, int i2) throws ;

        boolean dispatchMediaButtonEvent(KeyEvent keyEvent) throws ;

        Bundle getExtras() throws ;

        long getFlags() throws ;

        Object getMediaController() throws ;

        MediaMetadataCompat getMetadata() throws ;

        String getPackageName() throws ;

        PlaybackInfo getPlaybackInfo() throws ;

        PlaybackStateCompat getPlaybackState() throws ;

        List<QueueItem> getQueue() throws ;

        CharSequence getQueueTitle() throws ;

        int getRatingType() throws ;

        PendingIntent getSessionActivity() throws ;

        TransportControls getTransportControls() throws ;

        void registerCallback(Callback callback, Handler handler) throws ;

        void sendCommand(String str, Bundle bundle, ResultReceiver resultReceiver) throws ;

        void setVolumeTo(int i, int i2) throws ;

        void unregisterCallback(Callback callback) throws ;
    }

    static class MediaControllerImplApi21 implements MediaControllerImpl {
        protected final Object mControllerObj;

        public MediaControllerImplApi21(Context $r1, MediaSessionCompat $r2) throws  {
            this.mControllerObj = MediaControllerCompatApi21.fromToken($r1, $r2.getSessionToken().getToken());
        }

        public MediaControllerImplApi21(Context $r1, Token $r2) throws RemoteException {
            this.mControllerObj = MediaControllerCompatApi21.fromToken($r1, $r2.getToken());
            if (this.mControllerObj == null) {
                throw new RemoteException();
            }
        }

        public void registerCallback(Callback $r1, Handler $r2) throws  {
            MediaControllerCompatApi21.registerCallback(this.mControllerObj, $r1.mCallbackObj, $r2);
        }

        public void unregisterCallback(Callback $r1) throws  {
            MediaControllerCompatApi21.unregisterCallback(this.mControllerObj, $r1.mCallbackObj);
        }

        public boolean dispatchMediaButtonEvent(KeyEvent $r1) throws  {
            return MediaControllerCompatApi21.dispatchMediaButtonEvent(this.mControllerObj, $r1);
        }

        public TransportControls getTransportControls() throws  {
            Object $r1 = MediaControllerCompatApi21.getTransportControls(this.mControllerObj);
            return $r1 != null ? new TransportControlsApi21($r1) : null;
        }

        public PlaybackStateCompat getPlaybackState() throws  {
            Object $r1 = MediaControllerCompatApi21.getPlaybackState(this.mControllerObj);
            return $r1 != null ? PlaybackStateCompat.fromPlaybackState($r1) : null;
        }

        public MediaMetadataCompat getMetadata() throws  {
            Object $r1 = MediaControllerCompatApi21.getMetadata(this.mControllerObj);
            return $r1 != null ? MediaMetadataCompat.fromMediaMetadata($r1) : null;
        }

        public List<QueueItem> getQueue() throws  {
            List<Object> $r2 = MediaControllerCompatApi21.getQueue(this.mControllerObj);
            if ($r2 == null) {
                return null;
            }
            ArrayList $r3 = new ArrayList();
            for (Object $r1 : $r2) {
                $r3.add(QueueItem.obtain($r1));
            }
            return $r3;
        }

        public CharSequence getQueueTitle() throws  {
            return MediaControllerCompatApi21.getQueueTitle(this.mControllerObj);
        }

        public Bundle getExtras() throws  {
            return MediaControllerCompatApi21.getExtras(this.mControllerObj);
        }

        public int getRatingType() throws  {
            return MediaControllerCompatApi21.getRatingType(this.mControllerObj);
        }

        public long getFlags() throws  {
            return MediaControllerCompatApi21.getFlags(this.mControllerObj);
        }

        public PlaybackInfo getPlaybackInfo() throws  {
            Object $r1 = MediaControllerCompatApi21.getPlaybackInfo(this.mControllerObj);
            return $r1 != null ? new PlaybackInfo(android.support.v4.media.session.MediaControllerCompatApi21.PlaybackInfo.getPlaybackType($r1), android.support.v4.media.session.MediaControllerCompatApi21.PlaybackInfo.getLegacyAudioStream($r1), android.support.v4.media.session.MediaControllerCompatApi21.PlaybackInfo.getVolumeControl($r1), android.support.v4.media.session.MediaControllerCompatApi21.PlaybackInfo.getMaxVolume($r1), android.support.v4.media.session.MediaControllerCompatApi21.PlaybackInfo.getCurrentVolume($r1)) : null;
        }

        public PendingIntent getSessionActivity() throws  {
            return MediaControllerCompatApi21.getSessionActivity(this.mControllerObj);
        }

        public void setVolumeTo(int $i0, int $i1) throws  {
            MediaControllerCompatApi21.setVolumeTo(this.mControllerObj, $i0, $i1);
        }

        public void adjustVolume(int $i0, int $i1) throws  {
            MediaControllerCompatApi21.adjustVolume(this.mControllerObj, $i0, $i1);
        }

        public void sendCommand(String $r1, Bundle $r2, ResultReceiver $r3) throws  {
            MediaControllerCompatApi21.sendCommand(this.mControllerObj, $r1, $r2, $r3);
        }

        public String getPackageName() throws  {
            return MediaControllerCompatApi21.getPackageName(this.mControllerObj);
        }

        public Object getMediaController() throws  {
            return this.mControllerObj;
        }
    }

    static class MediaControllerImplApi23 extends MediaControllerImplApi21 {
        public MediaControllerImplApi23(Context $r1, MediaSessionCompat $r2) throws  {
            super($r1, $r2);
        }

        public MediaControllerImplApi23(Context $r1, Token $r2) throws RemoteException {
            super($r1, $r2);
        }

        public TransportControls getTransportControls() throws  {
            Object $r1 = MediaControllerCompatApi21.getTransportControls(this.mControllerObj);
            return $r1 != null ? new TransportControlsApi23($r1) : null;
        }
    }

    static class MediaControllerImplBase implements MediaControllerImpl {
        private IMediaSession mBinder;
        private Token mToken;
        private TransportControls mTransportControls;

        public Object getMediaController() throws  {
            return null;
        }

        public MediaControllerImplBase(Token $r1) throws  {
            this.mToken = $r1;
            this.mBinder = IMediaSession.Stub.asInterface((IBinder) $r1.getToken());
        }

        public void registerCallback(Callback $r1, Handler $r2) throws  {
            if ($r1 == null) {
                throw new IllegalArgumentException("callback may not be null.");
            }
            try {
                this.mBinder.asBinder().linkToDeath($r1, 0);
                this.mBinder.registerCallbackListener((IMediaControllerCallback) $r1.mCallbackObj);
                $r1.setHandler($r2);
                $r1.mRegistered = true;
            } catch (RemoteException $r3) {
                Log.e(MediaControllerCompat.TAG, "Dead object in registerCallback. " + $r3);
                $r1.onSessionDestroyed();
            }
        }

        public void unregisterCallback(Callback $r1) throws  {
            if ($r1 == null) {
                throw new IllegalArgumentException("callback may not be null.");
            }
            try {
                this.mBinder.unregisterCallbackListener((IMediaControllerCallback) $r1.mCallbackObj);
                this.mBinder.asBinder().unlinkToDeath($r1, 0);
                $r1.mRegistered = false;
            } catch (RemoteException $r2) {
                Log.e(MediaControllerCompat.TAG, "Dead object in unregisterCallback. " + $r2);
            }
        }

        public boolean dispatchMediaButtonEvent(KeyEvent $r1) throws  {
            if ($r1 == null) {
                throw new IllegalArgumentException("event may not be null.");
            }
            try {
                this.mBinder.sendMediaButton($r1);
            } catch (RemoteException $r2) {
                Log.e(MediaControllerCompat.TAG, "Dead object in dispatchMediaButtonEvent. " + $r2);
            }
            return false;
        }

        public TransportControls getTransportControls() throws  {
            if (this.mTransportControls == null) {
                this.mTransportControls = new TransportControlsBase(this.mBinder);
            }
            return this.mTransportControls;
        }

        public PlaybackStateCompat getPlaybackState() throws  {
            try {
                return this.mBinder.getPlaybackState();
            } catch (RemoteException $r1) {
                Log.e(MediaControllerCompat.TAG, "Dead object in getPlaybackState. " + $r1);
                return null;
            }
        }

        public MediaMetadataCompat getMetadata() throws  {
            try {
                return this.mBinder.getMetadata();
            } catch (RemoteException $r1) {
                Log.e(MediaControllerCompat.TAG, "Dead object in getMetadata. " + $r1);
                return null;
            }
        }

        public List<QueueItem> getQueue() throws  {
            try {
                return this.mBinder.getQueue();
            } catch (RemoteException $r1) {
                Log.e(MediaControllerCompat.TAG, "Dead object in getQueue. " + $r1);
                return null;
            }
        }

        public CharSequence getQueueTitle() throws  {
            try {
                return this.mBinder.getQueueTitle();
            } catch (RemoteException $r1) {
                Log.e(MediaControllerCompat.TAG, "Dead object in getQueueTitle. " + $r1);
                return null;
            }
        }

        public Bundle getExtras() throws  {
            try {
                return this.mBinder.getExtras();
            } catch (RemoteException $r1) {
                Log.e(MediaControllerCompat.TAG, "Dead object in getExtras. " + $r1);
                return null;
            }
        }

        public int getRatingType() throws  {
            try {
                return this.mBinder.getRatingType();
            } catch (RemoteException $r1) {
                Log.e(MediaControllerCompat.TAG, "Dead object in getRatingType. " + $r1);
                return 0;
            }
        }

        public long getFlags() throws  {
            try {
                return this.mBinder.getFlags();
            } catch (RemoteException $r1) {
                Log.e(MediaControllerCompat.TAG, "Dead object in getFlags. " + $r1);
                return 0;
            }
        }

        public PlaybackInfo getPlaybackInfo() throws  {
            try {
                ParcelableVolumeInfo $r3 = this.mBinder.getVolumeAttributes();
                return new PlaybackInfo($r3.volumeType, $r3.audioStream, $r3.controlType, $r3.maxVolume, $r3.currentVolume);
            } catch (RemoteException $r1) {
                Log.e(MediaControllerCompat.TAG, "Dead object in getPlaybackInfo. " + $r1);
                return null;
            }
        }

        public PendingIntent getSessionActivity() throws  {
            try {
                return this.mBinder.getLaunchPendingIntent();
            } catch (RemoteException $r1) {
                Log.e(MediaControllerCompat.TAG, "Dead object in getSessionActivity. " + $r1);
                return null;
            }
        }

        public void setVolumeTo(int $i0, int $i1) throws  {
            try {
                this.mBinder.setVolumeTo($i0, $i1, null);
            } catch (RemoteException $r1) {
                Log.e(MediaControllerCompat.TAG, "Dead object in setVolumeTo. " + $r1);
            }
        }

        public void adjustVolume(int $i0, int $i1) throws  {
            try {
                this.mBinder.adjustVolume($i0, $i1, null);
            } catch (RemoteException $r1) {
                Log.e(MediaControllerCompat.TAG, "Dead object in adjustVolume. " + $r1);
            }
        }

        public void sendCommand(String $r1, Bundle $r2, ResultReceiver $r3) throws  {
            try {
                this.mBinder.sendCommand($r1, $r2, new ResultReceiverWrapper($r3));
            } catch (RemoteException $r4) {
                Log.e(MediaControllerCompat.TAG, "Dead object in sendCommand. " + $r4);
            }
        }

        public String getPackageName() throws  {
            try {
                return this.mBinder.getPackageName();
            } catch (RemoteException $r1) {
                Log.e(MediaControllerCompat.TAG, "Dead object in getPackageName. " + $r1);
                return null;
            }
        }
    }

    public static final class PlaybackInfo {
        public static final int PLAYBACK_TYPE_LOCAL = 1;
        public static final int PLAYBACK_TYPE_REMOTE = 2;
        private final int mAudioStream;
        private final int mCurrentVolume;
        private final int mMaxVolume;
        private final int mPlaybackType;
        private final int mVolumeControl;

        PlaybackInfo(int $i0, int $i1, int $i2, int $i3, int $i4) throws  {
            this.mPlaybackType = $i0;
            this.mAudioStream = $i1;
            this.mVolumeControl = $i2;
            this.mMaxVolume = $i3;
            this.mCurrentVolume = $i4;
        }

        public int getPlaybackType() throws  {
            return this.mPlaybackType;
        }

        public int getAudioStream() throws  {
            return this.mAudioStream;
        }

        public int getVolumeControl() throws  {
            return this.mVolumeControl;
        }

        public int getMaxVolume() throws  {
            return this.mMaxVolume;
        }

        public int getCurrentVolume() throws  {
            return this.mCurrentVolume;
        }
    }

    public static abstract class TransportControls {
        public abstract void fastForward() throws ;

        public abstract void pause() throws ;

        public abstract void play() throws ;

        public abstract void playFromMediaId(String str, Bundle bundle) throws ;

        public abstract void playFromSearch(String str, Bundle bundle) throws ;

        public abstract void playFromUri(Uri uri, Bundle bundle) throws ;

        public abstract void rewind() throws ;

        public abstract void seekTo(long j) throws ;

        public abstract void sendCustomAction(CustomAction customAction, Bundle bundle) throws ;

        public abstract void sendCustomAction(String str, Bundle bundle) throws ;

        public abstract void setRating(RatingCompat ratingCompat) throws ;

        public abstract void skipToNext() throws ;

        public abstract void skipToPrevious() throws ;

        public abstract void skipToQueueItem(long j) throws ;

        public abstract void stop() throws ;

        TransportControls() throws  {
        }
    }

    static class TransportControlsApi21 extends TransportControls {
        protected final Object mControlsObj;

        public TransportControlsApi21(Object $r1) throws  {
            this.mControlsObj = $r1;
        }

        public void play() throws  {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.play(this.mControlsObj);
        }

        public void pause() throws  {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.pause(this.mControlsObj);
        }

        public void stop() throws  {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.stop(this.mControlsObj);
        }

        public void seekTo(long $l0) throws  {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.seekTo(this.mControlsObj, $l0);
        }

        public void fastForward() throws  {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.fastForward(this.mControlsObj);
        }

        public void rewind() throws  {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.rewind(this.mControlsObj);
        }

        public void skipToNext() throws  {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.skipToNext(this.mControlsObj);
        }

        public void skipToPrevious() throws  {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.skipToPrevious(this.mControlsObj);
        }

        public void setRating(RatingCompat $r1) throws  {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.setRating(this.mControlsObj, $r1 != null ? $r1.getRating() : null);
        }

        public void playFromMediaId(String $r1, Bundle $r2) throws  {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.playFromMediaId(this.mControlsObj, $r1, $r2);
        }

        public void playFromSearch(String $r1, Bundle $r2) throws  {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.playFromSearch(this.mControlsObj, $r1, $r2);
        }

        public void playFromUri(Uri $r1, Bundle $r2) throws  {
            if ($r1 == null || Uri.EMPTY.equals($r1)) {
                throw new IllegalArgumentException("You must specify a non-empty Uri for playFromUri.");
            }
            Bundle $r3 = new Bundle();
            $r3.putParcelable(MediaSessionCompat.ACTION_ARGUMENT_URI, $r1);
            $r3.putParcelable(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS, $r2);
            sendCustomAction(MediaSessionCompat.ACTION_PLAY_FROM_URI, $r3);
        }

        public void skipToQueueItem(long $l0) throws  {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.skipToQueueItem(this.mControlsObj, $l0);
        }

        public void sendCustomAction(CustomAction $r1, Bundle $r2) throws  {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.sendCustomAction(this.mControlsObj, $r1.getAction(), $r2);
        }

        public void sendCustomAction(String $r1, Bundle $r2) throws  {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.sendCustomAction(this.mControlsObj, $r1, $r2);
        }
    }

    static class TransportControlsApi23 extends TransportControlsApi21 {
        public TransportControlsApi23(Object $r1) throws  {
            super($r1);
        }

        public void playFromUri(Uri $r1, Bundle $r2) throws  {
            android.support.v4.media.session.MediaControllerCompatApi23.TransportControls.playFromUri(this.mControlsObj, $r1, $r2);
        }
    }

    static class TransportControlsBase extends TransportControls {
        private IMediaSession mBinder;

        public TransportControlsBase(IMediaSession $r1) throws  {
            this.mBinder = $r1;
        }

        public void play() throws  {
            try {
                this.mBinder.play();
            } catch (RemoteException $r1) {
                Log.e(MediaControllerCompat.TAG, "Dead object in play. " + $r1);
            }
        }

        public void playFromMediaId(String $r1, Bundle $r2) throws  {
            try {
                this.mBinder.playFromMediaId($r1, $r2);
            } catch (RemoteException $r3) {
                Log.e(MediaControllerCompat.TAG, "Dead object in playFromMediaId. " + $r3);
            }
        }

        public void playFromSearch(String $r1, Bundle $r2) throws  {
            try {
                this.mBinder.playFromSearch($r1, $r2);
            } catch (RemoteException $r3) {
                Log.e(MediaControllerCompat.TAG, "Dead object in playFromSearch. " + $r3);
            }
        }

        public void playFromUri(Uri $r1, Bundle $r2) throws  {
            try {
                this.mBinder.playFromUri($r1, $r2);
            } catch (RemoteException $r3) {
                Log.e(MediaControllerCompat.TAG, "Dead object in playFromUri. " + $r3);
            }
        }

        public void skipToQueueItem(long $l0) throws  {
            try {
                this.mBinder.skipToQueueItem($l0);
            } catch (RemoteException $r1) {
                Log.e(MediaControllerCompat.TAG, "Dead object in skipToQueueItem. " + $r1);
            }
        }

        public void pause() throws  {
            try {
                this.mBinder.pause();
            } catch (RemoteException $r1) {
                Log.e(MediaControllerCompat.TAG, "Dead object in pause. " + $r1);
            }
        }

        public void stop() throws  {
            try {
                this.mBinder.stop();
            } catch (RemoteException $r1) {
                Log.e(MediaControllerCompat.TAG, "Dead object in stop. " + $r1);
            }
        }

        public void seekTo(long $l0) throws  {
            try {
                this.mBinder.seekTo($l0);
            } catch (RemoteException $r1) {
                Log.e(MediaControllerCompat.TAG, "Dead object in seekTo. " + $r1);
            }
        }

        public void fastForward() throws  {
            try {
                this.mBinder.fastForward();
            } catch (RemoteException $r1) {
                Log.e(MediaControllerCompat.TAG, "Dead object in fastForward. " + $r1);
            }
        }

        public void skipToNext() throws  {
            try {
                this.mBinder.next();
            } catch (RemoteException $r1) {
                Log.e(MediaControllerCompat.TAG, "Dead object in skipToNext. " + $r1);
            }
        }

        public void rewind() throws  {
            try {
                this.mBinder.rewind();
            } catch (RemoteException $r1) {
                Log.e(MediaControllerCompat.TAG, "Dead object in rewind. " + $r1);
            }
        }

        public void skipToPrevious() throws  {
            try {
                this.mBinder.previous();
            } catch (RemoteException $r1) {
                Log.e(MediaControllerCompat.TAG, "Dead object in skipToPrevious. " + $r1);
            }
        }

        public void setRating(RatingCompat $r1) throws  {
            try {
                this.mBinder.rate($r1);
            } catch (RemoteException $r2) {
                Log.e(MediaControllerCompat.TAG, "Dead object in setRating. " + $r2);
            }
        }

        public void sendCustomAction(CustomAction $r1, Bundle $r2) throws  {
            sendCustomAction($r1.getAction(), $r2);
        }

        public void sendCustomAction(String $r1, Bundle $r2) throws  {
            try {
                this.mBinder.sendCustomAction($r1, $r2);
            } catch (RemoteException $r3) {
                Log.e(MediaControllerCompat.TAG, "Dead object in sendCustomAction. " + $r3);
            }
        }
    }

    public MediaControllerCompat(Context $r1, MediaSessionCompat $r2) throws  {
        if ($r2 == null) {
            throw new IllegalArgumentException("session must not be null");
        }
        this.mToken = $r2.getSessionToken();
        if (VERSION.SDK_INT >= 23) {
            this.mImpl = new MediaControllerImplApi23($r1, $r2);
        } else if (VERSION.SDK_INT >= 21) {
            this.mImpl = new MediaControllerImplApi21($r1, $r2);
        } else {
            this.mImpl = new MediaControllerImplBase(this.mToken);
        }
    }

    public MediaControllerCompat(Context $r1, Token $r2) throws RemoteException {
        if ($r2 == null) {
            throw new IllegalArgumentException("sessionToken must not be null");
        }
        this.mToken = $r2;
        if (VERSION.SDK_INT >= 23) {
            this.mImpl = new MediaControllerImplApi23($r1, $r2);
        } else if (VERSION.SDK_INT >= 21) {
            this.mImpl = new MediaControllerImplApi21($r1, $r2);
        } else {
            this.mImpl = new MediaControllerImplBase(this.mToken);
        }
    }

    public TransportControls getTransportControls() throws  {
        return this.mImpl.getTransportControls();
    }

    public boolean dispatchMediaButtonEvent(KeyEvent $r1) throws  {
        if ($r1 != null) {
            return this.mImpl.dispatchMediaButtonEvent($r1);
        }
        throw new IllegalArgumentException("KeyEvent may not be null");
    }

    public PlaybackStateCompat getPlaybackState() throws  {
        return this.mImpl.getPlaybackState();
    }

    public MediaMetadataCompat getMetadata() throws  {
        return this.mImpl.getMetadata();
    }

    public List<QueueItem> getQueue() throws  {
        return this.mImpl.getQueue();
    }

    public CharSequence getQueueTitle() throws  {
        return this.mImpl.getQueueTitle();
    }

    public Bundle getExtras() throws  {
        return this.mImpl.getExtras();
    }

    public int getRatingType() throws  {
        return this.mImpl.getRatingType();
    }

    public long getFlags() throws  {
        return this.mImpl.getFlags();
    }

    public PlaybackInfo getPlaybackInfo() throws  {
        return this.mImpl.getPlaybackInfo();
    }

    public PendingIntent getSessionActivity() throws  {
        return this.mImpl.getSessionActivity();
    }

    public Token getSessionToken() throws  {
        return this.mToken;
    }

    public void setVolumeTo(int $i0, int $i1) throws  {
        this.mImpl.setVolumeTo($i0, $i1);
    }

    public void adjustVolume(int $i0, int $i1) throws  {
        this.mImpl.adjustVolume($i0, $i1);
    }

    public void registerCallback(Callback $r1) throws  {
        registerCallback($r1, null);
    }

    public void registerCallback(Callback $r1, Handler $r2) throws  {
        if ($r1 == null) {
            throw new IllegalArgumentException("callback cannot be null");
        }
        if ($r2 == null) {
            $r2 = new Handler();
        }
        this.mImpl.registerCallback($r1, $r2);
    }

    public void unregisterCallback(Callback $r1) throws  {
        if ($r1 == null) {
            throw new IllegalArgumentException("callback cannot be null");
        }
        this.mImpl.unregisterCallback($r1);
    }

    public void sendCommand(String $r1, Bundle $r2, ResultReceiver $r3) throws  {
        if (TextUtils.isEmpty($r1)) {
            throw new IllegalArgumentException("command cannot be null or empty");
        }
        this.mImpl.sendCommand($r1, $r2, $r3);
    }

    public String getPackageName() throws  {
        return this.mImpl.getPackageName();
    }

    public Object getMediaController() throws  {
        return this.mImpl.getMediaController();
    }
}
