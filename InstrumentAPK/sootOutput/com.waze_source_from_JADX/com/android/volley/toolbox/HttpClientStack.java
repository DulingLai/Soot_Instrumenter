package com.android.volley.toolbox;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class HttpClientStack implements HttpStack {
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    protected final HttpClient mClient;

    public static final class HttpPatch extends HttpEntityEnclosingRequestBase {
        public static final String METHOD_NAME = "PATCH";

        public String getMethod() throws  {
            return METHOD_NAME;
        }

        public HttpPatch(URI $r1) throws  {
            setURI($r1);
        }

        public HttpPatch(String $r1) throws  {
            setURI(URI.create($r1));
        }
    }

    public HttpClientStack(HttpClient $r1) throws  {
        this.mClient = $r1;
    }

    private static void addHeaders(@Signature({"(", "Lorg/apache/http/client/methods/HttpUriRequest;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) HttpUriRequest $r0, @Signature({"(", "Lorg/apache/http/client/methods/HttpUriRequest;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) Map<String, String> $r1) throws  {
        for (String $r5 : $r1.keySet()) {
            $r0.setHeader($r5, (String) $r1.get($r5));
        }
    }

    private static List<NameValuePair> getPostParameterPairs(@Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)", "Ljava/util/List", "<", "Lorg/apache/http/NameValuePair;", ">;"}) Map<String, String> $r0) throws  {
        ArrayList $r1 = new ArrayList($r0.size());
        for (String $r6 : $r0.keySet()) {
            $r1.add(new BasicNameValuePair($r6, (String) $r0.get($r6)));
        }
        return $r1;
    }

    public HttpResponse performRequest(@Signature({"(", "Lcom/android/volley/Request", "<*>;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)", "Lorg/apache/http/HttpResponse;"}) Request<?> $r1, @Signature({"(", "Lcom/android/volley/Request", "<*>;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)", "Lorg/apache/http/HttpResponse;"}) Map<String, String> $r2) throws IOException, AuthFailureError {
        HttpUriRequest $r3 = createHttpRequest($r1, $r2);
        addHeaders($r3, $r2);
        addHeaders($r3, $r1.getHeaders());
        onPrepareRequest($r3);
        HttpParams $r4 = $r3.getParams();
        int $i0 = $r1.getTimeoutMs();
        HttpConnectionParams.setConnectionTimeout($r4, 5000);
        HttpConnectionParams.setSoTimeout($r4, $i0);
        return this.mClient.execute($r3);
    }

    static HttpUriRequest createHttpRequest(@Signature({"(", "Lcom/android/volley/Request", "<*>;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)", "Lorg/apache/http/client/methods/HttpUriRequest;"}) Request<?> $r0, @Signature({"(", "Lcom/android/volley/Request", "<*>;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)", "Lorg/apache/http/client/methods/HttpUriRequest;"}) Map<String, String> map) throws AuthFailureError {
        HttpPost $r7;
        HttpPost r10;
        HttpEntityEnclosingRequestBase httpPut;
        switch ($r0.getMethod()) {
            case -1:
                byte[] $r6 = $r0.getPostBody();
                if ($r6 == null) {
                    return new HttpGet($r0.getUrl());
                }
                $r7 = r10;
                r10 = new HttpPost($r0.getUrl());
                $r7.addHeader(HEADER_CONTENT_TYPE, $r0.getPostBodyContentType());
                $r7.setEntity(new ByteArrayEntity($r6));
                return $r7;
            case 0:
                return new HttpGet($r0.getUrl());
            case 1:
                $r7 = r10;
                r10 = new HttpPost($r0.getUrl());
                $r7.addHeader(HEADER_CONTENT_TYPE, $r0.getBodyContentType());
                setEntityIfNonEmptyBody($r7, $r0);
                return $r7;
            case 2:
                HttpEntityEnclosingRequestBase $r4 = httpPut;
                httpPut = new HttpPut($r0.getUrl());
                httpPut = $r4;
                httpPut.addHeader(HEADER_CONTENT_TYPE, $r0.getBodyContentType());
                setEntityIfNonEmptyBody($r4, $r0);
                return $r4;
            case 3:
                return new HttpDelete($r0.getUrl());
            case 4:
                return new HttpHead($r0.getUrl());
            case 5:
                return new HttpOptions($r0.getUrl());
            case 6:
                return new HttpTrace($r0.getUrl());
            case 7:
                HttpEntityEnclosingRequestBase $r3 = httpPut;
                httpPut = new HttpPatch($r0.getUrl());
                httpPut = $r3;
                httpPut.addHeader(HEADER_CONTENT_TYPE, $r0.getBodyContentType());
                setEntityIfNonEmptyBody($r3, $r0);
                return $r3;
            default:
                throw new IllegalStateException("Unknown request method.");
        }
    }

    private static void setEntityIfNonEmptyBody(@Signature({"(", "Lorg/apache/http/client/methods/HttpEntityEnclosingRequestBase;", "Lcom/android/volley/Request", "<*>;)V"}) HttpEntityEnclosingRequestBase $r0, @Signature({"(", "Lorg/apache/http/client/methods/HttpEntityEnclosingRequestBase;", "Lcom/android/volley/Request", "<*>;)V"}) Request<?> $r1) throws AuthFailureError {
        byte[] $r3 = $r1.getBody();
        if ($r3 != null) {
            $r0.setEntity(new ByteArrayEntity($r3));
        }
    }

    protected void onPrepareRequest(HttpUriRequest request) throws IOException {
    }
}
