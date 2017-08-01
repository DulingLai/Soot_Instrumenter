package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.google.android.gms.auth.api.credentials.internal.zzb;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;

/* compiled from: dalvik_source_com.waze.apk */
public final class IdToken extends AbstractSafeParcelable {
    public static final Creator<IdToken> CREATOR = new zze();
    @NonNull
    private final String eT;
    @NonNull
    private final String mAccountType;
    final int mVersionCode;

    IdToken(int $i0, @NonNull String $r1, @NonNull String $r2) throws  {
        zzb.zzen($r1);
        zzab.zzb(!TextUtils.isEmpty($r2), (Object) "id token string cannot be null or empty");
        this.mVersionCode = $i0;
        this.mAccountType = $r1;
        this.eT = $r2;
    }

    public IdToken(@NonNull String $r1, @NonNull String $r2) throws  {
        this(1, $r1, $r2);
    }

    @NonNull
    public String getAccountType() throws  {
        return this.mAccountType;
    }

    @NonNull
    public String getIdToken() throws  {
        return this.eT;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zze.zza(this, $r1, $i0);
    }
}
