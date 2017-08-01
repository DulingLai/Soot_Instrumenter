package com.spotify.protocol.client;

import dalvik.annotation.Signature;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public abstract class PendingResultBase<T> implements PendingResult<T> {
    protected volatile ErrorCallback mErrorCallback;
    protected volatile boolean mIsCanceled;
    protected volatile Result<T> mRecentResult;
    private final CountDownLatch mResultLatch = new CountDownLatch(1);

    protected abstract void onResultDelivered() throws ;

    public void cancel() throws  {
        this.mIsCanceled = true;
    }

    public boolean isCanceled() throws  {
        return this.mIsCanceled;
    }

    public Result<T> await() throws  {
        try {
            this.mResultLatch.await();
        } catch (InterruptedException $r1) {
            this.mRecentResult = ResultUtils.createErrorResult($r1);
        }
        return this.mRecentResult;
    }

    public Result<T> await(@Signature({"(J", "Ljava/util/concurrent/TimeUnit;", ")", "Lcom/spotify/protocol/client/Result", "<TT;>;"}) long $l0, @Signature({"(J", "Ljava/util/concurrent/TimeUnit;", ")", "Lcom/spotify/protocol/client/Result", "<TT;>;"}) TimeUnit $r1) throws  {
        try {
            if (!this.mResultLatch.await($l0, $r1)) {
                this.mRecentResult = ResultUtils.createErrorResult(new Exception("Result was not delivered on time."));
            }
        } catch (InterruptedException $r2) {
            this.mRecentResult = ResultUtils.createErrorResult($r2);
        }
        return this.mRecentResult;
    }

    public PendingResult<T> setErrorCallback(@Signature({"(", "Lcom/spotify/protocol/client/ErrorCallback;", ")", "Lcom/spotify/protocol/client/PendingResult", "<TT;>;"}) ErrorCallback $r1) throws  {
        this.mErrorCallback = $r1;
        if (isCanceled() || this.mErrorCallback == null || this.mRecentResult == null || this.mRecentResult.getError() == null) {
            return this;
        }
        this.mErrorCallback.onError(this.mRecentResult.getError());
        return this;
    }

    public void deliverResult(@Signature({"(", "Lcom/spotify/protocol/client/Result", "<TT;>;)V"}) Result<T> $r1) throws  {
        this.mRecentResult = (Result) Coding.checkNotNull($r1);
        this.mResultLatch.countDown();
        onResultDelivered();
    }

    public void deliverError(Throwable $r1) throws  {
        this.mRecentResult = ResultUtils.createErrorResult($r1);
        this.mResultLatch.countDown();
        if (!isCanceled() && this.mErrorCallback != null) {
            this.mErrorCallback.onError(this.mRecentResult.getError());
        }
    }
}
