package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class OtpResponse extends AbstractSafeParcelable {
    public static final OtpResponseCreator CREATOR = new OtpResponseCreator();
    final int mVersion;
    public final String otp;

    OtpResponse(int $i0, String $r1) throws  {
        this.mVersion = $i0;
        this.otp = $r1;
    }

    public OtpResponse(String $r1) throws  {
        this(1, $r1);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        OtpResponseCreator.zza(this, $r1, $i0);
    }
}
