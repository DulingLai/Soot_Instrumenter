package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.media.AudioManager;
import android.media.RemoteControlClient;
import android.os.SystemClock;
import android.util.Log;
import dalvik.annotation.Signature;

class MediaSessionCompatApi18 {
    private static final long ACTION_SEEK_TO = 256;
    private static final String TAG = "MediaSessionCompatApi18";
    private static boolean sIsMbrPendingIntentSupported = true;

    interface Callback {
        void onSeekTo(long j) throws ;
    }

    static class OnPlaybackPositionUpdateListener<T extends Callback> implements android.media.RemoteControlClient.OnPlaybackPositionUpdateListener {
        protected final T mCallback;

        public OnPlaybackPositionUpdateListener(@Signature({"(TT;)V"}) T $r1) throws  {
            this.mCallback = $r1;
        }

        public void onPlaybackPositionUpdate(long $l0) throws  {
            this.mCallback.onSeekTo($l0);
        }
    }

    MediaSessionCompatApi18() throws  {
    }

    public static Object createPlaybackPositionUpdateListener(Callback $r0) throws  {
        return new OnPlaybackPositionUpdateListener($r0);
    }

    public static void registerMediaButtonEventReceiver(Context $r0, PendingIntent $r1, ComponentName $r2) throws  {
        AudioManager $r5 = (AudioManager) $r0.getSystemService("audio");
        if (sIsMbrPendingIntentSupported) {
            try {
                $r5.registerMediaButtonEventReceiver($r1);
            } catch (NullPointerException e) {
                Log.w(TAG, "Unable to register media button event receiver with PendingIntent, falling back to ComponentName.");
                sIsMbrPendingIntentSupported = false;
            }
        }
        if (!sIsMbrPendingIntentSupported) {
            $r5.registerMediaButtonEventReceiver($r2);
        }
    }

    public static void unregisterMediaButtonEventReceiver(Context $r0, PendingIntent $r1, ComponentName $r2) throws  {
        AudioManager $r4 = (AudioManager) $r0.getSystemService("audio");
        if (sIsMbrPendingIntentSupported) {
            $r4.unregisterMediaButtonEventReceiver($r1);
        } else {
            $r4.unregisterMediaButtonEventReceiver($r2);
        }
    }

    public static void setState(Object $r0, int $i1, long position, float $f0, long $l0) throws  {
        long $l3 = SystemClock.elapsedRealtime();
        if ($i1 == 3 && position > 0) {
            long $l5 = 0;
            if ($l0 > 0) {
                $l5 = $l3 - $l0;
                if ($f0 > 0.0f && $f0 != 1.0f) {
                    $l5 = (long) (((float) $l5) * $f0);
                }
            }
            position += $l5;
        }
        ((RemoteControlClient) $r0).setPlaybackState(MediaSessionCompatApi14.getRccStateFromState($i1), position, $f0);
    }

    public static void setTransportControlFlags(Object $r0, long $l0) throws  {
        ((RemoteControlClient) $r0).setTransportControlFlags(getRccTransportControlFlagsFromActions($l0));
    }

    public static void setOnPlaybackPositionUpdateListener(Object $r0, Object $r1) throws  {
        ((RemoteControlClient) $r0).setPlaybackPositionUpdateListener((android.media.RemoteControlClient.OnPlaybackPositionUpdateListener) $r1);
    }

    static int getRccTransportControlFlagsFromActions(long $l0) throws  {
        int $i2 = MediaSessionCompatApi14.getRccTransportControlFlagsFromActions($l0);
        if ((256 & $l0) != 0) {
            return $i2 | 256;
        }
        return $i2;
    }
}
