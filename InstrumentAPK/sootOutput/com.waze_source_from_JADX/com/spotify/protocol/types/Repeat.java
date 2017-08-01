package com.spotify.protocol.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Repeat implements Item {
    public static final int ALL = 2;
    public static final int OFF = 0;
    public static final int ONE = 1;
    @SerializedName("repeat")
    @JsonProperty("repeat")
    public final int repeat;

    private Repeat() throws  {
        this(0);
    }

    public Repeat(int $i0) throws  {
        this.repeat = $i0;
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if ($r1 == null || getClass() != $r1.getClass()) {
            return false;
        }
        return this.repeat == ((Repeat) $r1).repeat;
    }

    public int hashCode() throws  {
        return this.repeat;
    }

    public String toString() throws  {
        return "Repeat{repeat=" + this.repeat + '}';
    }
}
