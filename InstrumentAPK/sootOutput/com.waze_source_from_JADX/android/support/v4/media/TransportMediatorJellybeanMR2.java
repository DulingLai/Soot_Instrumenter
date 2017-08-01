package android.support.v4.media;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.RemoteControlClient;
import android.media.RemoteControlClient.OnGetPlaybackPositionListener;
import android.media.RemoteControlClient.OnPlaybackPositionUpdateListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnWindowAttachListener;
import android.view.ViewTreeObserver.OnWindowFocusChangeListener;

class TransportMediatorJellybeanMR2 {
    OnAudioFocusChangeListener mAudioFocusChangeListener = new C00804();
    boolean mAudioFocused;
    final AudioManager mAudioManager;
    final Context mContext;
    boolean mFocused;
    final OnGetPlaybackPositionListener mGetPlaybackPositionListener = new C00815();
    final Intent mIntent;
    final BroadcastReceiver mMediaButtonReceiver = new C00793();
    PendingIntent mPendingIntent;
    int mPlayState = 0;
    final OnPlaybackPositionUpdateListener mPlaybackPositionUpdateListener = new C00826();
    final String mReceiverAction;
    final IntentFilter mReceiverFilter;
    RemoteControlClient mRemoteControl;
    final View mTargetView;
    final TransportMediatorCallback mTransportCallback;
    final OnWindowAttachListener mWindowAttachListener = new C00771();
    final OnWindowFocusChangeListener mWindowFocusListener = new C00782();

    class C00771 implements OnWindowAttachListener {
        C00771() throws  {
        }

        public void onWindowAttached() throws  {
            TransportMediatorJellybeanMR2.this.windowAttached();
        }

        public void onWindowDetached() throws  {
            TransportMediatorJellybeanMR2.this.windowDetached();
        }
    }

    class C00782 implements OnWindowFocusChangeListener {
        C00782() throws  {
        }

        public void onWindowFocusChanged(boolean $z0) throws  {
            if ($z0) {
                TransportMediatorJellybeanMR2.this.gainFocus();
            } else {
                TransportMediatorJellybeanMR2.this.loseFocus();
            }
        }
    }

    class C00793 extends BroadcastReceiver {
        C00793() throws  {
        }

        public void onReceive(Context context, Intent $r2) throws  {
            try {
                TransportMediatorJellybeanMR2.this.mTransportCallback.handleKey((KeyEvent) $r2.getParcelableExtra("android.intent.extra.KEY_EVENT"));
            } catch (ClassCastException $r3) {
                Log.w("TransportController", $r3);
            }
        }
    }

    class C00804 implements OnAudioFocusChangeListener {
        C00804() throws  {
        }

        public void onAudioFocusChange(int $i0) throws  {
            TransportMediatorJellybeanMR2.this.mTransportCallback.handleAudioFocusChange($i0);
        }
    }

    class C00815 implements OnGetPlaybackPositionListener {
        C00815() throws  {
        }

        public long onGetPlaybackPosition() throws  {
            return TransportMediatorJellybeanMR2.this.mTransportCallback.getPlaybackPosition();
        }
    }

    class C00826 implements OnPlaybackPositionUpdateListener {
        C00826() throws  {
        }

        public void onPlaybackPositionUpdate(long $l0) throws  {
            TransportMediatorJellybeanMR2.this.mTransportCallback.playbackPositionUpdate($l0);
        }
    }

    public TransportMediatorJellybeanMR2(Context $r1, AudioManager $r2, View $r3, TransportMediatorCallback $r4) throws  {
        TransportMediatorJellybeanMR2 transportMediatorJellybeanMR2 = this;
        this.mContext = $r1;
        this.mAudioManager = $r2;
        this.mTargetView = $r3;
        this.mTransportCallback = $r4;
        this.mReceiverAction = $r1.getPackageName() + ":transport:" + System.identityHashCode(this);
        this.mIntent = new Intent(this.mReceiverAction);
        this.mIntent.setPackage($r1.getPackageName());
        this.mReceiverFilter = new IntentFilter();
        this.mReceiverFilter.addAction(this.mReceiverAction);
        View $r32 = this.mTargetView;
        ViewTreeObserver $r15 = $r32.getViewTreeObserver();
        OnWindowAttachListener $r16 = this.mWindowAttachListener;
        $r15.addOnWindowAttachListener($r16);
        $r32 = this.mTargetView;
        $r15 = $r32.getViewTreeObserver();
        OnWindowFocusChangeListener $r17 = this.mWindowFocusListener;
        $r15.addOnWindowFocusChangeListener($r17);
    }

