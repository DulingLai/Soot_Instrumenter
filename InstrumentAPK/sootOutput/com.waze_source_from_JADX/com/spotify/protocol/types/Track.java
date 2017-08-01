package com.spotify.protocol.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import dalvik.annotation.Signature;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Track implements Item {
    @SerializedName("album")
    @JsonProperty("album")
    public final Album album;
    @SerializedName("artist")
    @JsonProperty("artist")
    public final Artist artist;
    @SerializedName("artists")
    @JsonProperty("artists")
    public final List<Artist> artists;
    @SerializedName("duration_ms")
    @JsonProperty("duration_ms")
    public final long duration;
    @SerializedName("image_id")
    @JsonProperty("image_id")
    public final ImageUri imageUri;
    @SerializedName("name")
    @JsonProperty("name")
    public final String name;
    @SerializedName("uri")
    @JsonProperty("uri")
    public final String uri;

    private Track() throws  {
        this(null, null, null, 0, null, null, null);
    }

    public Track(@Signature({"(", "Lcom/spotify/protocol/types/Artist;", "Ljava/util/List", "<", "Lcom/spotify/protocol/types/Artist;", ">;", "Lcom/spotify/protocol/types/Album;", "J", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/spotify/protocol/types/ImageUri;", ")V"}) Artist $r1, @Signature({"(", "Lcom/spotify/protocol/types/Artist;", "Ljava/util/List", "<", "Lcom/spotify/protocol/types/Artist;", ">;", "Lcom/spotify/protocol/types/Album;", "J", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/spotify/protocol/types/ImageUri;", ")V"}) List<Artist> $r2, @Signature({"(", "Lcom/spotify/protocol/types/Artist;", "Ljava/util/List", "<", "Lcom/spotify/protocol/types/Artist;", ">;", "Lcom/spotify/protocol/types/Album;", "J", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/spotify/protocol/types/ImageUri;", ")V"}) Album $r3, @Signature({"(", "Lcom/spotify/protocol/types/Artist;", "Ljava/util/List", "<", "Lcom/spotify/protocol/types/Artist;", ">;", "Lcom/spotify/protocol/types/Album;", "J", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/spotify/protocol/types/ImageUri;", ")V"}) long $l0, @Signature({"(", "Lcom/spotify/protocol/types/Artist;", "Ljava/util/List", "<", "Lcom/spotify/protocol/types/Artist;", ">;", "Lcom/spotify/protocol/types/Album;", "J", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/spotify/protocol/types/ImageUri;", ")V"}) String $r4, @Signature({"(", "Lcom/spotify/protocol/types/Artist;", "Ljava/util/List", "<", "Lcom/spotify/protocol/types/Artist;", ">;", "Lcom/spotify/protocol/types/Album;", "J", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/spotify/protocol/types/ImageUri;", ")V"}) String $r5, @Signature({"(", "Lcom/spotify/protocol/types/Artist;", "Ljava/util/List", "<", "Lcom/spotify/protocol/types/Artist;", ">;", "Lcom/spotify/protocol/types/Album;", "J", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/spotify/protocol/types/ImageUri;", ")V"}) ImageUri $r6) throws  {
        this.artist = $r1;
        this.artists = $r2;
        this.album = $r3;
        this.duration = $l0;
        this.name = $r4;
        this.uri = $r5;
        this.imageUri = $r6;
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
        Track $r4 = (Track) $r1;
        if (this.duration != $r4.duration) {
            return false;
        }
        if (this.artist != null) {
            if (!this.artist.equals($r4.artist)) {
                return false;
            }
        } else if ($r4.artist != null) {
            return false;
        }
        if (this.artists != null) {
            if (!this.artists.equals($r4.artists)) {
                return false;
            }
        } else if ($r4.artists != null) {
            return false;
        }
        if (this.album != null) {
            if (!this.album.equals($r4.album)) {
                return false;
            }
        } else if ($r4.album != null) {
            return false;
        }
        if (this.name != null) {
            if (!this.name.equals($r4.name)) {
                return false;
            }
        } else if ($r4.name != null) {
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
            $z0 = this.imageUri.equals($r4.imageUri);
        } else if ($r4.imageUri != null) {
            $z0 = false;
        }
        return $z0;
    }

    public int hashCode() throws  {
        int $i2;
        int $i0 = 0;
        int $i1 = (this.artist != null ? this.artist.hashCode() : 0) * 31;
        if (this.artists != null) {
            $i2 = this.artists.hashCode();
        } else {
            $i2 = 0;
        }
        $i1 = ($i1 + $i2) * 31;
        if (this.album != null) {
            $i2 = this.album.hashCode();
        } else {
            $i2 = 0;
        }
        $i1 = ((($i1 + $i2) * 31) + ((int) (this.duration ^ (this.duration >>> 32)))) * 31;
        if (this.name != null) {
            $i2 = this.name.hashCode();
        } else {
            $i2 = 0;
        }
        $i1 = ($i1 + $i2) * 31;
        if (this.uri != null) {
            $i2 = this.uri.hashCode();
        } else {
            $i2 = 0;
        }
        $i1 = ($i1 + $i2) * 31;
        if (this.imageUri != null) {
            $i0 = this.imageUri.hashCode();
        }
        return $i1 + $i0;
    }

    public String toString() throws  {
        return "Track{artist=" + this.artist + ", artists=" + this.artists + ", album=" + this.album + ", duration=" + this.duration + ", name='" + this.name + '\'' + ", uri='" + this.uri + '\'' + ", imageId='" + this.imageUri + '\'' + '}';
    }
}
