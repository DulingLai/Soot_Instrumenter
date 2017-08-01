package com.spotify.protocol.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaybackPosition implements Item {
    @SerializedName("position_ms")
    @JsonProperty("position_ms")
    public final long position;

    private PlaybackPosition() throws  {
        this(0);
    }

    public PlaybackPosition(long $l0) throws  {
        this.position = $l0;
    }
}
