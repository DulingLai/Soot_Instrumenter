package android.support.v4.media;

import android.view.KeyEvent;

interface TransportMediatorCallback {
    long getPlaybackPosition() throws ;

    void handleAudioFocusChange(int i) throws ;

    void handleKey(KeyEvent keyEvent) throws ;

    void playbackPositionUpdate(long j) throws ;
}
