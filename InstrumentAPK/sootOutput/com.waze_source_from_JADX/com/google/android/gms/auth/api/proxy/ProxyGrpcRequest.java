package com.google.android.gms.auth.api.proxy;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class ProxyGrpcRequest extends AbstractSafeParcelable {
    public static final Creator<ProxyGrpcRequest> CREATOR = new zza();
    public final byte[] body;
    public final String hostname;
    public final String method;
    public final int port;
    public final long timeoutMillis;
    final int versionCode;

    ProxyGrpcRequest(int $i0, String $r1, int $i1, long $l2, byte[] $r2, String $r3) throws  {
        this.versionCode = $i0;
        this.hostname = $r1;
        this.port = $i1;
        this.timeoutMillis = $l2;
        this.body = $r2;
        this.method = $r3;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zza.zza(this, $r1, $i0);
    }
}
