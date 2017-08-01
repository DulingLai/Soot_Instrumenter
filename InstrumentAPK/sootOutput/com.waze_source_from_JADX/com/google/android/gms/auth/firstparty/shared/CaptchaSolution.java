package com.google.android.gms.auth.firstparty.shared;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class CaptchaSolution extends AbstractSafeParcelable {
    public static final CaptchaSolutionCreator CREATOR = new CaptchaSolutionCreator();
    String gN;
    String iv;
    final int version;

    CaptchaSolution(int $i0, String $r1, String $r2) throws  {
        this.version = $i0;
        this.gN = $r1;
        this.iv = $r2;
    }

    public CaptchaSolution(String $r1, String $r2) throws  {
        this.version = 1;
        this.gN = $r1;
        this.iv = $r2;
    }

    public String getAnswer() throws  {
        return this.iv;
    }

    public String getToken() throws  {
        return this.gN;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        CaptchaSolutionCreator.zza(this, $r1, $i0);
    }
}
