package com.spotify.protocol.client.error;

import com.spotify.protocol.error.SpotifyAppRemoteException;

public class RemoteClientException extends SpotifyAppRemoteException {
    private static final long serialVersionUID = -2168285044517789333L;
    private final String mReasonUri;

    public RemoteClientException(String $r1, String $r2) throws  {
        super($r1);
        this.mReasonUri = $r2;
    }

    public String getReasonUri() throws  {
        return this.mReasonUri;
    }
}
