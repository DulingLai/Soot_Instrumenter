package com.spotify.protocol.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.spotify.protocol.coding.Objects;
import com.spotify.protocol.mappers.jackson.ImageUriJson.Deserializer;
import com.spotify.protocol.mappers.jackson.ImageUriJson.Serializer;

@JsonDeserialize(using = Deserializer.class)
@JsonSerialize(using = Serializer.class)
public class ImageUri implements Item {
    public final String raw;

    private ImageUri() throws  {
        this(null);
    }

    public ImageUri(String $r1) throws  {
        this.raw = $r1;
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if ($r1 == null || getClass() != $r1.getClass()) {
            return false;
        }
        return Objects.equals(this.raw, ((ImageUri) $r1).raw);
    }

    public int hashCode() throws  {
        return Objects.hashCode(this.raw);
    }

    public String toString() throws  {
        StringBuilder $r1 = new StringBuilder("ImageId{");
        $r1.append(this.raw).append('\'');
        $r1.append('}');
        return $r1.toString();
    }
}
