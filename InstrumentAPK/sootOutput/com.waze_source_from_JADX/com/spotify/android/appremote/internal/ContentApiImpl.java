package com.spotify.android.appremote.internal;

import com.spotify.android.appremote.api.ContentApi;
import com.spotify.android.appremote.api.ContentApi.ContentType;
import com.spotify.protocol.AppProtocol.CallUri;
import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.client.Coding;
import com.spotify.protocol.client.RemoteClient;
import com.spotify.protocol.types.ChildrenPageRequest;
import com.spotify.protocol.types.Empty;
import com.spotify.protocol.types.GetRecommendedContentItemsRequest;
import com.spotify.protocol.types.Identifier;
import com.spotify.protocol.types.ListItem;
import com.spotify.protocol.types.ListItems;
import dalvik.annotation.Signature;

public class ContentApiImpl implements ContentApi {
    private final RemoteClient mRemoteClient;

    public ContentApiImpl(RemoteClient $r1) throws  {
        this.mRemoteClient = $r1;
    }

    public CallResult<ListItems> getRecommendedContentItems(@Signature({"(", "Lcom/spotify/android/appremote/api/ContentApi$ContentType;", ")", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/ListItems;", ">;"}) ContentType $r1) throws  {
        Validate.checkNotNull($r1);
        return this.mRemoteClient.call(CallUri.GET_RECOMMENDED_ROOT_ITEMS, new GetRecommendedContentItemsRequest($r1.name), ListItems.class);
    }

    public CallResult<ListItems> getChildrenOfItem(@Signature({"(", "Lcom/spotify/protocol/types/ListItem;", "II)", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/ListItems;", ">;"}) ListItem $r1, @Signature({"(", "Lcom/spotify/protocol/types/ListItem;", "II)", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/ListItems;", ">;"}) int $i0, @Signature({"(", "Lcom/spotify/protocol/types/ListItem;", "II)", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/ListItems;", ">;"}) int $i1) throws  {
        Validate.checkNotNull($r1);
        return this.mRemoteClient.call(CallUri.GET_CHILDREN_OF_ITEM, new ChildrenPageRequest($r1.id, $i0, $i1), ListItems.class);
    }

    public CallResult<Empty> playContentItem(@Signature({"(", "Lcom/spotify/protocol/types/ListItem;", ")", "Lcom/spotify/protocol/client/CallResult", "<", "Lcom/spotify/protocol/types/Empty;", ">;"}) ListItem $r1) throws  {
        Coding.checkNotNull($r1);
        if ($r1.playable) {
            return this.mRemoteClient.call(CallUri.PLAY_ITEM, new Identifier($r1.id), Empty.class);
        }
        throw new IllegalArgumentException("The ContentItem is not playable.");
    }
}
