package com.spotify.protocol.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerRestrictions implements Item {
    public static final PlayerRestrictions DEFAULT = new PlayerRestrictions();
    @SerializedName("can_repeat_context")
    @JsonProperty("can_repeat_context")
    public final boolean canRepeatContext;
    @SerializedName("can_repeat_track")
    @JsonProperty("can_repeat_track")
    public final boolean canRepeatTrack;
    @SerializedName("can_seek")
    @JsonProperty("can_seek")
    public final boolean canSeek;
    @SerializedName("can_skip_next")
    @JsonProperty("can_skip_next")
    public final boolean canSkipNext;
    @SerializedName("can_skip_prev")
    @JsonProperty("can_skip_prev")
    public final boolean canSkipPrev;
    @SerializedName("can_toggle_shuffle")
    @JsonProperty("can_toggle_shuffle")
    public final boolean canToggleShuffle;

    private PlayerRestrictions() throws  {
        this(false, false, false, false, false, false);
    }

    public PlayerRestrictions(boolean $z0, boolean $z1, boolean $z2, boolean $z3, boolean $z4, boolean $z5) throws  {
        this.canSkipNext = $z0;
        this.canSkipPrev = $z1;
        this.canRepeatTrack = $z2;
        this.canRepeatContext = $z3;
        this.canToggleShuffle = $z4;
        this.canSeek = $z5;
    }

    public boolean equals(Object $r1) throws  {
        boolean $z0 = true;
        if (this == $r1) {
            return true;
        }
        if ($r1 == null) {
            return false;
        }
        if (getClass() != $r1.getClass()) {
            return false;
        }
        PlayerRestrictions $r4 = (PlayerRestrictions) $r1;
        if (this.canSkipNext != $r4.canSkipNext) {
            return false;
        }
        if (this.canSkipPrev != $r4.canSkipPrev) {
            return false;
        }
        if (this.canRepeatTrack != $r4.canRepeatTrack) {
            return false;
        }
        if (this.canRepeatContext != $r4.canRepeatContext) {
            return false;
        }
        if (this.canToggleShuffle != $r4.canToggleShuffle) {
            $z0 = false;
        }
        return $z0;
    }

    public int hashCode() throws  {
        byte $b1;
        byte $b0 = (byte) 1;
        int $i2 = (this.canSkipNext ? (byte) 1 : (byte) 0) * 31;
        if (this.canSkipPrev) {
            $b1 = (byte) 1;
        } else {
            $b1 = (byte) 0;
        }
        $i2 = ($i2 + $b1) * 31;
        if (this.canRepeatTrack) {
            $b1 = (byte) 1;
        } else {
            $b1 = (byte) 0;
        }
        $i2 = ($i2 + $b1) * 31;
        if (this.canRepeatContext) {
            $b1 = (byte) 1;
        } else {
            $b1 = (byte) 0;
        }
        $i2 = ($i2 + $b1) * 31;
        if (!this.canToggleShuffle) {
            $b0 = (byte) 0;
        }
        return $i2 + $b0;
    }

    public String toString() throws  {
        return "PlayerRestrictions{canSkipNext=" + this.canSkipNext + ", canSkipPrev=" + this.canSkipPrev + ", canRepeatTrack=" + this.canRepeatTrack + ", canRepeatContext=" + this.canRepeatContext + ", canToggleShuffle=" + this.canToggleShuffle + '}';
    }
}
