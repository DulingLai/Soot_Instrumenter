package com.spotify.protocol.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Uris implements Item {
    @JsonProperty("uris")
    public final String[] uris;

    public Uris() throws  {
        this(null);
    }

    public Uris(String[] $r1) throws  {
        this.uris = $r1;
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if ($r1 == null || getClass() != $r1.getClass()) {
            return false;
        }
        return Arrays.equals(this.uris, ((Uris) $r1).uris);
    }

    public int hashCode() throws  {
        return Arrays.hashCode(this.uris);
    }

    public String toString() throws  {
        return "Uris{uris=" + Arrays.toString(this.uris) + '}';
    }
}
