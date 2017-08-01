package com.spotify.protocol.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Shuffle implements Item {
    @SerializedName("shuffle")
    @JsonProperty("shuffle")
    public final boolean shuffle;

    private Shuffle() throws  {
        this(false);
    }

    public Shuffle(boolean $z0) throws  {
        this.shuffle = $z0;
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if ($r1 == null || getClass() != $r1.getClass()) {
            return false;
        }
        return this.shuffle == ((Shuffle) $r1).shuffle;
    }

    public int hashCode() throws  {
        return this.shuffle ? 1 : 0;
    }

    public String toString() throws  {
        return "Shuffle{shuffle=" + this.shuffle + '}';
    }
}
