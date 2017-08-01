package com.spotify.android.appremote.api.error;

import com.spotify.protocol.error.SpotifyAppRemoteException;

public class NotLoggedInException extends SpotifyAppRemoteException {
    private static final long serialVersionUID = -2168285044517789333L;

    public NotLoggedInException(String $r1, Throwable $r2) throws  {
        super($r1, $r2);
    }
}
