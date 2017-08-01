package com.google.android.gms.auth.api.proxy;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import dalvik.annotation.Signature;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: dalvik_source_com.waze.apk */
public class ProxyResponse extends AbstractSafeParcelable {
    public static final Creator<ProxyResponse> CREATOR = new zzc();
    public static final int STATUS_CODE_NO_CONNECTION = -1;
    public final byte[] body;
    final Bundle fk;
    public final int googlePlayServicesStatusCode;
    public final PendingIntent recoveryAction;
    public final int statusCode;
    final int versionCode;

    ProxyResponse(int $i0, int $i1, PendingIntent $r1, int $i2, Bundle $r2, byte[] $r3) throws  {
        this.versionCode = $i0;
        this.googlePlayServicesStatusCode = $i1;
        this.statusCode = $i2;
        this.fk = $r2;
        this.body = $r3;
        this.recoveryAction = $r1;
    }

    public ProxyResponse(int $i0, PendingIntent $r1, int $i1, Bundle $r2, byte[] $r3) throws  {
        this(1, $i0, $r1, $i1, $r2, $r3);
    }

    private ProxyResponse(int $i0, Bundle $r1, byte[] $r2) throws  {
        this(1, 0, null, $i0, $r1, $r2);
    }

    public ProxyResponse(@Signature({"(I", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;[B)V"}) int $i0, @Signature({"(I", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;[B)V"}) Map<String, String> $r1, @Signature({"(I", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;[B)V"}) byte[] $r2) throws  {
        this($i0, zzat($r1), $r2);
    }

    public static ProxyResponse createErrorProxyResponse(@Signature({"(I", "Landroid/app/PendingIntent;", "I", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;[B)", "Lcom/google/android/gms/auth/api/proxy/ProxyResponse;"}) int $i0, @Signature({"(I", "Landroid/app/PendingIntent;", "I", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;[B)", "Lcom/google/android/gms/auth/api/proxy/ProxyResponse;"}) PendingIntent $r0, @Signature({"(I", "Landroid/app/PendingIntent;", "I", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;[B)", "Lcom/google/android/gms/auth/api/proxy/ProxyResponse;"}) int $i1, @Signature({"(I", "Landroid/app/PendingIntent;", "I", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;[B)", "Lcom/google/android/gms/auth/api/proxy/ProxyResponse;"}) Map<String, String> $r1, @Signature({"(I", "Landroid/app/PendingIntent;", "I", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;[B)", "Lcom/google/android/gms/auth/api/proxy/ProxyResponse;"}) byte[] $r2) throws  {
        return new ProxyResponse(1, $i0, $r0, $i1, zzat($r1), $r2);
    }

    private static Bundle zzat(@Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)", "Landroid/os/Bundle;"}) Map<String, String> $r0) throws  {
        Bundle $r1 = new Bundle();
        if ($r0 == null) {
            return $r1;
        }
        for (Entry $r5 : $r0.entrySet()) {
            $r1.putString((String) $r5.getKey(), (String) $r5.getValue());
        }
        return $r1;
    }

    public Map<String, String> getHeaders() throws  {
        HashMap $r1 = new HashMap();
        for (String $r6 : this.fk.keySet()) {
            $r1.put($r6, this.fk.getString($r6));
        }
        return $r1;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzc.zza(this, $r1, $i0);
    }
}