    public Object getRemoteControlClient() throws  {
        return this.mRemoteControl;
    }

    public void destroy() throws  {
        windowDetached();
        this.mTargetView.getViewTreeObserver().removeOnWindowAttachListener(this.mWindowAttachListener);
        this.mTargetView.getViewTreeObserver().removeOnWindowFocusChangeListener(this.mWindowFocusListener);
    }

    void windowAttached() throws  {
        this.mContext.registerReceiver(this.mMediaButtonReceiver, this.mReceiverFilter);
        this.mPendingIntent = PendingIntent.getBroadcast(this.mContext, 0, this.mIntent, 268435456);
        this.mRemoteControl = new RemoteControlClient(this.mPendingIntent);
        this.mRemoteControl.setOnGetPlaybackPositionListener(this.mGetPlaybackPositionListener);
        this.mRemoteControl.setPlaybackPositionUpdateListener(this.mPlaybackPositionUpdateListener);
    }

    void gainFocus() throws  {
        if (!this.mFocused) {
            this.mFocused = true;
            this.mAudioManager.registerMediaButtonEventReceiver(this.mPendingIntent);
            this.mAudioManager.registerRemoteControlClient(this.mRemoteControl);
            if (this.mPlayState == 3) {
                takeAudioFocus();
            }
        }
    }

    void takeAudioFocus() throws  {
        if (!this.mAudioFocused) {
            this.mAudioFocused = true;
            this.mAudioManager.requestAudioFocus(this.mAudioFocusChangeListener, 3, 1);
        }
    }

    public void startPlaying() throws  {
        if (this.mPlayState != 3) {
            this.mPlayState = 3;
            this.mRemoteControl.setPlaybackState(3);
        }
        if (this.mFocused) {
            takeAudioFocus();
        }
    }

    public void refreshState(boolean $z0, long $l0, int $i1) throws  {
        if (this.mRemoteControl != null) {
            byte $b2;
            float $f0;
            RemoteControlClient $r1 = this.mRemoteControl;
            if ($z0) {
                $b2 = (byte) 3;
            } else {
                $b2 = (byte) 1;
            }
            if ($z0) {
                $f0 = 1.0f;
            } else {
                $f0 = 0.0f;
            }
            $r1.setPlaybackState($b2, $l0, $f0);
            this.mRemoteControl.setTransportControlFlags($i1);
        }
    }

    public void pausePlaying() throws  {
        if (this.mPlayState == 3) {
            this.mPlayState = 2;
            this.mRemoteControl.setPlaybackState(2);
        }
        dropAudioFocus();
    }

    public void stopPlaying() throws  {
        if (this.mPlayState != 1) {
            this.mPlayState = 1;
            this.mRemoteControl.setPlaybackState(1);
        }
        dropAudioFocus();
    }

    void dropAudioFocus() throws  {
        if (this.mAudioFocused) {
            this.mAudioFocused = false;
            this.mAudioManager.abandonAudioFocus(this.mAudioFocusChangeListener);
        }
    }

    void loseFocus() throws  {
        dropAudioFocus();
        if (this.mFocused) {
            this.mFocused = false;
            this.mAudioManager.unregisterRemoteControlClient(this.mRemoteControl);
            this.mAudioManager.unregisterMediaButtonEventReceiver(this.mPendingIntent);
        }
    }

    void windowDetached() throws  {
        loseFocus();
        if (this.mPendingIntent != null) {
            this.mContext.unregisterReceiver(this.mMediaButtonReceiver);
            this.mPendingIntent.cancel();
            this.mPendingIntent = null;
            this.mRemoteControl = null;
        }
    }
}
