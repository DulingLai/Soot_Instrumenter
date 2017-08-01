package com.spotify.android.appremote.internal;

import android.content.Context;
import com.spotify.android.appremote.api.ConnectionParams;

public class SdkRemoteClientConnectorFactory {
    public SdkRemoteClientConnector newConnector(Context $r1, ConnectionParams $r2, String $r3) throws  {
        return SdkRemoteClientConnector.create($r1, $r2, $r3);
    }
}
