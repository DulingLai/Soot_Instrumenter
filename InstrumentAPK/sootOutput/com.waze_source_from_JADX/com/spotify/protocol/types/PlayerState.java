package com.spotify.protocol.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerState implements Item {
    @SerializedName("is_paused")
    @JsonProperty("is_paused")
    public final boolean isPaused;
    @SerializedName("playback_options")
    @JsonProperty("playback_options")
    public final PlayerOptions playbackOptions;
    @SerializedName("playback_position")
    @JsonProperty("playback_position")
    public final long playbackPosition;
    @SerializedName("playback_restrictions")
    @JsonProperty("playback_restrictions")
    public final PlayerRestrictions playbackRestrictions;
    @SerializedName("playback_speed")
    @JsonProperty("playback_speed")
    public final float playbackSpeed;
    @SerializedName("track")
    @JsonProperty("track")
    public final Track track;

    private PlayerState() throws  {
        this(null, false, 0.0f, 0, null, null);
    }

    public PlayerState(Track $r1, boolean $z0, float $f0, long $l0, PlayerOptions $r2, PlayerRestrictions $r3) throws  {
        this.track = $r1;
        this.isPaused = $z0;
        this.playbackSpeed = $f0;
        this.playbackPosition = $l0;
        if ($r2 == null) {
            $r2 = PlayerOptions.DEFAULT;
        }
        this.playbackOptions = $r2;
        if ($r3 == null) {
            $r3 = PlayerRestrictions.DEFAULT;
        }
        this.playbackRestrictions = $r3;
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
        PlayerState $r4 = (PlayerState) $r1;
        if (this.isPaused != $r4.isPaused) {
            return false;
        }
        if (Float.compare($r4.playbackSpeed, this.playbackSpeed) != 0) {
            return false;
        }
        if (this.playbackPosition != $r4.playbackPosition) {
            return false;
        }
        if (this.track != null) {
            if (!this.track.equals($r4.track)) {
                return false;
            }
        } else if ($r4.track != null) {
            return false;
        }
        if (this.playbackOptions != null) {
            if (!this.playbackOptions.equals($r4.playbackOptions)) {
                return false;
            }
        } else if ($r4.playbackOptions != null) {
            return false;
        }
        if (this.playbackRestrictions != null) {
            $z0 = this.playbackRestrictions.equals($r4.playbackRestrictions);
        } else if ($r4.playbackRestrictions != null) {
            $z0 = false;
        }
        return $z0;
    }

    public int hashCode() throws  {
        byte $b2;
        int $i3;
        int $i0 = 0;
        int $i1 = (this.track != null ? this.track.hashCode() : 0) * 31;
        if (this.isPaused) {
            $b2 = (byte) 1;
        } else {
            $b2 = (byte) 0;
        }
        $i1 = ($i1 + $b2) * 31;
        if (this.playbackSpeed != 0.0f) {
            $i3 = Float.floatToIntBits(this.playbackSpeed);
        } else {
            $i3 = 0;
        }
        $i1 = ((($i1 + $i3) * 31) + ((int) (this.playbackPosition ^ (this.playbackPosition >>> 32)))) * 31;
        if (this.playbackOptions != null) {
            $i3 = this.playbackOptions.hashCode();
        } else {
            $i3 = 0;
        }
        $i1 = ($i1 + $i3) * 31;
        if (this.playbackRestrictions != null) {
            $i0 = this.playbackRestrictions.hashCode();
        }
        return $i1 + $i0;
    }

    public String toString() throws  {
        return "PlayerState{track=" + this.track + ", isPaused=" + this.isPaused + ", playbackSpeed=" + this.playbackSpeed + ", playbackPosition=" + this.playbackPosition + ", playbackOptions=" + this.playbackOptions + ", playbackRestrictions=" + this.playbackRestrictions + '}';
    }
}
