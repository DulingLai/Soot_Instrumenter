package com.facebook.login;

import android.content.Context;
import android.os.Bundle;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.PlatformServiceClient;

final class GetTokenClient extends PlatformServiceClient {
    GetTokenClient(Context $r1, String $r2) throws  {
        super($r1, 65536, NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REPLY, NativeProtocol.PROTOCOL_VERSION_20121101, $r2);
    }

    protected void populateRequestBundle(Bundle data) throws  {
    }
}
