package com.spotify.android.appremote.api;

import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.Empty;
import com.spotify.protocol.types.PlayerContext;
import com.spotify.protocol.types.PlayerState;
import dalvik.annotation.Signature;

public interface PlayerApi {
    CallResult<PlayerState> getPlayerState() throws ;

    CallResult<Empty> pause() throws ;

    CallResult<Empty> play(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/Empty;", ">;"}) String str) throws ;

    CallResult<Empty> play(@Signature({"([", "Ljava/lang/String;", ")", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/Empty;", ">;"}) String[] strArr) throws ;

    CallResult<Empty> queue(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/Empty;", ">;"}) String str) throws ;

    CallResult<Empty> resume() throws ;

    CallResult<Empty> seekTo(@Signature({"(J)", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/Empty;", ">;"}) long j) throws ;

    CallResult<Empty> setRepeat(@Signature({"(I)", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/Empty;", ">;"}) int i) throws ;

    CallResult<Empty> setShuffle(@Signature({"(Z)", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/Empty;", ">;"}) boolean z) throws ;

    CallResult<Empty> skipNext() throws ;

    CallResult<Empty> skipPrevious() throws ;

    Subscription<PlayerContext> subscribeToPlayerContext() throws ;

    Subscription<PlayerState> subscribeToPlayerState() throws ;
}
