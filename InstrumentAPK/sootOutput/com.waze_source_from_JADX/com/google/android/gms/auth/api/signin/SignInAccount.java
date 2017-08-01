package com.google.android.gms.auth.api.signin;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;

/* compiled from: dalvik_source_com.waze.apk */
public class SignInAccount extends AbstractSafeParcelable {
    public static final Creator<SignInAccount> CREATOR = new zzc();
    private GoogleSignInAccount fP;
    @Deprecated
    String fw;
    final int versionCode;
    @Deprecated
    String zzcva;

    SignInAccount(int $i0, String $r1, GoogleSignInAccount $r2, String $r3) throws  {
        this.versionCode = $i0;
        this.fP = $r2;
        this.fw = zzab.zzi($r1, "8.3 and 8.4 SDKs require non-null email");
        this.zzcva = zzab.zzi($r3, "8.3 and 8.4 SDKs require non-null userId");
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzc.zza(this, $r1, $i0);
    }

    public GoogleSignInAccount zzaeo() throws  {
        return this.fP;
    }
}
