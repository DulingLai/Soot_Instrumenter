package com.spotify.protocol.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerContext implements Item {
    @SerializedName("subtitle")
    @JsonProperty("subtitle")
    public final String subtitle;
    @SerializedName("title")
    @JsonProperty("title")
    public final String title;
    @SerializedName("type")
    @JsonProperty("type")
    public final String type;
    @SerializedName("uri")
    @JsonProperty("uri")
    public final String uri;

    public PlayerContext() throws  {
        this(null, null, null, null);
    }

    public PlayerContext(String $r1, String $r2, String $r3, String $r4) throws  {
        this.uri = $r1;
        this.title = $r2;
        this.subtitle = $r3;
        this.type = $r4;
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
        PlayerContext $r4 = (PlayerContext) $r1;
        if (this.uri != null) {
            if (!this.uri.equals($r4.uri)) {
                return false;
            }
        } else if ($r4.uri != null) {
            return false;
        }
        if (this.title != null) {
            if (!this.title.equals($r4.title)) {
                return false;
            }
        } else if ($r4.title != null) {
            return false;
        }
        if (this.subtitle != null) {
            if (!this.subtitle.equals($r4.subtitle)) {
                return false;
            }
        } else if ($r4.subtitle != null) {
            return false;
        }
        if (this.type != null) {
            if (this.type.equals($r4.type)) {
                $z0 = false;
            }
        } else if ($r4.type == null) {
            $z0 = false;
        }
        return $z0;
    }

    public int hashCode() throws  {
        int $i1;
        int $i2;
        int $i0 = 0;
        if (this.uri != null) {
            $i1 = this.uri.hashCode();
        } else {
            $i1 = 0;
        }
        $i1 *= 31;
        if (this.title != null) {
            $i2 = this.title.hashCode();
        } else {
            $i2 = 0;
        }
        $i1 = ($i1 + $i2) * 31;
        if (this.subtitle != null) {
            $i2 = this.subtitle.hashCode();
        } else {
            $i2 = 0;
        }
        $i1 = ($i1 + $i2) * 31;
        if (this.type != null) {
            $i0 = this.type.hashCode();
        }
        return $i1 + $i0;
    }

    public String toString() throws  {
        return "PlayerContext{, uri=" + this.uri + ", title=" + this.title + ", subtitle=" + this.subtitle + ", type=" + this.type + '}';
    }
}
