package com.spotify.android.appremote.internal;

import com.spotify.android.appremote.api.MotionStateApi;
import com.spotify.protocol.AppProtocol.Topic;
import com.spotify.protocol.client.RemoteClient;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.MotionState;

public class MotionStateApiImpl implements MotionStateApi {
    private final RemoteClient mClient;

    public MotionStateApiImpl(RemoteClient $r1) throws  {
        this.mClient = $r1;
    }

    public Subscription<MotionState> subscribeToMotionState() throws  {
        return this.mClient.subscribe(Topic.TEMPO_DETECTION_STATE, MotionState.class);
    }
}
