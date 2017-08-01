package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class GetAndAdvanceOtpCounterResponse extends AbstractSafeParcelable {
    public static final GetAndAdvanceOtpCounterResponseCreator CREATOR = new GetAndAdvanceOtpCounterResponseCreator();
    public final Long counter;
    final int mVersion;

    GetAndAdvanceOtpCounterResponse(int $i0, Long $r1) throws  {
        this.mVersion = $i0;
        this.counter = $r1;
    }

    public GetAndAdvanceOtpCounterResponse(Long $r1) throws  {
        this(1, $r1);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        GetAndAdvanceOtpCounterResponseCreator.zza(this, $r1, $i0);
    }
}
