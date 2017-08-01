package com.spotify.protocol.client;

import dalvik.annotation.Signature;
import java.util.concurrent.TimeUnit;

public interface PendingResult<T> {
    Result<T> await() throws ;

    Result<T> await(@Signature({"(J", "Ljava/util/concurrent/TimeUnit;", ")", "Lcom/spotify/protocol/client/Result", "<TT;>;"}) long j, @Signature({"(J", "Ljava/util/concurrent/TimeUnit;", ")", "Lcom/spotify/protocol/client/Result", "<TT;>;"}) TimeUnit timeUnit) throws ;

    void cancel() throws ;

    boolean isCanceled() throws ;

    PendingResult<T> setErrorCallback(@Signature({"(", "Lcom/spotify/protocol/client/ErrorCallback;", ")", "Lcom/spotify/protocol/client/PendingResult", "<TT;>;"}) ErrorCallback errorCallback) throws ;
}
