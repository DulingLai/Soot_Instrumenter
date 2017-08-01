package com.spotify.protocol.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WelcomeDetails implements Item {
    @SerializedName("roles")
    @JsonProperty("roles")
    public final Roles roles;

    private WelcomeDetails() throws  {
        this(null);
    }

    public WelcomeDetails(Roles $r1) throws  {
        this.roles = $r1;
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if ($r1 == null || getClass() != $r1.getClass()) {
            return false;
        }
        WelcomeDetails $r4 = (WelcomeDetails) $r1;
        if (this.roles != null) {
            return this.roles.equals($r4.roles);
        }
        return $r4.roles == null;
    }

    public int hashCode() throws  {
        return this.roles != null ? this.roles.hashCode() : 0;
    }

    public String toString() throws  {
        return "WelcomeDetails{roles=" + this.roles + '}';
    }
}
