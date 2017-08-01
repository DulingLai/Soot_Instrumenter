package com.google.android.gms.people.internal.agg;

import android.text.TextUtils;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.people.internal.zzq;
import com.google.android.gms.people.model.PhoneNumber;

/* compiled from: dalvik_source_com.waze.apk */
public class zzg implements PhoneNumber {
    private String aSs;
    private final String mValue;
    private final String zzcft;

    public zzg(String $r1, String $r2) throws  {
        this.zzcft = $r1;
        this.mValue = $r2;
    }

    private String zzcga() throws  {
        if (this.aSs == null) {
            this.aSs = zzrr(this.mValue);
        }
        return this.aSs;
    }

    static String zzrr(String $r0) throws  {
        if (TextUtils.isEmpty($r0)) {
            return "";
        }
        StringBuilder $r1 = zzq.zzcff();
        for (int $i0 = 0; $i0 < $r0.length(); $i0++) {
            char $c2 = $r0.charAt($i0);
            if (Character.digit($c2, 10) != -1 || $c2 == '+' || $c2 == ',' || $c2 == ';' || (('a' <= $c2 && $c2 <= 'z') || ('A' <= $c2 && $c2 <= 'Z'))) {
                $r1.append($c2);
            }
        }
        return $r1.toString();
    }

    public boolean equals(Object $r1) throws  {
        if (!($r1 instanceof zzg)) {
            return false;
        }
        return zzaa.equal(zzcga(), ((zzg) $r1).zzcga());
    }

    public String getType() throws  {
        return this.zzcft;
    }

    public String getValue() throws  {
        return this.mValue;
    }

    public String toString() throws  {
        String $r1 = this.mValue != null ? this.mValue : "null";
        String $r2 = this.zzcft != null ? this.zzcft : "null";
        return new StringBuilder((String.valueOf($r1).length() + 26) + String.valueOf($r2).length()).append("PhoneNumber:[Value=").append($r1).append(" Type=").append($r2).append("]").toString();
    }
}
