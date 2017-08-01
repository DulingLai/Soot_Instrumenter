package com.android.volley;

import dalvik.annotation.Signature;

public interface ResponseDelivery {
    void postError(@Signature({"(", "Lcom/android/volley/Request", "<*>;", "Lcom/android/volley/VolleyError;", ")V"}) Request<?> request, @Signature({"(", "Lcom/android/volley/Request", "<*>;", "Lcom/android/volley/VolleyError;", ")V"}) VolleyError volleyError) throws ;

    void postResponse(@Signature({"(", "Lcom/android/volley/Request", "<*>;", "Lcom/android/volley/Response", "<*>;)V"}) Request<?> request, @Signature({"(", "Lcom/android/volley/Request", "<*>;", "Lcom/android/volley/Response", "<*>;)V"}) Response<?> response) throws ;

    void postResponse(@Signature({"(", "Lcom/android/volley/Request", "<*>;", "Lcom/android/volley/Response", "<*>;", "Ljava/lang/Runnable;", ")V"}) Request<?> request, @Signature({"(", "Lcom/android/volley/Request", "<*>;", "Lcom/android/volley/Response", "<*>;", "Ljava/lang/Runnable;", ")V"}) Response<?> response, @Signature({"(", "Lcom/android/volley/Request", "<*>;", "Lcom/android/volley/Response", "<*>;", "Ljava/lang/Runnable;", ")V"}) Runnable runnable) throws ;
}
