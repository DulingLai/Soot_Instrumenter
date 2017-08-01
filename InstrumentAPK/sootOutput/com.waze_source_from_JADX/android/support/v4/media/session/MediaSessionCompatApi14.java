package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.RemoteControlClient;
import android.media.RemoteControlClient.MetadataEditor;
import android.os.Bundle;

class MediaSessionCompatApi14 {
    private static final long ACTION_FAST_FORWARD = 64;
    private static final long ACTION_PAUSE = 2;
    private static final long ACTION_PLAY = 4;
    private static final long ACTION_PLAY_PAUSE = 512;
    private static final long ACTION_REWIND = 8;
    private static final long ACTION_SKIP_TO_NEXT = 32;
    private static final long ACTION_SKIP_TO_PREVIOUS = 16;
    private static final long ACTION_STOP = 1;
    private static final String METADATA_KEY_ALBUM = "android.media.metadata.ALBUM";
    private static final String METADATA_KEY_ALBUM_ART = "android.media.metadata.ALBUM_ART";
    private static final String METADATA_KEY_ALBUM_ARTIST = "android.media.metadata.ALBUM_ARTIST";
    private static final String METADATA_KEY_ART = "android.media.metadata.ART";
    private static final String METADATA_KEY_ARTIST = "android.media.metadata.ARTIST";
    private static final String METADATA_KEY_AUTHOR = "android.media.metadata.AUTHOR";
    private static final String METADATA_KEY_COMPILATION = "android.media.metadata.COMPILATION";
    private static final String METADATA_KEY_COMPOSER = "android.media.metadata.COMPOSER";
    private static final String METADATA_KEY_DATE = "android.media.metadata.DATE";
    private static final String METADATA_KEY_DISC_NUMBER = "android.media.metadata.DISC_NUMBER";
    private static final String METADATA_KEY_DURATION = "android.media.metadata.DURATION";
    private static final String METADATA_KEY_GENRE = "android.media.metadata.GENRE";
    private static final String METADATA_KEY_TITLE = "android.media.metadata.TITLE";
    private static final String METADATA_KEY_TRACK_NUMBER = "android.media.metadata.TRACK_NUMBER";
    private static final String METADATA_KEY_WRITER = "android.media.metadata.WRITER";
    static final int RCC_PLAYSTATE_NONE = 0;
    static final int STATE_BUFFERING = 6;
    static final int STATE_CONNECTING = 8;
    static final int STATE_ERROR = 7;
    static final int STATE_FAST_FORWARDING = 4;
    static final int STATE_NONE = 0;
    static final int STATE_PAUSED = 2;
    static final int STATE_PLAYING = 3;
    static final int STATE_REWINDING = 5;
    static final int STATE_SKIPPING_TO_NEXT = 10;
    static final int STATE_SKIPPING_TO_PREVIOUS = 9;
    static final int STATE_SKIPPING_TO_QUEUE_ITEM = 11;
    static final int STATE_STOPPED = 1;

