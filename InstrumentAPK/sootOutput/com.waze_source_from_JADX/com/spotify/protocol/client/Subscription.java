package com.spotify.protocol.client;

import com.spotify.protocol.types.Types.RequestId;
import dalvik.annotation.Signature;

public class Subscription<T> extends PendingResultBase<T> {
    private volatile EventCallback<T> mEventCallback;
    private volatile LifecycleCallback mLifecycleCallback;
    private final RemoteClient mRemoteWampClient;
    private final RequestId mRequestId;

    public interface EventCallback<T> {
        void onEvent(@Signature({"(TT;)V"}) T t) throws ;
    }

    public interface LifecycleCallback {
        void onStart() throws ;

        void onStop() throws ;
    }

    public Subscription(RequestId $r1, RemoteClient $r2) throws  {
        this.mRequestId = $r1;
        this.mRemoteWampClient = $r2;
    }

    protected void onResultDelivered() throws  {
        if (!isCanceled() && this.mEventCallback != null) {
            this.mEventCallback.onEvent(this.mRecentResult.getData());
        }
    }

    public void cancel() throws  {
        if (!isCanceled()) {
            super.cancel();
            this.mRemoteWampClient.unsubscribe(this);
        }
    }

    public RequestId getRequestId() throws  {
        return this.mRequestId;
    }

    public Subscription<T> setEventCallback(@Signature({"(", "Lcom/spotify/protocol/client/Subscription$EventCallback", "<TT;>;)", "Lcom/spotify/protocol/client/Subscription", "<TT;>;"}) EventCallback<T> $r1) throws  {
        this.mEventCallback = $r1;
        if (this.mRecentResult == null || !this.mRecentResult.isSuccessful()) {
            return this;
        }
        onResultDelivered();
        return this;
    }

    public Subscription<T> setLifecycleCallback(@Signature({"(", "Lcom/spotify/protocol/client/Subscription$LifecycleCallback;", ")", "Lcom/spotify/protocol/client/Subscription", "<TT;>;"}) LifecycleCallback $r1) throws  {
        this.mLifecycleCallback = $r1;
        return this;
    }

    final void deliverStart() throws  {
        if (!isCanceled() && this.mLifecycleCallback != null) {
            this.mLifecycleCallback.onStart();
        }
    }

    final void deliverStop() throws  {
        if (!isCanceled() && this.mLifecycleCallback != null) {
            this.mLifecycleCallback.onStop();
        }
    }
}
