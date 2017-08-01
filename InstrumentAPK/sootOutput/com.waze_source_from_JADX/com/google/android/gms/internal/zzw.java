package com.google.android.gms.internal;

import com.android.volley.toolbox.HttpClientStack.HttpPatch;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import org.apache.http.HttpResponse;
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
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/* compiled from: dalvik_source_com.waze.apk */
public class zzw implements zzy {
    protected final HttpClient zzcc;

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zza extends HttpEntityEnclosingRequestBase {
        public zza(String $r1) throws  {
            setURI(URI.create($r1));
        }

        public String getMethod() throws  {
            return HttpPatch.METHOD_NAME;
        }
    }

    public zzw(HttpClient $r1) throws  {
        this.zzcc = $r1;
    }

    private static void zza(@Signature({"(", "Lorg/apache/http/client/methods/HttpEntityEnclosingRequestBase;", "Lcom/google/android/gms/internal/zzk", "<*>;)V"}) HttpEntityEnclosingRequestBase $r0, @Signature({"(", "Lorg/apache/http/client/methods/HttpEntityEnclosingRequestBase;", "Lcom/google/android/gms/internal/zzk", "<*>;)V"}) zzk<?> $r1) throws zza {
        byte[] $r3 = $r1.zzp();
        if ($r3 != null) {
            $r0.setEntity(new ByteArrayEntity($r3));
        }
    }

    private static void zza(@Signature({"(", "Lorg/apache/http/client/methods/HttpUriRequest;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) HttpUriRequest $r0, @Signature({"(", "Lorg/apache/http/client/methods/HttpUriRequest;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) Map<String, String> $r1) throws  {
        for (String $r5 : $r1.keySet()) {
            $r0.setHeader($r5, (String) $r1.get($r5));
        }
    }

    static HttpUriRequest zzb(@Signature({"(", "Lcom/google/android/gms/internal/zzk", "<*>;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)", "Lorg/apache/http/client/methods/HttpUriRequest;"}) zzk<?> $r0, @Signature({"(", "Lcom/google/android/gms/internal/zzk", "<*>;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)", "Lorg/apache/http/client/methods/HttpUriRequest;"}) Map<String, String> map) throws zza {
        HttpPost $r4;
        HttpPost r8;
        switch ($r0.getMethod()) {
            case -1:
                byte[] $r3 = $r0.zzl();
                if ($r3 == null) {
                    return new HttpGet($r0.getUrl());
                }
                $r4 = r8;
                r8 = new HttpPost($r0.getUrl());
                $r4.addHeader("Content-Type", $r0.zzk());
                $r4.setEntity(new ByteArrayEntity($r3));
                return $r4;
            case 0:
                return new HttpGet($r0.getUrl());
            case 1:
                $r4 = r8;
                r8 = new HttpPost($r0.getUrl());
                $r4.addHeader("Content-Type", $r0.zzo());
                zza((HttpEntityEnclosingRequestBase) $r4, (zzk) $r0);
                return $r4;
            case 2:
                HttpPut $r42 = r0;
                HttpPut httpPut = new HttpPut($r0.getUrl());
                httpPut = $r42;
                httpPut.addHeader("Content-Type", $r0.zzo());
                zza((HttpEntityEnclosingRequestBase) $r42, (zzk) $r0);
                return $r42;
            case 3:
                return new HttpDelete($r0.getUrl());
            case 4:
                return new HttpHead($r0.getUrl());
            case 5:
                return new HttpOptions($r0.getUrl());
            case 6:
                return new HttpTrace($r0.getUrl());
            case 7:
                zza $r43 = r0;
                zza com_google_android_gms_internal_zzw_zza = new zza($r0.getUrl());
                com_google_android_gms_internal_zzw_zza = $r43;
                com_google_android_gms_internal_zzw_zza.addHeader("Content-Type", $r0.zzo());
                zza((HttpEntityEnclosingRequestBase) $r43, (zzk) $r0);
                return $r43;
            default:
                throw new IllegalStateException("Unknown request method.");
        }
    }

    public HttpResponse zza(@Signature({"(", "Lcom/google/android/gms/internal/zzk", "<*>;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)", "Lorg/apache/http/HttpResponse;"}) zzk<?> $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzk", "<*>;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)", "Lorg/apache/http/HttpResponse;"}) Map<String, String> $r2) throws IOException, zza {
        HttpUriRequest $r3 = zzb($r1, $r2);
        zza($r3, (Map) $r2);
        zza($r3, $r1.getHeaders());
        zza($r3);
        HttpParams $r4 = $r3.getParams();
        int $i0 = $r1.zzs();
        HttpConnectionParams.setConnectionTimeout($r4, 5000);
        HttpConnectionParams.setSoTimeout($r4, $i0);
        return this.zzcc.execute($r3);
    }

    protected void zza(HttpUriRequest httpUriRequest) throws IOException {
    }
}
