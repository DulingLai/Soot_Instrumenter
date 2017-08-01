package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes.Builder;
import android.media.MediaDescription;
import android.media.MediaMetadata;
import android.media.Rating;
import android.media.VolumeProvider;
import android.media.session.MediaSession;
import android.media.session.MediaSession.Token;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.ResultReceiver;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class MediaSessionCompatApi21 {

    interface Callback extends Callback {
        void onCommand(String str, Bundle bundle, ResultReceiver resultReceiver) throws ;

        void onCustomAction(String str, Bundle bundle) throws ;

        void onFastForward() throws ;

        boolean onMediaButtonEvent(Intent intent) throws ;

        void onPause() throws ;

        void onPlay() throws ;

        void onPlayFromMediaId(String str, Bundle bundle) throws ;

        void onPlayFromSearch(String str, Bundle bundle) throws ;

        void onRewind() throws ;

        void onSkipToNext() throws ;

        void onSkipToPrevious() throws ;

        void onSkipToQueueItem(long j) throws ;

        void onStop() throws ;
    }

    static class CallbackProxy<T extends Callback> extends android.media.session.MediaSession.Callback {
        protected final T mCallback;

        public CallbackProxy(@Signature({"(TT;)V"}) T $r1) throws  {
            this.mCallback = $r1;
        }

        public void onCommand(String $r1, Bundle $r2, ResultReceiver $r3) throws  {
            this.mCallback.onCommand($r1, $r2, $r3);
        }

        public boolean onMediaButtonEvent(Intent $r1) throws  {
            return this.mCallback.onMediaButtonEvent($r1) || super.onMediaButtonEvent($r1);
        }

        public void onPlay() throws  {
            this.mCallback.onPlay();
        }

        public void onPlayFromMediaId(String $r1, Bundle $r2) throws  {
            this.mCallback.onPlayFromMediaId($r1, $r2);
        }

        public void onPlayFromSearch(String $r1, Bundle $r2) throws  {
            this.mCallback.onPlayFromSearch($r1, $r2);
        }

        public void onSkipToQueueItem(long $l0) throws  {
            this.mCallback.onSkipToQueueItem($l0);
        }

        public void onPause() throws  {
            this.mCallback.onPause();
        }

        public void onSkipToNext() throws  {
            this.mCallback.onSkipToNext();
        }

        public void onSkipToPrevious() throws  {
            this.mCallback.onSkipToPrevious();
        }

        public void onFastForward() throws  {
            this.mCallback.onFastForward();
        }

        public void onRewind() throws  {
            this.mCallback.onRewind();
        }

        public void onStop() throws  {
            this.mCallback.onStop();
        }

        public void onSeekTo(long $l0) throws  {
            this.mCallback.onSeekTo($l0);
        }

        public void onSetRating(Rating $r1) throws  {
            this.mCallback.onSetRating($r1);
        }

        public void onCustomAction(String $r1, Bundle $r2) throws  {
            this.mCallback.onCustomAction($r1, $r2);
        }
    }

    static class QueueItem {
        QueueItem() throws  {
        }

        public static Object createItem(Object $r1, long $l0) throws  {
            return new android.media.session.MediaSession.QueueItem((MediaDescription) $r1, $l0);
        }

        public static Object getDescription(Object $r1) throws  {
            return ((android.media.session.MediaSession.QueueItem) $r1).getDescription();
        }

        public static long getQueueId(Object $r0) throws  {
            return ((android.media.session.MediaSession.QueueItem) $r0).getQueueId();
        }
    }

    MediaSessionCompatApi21() throws  {
    }

    public static Object createSession(Context $r0, String $r1) throws  {
        return new MediaSession($r0, $r1);
    }

    public static Object verifySession(Object $r0) throws  {
        if ($r0 instanceof MediaSession) {
            return $r0;
        }
        throw new IllegalArgumentException("mediaSession is not a valid MediaSession object");
    }

    public static Object verifyToken(Object $r0) throws  {
        if ($r0 instanceof Token) {
            return $r0;
        }
        throw new IllegalArgumentException("token is not a valid MediaSession.Token object");
    }

    public static Object createCallback(Callback $r0) throws  {
        return new CallbackProxy($r0);
    }

    public static void setCallback(Object $r1, Object $r2, Handler $r0) throws  {
        ((MediaSession) $r1).setCallback((android.media.session.MediaSession.Callback) $r2, $r0);
    }

    public static void setFlags(Object $r0, int $i0) throws  {
        ((MediaSession) $r0).setFlags($i0);
    }

    public static void setPlaybackToLocal(Object $r2, int $i0) throws  {
        Builder $r0 = new Builder();
        $r0.setLegacyStreamType($i0);
        ((MediaSession) $r2).setPlaybackToLocal($r0.build());
    }

    public static void setPlaybackToRemote(Object $r0, Object $r1) throws  {
        ((MediaSession) $r0).setPlaybackToRemote((VolumeProvider) $r1);
    }

    public static void setActive(Object $r0, boolean $z0) throws  {
        ((MediaSession) $r0).setActive($z0);
    }

    public static boolean isActive(Object $r0) throws  {
        return ((MediaSession) $r0).isActive();
    }

    public static void sendSessionEvent(Object $r2, String $r0, Bundle $r1) throws  {
        ((MediaSession) $r2).sendSessionEvent($r0, $r1);
    }

    public static void release(Object $r0) throws  {
        ((MediaSession) $r0).release();
    }

    public static Parcelable getSessionToken(Object $r1) throws  {
        return ((MediaSession) $r1).getSessionToken();
    }

    public static void setPlaybackState(Object $r0, Object $r1) throws  {
        ((MediaSession) $r0).setPlaybackState((PlaybackState) $r1);
    }

    public static void setMetadata(Object $r0, Object $r1) throws  {
        ((MediaSession) $r0).setMetadata((MediaMetadata) $r1);
    }

    public static void setSessionActivity(Object $r1, PendingIntent $r0) throws  {
        ((MediaSession) $r1).setSessionActivity($r0);
    }

    public static void setMediaButtonReceiver(Object $r1, PendingIntent $r0) throws  {
        ((MediaSession) $r1).setMediaButtonReceiver($r0);
    }

    public static void setQueue(@Signature({"(", "Ljava/lang/Object;", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;)V"}) Object $r2, @Signature({"(", "Ljava/lang/Object;", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;)V"}) List<Object> $r0) throws  {
        if ($r0 == null) {
            ((MediaSession) $r2).setQueue(null);
            return;
        }
        ArrayList $r1 = new ArrayList();
        Iterator $r4 = $r0.iterator();
        while ($r4.hasNext()) {
            $r1.add((android.media.session.MediaSession.QueueItem) $r4.next());
        }
        ((MediaSession) $r2).setQueue($r1);
    }

    public static void setQueueTitle(Object $r1, CharSequence $r0) throws  {
        ((MediaSession) $r1).setQueueTitle($r0);
    }

    public static void setExtras(Object $r1, Bundle $r0) throws  {
        ((MediaSession) $r1).setExtras($r0);
    }
}
