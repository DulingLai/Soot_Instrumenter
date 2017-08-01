package com.android.volley;

public class DefaultRetryPolicy implements RetryPolicy {
    public static final float DEFAULT_BACKOFF_MULT = 1.0f;
    public static final int DEFAULT_MAX_RETRIES = 1;
    public static final int DEFAULT_TIMEOUT_MS = 2500;
    private final float mBackoffMultiplier;
    private int mCurrentRetryCount;
    private int mCurrentTimeoutMs;
    private final int mMaxNumRetries;

    public DefaultRetryPolicy() throws  {
        this(2500, 1, 1.0f);
    }

    public DefaultRetryPolicy(int $i0, int $i1, float $f0) throws  {
        this.mCurrentTimeoutMs = $i0;
        this.mMaxNumRetries = $i1;
        this.mBackoffMultiplier = $f0;
    }

    public int getCurrentTimeout() throws  {
        return this.mCurrentTimeoutMs;
    }

    public int getCurrentRetryCount() throws  {
        return this.mCurrentRetryCount;
    }

    public void retry(VolleyError $r1) throws VolleyError {
        this.mCurrentRetryCount++;
        this.mCurrentTimeoutMs = (int) (((float) this.mCurrentTimeoutMs) + (((float) this.mCurrentTimeoutMs) * this.mBackoffMultiplier));
        if (!hasAttemptRemaining()) {
            throw $r1;
        }
    }

    protected boolean hasAttemptRemaining() throws  {
        return this.mCurrentRetryCount <= this.mMaxNumRetries;
    }
}
