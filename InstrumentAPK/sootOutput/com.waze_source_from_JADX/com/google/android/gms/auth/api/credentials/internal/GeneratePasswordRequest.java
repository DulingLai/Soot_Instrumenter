package com.google.android.gms.auth.api.credentials.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.api.credentials.PasswordSpecification;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public final class GeneratePasswordRequest extends AbstractSafeParcelable {
    public static final Creator<GeneratePasswordRequest> CREATOR = new zzi();
    private final PasswordSpecification eA;
    final int mVersionCode;

    GeneratePasswordRequest(int $i0, PasswordSpecification $r1) throws  {
        this.mVersionCode = $i0;
        this.eA = $r1;
    }

    public GeneratePasswordRequest(PasswordSpecification $r1) throws  {
        this(1, $r1);
    }

    public PasswordSpecification getPasswordSpecification() throws  {
        return this.eA;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzi.zza(this, $r1, $i0);
    }
}
