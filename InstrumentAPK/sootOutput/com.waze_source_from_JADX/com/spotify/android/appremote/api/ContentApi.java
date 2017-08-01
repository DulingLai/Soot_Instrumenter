package com.spotify.android.appremote.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.types.Empty;
import com.spotify.protocol.types.ListItem;
import com.spotify.protocol.types.ListItems;
import dalvik.annotation.Signature;

public interface ContentApi {

    public enum ContentType {
        DEFAULT("default"),
        NAVIGATION("navigation"),
        FITNESS("fitness");
        
        @SerializedName("type")
        @JsonProperty("type")
        public final String name;

        private ContentType(@Signature({"(", "Ljava/lang/String;", ")V"}) String $r2) throws  {
            this.name = $r2;
        }
    }

    CallResult<ListItems> getChildrenOfItem(@Signature({"(", "Lcom/spotify/protocol/types/ListItem;", "II)", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/ListItems;", ">;"}) ListItem listItem, @Signature({"(", "Lcom/spotify/protocol/types/ListItem;", "II)", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/ListItems;", ">;"}) int i, @Signature({"(", "Lcom/spotify/protocol/types/ListItem;", "II)", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/ListItems;", ">;"}) int i2) throws ;

    CallResult<ListItems> getRecommendedContentItems(@Signature({"(", "Lcom/spotify/android/appremote/api/ContentApi$ContentType;", ")", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/ListItems;", ">;"}) ContentType contentType) throws ;

    CallResult<Empty> playContentItem(@Signature({"(", "Lcom/spotify/protocol/types/ListItem;", ")", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/Empty;", ">;"}) ListItem listItem) throws ;
}
