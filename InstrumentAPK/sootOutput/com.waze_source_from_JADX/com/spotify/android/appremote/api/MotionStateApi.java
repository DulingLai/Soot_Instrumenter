package com.spotify.android.appremote.api;

import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.MotionState;

public interface MotionStateApi {
    Subscription<MotionState> subscribeToMotionState() throws ;
}
