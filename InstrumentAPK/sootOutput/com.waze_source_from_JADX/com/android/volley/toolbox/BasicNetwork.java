package com.android.volley.toolbox;

import android.os.SystemClock;
import com.android.volley.AuthFailureError;
import com.android.volley.Cache.Entry;
import com.android.volley.Network;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.cookie.DateUtils;

public class BasicNetwork implements Network {
    protected static final boolean DEBUG = VolleyLog.DEBUG;
    private static int DEFAULT_POOL_SIZE = 4096;
    private static int SLOW_REQUEST_THRESHOLD_MS = 3000;
    protected final HttpStack mHttpStack;
    protected final ByteArrayPool mPool;

    public BasicNetwork(HttpStack $r1) throws  {
        this($r1, new ByteArrayPool(DEFAULT_POOL_SIZE));
    }

    public BasicNetwork(HttpStack $r1, ByteArrayPool $r2) throws  {
        this.mHttpStack = $r1;
        this.mPool = $r2;
    }

    public NetworkResponse performRequest(@Signature({"(", "Lcom/android/volley/Request", "<*>;)", "Lcom/android/volley/NetworkResponse;"}) Request<?> $r1) throws VolleyError {
        NetworkResponse $r12;
        NetworkResponse networkResponse;
        long $l1 = SystemClock.elapsedRealtime();
        while (true) {
            HashMap r29;
            HttpResponse $r3 = null;
            byte[] $r4 = null;
            Map $r5 = $r2;
            HashMap $r2 = new HashMap();
            $r2 = r29;
            int $i2;
            try {
                r29 = new HashMap();
                addCacheHeaders($r2, $r1.getCacheEntry());
                HttpResponse $r8 = this.mHttpStack.performRequest($r1, $r2);
                $r3 = $r8;
                StatusLine $r9 = $r8.getStatusLine();
                $i2 = $r9.getStatusCode();
                Map $r11 = convertHeaders($r8.getAllHeaders());
                $r5 = $r11;
                if ($i2 == 304) {
                    $r12 = networkResponse;
                    networkResponse = new NetworkResponse(304, $r1.getCacheEntry().data, $r11, true);
                    return $r12;
                }
                if ($r8.getEntity() != null) {
                    $r4 = entityToBytes($r8.getEntity());
                } else {
                    $r4 = new byte[null];
                }
                logSlowRequests(SystemClock.elapsedRealtime() - $l1, $r1, $r4, $r9);
                if ($i2 >= 200 && $i2 <= 299) {
                    return new NetworkResponse($i2, $r4, $r11, false);
                }
                throw new IOException();
            } catch (SocketTimeoutException e) {
                attemptRetryOnException("socket", $r1, new TimeoutError());
            } catch (ConnectTimeoutException e2) {
                attemptRetryOnException("connection", $r1, new TimeoutError());
            } catch (Throwable $r19) {
                throw new RuntimeException("Bad URL " + $r1.getUrl(), $r19);
            } catch (Throwable $r15) {
                if ($r3 != null) {
                    $i2 = $r3.getStatusLine().getStatusCode();
                    VolleyLog.m16e("Unexpected response code %d for %s", Integer.valueOf($i2), $r1.getUrl());
                    if ($r4 != null) {
                        $r12 = networkResponse;
                        networkResponse = new NetworkResponse($i2, $r4, $r5, false);
                        if ($i2 == 401 || $i2 == 403) {
                            attemptRetryOnException("auth", $r1, new AuthFailureError($r12));
                        } else {
                            throw new ServerError($r12);
                        }
                    }
                    throw new NetworkError(null);
                }
                throw new NoConnectionError($r15);
            }
        }
    }

    private void logSlowRequests(@Signature({"(J", "Lcom/android/volley/Request", "<*>;[B", "Lorg/apache/http/StatusLine;", ")V"}) long $l0, @Signature({"(J", "Lcom/android/volley/Request", "<*>;[B", "Lorg/apache/http/StatusLine;", ")V"}) Request<?> $r1, @Signature({"(J", "Lcom/android/volley/Request", "<*>;[B", "Lorg/apache/http/StatusLine;", ")V"}) byte[] $r2, @Signature({"(J", "Lcom/android/volley/Request", "<*>;[B", "Lorg/apache/http/StatusLine;", ")V"}) StatusLine $r3) throws  {
        if (DEBUG || $l0 > ((long) SLOW_REQUEST_THRESHOLD_MS)) {
            Object[] $r4 = new Object[5];
            $r4[0] = $r1;
            $r4[1] = Long.valueOf($l0);
            $r4[2] = $r2 != null ? Integer.valueOf($r2.length) : "null";
            $r4[3] = Integer.valueOf($r3.getStatusCode());
            $r4[4] = Integer.valueOf($r1.getRetryPolicy().getCurrentRetryCount());
            VolleyLog.m15d("HTTP response for request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]", $r4);
        }
    }

