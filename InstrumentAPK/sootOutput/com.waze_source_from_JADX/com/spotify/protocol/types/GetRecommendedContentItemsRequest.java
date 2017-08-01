package com.spotify.protocol.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetRecommendedContentItemsRequest {
    @SerializedName("type")
    @JsonProperty("type")
    public final String type;

    public GetRecommendedContentItemsRequest() throws  {
        this("");
    }

    public GetRecommendedContentItemsRequest(String $r1) throws  {
        this.type = $r1;
    }
}
