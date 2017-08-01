package com.spotify.android.appremote.internal;

import android.util.Log;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.Debug.Assertion;
import com.spotify.protocol.client.Debug.Logger;

public class AppRemoteDebugImpl implements Assertion, Logger {
    private static final String SPOTIFY_APP_REMOTE = "SPOTIFY_APP_REMOTE";

    public void mo3663e(Throwable $r1, String $r2, Object... $r3) throws  {
        Log.e(SPOTIFY_APP_REMOTE, String.format($r2, $r3), $r1);
    }

    public void mo3661d(Throwable $r1, String $r2, Object... $r3) throws  {
        if (SpotifyAppRemote.isDebugMode()) {
            Log.d(SPOTIFY_APP_REMOTE, String.format($r2, $r3), $r1);
        }
    }

    public void mo3662e(String $r1, Object... $r2) throws  {
        mo3663e(null, $r1, $r2);
    }

    public void mo3660d(String $r1, Object... $r2) throws  {
        mo3661d(null, $r1, $r2);
    }

    public void assertTrue(boolean $z0, String $r1) throws  {
        if (SpotifyAppRemote.isDebugMode() && !$z0) {
            throw new AssertionError($r1);
        }
    }
}
