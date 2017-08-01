package com.android.volley;

import dalvik.annotation.Signature;
import java.util.Collections;
import java.util.Map;

public class NetworkResponse {
    public final byte[] data;
    public final Map<String, String> headers;
    public final boolean notModified;
    public final int statusCode;

    public NetworkResponse(@Signature({"(I[B", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;Z)V"}) int $i0, @Signature({"(I[B", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;Z)V"}) byte[] $r1, @Signature({"(I[B", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;Z)V"}) Map<String, String> $r2, @Signature({"(I[B", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;Z)V"}) boolean $z0) throws  {
        this.statusCode = $i0;
        this.data = $r1;
        this.headers = $r2;
        this.notModified = $z0;
    }

    public NetworkResponse(byte[] $r1) throws  {
        this(200, $r1, Collections.emptyMap(), false);
    }

    public NetworkResponse(@Signature({"([B", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) byte[] $r1, @Signature({"([B", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) Map<String, String> $r2) throws  {
        this(200, $r1, $r2, false);
    }
}
