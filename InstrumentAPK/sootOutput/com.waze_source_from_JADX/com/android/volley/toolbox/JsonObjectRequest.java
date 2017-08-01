package com.android.volley.toolbox;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import dalvik.annotation.Signature;
import org.json.JSONObject;

public class JsonObjectRequest extends JsonRequest<JSONObject> {
    public JsonObjectRequest(@Signature({"(I", "Ljava/lang/String;", "Lorg/json/JSONObject;", "Lcom/android/volley/Response$Listener", "<", "Lorg/json/JSONObject;", ">;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) int $i0, @Signature({"(I", "Ljava/lang/String;", "Lorg/json/JSONObject;", "Lcom/android/volley/Response$Listener", "<", "Lorg/json/JSONObject;", ">;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) String $r1, @Signature({"(I", "Ljava/lang/String;", "Lorg/json/JSONObject;", "Lcom/android/volley/Response$Listener", "<", "Lorg/json/JSONObject;", ">;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) JSONObject $r2, @Signature({"(I", "Ljava/lang/String;", "Lorg/json/JSONObject;", "Lcom/android/volley/Response$Listener", "<", "Lorg/json/JSONObject;", ">;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) Listener<JSONObject> $r3, @Signature({"(I", "Ljava/lang/String;", "Lorg/json/JSONObject;", "Lcom/android/volley/Response$Listener", "<", "Lorg/json/JSONObject;", ">;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) ErrorListener $r4) throws  {
        super($i0, $r1, $r2 == null ? null : $r2.toString(), $r3, $r4);
    }

    public JsonObjectRequest(@Signature({"(", "Ljava/lang/String;", "Lorg/json/JSONObject;", "Lcom/android/volley/Response$Listener", "<", "Lorg/json/JSONObject;", ">;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Lorg/json/JSONObject;", "Lcom/android/volley/Response$Listener", "<", "Lorg/json/JSONObject;", ">;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) JSONObject $r2, @Signature({"(", "Ljava/lang/String;", "Lorg/json/JSONObject;", "Lcom/android/volley/Response$Listener", "<", "Lorg/json/JSONObject;", ">;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) Listener<JSONObject> $r3, @Signature({"(", "Ljava/lang/String;", "Lorg/json/JSONObject;", "Lcom/android/volley/Response$Listener", "<", "Lorg/json/JSONObject;", ">;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) ErrorListener $r4) throws  {
        this($r2 == null ? (byte) 0 : (byte) 1, $r1, $r2, $r3, $r4);
    }

    protected Response<JSONObject> parseNetworkResponse(@Signature({"(", "Lcom/android/volley/NetworkResponse;", ")", "Lcom/android/volley/Response", "<", "Lorg/json/JSONObject;", ">;"}) NetworkResponse $r1) throws  {
        try {
            return Response.success(new JSONObject(new String($r1.data, HttpHeaderParser.parseCharset($r1.headers))), HttpHeaderParser.parseCacheHeaders($r1));
        } catch (Throwable $r2) {
            return Response.error(new ParseError($r2));
        } catch (Throwable $r3) {
            return Response.error(new ParseError($r3));
        }
    }
}
