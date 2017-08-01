package com.spotify.protocol.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListItem implements Item {
    @SerializedName("has_children")
    @JsonProperty("has_children")
    public final boolean hasChildren;
    @SerializedName("id")
    @JsonProperty("id")
    public final String id;
    @SerializedName("image_id")
    @JsonProperty("image_id")
    public final ImageUri imageUri;
    @SerializedName("playable")
    @JsonProperty("playable")
    public final boolean playable;
    @SerializedName("subtitle")
    @JsonProperty("subtitle")
    public final String subtitle;
    @SerializedName("title")
    @JsonProperty("title")
    public final String title;
    @SerializedName("uri")
    @JsonProperty("uri")
    public final String uri;

    private ListItem() throws  {
        this(null, null, null, null, null, false, false);
    }

    public ListItem(String $r1, String $r2, ImageUri $r3, String $r4, String $r5, boolean $z0, boolean $z1) throws  {
        this.id = $r1;
        this.uri = $r2;
        this.imageUri = $r3;
        this.title = $r4;
        this.subtitle = $r5;
        this.playable = $z0;
        this.hasChildren = $z1;
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
        ListItem $r4 = (ListItem) $r1;
        if (this.playable != $r4.playable) {
            return false;
        }
        if (this.hasChildren != $r4.hasChildren) {
            return false;
        }
        if (this.id != null) {
            if (!this.id.equals($r4.id)) {
                return false;
            }
        } else if ($r4.id != null) {
            return false;
        }
        if (this.uri != null) {
            if (!this.uri.equals($r4.uri)) {
                return false;
            }
        } else if ($r4.uri != null) {
            return false;
        }
        if (this.imageUri != null) {
            if (!this.imageUri.equals($r4.imageUri)) {
                return false;
            }
        } else if ($r4.imageUri != null) {
            return false;
        }
        if (this.title != null) {
            if (!this.title.equals($r4.title)) {
                return false;
            }
        } else if ($r4.title != null) {
            return false;
        }
        if (this.subtitle != null) {
            $z0 = this.subtitle.equals($r4.subtitle);
        } else if ($r4.subtitle != null) {
            $z0 = false;
        }
        return $z0;
    }

    public int hashCode() throws  {
        int $i2;
        byte $b3;
        byte $b0 = (byte) 1;
        int $i1 = (this.id != null ? this.id.hashCode() : 0) * 31;
        if (this.uri != null) {
            $i2 = this.uri.hashCode();
        } else {
            $i2 = 0;
        }
        $i1 = ($i1 + $i2) * 31;
        if (this.imageUri != null) {
            $i2 = this.imageUri.hashCode();
        } else {
            $i2 = 0;
        }
        $i1 = ($i1 + $i2) * 31;
        if (this.title != null) {
            $i2 = this.title.hashCode();
        } else {
            $i2 = 0;
        }
        $i1 = ($i1 + $i2) * 31;
        if (this.subtitle != null) {
            $i2 = this.subtitle.hashCode();
        } else {
            $i2 = 0;
        }
        $i1 = ($i1 + $i2) * 31;
        if (this.playable) {
            $b3 = (byte) 1;
        } else {
            $b3 = (byte) 0;
        }
        $i1 = ($i1 + $b3) * 31;
        if (!this.hasChildren) {
            $b0 = (byte) 0;
        }
        return $i1 + $b0;
    }

    public String toString() throws  {
        return "ListItem{id='" + this.id + '\'' + ", uri='" + this.uri + '\'' + ", imageId='" + this.imageUri + '\'' + ", title='" + this.title + '\'' + ", subtitle='" + this.subtitle + '\'' + ", playable=" + this.playable + ", hasChildren=" + this.hasChildren + '}';
    }
}
