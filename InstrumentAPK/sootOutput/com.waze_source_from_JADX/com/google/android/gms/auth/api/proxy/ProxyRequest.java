package com.google.android.gms.auth.api.proxy;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Patterns;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: dalvik_source_com.waze.apk */
public class ProxyRequest extends AbstractSafeParcelable {
    public static final Creator<ProxyRequest> CREATOR = new zzb();
    public static final int HTTP_METHOD_DELETE = 3;
    public static final int HTTP_METHOD_GET = 0;
    public static final int HTTP_METHOD_HEAD = 4;
    public static final int HTTP_METHOD_OPTIONS = 5;
    public static final int HTTP_METHOD_PATCH = 7;
    public static final int HTTP_METHOD_POST = 1;
    public static final int HTTP_METHOD_PUT = 2;
    public static final int HTTP_METHOD_TRACE = 6;
    public static final int LAST_CODE = 7;
    public static final int VERSION_CODE = 2;
    public final byte[] body;
    Bundle fk;
    public final int httpMethod;
    public final long timeoutMillis;
    public final String url;
    final int versionCode;

    /* compiled from: dalvik_source_com.waze.apk */
    public static class Builder {
        private String fl;
        private int fm = ProxyRequest.HTTP_METHOD_GET;
        private long fn = 3000;
        private byte[] fo = null;
        private Bundle fp = new Bundle();

        public Builder(String $r1) throws  {
            zzab.zzgy($r1);
            if (Patterns.WEB_URL.matcher($r1).matches()) {
                this.fl = $r1;
                return;
            }
            throw new IllegalArgumentException(new StringBuilder(String.valueOf($r1).length() + 51).append("The supplied url [ ").append($r1).append("] is not match Patterns.WEB_URL!").toString());
        }

        public ProxyRequest build() throws  {
            if (this.fo == null) {
                this.fo = new byte[0];
            }
            return new ProxyRequest(2, this.fl, this.fm, this.fn, this.fo, this.fp);
        }

        public Builder putHeader(String $r1, String $r2) throws  {
            zzab.zzi($r1, "Header name cannot be null or empty!");
            Bundle $r3 = this.fp;
            if ($r2 == null) {
                $r2 = "";
            }
            $r3.putString($r1, $r2);
            return this;
        }

        public Builder setBody(byte[] $r1) throws  {
            this.fo = $r1;
            return this;
        }

        public Builder setHttpMethod(int $i0) throws  {
            boolean $z0 = $i0 >= 0 && $i0 <= ProxyRequest.LAST_CODE;
            zzab.zzb($z0, (Object) "Unrecognized http method code.");
            this.fm = $i0;
            return this;
        }

        public Builder setTimeoutMillis(long $l0) throws  {
            zzab.zzb($l0 >= 0, (Object) "The specified timeout must be non-negative.");
            this.fn = $l0;
            return this;
        }
    }

    ProxyRequest(int $i0, String $r1, int $i1, long $l2, byte[] $r2, Bundle $r3) throws  {
        this.versionCode = $i0;
        this.url = $r1;
        this.httpMethod = $i1;
        this.timeoutMillis = $l2;
        this.body = $r2;
        this.fk = $r3;
    }

    public Map<String, String> getHeaderMap() throws  {
        LinkedHashMap $r1 = new LinkedHashMap(this.fk.size());
        for (String $r6 : this.fk.keySet()) {
            $r1.put($r6, this.fk.getString($r6));
        }
        return Collections.unmodifiableMap($r1);
    }

    public String toString() throws  {
        String $r1 = this.url;
        return new StringBuilder(String.valueOf($r1).length() + 42).append("ProxyRequest[ url: ").append($r1).append(", method: ").append(this.httpMethod).append(" ]").toString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzb.zza(this, $r1, $i0);
    }
}
