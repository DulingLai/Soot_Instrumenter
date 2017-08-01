package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import com.google.android.gms.auth.firstparty.shared.AppDescription;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;

/* compiled from: dalvik_source_com.waze.apk */
public class OtpRequest extends AbstractSafeParcelable {
    public static final OtpRequestCreator CREATOR = new OtpRequestCreator();
    public final String accountName;
    public final AppDescription callerDescription;
    public final byte[] challenge;
    public final boolean isLegacyRequest;
    final int mVersion;

    OtpRequest(int $i0, String $r1, AppDescription $r2, byte[] $r3, boolean $z0) throws  {
        this.mVersion = $i0;
        this.accountName = $r1;
        this.challenge = $r3;
        this.callerDescription = (AppDescription) zzab.zzb((Object) $r2, (Object) "Caller's app description cannot be null!");
        this.isLegacyRequest = $z0;
    }

    public OtpRequest(String $r1, AppDescription $r2) throws  {
        this(1, $r1, $r2, null, false);
    }

    public OtpRequest(String $r1, AppDescription $r2, byte[] $r3) throws  {
        this(1, $r1, $r2, $r3, false);
    }

    public static OtpRequest newLegacyOtpRequest(String $r0, AppDescription $r1) throws  {
        return new OtpRequest(1, $r0, $r1, null, true);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        OtpRequestCreator.zza(this, $r1, $i0);
    }
}
