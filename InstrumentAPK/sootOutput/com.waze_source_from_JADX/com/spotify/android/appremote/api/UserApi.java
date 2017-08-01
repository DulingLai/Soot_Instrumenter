package com.spotify.android.appremote.api;

import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.Capabilities;
import com.spotify.protocol.types.Empty;
import com.spotify.protocol.types.LibraryState;
import com.spotify.protocol.types.UserStatus;
import dalvik.annotation.Signature;

public interface UserApi {
    CallResult<Empty> addToLibrary(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/Empty;", ">;"}) String str) throws ;

    CallResult<Capabilities> getCapabilities() throws ;

    CallResult<LibraryState> getLibraryState(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/LibraryState;", ">;"}) String str) throws ;

    CallResult<Empty> removeFromLibrary(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/Empty;", ">;"}) String str) throws ;

    Subscription<Capabilities> subscribeToCapabilities() throws ;

    Subscription<UserStatus> subscribeToUserStatus() throws ;
}