    private static void attemptRetryOnException(@Signature({"(", "Ljava/lang/String;", "Lcom/android/volley/Request", "<*>;", "Lcom/android/volley/VolleyError;", ")V"}) String $r0, @Signature({"(", "Ljava/lang/String;", "Lcom/android/volley/Request", "<*>;", "Lcom/android/volley/VolleyError;", ")V"}) Request<?> $r1, @Signature({"(", "Ljava/lang/String;", "Lcom/android/volley/Request", "<*>;", "Lcom/android/volley/VolleyError;", ")V"}) VolleyError $r2) throws VolleyError {
        RetryPolicy $r4 = $r1.getRetryPolicy();
        int $i0 = $r1.getTimeoutMs();
        try {
            $r4.retry($r2);
            $r1.addMarker(String.format("%s-retry [timeout=%s]", new Object[]{$r0, Integer.valueOf($i0)}));
        } catch (VolleyError $r3) {
            $r1.addMarker(String.format("%s-timeout-giveup [timeout=%s]", new Object[]{$r0, Integer.valueOf($i0)}));
            throw $r3;
        }
    }

    private void addCacheHeaders(@Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Lcom/android/volley/Cache$Entry;", ")V"}) Map<String, String> $r1, @Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Lcom/android/volley/Cache$Entry;", ")V"}) Entry $r2) throws  {
        if ($r2 != null) {
            if ($r2.etag != null) {
                $r1.put("If-None-Match", $r2.etag);
            }
            if ($r2.serverDate > 0) {
                $r1.put("If-Modified-Since", DateUtils.formatDate(new Date($r2.serverDate)));
            }
        }
    }

    protected void logError(String $r1, String $r2, long $l0) throws  {
        long $l1 = SystemClock.elapsedRealtime();
        VolleyLog.m18v("HTTP ERROR(%s) %d ms to fetch %s", $r1, Long.valueOf($l1 - $l0), $r2);
    }

    private byte[] entityToBytes(HttpEntity $r1) throws IOException, ServerError {
        PoolingByteArrayOutputStream $r2 = new PoolingByteArrayOutputStream(this.mPool, (int) $r1.getContentLength());
        byte[] $r4 = null;
        byte[] $r8;
        try {
            InputStream $r5 = $r1.getContent();
            if ($r5 == null) {
                throw new ServerError();
            }
            $r8 = this.mPool.getBuf(1024);
            $r4 = $r8;
            while (true) {
                int $i1 = $r5.read($r8);
                if ($i1 == -1) {
                    break;
                }
                $r2.write($r8, 0, $i1);
            }
            $r4 = $r2.toByteArray();
            return $r4;
        } finally {
            try {
                $r4 = 
/*
Method generation error in method: com.android.volley.toolbox.BasicNetwork.entityToBytes(org.apache.http.HttpEntity):byte[]
jadx.core.utils.exceptions.CodegenException: Error generate insn: ?: MERGE  (r6_4 '$r4' byte[]) = (r6_3 '$r4' byte[]), (r10_0 '$r8' byte[]) in method: com.android.volley.toolbox.BasicNetwork.entityToBytes(org.apache.http.HttpEntity):byte[]
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:226)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:203)
	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:100)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:50)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:93)
	at jadx.core.codegen.RegionGen.makeTryCatch(RegionGen.java:277)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:93)
	at jadx.core.codegen.RegionGen.makeTryCatch(RegionGen.java:297)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:183)
	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:328)
	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:265)
	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:228)
	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:118)
	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:83)
	at jadx.core.codegen.CodeGen.visit(CodeGen.java:19)
	at jadx.core.ProcessClass.process(ProcessClass.java:43)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.CodegenException: MERGE can be used only in fallback mode
	at jadx.core.codegen.InsnGen.fallbackOnlyInsn(InsnGen.java:530)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:514)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:220)
	... 26 more

*/

                private static Map<String, String> convertHeaders(@Signature({"([", "Lorg/apache/http/Header;", ")", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;"}) Header[] $r0) throws  {
                    HashMap $r1 = new HashMap();
                    for (int $i0 = 0; $i0 < $r0.length; $i0++) {
                        $r1.put($r0[$i0].getName(), $r0[$i0].getValue());
                    }
                    return $r1;
                }
            }
