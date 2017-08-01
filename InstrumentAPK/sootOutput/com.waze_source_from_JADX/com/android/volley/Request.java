package com.android.volley;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import com.android.volley.Cache.Entry;
import com.android.volley.Response.ErrorListener;
import dalvik.annotation.Signature;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;

public abstract class Request<T> implements Comparable<Request<T>> {
    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
    private static final long SLOW_REQUEST_THRESHOLD_MS = 3000;
    private Entry mCacheEntry;
    private boolean mCanceled;
    private final int mDefaultTrafficStatsTag;
    private final ErrorListener mErrorListener;
    private final MarkerLog mEventLog;
    private final int mMethod;
    private long mRequestBirthTime;
    private RequestQueue mRequestQueue;
    private boolean mResponseDelivered;
    private RetryPolicy mRetryPolicy;
    private Integer mSequence;
    private boolean mShouldCache;
    private Object mTag;
    private final String mUrl;

    public interface Method {
        public static final int DELETE = 3;
        public static final int DEPRECATED_GET_OR_POST = -1;
        public static final int GET = 0;
        public static final int HEAD = 4;
        public static final int OPTIONS = 5;
        public static final int PATCH = 7;
        public static final int POST = 1;
        public static final int PUT = 2;
        public static final int TRACE = 6;
    }

    public enum Priority {
        LOW,
        NORMAL,
        HIGH,
        IMMEDIATE
    }

    protected abstract void deliverResponse(@Signature({"(TT;)V"}) T t) throws ;

    protected Map<String, String> getParams() throws AuthFailureError {
        return null;
    }

    protected String getParamsEncoding() throws  {
        return DEFAULT_PARAMS_ENCODING;
    }

    protected abstract Response<T> parseNetworkResponse(@Signature({"(", "Lcom/android/volley/NetworkResponse;", ")", "Lcom/android/volley/Response", "<TT;>;"}) NetworkResponse networkResponse) throws ;

    public Request(String $r1, ErrorListener $r2) throws  {
        this(-1, $r1, $r2);
    }

    public Request(int $i0, String $r1, ErrorListener $r2) throws  {
        this.mEventLog = MarkerLog.ENABLED ? new MarkerLog() : null;
        this.mShouldCache = true;
        this.mCanceled = false;
        this.mResponseDelivered = false;
        this.mRequestBirthTime = 0;
        this.mCacheEntry = null;
        this.mMethod = $i0;
        this.mUrl = $r1;
        this.mErrorListener = $r2;
        setRetryPolicy(new DefaultRetryPolicy());
        this.mDefaultTrafficStatsTag = TextUtils.isEmpty($r1) ? 0 : Uri.parse($r1).getHost().hashCode();
    }

    public int getMethod() throws  {
        return this.mMethod;
    }

    public void setTag(Object $r1) throws  {
        this.mTag = $r1;
    }

    public Object getTag() throws  {
        return this.mTag;
    }

    public int getTrafficStatsTag() throws  {
        return this.mDefaultTrafficStatsTag;
    }

    public void setRetryPolicy(RetryPolicy $r1) throws  {
        this.mRetryPolicy = $r1;
    }

    public void addMarker(String $r1) throws  {
        if (MarkerLog.ENABLED) {
            this.mEventLog.add($r1, Thread.currentThread().getId());
        } else if (this.mRequestBirthTime == 0) {
            this.mRequestBirthTime = SystemClock.elapsedRealtime();
        }
    }

