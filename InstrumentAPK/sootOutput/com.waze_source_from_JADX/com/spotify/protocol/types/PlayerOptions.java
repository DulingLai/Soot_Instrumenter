package com.spotify.protocol.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerOptions implements Item {
    public static final PlayerOptions DEFAULT = new PlayerOptions();
    @SerializedName("shuffle")
    @JsonProperty("shuffle")
    public final boolean isShuffling;
    @SerializedName("repeat")
    @JsonProperty("repeat")
    public final int repeatMode;

    private PlayerOptions() throws  {
        this(false, 0);
    }

    public PlayerOptions(boolean $z0, int $i0) throws  {
        this.isShuffling = $z0;
        this.repeatMode = $i0;
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if ($r1 == null || getClass() != $r1.getClass()) {
            return false;
        }
        PlayerOptions $r4 = (PlayerOptions) $r1;
        if (this.isShuffling != $r4.isShuffling) {
            return false;
        }
        return this.repeatMode == $r4.repeatMode;
    }

    public int hashCode() throws  {
        return ((this.isShuffling ? (byte) 1 : (byte) 0) * 31) + this.repeatMode;
    }

    public String toString() throws  {
        return "PlayerOptions{isShuffling=" + this.isShuffling + ", repeatMode=" + this.repeatMode + '}';
    }
}
