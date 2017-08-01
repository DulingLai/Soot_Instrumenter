package com.spotify.protocol.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListItems implements Item {
    @SerializedName("items")
    @JsonProperty("items")
    public final ListItem[] items;
    @SerializedName("limit")
    @JsonProperty("limit")
    public final int limit;
    @SerializedName("offset")
    @JsonProperty("offset")
    public final int offset;
    @SerializedName("total")
    @JsonProperty("total")
    public final int total;

    private ListItems() throws  {
        this(0, 0, 0, null);
    }

    public ListItems(int $i0, int $i1, int $i2, ListItem[] $r1) throws  {
        this.limit = $i0;
        this.offset = $i1;
        this.total = $i2;
        this.items = $r1;
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
        ListItems $r4 = (ListItems) $r1;
        if (this.limit != $r4.limit) {
            return false;
        }
        if (this.offset == $r4.offset) {
            return this.total == $r4.total ? Arrays.equals(this.items, $r4.items) : false;
        } else {
            return false;
        }
    }

    public int hashCode() throws  {
        return (((((this.limit * 31) + this.offset) * 31) + this.total) * 31) + Arrays.hashCode(this.items);
    }

    public String toString() throws  {
        return "ListItems{limit=" + this.limit + ", offset=" + this.offset + ", total=" + this.total + ", items=" + Arrays.toString(this.items) + '}';
    }
}
