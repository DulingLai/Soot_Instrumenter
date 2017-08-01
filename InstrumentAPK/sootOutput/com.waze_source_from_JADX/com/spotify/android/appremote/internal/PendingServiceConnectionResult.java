package com.spotify.android.appremote.internal;

import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.client.ResultUtils;
import com.spotify.protocol.types.Types.RequestId;

public class PendingServiceConnectionResult extends CallResult<Void> {
    public PendingServiceConnectionResult() throws  {
        super(RequestId.NONE);
    }

    final void deliverSuccess() throws  {
        super.deliverResult(ResultUtils.createSuccessfulResult(null));
    }

    final void deliverFailure(Throwable $r1) throws  {
        super.deliverResult(ResultUtils.createErrorResult($r1));
    }
}
