package android.support.v4.media;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Build.VERSION;
import android.support.v4.view.KeyEventCompat;
import android.view.KeyEvent;
import android.view.KeyEvent.Callback;
import android.view.View;
import java.util.ArrayList;

public class TransportMediator extends TransportController {
    public static final int FLAG_KEY_MEDIA_FAST_FORWARD = 64;
    public static final int FLAG_KEY_MEDIA_NEXT = 128;
    public static final int FLAG_KEY_MEDIA_PAUSE = 16;
    public static final int FLAG_KEY_MEDIA_PLAY = 4;
    public static final int FLAG_KEY_MEDIA_PLAY_PAUSE = 8;
    public static final int FLAG_KEY_MEDIA_PREVIOUS = 1;
    public static final int FLAG_KEY_MEDIA_REWIND = 2;
    public static final int FLAG_KEY_MEDIA_STOP = 32;
    public static final int KEYCODE_MEDIA_PAUSE = 127;
    public static final int KEYCODE_MEDIA_PLAY = 126;
    public static final int KEYCODE_MEDIA_RECORD = 130;
    final AudioManager mAudioManager;
    final TransportPerformer mCallbacks;
    final Context mContext;
    final TransportMediatorJellybeanMR2 mController;
    final Object mDispatcherState;
    final Callback mKeyEventCallback;
    final ArrayList<TransportStateListener> mListeners;
    final TransportMediatorCallback mTransportKeyCallback;
    final View mView;

    class C00751 implements TransportMediatorCallback {
        C00751() throws  {
        }

        public void handleKey(KeyEvent $r1) throws  {
            $r1.dispatch(TransportMediator.this.mKeyEventCallback);
        }

        public void handleAudioFocusChange(int $i0) throws  {
            TransportMediator.this.mCallbacks.onAudioFocusChange($i0);
        }

        public long getPlaybackPosition() throws  {
            return TransportMediator.this.mCallbacks.onGetCurrentPosition();
        }

        public void playbackPositionUpdate(long $l0) throws  {
            TransportMediator.this.mCallbacks.onSeekTo($l0);
        }
    }

    class C00762 implements Callback {
        public boolean onKeyLongPress(int keyCode, KeyEvent event) throws  {
            return false;
        }

        public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) throws  {
            return false;
        }

        C00762() throws  {
        }

        public boolean onKeyDown(int $i0, KeyEvent $r1) throws  {
            return TransportMediator.isMediaKey($i0) ? TransportMediator.this.mCallbacks.onMediaButtonDown($i0, $r1) : false;
        }

        public boolean onKeyUp(int $i0, KeyEvent $r1) throws  {
            return TransportMediator.isMediaKey($i0) ? TransportMediator.this.mCallbacks.onMediaButtonUp($i0, $r1) : false;
        }
    }

    static boolean isMediaKey(int $i0) throws  {
        switch ($i0) {
            case 79:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 126:
            case 127:
            case 130:
                return true;
            default:
                return false;
        }
    }

    public TransportMediator(Activity $r1, TransportPerformer $r2) throws  {
        this($r1, null, $r2);
    }

    public TransportMediator(View $r1, TransportPerformer $r2) throws  {
        this(null, $r1, $r2);
    }

    private TransportMediator(Activity $r1, View $r4, TransportPerformer $r2) throws  {
        Context context;
        this.mListeners = new ArrayList();
        this.mTransportKeyCallback = new C00751();
        this.mKeyEventCallback = new C00762();
        if ($r1 != null) {
            context = $r1;
        } else {
            context = $r4.getContext();
        }
        this.mContext = context;
        this.mCallbacks = $r2;
        this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
        if ($r1 != null) {
            $r4 = $r1.getWindow().getDecorView();
        }
        this.mView = $r4;
        View $r42 = this.mView;
        this.mDispatcherState = KeyEventCompat.getKeyDispatcherState($r42);
        if (VERSION.SDK_INT >= 18) {
            this.mController = new TransportMediatorJellybeanMR2(this.mContext, this.mAudioManager, this.mView, this.mTransportKeyCallback);
            return;
        }
        this.mController = null;
    }

    public Object getRemoteControlClient() throws  {
        return this.mController != null ? this.mController.getRemoteControlClient() : null;
    }

    public boolean dispatchKeyEvent(KeyEvent $r1) throws  {
        return KeyEventCompat.dispatch($r1, this.mKeyEventCallback, this.mDispatcherState, this);
    }

    public void registerStateListener(TransportStateListener $r1) throws  {
        this.mListeners.add($r1);
    }

    public void unregisterStateListener(TransportStateListener $r1) throws  {
        this.mListeners.remove($r1);
    }

    private TransportStateListener[] getListeners() throws  {
        if (this.mListeners.size() <= 0) {
            return null;
        }
        TransportStateListener[] $r2 = new TransportStateListener[this.mListeners.size()];
        this.mListeners.toArray($r2);
        return $r2;
    }

    private void reportPlayingChanged() throws  {
        TransportStateListener[] $r2 = getListeners();
        if ($r2 != null) {
            for (TransportStateListener $r1 : $r2) {
                $r1.onPlayingChanged(this);
            }
        }
    }

    private void reportTransportControlsChanged() throws  {
        TransportStateListener[] $r2 = getListeners();
        if ($r2 != null) {
            for (TransportStateListener $r1 : $r2) {
                $r1.onTransportControlsChanged(this);
            }
        }
    }

    private void pushControllerState() throws  {
        if (this.mController != null) {
            this.mController.refreshState(this.mCallbacks.onIsPlaying(), this.mCallbacks.onGetCurrentPosition(), this.mCallbacks.onGetTransportControlFlags());
        }
    }

    public void refreshState() throws  {
        pushControllerState();
        reportPlayingChanged();
        reportTransportControlsChanged();
    }

    public void startPlaying() throws  {
        if (this.mController != null) {
            this.mController.startPlaying();
        }
        this.mCallbacks.onStart();
        pushControllerState();
        reportPlayingChanged();
    }

    public void pausePlaying() throws  {
        if (this.mController != null) {
            this.mController.pausePlaying();
        }
        this.mCallbacks.onPause();
        pushControllerState();
        reportPlayingChanged();
    }

    public void stopPlaying() throws  {
        if (this.mController != null) {
            this.mController.stopPlaying();
        }
        this.mCallbacks.onStop();
        pushControllerState();
        reportPlayingChanged();
    }

    public long getDuration() throws  {
        return this.mCallbacks.onGetDuration();
    }

    public long getCurrentPosition() throws  {
        return this.mCallbacks.onGetCurrentPosition();
    }

    public void seekTo(long $l0) throws  {
        this.mCallbacks.onSeekTo($l0);
    }

    public boolean isPlaying() throws  {
        return this.mCallbacks.onIsPlaying();
    }

    public int getBufferPercentage() throws  {
        return this.mCallbacks.onGetBufferPercentage();
    }

    public int getTransportControlFlags() throws  {
        return this.mCallbacks.onGetTransportControlFlags();
    }

    public void destroy() throws  {
        this.mController.destroy();
    }
}
