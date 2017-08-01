package com.spotify.android.appremote.api;

import android.content.Context;

public interface Connector {

    public interface ConnectionListener {
        void onConnected(SpotifyAppRemote spotifyAppRemote) throws ;

        void onFailure(Throwable th) throws ;
    }

    void connect(Context context, ConnectionParams connectionParams, ConnectionListener connectionListener) throws ;

    void disconnect(SpotifyAppRemote spotifyAppRemote) throws ;
}
