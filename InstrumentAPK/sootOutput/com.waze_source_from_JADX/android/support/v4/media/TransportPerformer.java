package android.support.v4.media;

import android.os.SystemClock;
import android.view.KeyEvent;

public abstract class TransportPerformer {
    static final int AUDIOFOCUS_GAIN = 1;
    static final int AUDIOFOCUS_GAIN_TRANSIENT = 2;
    static final int AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK = 3;
    static final int AUDIOFOCUS_LOSS = -1;
    static final int AUDIOFOCUS_LOSS_TRANSIENT = -2;
    static final int AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK = -3;

    public int onGetBufferPercentage() throws  {
        return 100;
    }

    public abstract long onGetCurrentPosition() throws ;

    public abstract long onGetDuration() throws ;

    public int onGetTransportControlFlags() throws  {
        return 60;
    }

    public abstract boolean onIsPlaying() throws ;

    public boolean onMediaButtonUp(int keyCode, KeyEvent event) throws  {
        return true;
    }

    public abstract void onPause() throws ;

    public abstract void onSeekTo(long j) throws ;

    public abstract void onStart() throws ;

    public abstract void onStop() throws ;

    public boolean onMediaButtonDown(int $i0, KeyEvent event) throws  {
        switch ($i0) {
            case 79:
            case 85:
                if (onIsPlaying()) {
                    onPause();
                    return true;
                }
                onStart();
                return true;
            case 86:
                onStop();
                return true;
            case 126:
                onStart();
                return true;
            case 127:
                onPause();
                return true;
            default:
                return true;
        }
    }

    public void onAudioFocusChange(int $i0) throws  {
        byte $b2 = (byte) 0;
        switch ($i0) {
            case -1:
                $b2 = Byte.MAX_VALUE;
                break;
            default:
                break;
        }
        if ($b2 != (byte) 0) {
            long $l1 = SystemClock.uptimeMillis();
            onMediaButtonDown($b2, new KeyEvent($l1, $l1, 0, $b2, 0));
            onMediaButtonUp($b2, new KeyEvent($l1, $l1, 1, $b2, 0));
        }
    }
}
