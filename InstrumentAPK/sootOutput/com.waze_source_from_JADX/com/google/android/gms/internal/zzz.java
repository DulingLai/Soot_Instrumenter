package com.google.android.gms.internal;

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

/* compiled from: dalvik_source_com.waze.apk */
public class zzz implements zzy {
    private final zza zzcd;
    private final SSLSocketFactory zzce;

    /* compiled from: dalvik_source_com.waze.apk */
    public interface zza {
        String zzh(String str) throws ;
    }

    public zzz() throws  {
        this(null);
    }

    public zzz(zza $r1) throws  {
        this($r1, null);
    }

    public zzz(zza $r1, SSLSocketFactory $r2) throws  {
        this.zzcd = $r1;
        this.zzce = $r2;
    }

    private HttpURLConnection zza(@Signature({"(", "Ljava/net/URL;", "Lcom/google/android/gms/internal/zzk", "<*>;)", "Ljava/net/HttpURLConnection;"}) URL $r1, @Signature({"(", "Ljava/net/URL;", "Lcom/google/android/gms/internal/zzk", "<*>;)", "Ljava/net/HttpURLConnection;"}) zzk<?> $r2) throws IOException {
        HttpURLConnection $r3 = zza($r1);
        int $i0 = $r2.zzs();
        $r3.setConnectTimeout($i0);
        $r3.setReadTimeout($i0);
        $r3.setUseCaches(false);
        $r3.setDoInput(true);
        if (!"https".equals($r1.getProtocol()) || this.zzce == null) {
            return $r3;
        }
        ((HttpsURLConnection) $r3).setSSLSocketFactory(this.zzce);
        return $r3;
    }

    private static HttpEntity zza(HttpURLConnection $r0) throws  {
        InputStream $r2;
        BasicHttpEntity $r1 = new BasicHttpEntity();
        try {
            $r2 = $r0.getInputStream();
        } catch (IOException e) {
            $r2 = $r0.getErrorStream();
        }
        $r1.setContent($r2);
        $r1.setContentLength((long) $r0.getContentLength());
        $r1.setContentEncoding($r0.getContentEncoding());
        $r1.setContentType($r0.getContentType());
        return $r1;
    }

    static void zza(@Signature({"(", "Ljava/net/HttpURLConnection;", "Lcom/google/android/gms/internal/zzk", "<*>;)V"}) HttpURLConnection $r0, @Signature({"(", "Ljava/net/HttpURLConnection;", "Lcom/google/android/gms/internal/zzk", "<*>;)V"}) zzk<?> $r1) throws IOException, zza {
        switch ($r1.getMethod()) {
            case -1:
                byte[] $r3 = $r1.zzl();
                if ($r3 != null) {
                    $r0.setDoOutput(true);
                    $r0.setRequestMethod("POST");
                    $r0.addRequestProperty("Content-Type", $r1.zzk());
                    DataOutputStream $r5 = new DataOutputStream($r0.getOutputStream());
                    $r5.write($r3);
                    $r5.close();
                    return;
                }
                return;
            case 0:
                $r0.setRequestMethod("GET");
                return;
            case 1:
                $r0.setRequestMethod("POST");
                zzb($r0, $r1);
                return;
            case 2:
                $r0.setRequestMethod("PUT");
                zzb($r0, $r1);
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
                $r0.setRequestMethod(HttpPatch.METHOD_NAME);
                zzb($r0, $r1);
                return;
            default:
                throw new IllegalStateException("Unknown method type.");
        }
    }

    private static void zzb(@Signature({"(", "Ljava/net/HttpURLConnection;", "Lcom/google/android/gms/internal/zzk", "<*>;)V"}) HttpURLConnection $r0, @Signature({"(", "Ljava/net/HttpURLConnection;", "Lcom/google/android/gms/internal/zzk", "<*>;)V"}) zzk<?> $r1) throws IOException, zza {
        byte[] $r2 = $r1.zzp();
        if ($r2 != null) {
            $r0.setDoOutput(true);
            $r0.addRequestProperty("Content-Type", $r1.zzo());
            DataOutputStream $r4 = new DataOutputStream($r0.getOutputStream());
            $r4.write($r2);
            $r4.close();
        }
    }

    protected HttpURLConnection zza(URL $r1) throws IOException {
        return (HttpURLConnection) $r1.openConnection();
    }

    public HttpResponse zza(@Signature({"(", "Lcom/google/android/gms/internal/zzk", "<*>;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)", "Lorg/apache/http/HttpResponse;"}) zzk<?> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzk", "<*>;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)", "Lorg/apache/http/HttpResponse;"}) Map<String, String> $r2) throws IOException, zza {
        String $r8;
        String $r3 = $r1.getUrl();
        HashMap $r4 = new HashMap();
        $r4.putAll($r1.getHeaders());
        $r4.putAll($r2);
        if (this.zzcd != null) {
            String $r7 = this.zzcd.zzh($r3);
            $r8 = $r7;
            if ($r7 == null) {
                $r7 = "URL blocked by rewriter: ";
                $r3 = String.valueOf($r3);
                throw new IOException($r3.length() != 0 ? $r7.concat($r3) : new String("URL blocked by rewriter: "));
            }
        }
        $r8 = $r3;
        HttpURLConnection $r11 = zza(new URL($r8), (zzk) $r1);
        for (String $r32 : $r4.keySet()) {
            $r11.addRequestProperty($r32, (String) $r4.get($r32));
        }
        zza($r11, (zzk) $r1);
        ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
        if ($r11.getResponseCode() == -1) {
            throw new IOException("Could not retrieve response code from HttpUrlConnection.");
        }
        BasicHttpResponse basicHttpResponse = new BasicHttpResponse(new BasicStatusLine(protocolVersion, $r11.getResponseCode(), $r11.getResponseMessage()));
        basicHttpResponse.setEntity(zza($r11));
        for (Entry $r19 : $r11.getHeaderFields().entrySet()) {
            if ($r19.getKey() != null) {
                basicHttpResponse.addHeader(new BasicHeader((String) $r19.getKey(), (String) ((List) $r19.getValue()).get(0)));
            }
        }
        return basicHttpResponse;
    }
}