    static int getRccStateFromState(int $i0) throws  {
        switch ($i0) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
            case 8:
                return 8;
            case 7:
                return 9;
            case 9:
                return 7;
            case 10:
            case 11:
                return 6;
            default:
                return -1;
        }
    }

    MediaSessionCompatApi14() throws  {
    }

    public static Object createRemoteControlClient(PendingIntent $r0) throws  {
        return new RemoteControlClient($r0);
    }

    public static void setState(Object $r0, int $i0) throws  {
        ((RemoteControlClient) $r0).setPlaybackState(getRccStateFromState($i0));
    }

    public static void setTransportControlFlags(Object $r0, long $l0) throws  {
        ((RemoteControlClient) $r0).setTransportControlFlags(getRccTransportControlFlagsFromActions($l0));
    }

    public static void setMetadata(Object $r2, Bundle $r0) throws  {
        MetadataEditor $r1 = ((RemoteControlClient) $r2).editMetadata(true);
        buildOldMetadata($r0, $r1);
        $r1.apply();
    }

    public static void registerRemoteControlClient(Context $r0, Object $r2) throws  {
        ((AudioManager) $r0.getSystemService("audio")).registerRemoteControlClient((RemoteControlClient) $r2);
    }

    public static void unregisterRemoteControlClient(Context $r0, Object $r2) throws  {
        ((AudioManager) $r0.getSystemService("audio")).unregisterRemoteControlClient((RemoteControlClient) $r2);
    }

    static int getRccTransportControlFlagsFromActions(long $l0) throws  {
        short $s1 = (short) 0;
        if ((1 & $l0) != 0) {
            $s1 = (short) 0 | (short) 32;
        }
        if ((2 & $l0) != 0) {
            $s1 |= (short) 16;
        }
        if ((4 & $l0) != 0) {
            $s1 |= (short) 4;
        }
        if ((8 & $l0) != 0) {
            $s1 |= (short) 2;
        }
        if ((16 & $l0) != 0) {
            $s1 |= (short) 1;
        }
        if ((32 & $l0) != 0) {
            $s1 |= (short) 128;
        }
        if ((64 & $l0) != 0) {
            $s1 |= (short) 64;
        }
        if ((512 & $l0) != 0) {
            return $s1 | (short) 8;
        }
        return $s1;
    }

    static void buildOldMetadata(Bundle $r0, MetadataEditor $r1) throws  {
        if ($r0 != null) {
            if ($r0.containsKey("android.media.metadata.ART")) {
                $r1.putBitmap(100, (Bitmap) $r0.getParcelable("android.media.metadata.ART"));
            } else if ($r0.containsKey("android.media.metadata.ALBUM_ART")) {
                $r1.putBitmap(100, (Bitmap) $r0.getParcelable("android.media.metadata.ALBUM_ART"));
            }
            if ($r0.containsKey("android.media.metadata.ALBUM")) {
                $r1.putString(1, $r0.getString("android.media.metadata.ALBUM"));
            }
            if ($r0.containsKey("android.media.metadata.ALBUM_ARTIST")) {
                $r1.putString(13, $r0.getString("android.media.metadata.ALBUM_ARTIST"));
            }
            if ($r0.containsKey("android.media.metadata.ARTIST")) {
                $r1.putString(2, $r0.getString("android.media.metadata.ARTIST"));
            }
            if ($r0.containsKey("android.media.metadata.AUTHOR")) {
                $r1.putString(3, $r0.getString("android.media.metadata.AUTHOR"));
            }
            if ($r0.containsKey("android.media.metadata.COMPILATION")) {
                $r1.putString(15, $r0.getString("android.media.metadata.COMPILATION"));
            }
            if ($r0.containsKey("android.media.metadata.COMPOSER")) {
                $r1.putString(4, $r0.getString("android.media.metadata.COMPOSER"));
            }
            if ($r0.containsKey("android.media.metadata.DATE")) {
                $r1.putString(5, $r0.getString("android.media.metadata.DATE"));
            }
            if ($r0.containsKey("android.media.metadata.DISC_NUMBER")) {
                $r1.putLong(14, $r0.getLong("android.media.metadata.DISC_NUMBER"));
            }
            if ($r0.containsKey("android.media.metadata.DURATION")) {
                $r1.putLong(9, $r0.getLong("android.media.metadata.DURATION"));
            }
            if ($r0.containsKey("android.media.metadata.GENRE")) {
                $r1.putString(6, $r0.getString("android.media.metadata.GENRE"));
            }
            if ($r0.containsKey("android.media.metadata.TITLE")) {
                $r1.putString(7, $r0.getString("android.media.metadata.TITLE"));
            }
            if ($r0.containsKey("android.media.metadata.TRACK_NUMBER")) {
                $r1.putLong(0, $r0.getLong("android.media.metadata.TRACK_NUMBER"));
            }
            if ($r0.containsKey("android.media.metadata.WRITER")) {
                $r1.putString(11, $r0.getString("android.media.metadata.WRITER"));
            }
        }
    }
}
