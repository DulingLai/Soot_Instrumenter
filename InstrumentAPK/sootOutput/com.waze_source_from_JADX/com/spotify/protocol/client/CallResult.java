package com.spotify.protocol.client;

import com.spotify.protocol.types.Types.RequestId;
import dalvik.annotation.Signature;

public class CallResult<T> extends PendingResultBase<T> {
    private final RequestId mRequestId;
    private ResultCallback<T> mResultCallback;

    public interface ResultCallback<T> {
        void onResult(@Signature({"(TT;)V"}) T t) throws ;
    }

    public CallResult(RequestId $r1) throws  {
        this.mRequestId = $r1;
    }

    public RequestId getRequestId() throws  {
        return this.mRequestId;
    }

    public CallResult<T> setResultCallback(@Signature({"(", "Lcom/spotify/protocol/client/CallResult$ResultCallback", "<TT;>;)", "Lcom/spotify/protocol/client/CallResult", "<TT;>;"}) ResultCallback<T> $r1) throws  {
        this.mResultCallback = $r1;
        if (this.mRecentResult == null || !this.mRecentResult.isSuccessful()) {
            return this;
        }
        onResultDelivered();
        return this;
    }

    protected void onResultDelivered() throws  {
        if (!isCanceled() && this.mResultCallback != null) {
            this.mResultCallback.onResult(this.mRecentResult.getData());
        }
    }
}
