package com.spotify.protocol.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LibraryState implements Item {
    @SerializedName("can_save")
    @JsonProperty("can_save")
    public final boolean canAdd;
    @SerializedName("saved")
    @JsonProperty("saved")
    public final boolean isAdded;
    @SerializedName("uri")
    @JsonProperty("uri")
    public final String uri;

    private LibraryState() throws  {
        this(null, false, false);
    }

    public LibraryState(String $r1, boolean $z0, boolean $z1) throws  {
        this.uri = $r1;
        this.isAdded = $z0;
        this.canAdd = $z1;
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
        LibraryState $r4 = (LibraryState) $r1;
        if (this.isAdded != $r4.isAdded) {
            return false;
        }
        if (this.canAdd != $r4.canAdd) {
            return false;
        }
        if (this.uri != null) {
            $z0 = this.uri.equals($r4.uri);
        } else if ($r4.uri != null) {
            $z0 = false;
        }
        return $z0;
    }

    public int hashCode() throws  {
        int $i0;
        byte $b2;
        byte $b1 = (byte) 1;
        if (this.uri != null) {
            $i0 = this.uri.hashCode();
        } else {
            $i0 = 0;
        }
        $i0 *= 31;
        if (this.isAdded) {
            $b2 = (byte) 1;
        } else {
            $b2 = (byte) 0;
        }
        $i0 = ($i0 + $b2) * 31;
        if (!this.canAdd) {
            $b1 = (byte) 0;
        }
        return $i0 + $b1;
    }

    public String toString() throws  {
        return "LibraryState{uri='" + this.uri + '\'' + ", isAdded=" + this.isAdded + ", canAdd=" + this.canAdd + '}';
    }
}
