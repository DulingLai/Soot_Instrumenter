package com.android.volley.toolbox;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.util.Map;
import org.apache.http.HttpResponse;

public interface HttpStack {
    HttpResponse performRequest(@Signature({"(", "Lcom/android/volley/Request", "<*>;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)", "Lorg/apache/http/HttpResponse;"}) Request<?> request, @Signature({"(", "Lcom/android/volley/Request", "<*>;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)", "Lorg/apache/http/HttpResponse;"}) Map<String, String> map) throws IOException, AuthFailureError;
}
