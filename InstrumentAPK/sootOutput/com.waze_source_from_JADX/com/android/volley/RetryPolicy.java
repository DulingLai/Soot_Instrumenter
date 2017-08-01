package com.android.volley;

public interface RetryPolicy {
    int getCurrentRetryCount() throws ;

    int getCurrentTimeout() throws ;

    void retry(VolleyError volleyError) throws VolleyError;
}
