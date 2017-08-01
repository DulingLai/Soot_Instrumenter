package com.spotify.android.appremote.internal;

import com.spotify.android.appremote.api.UserApi;
import com.spotify.protocol.AppProtocol.CallUri;
import com.spotify.protocol.AppProtocol.Topic;
import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.client.RemoteClient;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.Capabilities;
import com.spotify.protocol.types.Empty;
import com.spotify.protocol.types.Identifier;
import com.spotify.protocol.types.LibraryState;
import com.spotify.protocol.types.UserStatus;
import dalvik.annotation.Signature;

public class UserApiImpl implements UserApi {
    private final RemoteClient mRemoteClient;
    private final Validator mValidator = new Validator();

    public UserApiImpl(RemoteClient $r1) throws  {
        this.mRemoteClient = $r1;
    }

    public CallResult<Capabilities> getCapabilities() throws  {
        return this.mRemoteClient.call(CallUri.CAPABILITIES, Capabilities.class);
    }

    public Subscription<Capabilities> subscribeToCapabilities() throws  {
        return this.mRemoteClient.subscribe(Topic.CAPABILITIES, Capabilities.class);
    }

    public Subscription<UserStatus> subscribeToUserStatus() throws  {
        return this.mRemoteClient.subscribe(Topic.STATUS, UserStatus.class);
    }

    public CallResult<Empty> addToLibrary(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/Empty;", ">;"}) String $r1) throws  {
        return this.mRemoteClient.call(CallUri.SET_SAVED, new LibraryState($r1, true, false), Empty.class);
    }

    public CallResult<Empty> removeFromLibrary(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/Empty;", ">;"}) String $r1) throws  {
        return this.mRemoteClient.call(CallUri.SET_SAVED, new LibraryState($r1, false, false), Empty.class);
    }

    public CallResult<LibraryState> getLibraryState(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/LibraryState;", ">;"}) String $r1) throws  {
        return this.mRemoteClient.call(CallUri.GET_SAVED, validateAndPrepareUri($r1), LibraryState.class);
    }

    private Identifier validateAndPrepareUri(String $r1) throws  {
        boolean $z0;
        if (this.mValidator.isValidTrackUri($r1) || this.mValidator.isValidAlbumUri($r1) || this.mValidator.isValidArtistUri($r1) || this.mValidator.isValidPlaylistUri($r1)) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        if ($z0) {
            return new Identifier($r1);
        }
        throw new IllegalArgumentException(String.format("%s invalid uri", new Object[]{$r1}));
    }
}
