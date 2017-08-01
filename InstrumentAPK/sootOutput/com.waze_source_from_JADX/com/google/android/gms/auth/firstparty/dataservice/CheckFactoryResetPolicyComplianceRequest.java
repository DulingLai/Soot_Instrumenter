package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class CheckFactoryResetPolicyComplianceRequest extends AbstractSafeParcelable {
    public static final CheckFactoryResetPolicyComplianceRequestCreator CREATOR = new CheckFactoryResetPolicyComplianceRequestCreator();
    public final String accountId;
    final int version;

    CheckFactoryResetPolicyComplianceRequest(int $i0, String $r1) throws  {
        this.version = $i0;
        this.accountId = $r1;
    }

    public static CheckFactoryResetPolicyComplianceRequest from(String $r0) throws  {
        return new CheckFactoryResetPolicyComplianceRequest(1, $r0);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        CheckFactoryResetPolicyComplianceRequestCreator.zza(this, $r1, $i0);
    }
}
