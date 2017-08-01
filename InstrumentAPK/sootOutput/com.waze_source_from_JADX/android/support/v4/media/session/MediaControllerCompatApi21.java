package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaMetadata;
import android.media.Rating;
import android.media.session.MediaController;
import android.media.session.MediaSession.Token;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.KeyEvent;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

class MediaControllerCompatApi21 {

    public interface Callback {
        void onMetadataChanged(Object obj) throws ;

        void onPlaybackStateChanged(Object obj) throws ;

        void onSessionDestroyed() throws ;

        void onSessionEvent(String str, Bundle bundle) throws ;
    }

    static class CallbackProxy<T extends Callback> extends android.media.session.MediaController.Callback {
        protected final T mCallback;

        public CallbackProxy(@Signature({"(TT;)V"}) T $r1) throws  {
            this.mCallback = $r1;
        }

        public void onSessionDestroyed() throws  {
            this.mCallback.onSessionDestroyed();
        }

        public void onSessionEvent(String $r1, Bundle $r2) throws  {
            this.mCallback.onSessionEvent($r1, $r2);
        }

        public void onPlaybackStateChanged(PlaybackState $r1) throws  {
            this.mCallback.onPlaybackStateChanged($r1);
        }

        public void onMetadataChanged(MediaMetadata $r1) throws  {
            this.mCallback.onMetadataChanged($r1);
        }
    }

    public static class PlaybackInfo {
        private static final int FLAG_SCO = 4;
        private static final int STREAM_BLUETOOTH_SCO = 6;
        private static final int STREAM_SYSTEM_ENFORCED = 7;

        public static int getPlaybackType(Object $r0) throws  {
            return ((android.media.session.MediaController.PlaybackInfo) $r0).getPlaybackType();
        }

        public static AudioAttributes getAudioAttributes(Object $r1) throws  {
            return ((android.media.session.MediaController.PlaybackInfo) $r1).getAudioAttributes();
        }

        public static int getLegacyAudioStream(Object $r0) throws  {
            return toLegacyStreamType(getAudioAttributes($r0));
        }

        public static int getVolumeControl(Object $r0) throws  {
            return ((android.media.session.MediaController.PlaybackInfo) $r0).getVolumeControl();
        }

        public static int getMaxVolume(Object $r0) throws  {
            return ((android.media.session.MediaController.PlaybackInfo) $r0).getMaxVolume();
        }

        public static int getCurrentVolume(Object $r0) throws  {
            return ((android.media.session.MediaController.PlaybackInfo) $r0).getCurrentVolume();
        }

        private static int toLegacyStreamType(AudioAttributes $r0) throws  {
            byte $b0 = (byte) 3;
            if (($r0.getFlags() & 1) == 1) {
                $b0 = (byte) 7;
            } else if (($r0.getFlags() & 4) == 4) {
                return 6;
            } else {
                switch ($r0.getUsage()) {
                    case 1:
                    case 11:
                    case 12:
                    case 14:
                        break;
                    case 2:
                        return 0;
                    case 3:
                        return 8;
                    case 4:
                        return 4;
                    case 5:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                        return 5;
                    case 6:
                        return 2;
                    case 13:
                        return 1;
                    default:
                        return 3;
                }
            }
            return $b0;
        }
    }

    public static class TransportControls {
        public static void play(Object $r0) throws  {
            ((android.media.session.MediaController.TransportControls) $r0).play();
        }

        public static void pause(Object $r0) throws  {
            ((android.media.session.MediaController.TransportControls) $r0).pause();
        }

        public static void stop(Object $r0) throws  {
            ((android.media.session.MediaController.TransportControls) $r0).stop();
        }

        public static void seekTo(Object $r0, long $l0) throws  {
            ((android.media.session.MediaController.TransportControls) $r0).seekTo($l0);
        }

        public static void fastForward(Object $r0) throws  {
            ((android.media.session.MediaController.TransportControls) $r0).fastForward();
        }

        public static void rewind(Object $r0) throws  {
            ((android.media.session.MediaController.TransportControls) $r0).rewind();
        }

