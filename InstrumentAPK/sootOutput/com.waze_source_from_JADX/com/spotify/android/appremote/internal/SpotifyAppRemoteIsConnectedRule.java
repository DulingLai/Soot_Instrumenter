package com.spotify.android.appremote.internal;

import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.android.appremote.api.error.SpotifyDisconnectedException;
import com.spotify.android.appremote.internal.StrictRemoteClient.Rule;

public class SpotifyAppRemoteIsConnectedRule implements Rule {
    private final SpotifyAppRemote mSpotifyAppRemote;

    public SpotifyAppRemoteIsConnectedRule(SpotifyAppRemote $r1) throws  {
        this.mSpotifyAppRemote = $r1;
    }

    public boolean isSatisfied() throws  {
        return this.mSpotifyAppRemote.isConnected();
    }

    public Throwable getError() throws  {
        return new SpotifyDisconnectedException();
    }
}
