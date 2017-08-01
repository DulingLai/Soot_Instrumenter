package com.spotify.protocol.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Roles implements Item {
    @SerializedName("broker")
    @JsonProperty("broker")
    public final Empty broker;
    @SerializedName("caller")
    @JsonProperty("caller")
    public final Empty caller;
    @SerializedName("dealer")
    @JsonProperty("dealer")
    public final Empty dealer;
    @SerializedName("subscriber")
    @JsonProperty("subscriber")
    public final Empty subscriber;

    public Roles() throws  {
        this(null, null, null, null);
    }

    public Roles(Empty $r1, Empty $r2, Empty $r3, Empty $r4) throws  {
        this.dealer = $r1;
        this.broker = $r2;
        this.subscriber = $r3;
        this.caller = $r4;
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
        Roles $r4 = (Roles) $r1;
        if (this.dealer != null) {
            if (!this.dealer.equals($r4.dealer)) {
                return false;
            }
        } else if ($r4.dealer != null) {
            return false;
        }
        if (this.broker != null) {
            if (!this.broker.equals($r4.broker)) {
                return false;
            }
        } else if ($r4.broker != null) {
            return false;
        }
        if (this.subscriber != null) {
            if (!this.subscriber.equals($r4.subscriber)) {
                return false;
            }
        } else if ($r4.subscriber != null) {
            return false;
        }
        if (this.caller != null) {
            $z0 = this.caller.equals($r4.caller);
        } else if ($r4.caller != null) {
            $z0 = false;
        }
        return $z0;
    }

    public int hashCode() throws  {
        int $i1;
        int $i2;
        int $i0 = 0;
        if (this.dealer != null) {
            $i1 = this.dealer.hashCode();
        } else {
            $i1 = 0;
        }
        $i1 *= 31;
        if (this.broker != null) {
            $i2 = this.broker.hashCode();
        } else {
            $i2 = 0;
        }
        $i1 = ($i1 + $i2) * 31;
        if (this.subscriber != null) {
            $i2 = this.subscriber.hashCode();
        } else {
            $i2 = 0;
        }
        $i1 = ($i1 + $i2) * 31;
        if (this.caller != null) {
            $i0 = this.caller.hashCode();
        }
        return $i1 + $i0;
    }

    public String toString() throws  {
        return "Roles{dealer=" + this.dealer + ", broker=" + this.broker + ", subscriber=" + this.subscriber + ", caller=" + this.caller + '}';
    }
}
