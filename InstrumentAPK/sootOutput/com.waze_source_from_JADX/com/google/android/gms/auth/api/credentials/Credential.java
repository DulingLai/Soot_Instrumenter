package com.google.android.gms.auth.api.credentials;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.auth.api.credentials.internal.zzb;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzab;
import dalvik.annotation.Signature;
import java.util.Collections;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class Credential extends AbstractSafeParcelable {
    public static final Creator<Credential> CREATOR = new zza();
    public static final String EXTRA_KEY = "com.google.android.gms.credentials.Credential";
    @Nullable
    private final Uri eF;
    private final List<IdToken> eG;
    @Nullable
    private final String eH;
    @Nullable
    private final String eI;
    @Nullable
    private final String eJ;
    @Nullable
    private final String mAccountType;
    @Nullable
    private final String mName;
    final int mVersionCode;
    private final String zzbgd;

    /* compiled from: dalvik_source_com.waze.apk */
    public static class Builder {
        private Uri eF;
        private List<IdToken> eG;
        private String eH;
        private String eI;
        private String eJ;
        private String mAccountType;
        private String mName;
        private final String zzbgd;

        public Builder(Credential $r1) throws  {
            this.zzbgd = $r1.zzbgd;
            this.mName = $r1.mName;
            this.eF = $r1.eF;
            this.eG = $r1.eG;
            this.eH = $r1.eH;
            this.mAccountType = $r1.mAccountType;
            this.eI = $r1.eI;
            this.eJ = $r1.eJ;
        }

        public Builder(String $r1) throws  {
            this.zzbgd = $r1;
        }

        public Credential build() throws  {
            return new Credential(3, this.zzbgd, this.mName, this.eF, this.eG, this.eH, this.mAccountType, this.eI, this.eJ);
        }

        public Builder setAccountType(String $r1) throws  {
            this.mAccountType = $r1;
            return this;
        }

        public Builder setName(String $r1) throws  {
            this.mName = $r1;
            return this;
        }

        public Builder setPassword(String $r1) throws  {
            this.eH = $r1;
            return this;
        }

        public Builder setProfilePictureUri(Uri $r1) throws  {
            this.eF = $r1;
            return this;
        }
    }

    Credential(@Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/api/credentials/IdToken;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/api/credentials/IdToken;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r1, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/api/credentials/IdToken;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r8, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/api/credentials/IdToken;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Uri $r2, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/api/credentials/IdToken;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) List<IdToken> $r3, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/api/credentials/IdToken;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/api/credentials/IdToken;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r5, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/api/credentials/IdToken;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r6, @Signature({"(I", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Ljava/util/List", "<", "Lcom/google/android/gms/auth/api/credentials/IdToken;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r7) throws  {
        this.mVersionCode = $i0;
        $r1 = ((String) zzab.zzb((Object) $r1, (Object) "credential identifier cannot be null")).trim();
        zzab.zzi($r1, "credential identifier cannot be empty");
        this.zzbgd = $r1;
        if ($r8 != null && TextUtils.isEmpty($r8.trim())) {
            $r8 = null;
        }
        this.mName = $r8;
        this.eF = $r2;
        this.eG = $r3 == null ? Collections.emptyList() : Collections.unmodifiableList($r3);
        this.eH = $r4;
        if ($r4 == null || !$r4.isEmpty()) {
            if (!TextUtils.isEmpty($r5)) {
                zzb.zzen($r5);
            }
            this.mAccountType = $r5;
            this.eI = $r6;
            this.eJ = $r7;
            if (!TextUtils.isEmpty(this.eH) && !TextUtils.isEmpty(this.mAccountType)) {
                throw new IllegalStateException("password and accountType cannot both be set");
            }
            return;
        }
        throw new IllegalArgumentException("password cannot be empty");
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if (!($r1 instanceof Credential)) {
            return false;
        }
        Credential $r2 = (Credential) $r1;
        return TextUtils.equals(this.zzbgd, $r2.zzbgd) && TextUtils.equals(this.mName, $r2.mName) && zzaa.equal(this.eF, $r2.eF) && TextUtils.equals(this.eH, $r2.eH) && TextUtils.equals(this.mAccountType, $r2.mAccountType) && TextUtils.equals(this.eI, $r2.eI);
    }

    @Nullable
    public String getAccountType() throws  {
        return this.mAccountType;
    }

    @Nullable
    public String getGeneratedPassword() throws  {
        return this.eI;
    }

    public String getId() throws  {
        return this.zzbgd;
    }

    public List<IdToken> getIdTokens() throws  {
        return this.eG;
    }

    @Nullable
    public String getName() throws  {
        return this.mName;
    }

    @Nullable
    public String getPassword() throws  {
        return this.eH;
    }

    @Nullable
    public Uri getProfilePictureUri() throws  {
        return this.eF;
    }

    public int hashCode() throws  {
        return zzaa.hashCode(this.zzbgd, this.mName, this.eF, this.eH, this.mAccountType, this.eI);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zza.zza(this, $r1, $i0);
    }

    public String zzadx() throws  {
        return this.eJ;
    }
}
