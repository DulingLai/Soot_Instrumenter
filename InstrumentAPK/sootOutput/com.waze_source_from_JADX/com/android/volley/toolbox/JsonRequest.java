package com.android.volley.toolbox;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyLog;
import dalvik.annotation.Signature;
import java.io.UnsupportedEncodingException;

public abstract class JsonRequest<T> extends Request<T> {
    private static final String PROTOCOL_CHARSET = "utf-8";
    private static final String PROTOCOL_CONTENT_TYPE = String.format("application/json; charset=%s", new Object[]{PROTOCOL_CHARSET});
    private final Listener<T> mListener;
    private final String mRequestBody;

    protected abstract Response<T> parseNetworkResponse(@Signature({"(", "Lcom/android/volley/NetworkResponse;", ")", "Lcom/android/volley/Response", "<TT;>;"}) NetworkResponse networkResponse) throws ;

    public JsonRequest(@Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/android/volley/Response$Listener", "<TT;>;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/android/volley/Response$Listener", "<TT;>;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) String $r2, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/android/volley/Response$Listener", "<TT;>;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) Listener<T> $r3, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/android/volley/Response$Listener", "<TT;>;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) ErrorListener $r4) throws  {
        this(-1, $r1, $r2, $r3, $r4);
    }

    public JsonRequest(@Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/android/volley/Response$Listener", "<TT;>;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) int $i0, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/android/volley/Response$Listener", "<TT;>;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) String $r1, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/android/volley/Response$Listener", "<TT;>;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) String $r2, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/android/volley/Response$Listener", "<TT;>;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) Listener<T> $r3, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/android/volley/Response$Listener", "<TT;>;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) ErrorListener $r4) throws  {
        super($i0, $r1, $r4);
        this.mListener = $r3;
        this.mRequestBody = $r2;
    }

    protected void deliverResponse(@Signature({"(TT;)V"}) T $r1) throws  {
        this.mListener.onResponse($r1);
    }

    public String getPostBodyContentType() throws  {
        return getBodyContentType();
    }

    public byte[] getPostBody() throws  {
        return getBody();
    }

    public String getBodyContentType() throws  {
        return PROTOCOL_CONTENT_TYPE;
    }

    public byte[] getBody() throws  {
        if (this.mRequestBody == null) {
            return null;
        }
        try {
            return this.mRequestBody.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException e) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", this.mRequestBody, PROTOCOL_CHARSET);
            return null;
        }
    }
}
