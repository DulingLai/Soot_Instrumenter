package com.google.android.gms.auth.firstparty.shared;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;

/* compiled from: dalvik_source_com.waze.apk */
public class PlusProfileCreationResponse extends AbstractSafeParcelable {
    public static final PlusProfileCreationResponseCreator CREATOR = new PlusProfileCreationResponseCreator();
    String iJ;
    final int version;

    PlusProfileCreationResponse(int $i0, String $r1) throws  {
        this.version = $i0;
        this.iJ = $r1;
    }

    public PlusProfileCreationResponse(Status $r1) throws  {
        this.version = 1;
        this.iJ = ((Status) zzab.zzag($r1)).getWire();
    }

    public Status getStatus() throws  {
        return Status.fromWireCode(this.iJ);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        PlusProfileCreationResponseCreator.zza(this, $r1, $i0);
    }
}
