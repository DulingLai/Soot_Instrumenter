package com.spotify.protocol.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Identifier implements Item {
    @SerializedName("id")
    @JsonProperty("id")
    public final String id;

    private Identifier() throws  {
        this(null);
    }

    public Identifier(String $r1) throws  {
        this.id = $r1;
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if ($r1 == null || getClass() != $r1.getClass()) {
            return false;
        }
        Identifier $r4 = (Identifier) $r1;
        if (this.id != null) {
            return this.id.equals($r4.id);
        }
        return $r4.id == null;
    }

    public int hashCode() throws  {
        return this.id != null ? this.id.hashCode() : 0;
    }

    public String toString() throws  {
        return "Identifier{id='" + this.id + '\'' + '}';
    }
}
