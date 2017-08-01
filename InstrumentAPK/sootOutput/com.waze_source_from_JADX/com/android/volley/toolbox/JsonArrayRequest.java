package com.android.volley.toolbox;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import dalvik.annotation.Signature;
import org.json.JSONArray;

public class JsonArrayRequest extends JsonRequest<JSONArray> {
    public JsonArrayRequest(@Signature({"(", "Ljava/lang/String;", "Lcom/android/volley/Response$Listener", "<", "Lorg/json/JSONArray;", ">;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Lcom/android/volley/Response$Listener", "<", "Lorg/json/JSONArray;", ">;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) Listener<JSONArray> $r2, @Signature({"(", "Ljava/lang/String;", "Lcom/android/volley/Response$Listener", "<", "Lorg/json/JSONArray;", ">;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) ErrorListener $r3) throws  {
        super(0, $r1, null, $r2, $r3);
    }

    protected Response<JSONArray> parseNetworkResponse(@Signature({"(", "Lcom/android/volley/NetworkResponse;", ")", "Lcom/android/volley/Response", "<", "Lorg/json/JSONArray;", ">;"}) NetworkResponse $r1) throws  {
        try {
            return Response.success(new JSONArray(new String($r1.data, HttpHeaderParser.parseCharset($r1.headers))), HttpHeaderParser.parseCacheHeaders($r1));
        } catch (Throwable $r2) {
            return Response.error(new ParseError($r2));
        } catch (Throwable $r3) {
            return Response.error(new ParseError($r3));
        }
    }
}
