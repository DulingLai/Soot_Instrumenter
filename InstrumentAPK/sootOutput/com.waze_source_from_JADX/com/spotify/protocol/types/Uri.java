package com.spotify.protocol.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Uri implements Item {
    @SerializedName("uri")
    @JsonProperty("uri")
    public final String uri;

    private Uri() throws  {
        this(null);
    }

    public Uri(String $r1) throws  {
        this.uri = $r1;
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if ($r1 == null || getClass() != $r1.getClass()) {
            return false;
        }
        Uri $r4 = (Uri) $r1;
        if (this.uri != null) {
            return this.uri.equals($r4.uri);
        }
        return $r4.uri == null;
    }

    public int hashCode() throws  {
        return this.uri != null ? this.uri.hashCode() : 0;
    }

    public String toString() throws  {
        return "Uri{uri='" + this.uri + '\'' + '}';
    }
}
