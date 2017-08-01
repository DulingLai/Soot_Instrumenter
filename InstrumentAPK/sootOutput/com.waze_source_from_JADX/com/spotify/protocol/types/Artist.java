package com.spotify.protocol.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist implements Item {
    @SerializedName("name")
    @JsonProperty("name")
    public final String name;
    @SerializedName("uri")
    @JsonProperty("uri")
    public final String uri;

    private Artist() throws  {
        this(null, null);
    }

    public Artist(String $r1, String $r2) throws  {
        this.name = $r1;
        this.uri = $r2;
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if ($r1 == null || getClass() != $r1.getClass()) {
            return false;
        }
        Artist $r4 = (Artist) $r1;
        if (this.name == null ? $r4.name != null : !this.name.equals($r4.name)) {
            return false;
        }
        if (this.uri != null) {
            return this.uri.equals($r4.uri);
        }
        return $r4.uri == null;
    }

    public int hashCode() throws  {
        int $i1;
        int $i0 = 0;
        if (this.name != null) {
            $i1 = this.name.hashCode();
        } else {
            $i1 = 0;
        }
        $i1 *= 31;
        if (this.uri != null) {
            $i0 = this.uri.hashCode();
        }
        return $i1 + $i0;
    }

    public String toString() throws  {
        return "Artist{name='" + this.name + '\'' + ", uri='" + this.uri + '\'' + '}';
    }
}
