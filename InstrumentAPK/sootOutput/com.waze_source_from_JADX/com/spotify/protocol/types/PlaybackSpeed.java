package com.spotify.protocol.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaybackSpeed implements Item {
    @SerializedName("playback_speed")
    @JsonProperty("playback_speed")
    public final int playbackSpeed;

    protected PlaybackSpeed() throws  {
        this(0);
    }

    public PlaybackSpeed(int $i0) throws  {
        this.playbackSpeed = $i0;
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if ($r1 == null || getClass() != $r1.getClass()) {
            return false;
        }
        return this.playbackSpeed == ((PlaybackSpeed) $r1).playbackSpeed;
    }

    public int hashCode() throws  {
        return this.playbackSpeed;
    }

    public String toString() throws  {
        return "PlaybackSpeed{playbackSpeed=" + this.playbackSpeed + '}';
    }
}
