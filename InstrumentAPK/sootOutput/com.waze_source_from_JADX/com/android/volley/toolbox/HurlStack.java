package com.android.volley.toolbox;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.HttpClientStack.HttpPatch;
import com.waze.analytics.AnalyticsEvents;
import dalvik.annotation.Signature;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

public class HurlStack implements HttpStack {
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private final SSLSocketFactory mSslSocketFactory;
    private final UrlRewriter mUrlRewriter;

    public interface UrlRewriter {
        String rewriteUrl(String str) throws ;
    }

    public HurlStack() throws  {
        this(null);
    }

    public HurlStack(UrlRewriter $r1) throws  {
        this($r1, null);
    }

    public HurlStack(UrlRewriter $r1, SSLSocketFactory $r2) throws  {
        this.mUrlRewriter = $r1;
        this.mSslSocketFactory = $r2;
    }

    public HttpResponse performRequest(@Signature({"(", "Lcom/android/volley/Request", "<*>;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)", "Lorg/apache/http/HttpResponse;"}) Request<?> $r1, @Signature({"(", "Lcom/android/volley/Request", "<*>;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)", "Lorg/apache/http/HttpResponse;"}) Map<String, String> $r2) throws IOException, AuthFailureError {
        String $r9 = $r1.getUrl();
        String $r10 = $r9;
        HashMap $r4 = new HashMap();
        $r4.putAll($r1.getHeaders());
        $r4.putAll($r2);
        if (this.mUrlRewriter != null) {
            $r10 = this.mUrlRewriter.rewriteUrl($r9);
            if ($r10 == null) {
                throw new IOException("URL blocked by rewriter: " + $r9);
            }
        }
        HttpURLConnection $r15 = openConnection(new URL($r10), $r1);
        for (String $r92 : $r4.keySet()) {
            $r15.addRequestProperty($r92, (String) $r4.get($r92));
        }
        setConnectionParametersForRequest($r15, $r1);
        ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
        if ($r15.getResponseCode() == -1) {
            throw new IOException("Could not retrieve response code from HttpUrlConnection.");
        }
        BasicHttpResponse basicHttpResponse = new BasicHttpResponse(new BasicStatusLine(protocolVersion, $r15.getResponseCode(), $r15.getResponseMessage()));
        basicHttpResponse.setEntity(entityFromConnection($r15));
        for (Entry $r20 : $r15.getHeaderFields().entrySet()) {
            if ($r20.getKey() != null) {
                basicHttpResponse.addHeader(new BasicHeader((String) $r20.getKey(), (String) ((List) $r20.getValue()).get(0)));
            }
        }
        return basicHttpResponse;
    }

    private static HttpEntity entityFromConnection(HttpURLConnection $r0) throws  {
        InputStream $r3;
        BasicHttpEntity $r1 = new BasicHttpEntity();
        try {
            $r3 = $r0.getInputStream();
        } catch (IOException e) {
            $r3 = $r0.getErrorStream();
        }
        $r1.setContent($r3);
        $r1.setContentLength((long) $r0.getContentLength());
        $r1.setContentEncoding($r0.getContentEncoding());
        $r1.setContentType($r0.getContentType());
        return $r1;
    }

    protected HttpURLConnection createConnection(URL $r1) throws IOException {
        return (HttpURLConnection) $r1.openConnection();
    }

    private HttpURLConnection openConnection(@Signature({"(", "Ljava/net/URL;", "Lcom/android/volley/Request", "<*>;)", "Ljava/net/HttpURLConnection;"}) URL $r1, @Signature({"(", "Ljava/net/URL;", "Lcom/android/volley/Request", "<*>;)", "Ljava/net/HttpURLConnection;"}) Request<?> $r2) throws IOException {
        HttpURLConnection $r3 = createConnection($r1);
        int $i0 = $r2.getTimeoutMs();
        $r3.setConnectTimeout($i0);
        $r3.setReadTimeout($i0);
        $r3.setUseCaches(false);
        $r3.setDoInput(true);
        if (!"https".equals($r1.getProtocol()) || this.mSslSocketFactory == null) {
            return $r3;
        }
        ((HttpsURLConnection) $r3).setSSLSocketFactory(this.mSslSocketFactory);
        return $r3;
    }

    static void setConnectionParametersForRequest(@Signature({"(", "Ljava/net/HttpURLConnection;", "Lcom/android/volley/Request", "<*>;)V"}) HttpURLConnection $r0, @Signature({"(", "Ljava/net/HttpURLConnection;", "Lcom/android/volley/Request", "<*>;)V"}) Request<?> $r1) throws IOException, AuthFailureError {
        switch ($r1.getMethod()) {
            case -1:
                byte[] $r4 = $r1.getPostBody();
                if ($r4 != null) {
                    $r0.setDoOutput(true);
                    $r0.setRequestMethod("POST");
                    $r0.addRequestProperty(HEADER_CONTENT_TYPE, $r1.getPostBodyContentType());
                    DataOutputStream $r2 = new DataOutputStream($r0.getOutputStream());
                    $r2.write($r4);
                    $r2.close();
                    return;
                }
                return;
            case 0:
                $r0.setRequestMethod("GET");
                return;
            case 1:
                $r0.setRequestMethod("POST");
                addBodyIfExists($r0, $r1);
                return;
            case 2:
                $r0.setRequestMethod("PUT");
                addBodyIfExists($r0, $r1);
                return;
            case 3:
                $r0.setRequestMethod("DELETE");
                return;
            case 4:
                $r0.setRequestMethod("HEAD");
                return;
            case 5:
                $r0.setRequestMethod(AnalyticsEvents.ANALYTICS_EVENT_VALUE_OPTIONS);
                return;
            case 6:
                $r0.setRequestMethod("TRACE");
                return;
            case 7:
                addBodyIfExists($r0, $r1);
                $r0.setRequestMethod(HttpPatch.METHOD_NAME);
                return;
            default:
                throw new IllegalStateException("Unknown method type.");
        }
    }

    private static void addBodyIfExists(@Signature({"(", "Ljava/net/HttpURLConnection;", "Lcom/android/volley/Request", "<*>;)V"}) HttpURLConnection $r0, @Signature({"(", "Ljava/net/HttpURLConnection;", "Lcom/android/volley/Request", "<*>;)V"}) Request<?> $r1) throws IOException, AuthFailureError {
        byte[] $r3 = $r1.getBody();
        if ($r3 != null) {
            $r0.setDoOutput(true);
            $r0.addRequestProperty(HEADER_CONTENT_TYPE, $r1.getBodyContentType());
            DataOutputStream $r2 = new DataOutputStream($r0.getOutputStream());
            $r2.write($r3);
            $r2.close();
        }
    }
}
