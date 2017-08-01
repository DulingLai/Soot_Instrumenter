package com.spotify.protocol.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChildrenPageRequest implements Item {
    @SerializedName("limit")
    @JsonProperty("limit")
    public final int limit;
    @SerializedName("offset")
    @JsonProperty("offset")
    public final int offset;
    @SerializedName("parent_id")
    @JsonProperty("parent_id")
    public final String parentId;

    private ChildrenPageRequest() throws  {
        this(null, 0, 0);
    }

    public ChildrenPageRequest(String $r1, int $i0, int $i1) throws  {
        this.parentId = $r1;
        this.limit = $i0;
        this.offset = $i1;
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
        ChildrenPageRequest $r4 = (ChildrenPageRequest) $r1;
        if (this.limit != $r4.limit) {
            return false;
        }
        if (this.offset != $r4.offset) {
            return false;
        }
        if (this.parentId != null) {
            $z0 = this.parentId.equals($r4.parentId);
        } else if ($r4.parentId != null) {
            $z0 = false;
        }
        return $z0;
    }

    public int hashCode() throws  {
        return ((((this.parentId != null ? this.parentId.hashCode() : 0) * 31) + this.limit) * 31) + this.offset;
    }

    public String toString() throws  {
        return "ChildrenPageRequest{parentId='" + this.parentId + '\'' + ", limit=" + this.limit + ", offset=" + this.offset + '}';
    }
}