    void finish(String $r1) throws  {
        if (this.mRequestQueue != null) {
            this.mRequestQueue.finish(this);
        }
        if (MarkerLog.ENABLED) {
            final long $l0 = Thread.currentThread().getId();
            if (Looper.myLooper() != Looper.getMainLooper()) {
                final String str = $r1;
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() throws  {
                        Request.this.mEventLog.add(str, $l0);
                        Request.this.mEventLog.finish(toString());
                    }
                });
                return;
            }
            this.mEventLog.add($r1, $l0);
            this.mEventLog.finish(toString());
            return;
        }
        if (SystemClock.elapsedRealtime() - this.mRequestBirthTime >= SLOW_REQUEST_THRESHOLD_MS) {
            VolleyLog.m15d("%d ms: %s", Long.valueOf(SystemClock.elapsedRealtime() - this.mRequestBirthTime), toString());
        }
    }

    public void setRequestQueue(RequestQueue $r1) throws  {
        this.mRequestQueue = $r1;
    }

    public final void setSequence(int $i0) throws  {
        this.mSequence = Integer.valueOf($i0);
    }

    public final int getSequence() throws  {
        if (this.mSequence != null) {
            return this.mSequence.intValue();
        }
        throw new IllegalStateException("getSequence called before setSequence");
    }

    public String getUrl() throws  {
        return this.mUrl;
    }

    public String getCacheKey() throws  {
        return getUrl();
    }

    public void setCacheEntry(Entry $r1) throws  {
        this.mCacheEntry = $r1;
    }

    public Entry getCacheEntry() throws  {
        return this.mCacheEntry;
    }

    public void cancel() throws  {
        this.mCanceled = true;
    }

    public boolean isCanceled() throws  {
        return this.mCanceled;
    }

    public Map<String, String> getHeaders() throws AuthFailureError {
        return Collections.emptyMap();
    }

    protected Map<String, String> getPostParams() throws AuthFailureError {
        return getParams();
    }

    protected String getPostParamsEncoding() throws  {
        return getParamsEncoding();
    }

    public String getPostBodyContentType() throws  {
        return getBodyContentType();
    }

    public byte[] getPostBody() throws AuthFailureError {
        Map $r1 = getPostParams();
        if ($r1 == null || $r1.size() <= 0) {
            return null;
        }
        return encodeParameters($r1, getPostParamsEncoding());
    }

    public String getBodyContentType() throws  {
        return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
    }

    public byte[] getBody() throws AuthFailureError {
        Map $r1 = getParams();
        if ($r1 == null || $r1.size() <= 0) {
            return null;
        }
        return encodeParameters($r1, getParamsEncoding());
    }

    private byte[] encodeParameters(@Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")[B"}) Map<String, String> $r1, @Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")[B"}) String $r2) throws  {
        StringBuilder $r3 = new StringBuilder();
        try {
            for (Map.Entry $r8 : $r1.entrySet()) {
                $r3.append(URLEncoder.encode((String) $r8.getKey(), $r2));
                $r3.append('=');
                $r3.append(URLEncoder.encode((String) $r8.getValue(), $r2));
                $r3.append('&');
            }
            return $r3.toString().getBytes($r2);
        } catch (UnsupportedEncodingException $r4) {
            throw new RuntimeException("Encoding not supported: " + $r2, $r4);
        }
    }

    public final void setShouldCache(boolean $z0) throws  {
        this.mShouldCache = $z0;
    }

    public final boolean shouldCache() throws  {
        return this.mShouldCache;
    }

    public Priority getPriority() throws  {
        return Priority.NORMAL;
    }

    public final int getTimeoutMs() throws  {
        return this.mRetryPolicy.getCurrentTimeout();
    }

    public RetryPolicy getRetryPolicy() throws  {
        return this.mRetryPolicy;
    }

    public void markDelivered() throws  {
        this.mResponseDelivered = true;
    }

    public boolean hasHadResponseDelivered() throws  {
        return this.mResponseDelivered;
    }

    protected VolleyError parseNetworkError(VolleyError $r1) throws  {
        return $r1;
    }

    public void deliverError(VolleyError $r1) throws  {
        if (this.mErrorListener != null) {
            this.mErrorListener.onErrorResponse($r1);
        }
    }

    public int compareTo(@Signature({"(", "Lcom/android/volley/Request", "<TT;>;)I"}) Request<T> $r1) throws  {
        Priority $r2 = getPriority();
        Priority $r3 = $r1.getPriority();
        return $r2 == $r3 ? this.mSequence.intValue() - $r1.mSequence.intValue() : $r3.ordinal() - $r2.ordinal();
    }

    public String toString() throws  {
        return (this.mCanceled ? "[X] " : "[ ] ") + getUrl() + " " + ("0x" + Integer.toHexString(getTrafficStatsTag())) + " " + getPriority() + " " + this.mSequence;
    }
}
