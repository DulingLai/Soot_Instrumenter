package com.google.android.gms.internal;

import dalvik.annotation.Signature;
import java.util.Map;

/* compiled from: dalvik_source_com.waze.apk */
public class zzi {
    public final byte[] data;
    public final Map<String, String> headers;
    public final int statusCode;
    public final long zzaa;
    public final boolean zzz;

    public zzi(@Signature({"(I[B", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;ZJ)V"}) int $i0, @Signature({"(I[B", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;ZJ)V"}) byte[] $r1, @Signature({"(I[B", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;ZJ)V"}) Map<String, String> $r2, @Signature({"(I[B", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;ZJ)V"}) boolean $z0, @Signature({"(I[B", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;ZJ)V"}) long $l1) throws  {
        this.statusCode = $i0;
        this.data = $r1;
        this.headers = $r2;
        this.zzz = $z0;
        this.zzaa = $l1;
    }

    public zzi(@Signature({"([B", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) byte[] $r1, @Signature({"([B", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) Map<String, String> $r2) throws  {
        this(200, $r1, $r2, false, 0);
    }
}
