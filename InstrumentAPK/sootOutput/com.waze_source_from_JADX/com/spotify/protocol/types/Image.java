package com.spotify.protocol.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Image implements Item {
    @SerializedName("height")
    @JsonProperty("height")
    public final int height;
    @SerializedName("image_data")
    @JsonProperty("image_data")
    public final byte[] imageData;
    @SerializedName("width")
    @JsonProperty("width")
    public final int width;

    private Image() throws  {
        this(null, 0, 0);
    }

    public Image(byte[] $r1, int $i0, int $i1) throws  {
        this.imageData = $r1;
        this.width = $i0;
        this.height = $i1;
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if ($r1 == null) {
            return false;
        }
        if (getClass() != $r1.getClass()) {
            return false;
        }
        Image $r4 = (Image) $r1;
        if (this.width == $r4.width) {
            return this.height == $r4.height ? Arrays.equals(this.imageData, $r4.imageData) : false;
        } else {
            return false;
        }
    }

    public int hashCode() throws  {
        return (((Arrays.hashCode(this.imageData) * 31) + this.width) * 31) + this.height;
    }

    public String toString() throws  {
        return "Image{imageData=" + Arrays.toString(this.imageData) + ", width=" + this.width + ", height=" + this.height + '}';
    }
}
