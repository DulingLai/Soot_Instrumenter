package com.android.volley.toolbox;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import dalvik.annotation.Signature;
import java.io.UnsupportedEncodingException;

public class StringRequest extends Request<String> {
    private final Listener<String> mListener;

    public StringRequest(@Signature({"(I", "Ljava/lang/String;", "Lcom/android/volley/Response$Listener", "<", "Ljava/lang/String;", ">;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) int $i0, @Signature({"(I", "Ljava/lang/String;", "Lcom/android/volley/Response$Listener", "<", "Ljava/lang/String;", ">;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) String $r1, @Signature({"(I", "Ljava/lang/String;", "Lcom/android/volley/Response$Listener", "<", "Ljava/lang/String;", ">;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) Listener<String> $r2, @Signature({"(I", "Ljava/lang/String;", "Lcom/android/volley/Response$Listener", "<", "Ljava/lang/String;", ">;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) ErrorListener $r3) throws  {
        super($i0, $r1, $r3);
        this.mListener = $r2;
    }

    public StringRequest(@Signature({"(", "Ljava/lang/String;", "Lcom/android/volley/Response$Listener", "<", "Ljava/lang/String;", ">;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Lcom/android/volley/Response$Listener", "<", "Ljava/lang/String;", ">;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) Listener<String> $r2, @Signature({"(", "Ljava/lang/String;", "Lcom/android/volley/Response$Listener", "<", "Ljava/lang/String;", ">;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) ErrorListener $r3) throws  {
        this(0, $r1, $r2, $r3);
    }

    protected void deliverResponse(String $r1) throws  {
        this.mListener.onResponse($r1);
    }

    protected Response<String> parseNetworkResponse(@Signature({"(", "Lcom/android/volley/NetworkResponse;", ")", "Lcom/android/volley/Response", "<", "Ljava/lang/String;", ">;"}) NetworkResponse $r1) throws  {
        String $r3;
        try {
            $r3 = new String($r1.data, HttpHeaderParser.parseCharset($r1.headers));
        } catch (UnsupportedEncodingException e) {
            $r3 = new String($r1.data);
        }
        return Response.success($r3, HttpHeaderParser.parseCacheHeaders($r1));
    }
}
