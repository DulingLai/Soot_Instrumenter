package com.spotify.sdk.android.authentication;

import android.app.Activity;
import android.util.Log;
import com.spotify.sdk.android.authentication.AuthenticationHandler.OnCompleteListener;

class SpotifyAuthHandler implements AuthenticationHandler {
    private static String TAG = SpotifyAuthHandler.class.getSimpleName();
    private SpotifyNativeAuthUtil mSpotifyNativeAuthUtil;

    SpotifyAuthHandler() throws  {
    }

    public boolean start(Activity $r1, AuthenticationRequest $r2) throws  {
        Log.d(TAG, "start");
        this.mSpotifyNativeAuthUtil = new SpotifyNativeAuthUtil($r1, $r2);
        return this.mSpotifyNativeAuthUtil.startAuthActivity();
    }

    public void stop() throws  {
        Log.d(TAG, "stop");
        if (this.mSpotifyNativeAuthUtil != null) {
            this.mSpotifyNativeAuthUtil.stopAuthActivity();
        }
    }

    public void setOnCompleteListener(OnCompleteListener listener) throws  {
    }
}
