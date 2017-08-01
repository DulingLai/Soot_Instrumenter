package com.android.volley;

import com.android.volley.Cache.Entry;
import dalvik.annotation.Signature;

public class Response<T> {
    public final Entry cacheEntry;
    public final VolleyError error;
    public boolean intermediate;
    public final T result;

    public interface ErrorListener {
        void onErrorResponse(VolleyError volleyError) throws ;
    }

    public interface Listener<T> {
        void onResponse(@Signature({"(TT;)V"}) T t) throws ;
    }

    public static <T> Response<T> success(@Signature({"<T:", "Ljava/lang/Object;", ">(TT;", "Lcom/android/volley/Cache$Entry;", ")", "Lcom/android/volley/Response", "<TT;>;"}) T $r0, @Signature({"<T:", "Ljava/lang/Object;", ">(TT;", "Lcom/android/volley/Cache$Entry;", ")", "Lcom/android/volley/Response", "<TT;>;"}) Entry $r1) throws  {
        return new Response($r0, $r1);
    }

    public static <T> Response<T> error(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/android/volley/VolleyError;", ")", "Lcom/android/volley/Response", "<TT;>;"}) VolleyError $r0) throws  {
        return new Response($r0);
    }

    public boolean isSuccess() throws  {
        return this.error == null;
    }

    private Response(@Signature({"(TT;", "Lcom/android/volley/Cache$Entry;", ")V"}) T $r1, @Signature({"(TT;", "Lcom/android/volley/Cache$Entry;", ")V"}) Entry $r2) throws  {
        this.intermediate = false;
        this.result = $r1;
        this.cacheEntry = $r2;
        this.error = null;
    }

    private Response(VolleyError $r1) throws  {
        this.intermediate = false;
        this.result = null;
        this.cacheEntry = null;
        this.error = $r1;
    }
}
