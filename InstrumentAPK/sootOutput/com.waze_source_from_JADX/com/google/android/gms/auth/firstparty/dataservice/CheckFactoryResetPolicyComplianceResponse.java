package com.google.android.gms.auth.firstparty.dataservice;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class CheckFactoryResetPolicyComplianceResponse extends AbstractSafeParcelable {
    public static final CheckFactoryResetPolicyComplianceResponseCreator CREATOR = new CheckFactoryResetPolicyComplianceResponseCreator();
    public final boolean isCompliant;
    final int version;

    CheckFactoryResetPolicyComplianceResponse(int $i0, boolean $z0) throws  {
        this.version = $i0;
        this.isCompliant = $z0;
    }

    public static CheckFactoryResetPolicyComplianceResponse from(boolean $z0) throws  {
        return new CheckFactoryResetPolicyComplianceResponse(1, $z0);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        CheckFactoryResetPolicyComplianceResponseCreator.zza(this, $r1, $i0);
    }
}