        public static void skipToNext(Object $r0) throws  {
            ((android.media.session.MediaController.TransportControls) $r0).skipToNext();
        }

        public static void skipToPrevious(Object $r0) throws  {
            ((android.media.session.MediaController.TransportControls) $r0).skipToPrevious();
        }

        public static void setRating(Object $r0, Object $r1) throws  {
            ((android.media.session.MediaController.TransportControls) $r0).setRating((Rating) $r1);
        }

        public static void playFromMediaId(Object $r2, String $r0, Bundle $r1) throws  {
            ((android.media.session.MediaController.TransportControls) $r2).playFromMediaId($r0, $r1);
        }

        public static void playFromSearch(Object $r2, String $r0, Bundle $r1) throws  {
            ((android.media.session.MediaController.TransportControls) $r2).playFromSearch($r0, $r1);
        }

        public static void skipToQueueItem(Object $r0, long $l0) throws  {
            ((android.media.session.MediaController.TransportControls) $r0).skipToQueueItem($l0);
        }

        public static void sendCustomAction(Object $r2, String $r0, Bundle $r1) throws  {
            ((android.media.session.MediaController.TransportControls) $r2).sendCustomAction($r0, $r1);
        }
    }

    MediaControllerCompatApi21() throws  {
    }

    public static Object fromToken(Context $r0, Object $r2) throws  {
        return new MediaController($r0, (Token) $r2);
    }

    public static Object createCallback(Callback $r0) throws  {
        return new CallbackProxy($r0);
    }

    public static void registerCallback(Object $r1, Object $r2, Handler $r0) throws  {
        ((MediaController) $r1).registerCallback((android.media.session.MediaController.Callback) $r2, $r0);
    }

    public static void unregisterCallback(Object $r0, Object $r1) throws  {
        ((MediaController) $r0).unregisterCallback((android.media.session.MediaController.Callback) $r1);
    }

    public static Object getTransportControls(Object $r1) throws  {
        return ((MediaController) $r1).getTransportControls();
    }

    public static Object getPlaybackState(Object $r1) throws  {
        return ((MediaController) $r1).getPlaybackState();
    }

    public static Object getMetadata(Object $r1) throws  {
        return ((MediaController) $r1).getMetadata();
    }

    public static List<Object> getQueue(@Signature({"(", "Ljava/lang/Object;", ")", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) Object $r1) throws  {
        List $r0 = ((MediaController) $r1).getQueue();
        if ($r0 == null) {
            return null;
        }
        return new ArrayList($r0);
    }

    public static CharSequence getQueueTitle(Object $r1) throws  {
        return ((MediaController) $r1).getQueueTitle();
    }

    public static Bundle getExtras(Object $r1) throws  {
        return ((MediaController) $r1).getExtras();
    }

    public static int getRatingType(Object $r0) throws  {
        return ((MediaController) $r0).getRatingType();
    }

    public static long getFlags(Object $r0) throws  {
        return ((MediaController) $r0).getFlags();
    }

    public static Object getPlaybackInfo(Object $r1) throws  {
        return ((MediaController) $r1).getPlaybackInfo();
    }

    public static PendingIntent getSessionActivity(Object $r1) throws  {
        return ((MediaController) $r1).getSessionActivity();
    }

    public static boolean dispatchMediaButtonEvent(Object $r1, KeyEvent $r0) throws  {
        return ((MediaController) $r1).dispatchMediaButtonEvent($r0);
    }

    public static void setVolumeTo(Object $r0, int $i0, int $i1) throws  {
        ((MediaController) $r0).setVolumeTo($i0, $i1);
    }

    public static void adjustVolume(Object $r0, int $i0, int $i1) throws  {
        ((MediaController) $r0).adjustVolume($i0, $i1);
    }

    public static void sendCommand(Object $r3, String $r0, Bundle $r1, ResultReceiver $r2) throws  {
        ((MediaController) $r3).sendCommand($r0, $r1, $r2);
    }

    public static String getPackageName(Object $r1) throws  {
        return ((MediaController) $r1).getPackageName();
    }
}
