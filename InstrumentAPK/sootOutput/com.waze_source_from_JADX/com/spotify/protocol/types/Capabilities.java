package com.spotify.protocol.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Capabilities implements Item {
    @SerializedName("can_play_on_demand")
    @JsonProperty("can_play_on_demand")
    public final boolean canPlayOnDemand;

    private Capabilities() throws  {
        this(false);
    }

    public Capabilities(boolean $z0) throws  {
        this.canPlayOnDemand = $z0;
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if ($r1 == null || getClass() != $r1.getClass()) {
            return false;
        }
        return this.canPlayOnDemand == ((Capabilities) $r1).canPlayOnDemand;
    }

    public int hashCode() throws  {
        return this.canPlayOnDemand ? 1 : 0;
    }

    public String toString() throws  {
        return "Capabilities{canPlayOnDemand=" + this.canPlayOnDemand + '}';
    }
}
