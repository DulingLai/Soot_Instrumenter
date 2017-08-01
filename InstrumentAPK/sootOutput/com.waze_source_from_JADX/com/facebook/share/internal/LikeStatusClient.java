package com.facebook.share.internal;

import android.content.Context;
import android.os.Bundle;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.PlatformServiceClient;

final class LikeStatusClient extends PlatformServiceClient {
    private String objectId;

    LikeStatusClient(Context $r1, String $r2, String $r3) throws  {
        super($r1, NativeProtocol.MESSAGE_GET_LIKE_STATUS_REQUEST, NativeProtocol.MESSAGE_GET_LIKE_STATUS_REPLY, NativeProtocol.PROTOCOL_VERSION_20141001, $r2);
        this.objectId = $r3;
    }

    protected void populateRequestBundle(Bundle $r1) throws  {
        $r1.putString(ShareConstants.EXTRA_OBJECT_ID, this.objectId);
    }
}
