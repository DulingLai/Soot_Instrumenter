package com.spotify.protocol.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import dalvik.annotation.Signature;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Info implements Item {
    @SerializedName("default_image_height")
    @JsonProperty("default_image_height")
    public final int defaultImageHeight;
    @SerializedName("default_image_width")
    @JsonProperty("default_image_width")
    public final int defaultImageWidth;
    @SerializedName("default_thumbnail_image_height")
    @JsonProperty("default_thumbnail_image_height")
    public final int defaultThumbnailImageHeight;
    @SerializedName("default_thumbnail_image_width")
    @JsonProperty("default_thumbnail_image_width")
    public final int defaultThumbnailImageWidth;
    @SerializedName("id")
    @JsonProperty("id")
    public final String id;
    @SerializedName("image_type")
    @JsonProperty("image_type")
    public final String imageType;
    @SerializedName("model")
    @JsonProperty("model")
    public final String model;
    @SerializedName("name")
    @JsonProperty("name")
    public final String name;
    @SerializedName("protocol_version")
    @JsonProperty("protocol_version")
    public final int protocolVersion;
    @SerializedName("required_features")
    @JsonProperty("required_features")
    public final List<String> requiredFeatures;

    private Info() throws  {
        this(0, null, null, null, null, null, 0, 0, 0, 0);
    }

    public Info(@Signature({"(I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IIII)V"}) int $i0, @Signature({"(I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IIII)V"}) List<String> $r1, @Signature({"(I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IIII)V"}) String $r2, @Signature({"(I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IIII)V"}) String $r3, @Signature({"(I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IIII)V"}) String $r4, @Signature({"(I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IIII)V"}) String $r5, @Signature({"(I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IIII)V"}) int $i1, @Signature({"(I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IIII)V"}) int $i2, @Signature({"(I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IIII)V"}) int $i3, @Signature({"(I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "IIII)V"}) int $i4) throws  {
        this.protocolVersion = $i0;
        this.id = $r2;
        this.name = $r3;
        this.model = $r4;
        this.imageType = $r5;
        this.requiredFeatures = $r1;
        this.defaultImageHeight = $i2;
        this.defaultImageWidth = $i1;
        this.defaultThumbnailImageHeight = $i4;
        this.defaultThumbnailImageWidth = $i3;
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
        Info $r4 = (Info) $r1;
        if (this.protocolVersion != $r4.protocolVersion) {
            return false;
        }
        if (this.defaultImageHeight != $r4.defaultImageHeight) {
            return false;
        }
        if (this.defaultImageWidth != $r4.defaultImageWidth) {
            return false;
        }
        if (this.defaultThumbnailImageHeight != $r4.defaultThumbnailImageHeight) {
            return false;
        }
        if (this.defaultThumbnailImageWidth != $r4.defaultThumbnailImageWidth) {
            return false;
        }
        if (this.id != null) {
            if (!this.id.equals($r4.id)) {
                return false;
            }
        } else if ($r4.id != null) {
            return false;
        }
        if (this.name != null) {
            if (!this.name.equals($r4.name)) {
                return false;
            }
        } else if ($r4.name != null) {
            return false;
        }
        if (this.model != null) {
            if (!this.model.equals($r4.model)) {
                return false;
            }
        } else if ($r4.model != null) {
            return false;
        }
        if (this.imageType != null) {
            if (!this.imageType.equals($r4.imageType)) {
                return false;
            }
        } else if ($r4.imageType != null) {
            return false;
        }
        if (this.requiredFeatures != null) {
            $z0 = this.requiredFeatures.equals($r4.requiredFeatures);
        } else if ($r4.requiredFeatures != null) {
            $z0 = false;
        }
        return $z0;
    }

    public int hashCode() throws  {
        int $i2;
        int $i0 = 0;
        int $i1 = ((this.protocolVersion * 31) + (this.id != null ? this.id.hashCode() : 0)) * 31;
        if (this.name != null) {
            $i2 = this.name.hashCode();
        } else {
            $i2 = 0;
        }
        $i1 = ($i1 + $i2) * 31;
        if (this.model != null) {
            $i2 = this.model.hashCode();
        } else {
            $i2 = 0;
        }
        $i1 = ($i1 + $i2) * 31;
        if (this.imageType != null) {
            $i2 = this.imageType.hashCode();
        } else {
            $i2 = 0;
        }
        $i1 = ($i1 + $i2) * 31;
        if (this.requiredFeatures != null) {
            $i0 = this.requiredFeatures.hashCode();
        }
        return (((((((($i1 + $i0) * 31) + this.defaultImageHeight) * 31) + this.defaultImageWidth) * 31) + this.defaultThumbnailImageHeight) * 31) + this.defaultThumbnailImageWidth;
    }

    public String toString() throws  {
        return "Info{protocolVersion=" + this.protocolVersion + ", id='" + this.id + '\'' + ", name='" + this.name + '\'' + ", model='" + this.model + '\'' + ", imageType='" + this.imageType + '\'' + ", requiredFeatures=" + this.requiredFeatures + ", defaultImageHeight=" + this.defaultImageHeight + ", defaultImageWidth=" + this.defaultImageWidth + ", defaultThumbnailImageHeight=" + this.defaultThumbnailImageHeight + ", defaultThumbnailImageWidth=" + this.defaultThumbnailImageWidth + '}';
    }
}
