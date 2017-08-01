package com.spotify.android.appremote.internal;

import com.spotify.android.appremote.api.PlayerApi;
import com.spotify.protocol.AppProtocol.CallUri;
import com.spotify.protocol.AppProtocol.Topic;
import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.client.RemoteClient;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.Empty;
import com.spotify.protocol.types.PlaybackPosition;
import com.spotify.protocol.types.PlaybackSpeed;
import com.spotify.protocol.types.PlayerContext;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Repeat;
import com.spotify.protocol.types.Shuffle;
import com.spotify.protocol.types.Uri;
import com.spotify.protocol.types.Uris;
import dalvik.annotation.Signature;

public class PlayerApiImpl implements PlayerApi {
    private static final int PLAYBACK_PAUSED = 0;
    private static final int PLAYBACK_PLAYING = 1;
    private final RemoteClient mClient;
    private final Validator mValidator = new Validator();

    public PlayerApiImpl(RemoteClient $r1) throws  {
        this.mClient = $r1;
    }

    public CallResult<Empty> play(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/Empty;", ">;"}) String $r1) throws  {
        boolean $z0;
        if (this.mValidator.isValidTrackUri($r1) || this.mValidator.isValidPlaylistUri($r1) || this.mValidator.isValidAlbumUri($r1) || this.mValidator.isValidArtistUri($r1)) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        if ($z0) {
            return this.mClient.call(CallUri.PLAY_URI, new Uri($r1), Empty.class);
        }
        throw new IllegalArgumentException(String.format("%s cannot be played", new Object[]{$r1}));
    }

    public CallResult<Empty> play(@Signature({"([", "Ljava/lang/String;", ")", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/Empty;", ">;"}) String[] $r1) throws  {
        int $i0 = $r1.length;
        int $i1 = 0;
        while ($i1 < $i0) {
            if (this.mValidator.isValidTrackUri($r1[$i1])) {
                $i1++;
            } else {
                throw new IllegalArgumentException(String.format("%s cannot be played", new Object[]{$r1[$i1]}));
            }
        }
        return this.mClient.call(CallUri.PLAY_URIS, new Uris($r1), Empty.class);
    }

    public CallResult<Empty> queue(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/Empty;", ">;"}) String $r1) throws  {
        if (this.mValidator.isValidTrackUri($r1)) {
            return this.mClient.call(CallUri.PLAY_QUEUE, new Uri($r1), Empty.class);
        }
        throw new IllegalArgumentException(String.format("Cannot queue %s", new Object[]{$r1}));
    }

    public CallResult<Empty> resume() throws  {
        return setPlaybackSpeed(1);
    }

    public CallResult<Empty> pause() throws  {
        return setPlaybackSpeed(0);
    }

    public CallResult<Empty> skipNext() throws  {
        return this.mClient.call(CallUri.SKIP_NEXT, Empty.class);
    }

    public CallResult<Empty> skipPrevious() throws  {
        return this.mClient.call(CallUri.SKIP_PREVIOUS, Empty.class);
    }

    public CallResult<Empty> setShuffle(@Signature({"(Z)", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/Empty;", ">;"}) boolean $z0) throws  {
        return this.mClient.call(CallUri.SET_SHUFFLE, new Shuffle($z0), Empty.class);
    }

    public CallResult<Empty> setRepeat(@Signature({"(I)", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/Empty;", ">;"}) int $i0) throws  {
        return this.mClient.call(CallUri.SET_REPEAT, new Repeat($i0), Empty.class);
    }

    public CallResult<Empty> seekTo(@Signature({"(J)", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/Empty;", ">;"}) long $l0) throws  {
        return this.mClient.call(CallUri.SET_PLAYBACK_POSITION, new PlaybackPosition($l0), Empty.class);
    }

    public CallResult<PlayerState> getPlayerState() throws  {
        return this.mClient.call(CallUri.GET_PLAYER_STATE, PlayerState.class);
    }

    public Subscription<PlayerState> subscribeToPlayerState() throws  {
        return this.mClient.subscribe(Topic.PLAYER_STATE, PlayerState.class);
    }

    public Subscription<PlayerContext> subscribeToPlayerContext() throws  {
        return this.mClient.subscribe(Topic.PLAYER_CONTEXT, PlayerContext.class);
    }

    private CallResult<Empty> setPlaybackSpeed(@Signature({"(I)", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/Empty;", ">;"}) int $i0) throws  {
        return this.mClient.call(CallUri.SET_PLAYBACK_SPEED, new PlaybackSpeed($i0), Empty.class);
    }
}
