package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class VerifyPinResponse extends AbstractSafeParcelable {
    public static final VerifyPinResponseCreator CREATOR = new VerifyPinResponseCreator();
    public final String rapt;
    public final int status;
    final int version;

    public VerifyPinResponse(int $i0) throws  {
        this(1, $i0, null);
    }

    VerifyPinResponse(int $i0, int $i1, String $r1) throws  {
        this.version = $i0;
        this.status = $i1;
        this.rapt = $r1;
    }

    public VerifyPinResponse(String $r1) throws  {
        this(1, 0, $r1);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        VerifyPinResponseCreator.zza(this, $r1, $i0);
    }
}
