package com.spotify.protocol.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MotionState implements Item {
    public static final int DETECTED = 2;
    public static final int DETECTING = 1;
    public static final int ERROR = 3;
    public static final int INITIAL = 0;
    public static final int SKIPPED = 4;
    public static final int UNKNOWN = -1;
    @SerializedName("state")
    @JsonProperty("state")
    public final int state;

    private MotionState() throws  {
        this(-1);
    }

    public MotionState(int $i0) throws  {
        this.state = $i0;
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if ($r1 == null || getClass() != $r1.getClass()) {
            return false;
        }
        return this.state == ((MotionState) $r1).state;
    }

    public int hashCode() throws  {
        return this.state;
    }

    public String toString() throws  {
        return "MotionState{state=" + this.state + '}';
    }
}
