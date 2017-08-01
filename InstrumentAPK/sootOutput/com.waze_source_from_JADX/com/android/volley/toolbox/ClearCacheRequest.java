package com.android.volley.toolbox;

import android.os.Handler;
import android.os.Looper;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Request.Priority;
import com.android.volley.Response;
import dalvik.annotation.Signature;

public class ClearCacheRequest extends Request<Object> {
    private final Cache mCache;
    private final Runnable mCallback;

    protected Response<Object> parseNetworkResponse(@Signature({"(", "Lcom/android/volley/NetworkResponse;", ")", "Lcom/android/volley/Response", "<", "Ljava/lang/Object;", ">;"}) NetworkResponse response) throws  {
        return null;
    }

    public ClearCacheRequest(Cache $r1, Runnable $r2) throws  {
        super(0, null, null);
        this.mCache = $r1;
        this.mCallback = $r2;
    }

    public boolean isCanceled() throws  {
        this.mCache.clear();
        if (this.mCallback != null) {
            new Handler(Looper.getMainLooper()).postAtFrontOfQueue(this.mCallback);
        }
        return true;
    }

    public Priority getPriority() throws  {
        return Priority.IMMEDIATE;
    }

    protected void deliverResponse(Object response) throws  {
    }
}
