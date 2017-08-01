package com.google.android.gms.auth;

import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzab;
import dalvik.annotation.Signature;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class TokenData extends AbstractSafeParcelable {
    public static final zzd CREATOR = new zzd();
    private final String ee;
    private final Long ef;
    private final boolean eg;
    private final boolean eh;
    private final List<String> ei;
    final int mVersionCode;

    /* compiled from: dalvik_source_com.waze.apk */
    public static class zza {
        private String ee = null;
        private Long ef = null;
        private boolean eg = false;
        private boolean eh = false;
        private List<String> ei = null;

        @Nullable
        public TokenData zzadv() throws  {
            return TextUtils.isEmpty(this.ee) ? null : new TokenData(1, this.ee, null, false, false, null);
        }

        public zza zzem(String $r1) throws  {
            this.ee = $r1;
            return this;
        }
    }

    TokenData(@Signature({"(I", "Ljava/lang/String;", "Ljava/lang/Long;", "ZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) int $i0, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/Long;", "ZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r1, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/Long;", "ZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) Long $r2, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/Long;", "ZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) boolean $z0, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/Long;", "ZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) boolean $z1, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/Long;", "ZZ", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r3) throws  {
        this.mVersionCode = $i0;
        this.ee = zzab.zzgy($r1);
        this.ef = $r2;
        this.eg = $z0;
        this.eh = $z1;
        this.ei = $r3;
    }

    @Nullable
    public static TokenData zzd(Bundle $r0, String $r1) throws  {
        $r0.setClassLoader(TokenData.class.getClassLoader());
        $r0 = $r0.getBundle($r1);
        if ($r0 == null) {
            return null;
        }
        $r0.setClassLoader(TokenData.class.getClassLoader());
        return (TokenData) $r0.getParcelable("TokenData");
    }

    public boolean equals(Object $r1) throws  {
        if (!($r1 instanceof TokenData)) {
            return false;
        }
        TokenData $r2 = (TokenData) $r1;
        return TextUtils.equals(this.ee, $r2.ee) ? zzaa.equal(this.ef, $r2.ef) ? this.eg == $r2.eg ? this.eh == $r2.eh ? zzaa.equal(this.ei, $r2.ei) : false : false : false : false;
    }

    public String getToken() throws  {
        return this.ee;
    }

    public int hashCode() throws  {
        return zzaa.hashCode(this.ee, this.ef, Boolean.valueOf(this.eg), Boolean.valueOf(this.eh), this.ei);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzd.zza(this, $r1, $i0);
    }

    @Nullable
    public Long zzadr() throws  {
        return this.ef;
    }

    public boolean zzads() throws  {
        return this.eg;
    }

    public boolean zzadt() throws  {
        return this.eh;
    }

    @Nullable
    public List<String> zzadu() throws  {
        return this.ei;
    }
}
