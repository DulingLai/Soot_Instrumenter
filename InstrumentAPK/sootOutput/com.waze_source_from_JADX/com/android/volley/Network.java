package com.android.volley;

import dalvik.annotation.Signature;

public interface Network {
    NetworkResponse performRequest(@Signature({"(", "Lcom/android/volley/Request", "<*>;)", "Lcom/android/volley/NetworkResponse;"}) Request<?> request) throws VolleyError;
}
